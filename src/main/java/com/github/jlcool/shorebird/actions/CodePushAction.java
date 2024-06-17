package com.github.jlcool.shorebird.actions;

import static com.github.jlcool.shorebird.services.PgyUpload.uploadApk;

import com.github.jlcool.shorebird.ui.MyDialog;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.content.ContentManagerAdapter;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.MessageView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.flutter.console.FlutterConsoles;
import io.flutter.pub.PubRoot;
import io.flutter.run.SdkFields;
import io.flutter.run.SdkRunConfig;
import io.flutter.run.LaunchState;
import io.flutter.sdk.FlutterSdk;

public class CodePushAction extends AnAction {
    static  String basePath="";
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        MyDialog dialog = new MyDialog();

        // 显示对话框
        if (dialog.showAndGet()) {
            Project project = event.getProject();
            if (project == null) {
                return;
            }
            basePath = project.getBasePath();
            final FlutterSdk sdk = FlutterSdk.getFlutterSdk(project);
            if (sdk == null) {
                return;
            }

            RunManager runManager = RunManager.getInstance(project);
            RunnerAndConfigurationSettings configurationSettings = runManager.getSelectedConfiguration();
            if (configurationSettings == null) {
                return;
            }
            RunConfiguration configuration = configurationSettings.getConfiguration();
            if (configuration instanceof SdkRunConfig sdkRunConfig) {
                SdkFields someField = sdkRunConfig.getFields();
                String filePath = someField.getFilePath();
                String flavor = someField.getBuildFlavor();
                String additionalArgs = someField.getAdditionalArgs();
                try {
                    String osName = System.getProperty("os.name").toLowerCase();
                    GeneralCommandLine commandLine = new GeneralCommandLine(osName.contains("win")?"powershell.exe":"shorebird");
                    if(osName.contains("win"))
                    {
                        commandLine.addParameter("shorebird");
                    }
                    String command=dialog.getCommandRadio();

                    if(Objects.equals(command, "reinit")){
                        commandLine.addParameter("init");
                        commandLine.addParameter("--force");
                    }else {
                        commandLine.addParameter(command);
                    }
                    if(!command.equals("init") && !command.equals("reinit")) {
                        commandLine.addParameter(dialog.getTypeRadio());

                        if(dialog.isStaging()) {
                            commandLine.addParameter("--staging");
                        }
                        if(dialog.isArtifact()) {
                            commandLine.addParameter("--artifact");
                            commandLine.addParameter("apk");
                        }
                        if(!Objects.equals(dialog.getFlutterVersion(), "")){
                            commandLine.addParameter("--flutter-version");
                            commandLine.addParameter(dialog.getFlutterVersion());
                        }
                        if (!Objects.equals(dialog.getReleaseVersion(), "")){
                            commandLine.addParameter("--release-version");
                            commandLine.addParameter(dialog.getFlutterVersion());
                        }
                        if(!dialog.isUploadToShorebird()) {
                            commandLine.addParameter("-n");
                        }
                        // 检查flavor是否不为空或特定条件满足
                        if (flavor != null && !flavor.isEmpty()) {
                            // 添加flavor参数到命令列表
                            commandLine.addParameter("--flavor");
                            commandLine.addParameter(flavor);
                        }
                        if (additionalArgs != null && !additionalArgs.isEmpty()) {
                            commandLine.addParameter(additionalArgs);
                        }
                        if (filePath != null && !filePath.isEmpty()) {
                            commandLine.addParameter("-t");
                            commandLine.addParameter(filePath);
                        }



                    }
                    commandLine.setWorkDirectory(project.getBasePath());

                    ColoredProcessHandler handler = new ColoredProcessHandler(commandLine);
                    final PubRoot pubRoot = PubRoot.forEventWithRefresh(event);
                    if (pubRoot != null) {
                        com.intellij.openapi.module.Module module = pubRoot.getModule(project);
                        if (module != null) {
                            FlutterConsoles.displayProcessLater(handler, module.getProject(), module, handler::startNotify);

                            MessageView messageView =MessageView.getInstance(event.getProject());
                            //如果编译窗口关闭则停止编译
                            messageView.getContentManager().addContentManagerListener(new ContentManagerAdapter() {
                                @Override
                                public void contentRemoved(ContentManagerEvent event) {
                                    handler.destroyProcess(); // 停止正在执行的命令
                                }
                            });


                            handler.addProcessListener(new ProcessAdapter() {
                                @Override
                                public void processTerminated(@NotNull ProcessEvent event) {
                                    int exitCode = event.getExitCode();
                                    if (exitCode == 0) {
                                        String pathname=project.getBasePath() + "/build/app/outputs/flutter-apk/app" + (flavor != null && !flavor.isEmpty() ? "-" : "") + flavor + "-release.apk";
                                        if(Objects.equals(dialog.getTypeRadio(), "ios")){
                                            pathname=project.getBasePath() + "/build/app/outputs/flutter-apk/app" + (flavor != null && !flavor.isEmpty() ? "-" : "") + flavor + "-release.apk";
                                        }
                                        System.out.println("Flutter build completed successfully.");
                                        if(dialog.isUploadToPgy()) {
                                            uploadApk(pathname, project, module, dialog);
                                        }
                                    } else {
                                        System.err.println("Flutter build failed with exit code: " + exitCode);
                                    }
                                    handler.destroyProcess();
                                }
                            });

                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
    @Override
    public void update(@NotNull final AnActionEvent e) {
        var project=e.getProject();
        if (project ==null)
        {
            return;
        }
        RunManager runManager = RunManager.getInstance(project);
        RunnerAndConfigurationSettings configurationSettings = runManager.getSelectedConfiguration();
        if (configurationSettings == null) {
            return;
        }
        RunConfiguration configuration = configurationSettings.getConfiguration();
        var enable= configuration instanceof SdkRunConfig && LaunchState.getRunningAppProcess((SdkRunConfig)configuration) == null;
        e.getPresentation().setEnabled(enable);
        e.getPresentation().setVisible(enable);
    }
}
