package com.github.jlcool.shorebird.actions;

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

                    GeneralCommandLine commandLine = new GeneralCommandLine();
                    commandLine.withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE);
                    commandLine.setExePath("shorebird.bat");
                    commandLine.addParameter("release");
                    commandLine.addParameter(dialog.getTypeRadio());

                    commandLine.addParameter("--release");

                    // 检查flavor是否不为空或特定条件满足
                    if (flavor != null && !flavor.isEmpty()) {
                        // 添加flavor参数到命令列表
                        commandLine.addParameter("--flavor");
                        commandLine.addParameter(flavor);
                    }
                    if (additionalArgs != null && !additionalArgs.isEmpty()) {
                        commandLine.addParameter(additionalArgs);
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
                                        System.out.println("Flutter build completed successfully.");
                                    } else {
                                        System.err.println("Flutter build failed with exit code: " + exitCode);
                                    }
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
