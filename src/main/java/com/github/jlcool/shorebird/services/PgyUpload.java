package com.github.jlcool.shorebird.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jlcool.shorebird.ui.MyDialog;
import com.github.jlcool.shorebird.ui.MydialogForm;
import com.intellij.openapi.project.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.flutter.console.FlutterConsoles;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PgyUpload {
    public static void uploadApk(String pathname, Project project, com.intellij.openapi.module.Module module, MydialogForm dialog) {
        File apkFile = new File(pathname);
        if (apkFile.exists()) {
            OkHttpClient client = new OkHttpClient();
            ProgressRequestBody requestBody = new ProgressRequestBody(apkFile, new ProgressRequestBody.UploadCallback() {
                @Override
                public void onProgressUpdate(int percentage) {
                    FlutterConsoles.displayMessage(project, module, "\u4e0a\u4f20\u8fdb\u5ea6: " + percentage + "%\n");
                }
            });


            File changelogFile = new File(project.getBasePath() + "/CHANGELOG.md");
            StringBuilder changelogContent = new StringBuilder();
            String targetVersion = getVersionNameFromPubspecYaml(project, module); // 需要读取的版本号
            boolean isTargetVersion = false;
            if (changelogFile.exists()) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(changelogFile), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().equals(targetVersion)) {
                            isTargetVersion = true;
                            continue;
                        }
                        if (isTargetVersion) { // 如果已找到目标版本且当前行不是版本号
                            if (line.matches("\\d+\\.\\d+\\.\\d+")) {
                                break;
                            }
                            changelogContent.append(line).append("\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    FlutterConsoles.displayMessage(project, module, "读取 CHANGELOG.md 文件失败: " + e.getMessage() + "\n");
                }
            } else {
                FlutterConsoles.displayMessage(project, module, "CHANGELOG.md 文件不存在.\n");
            }

            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", apkFile.getName(), requestBody)
                    .addFormDataPart("_api_key", dialog.getApiKey())
                    .addFormDataPart("buildInstallType", "1")
                    .addFormDataPart("buildUpdateDescription", changelogContent.toString())

                    .build();
            Request request = new Request.Builder()
                    .url("https://www.pgyer.com/apiv2/app/upload") // Replace with your server URL
                    .post(multipartBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    FlutterConsoles.displayMessage(project, module, "\u4e0a\u4f20\u5931\u8d25: " + e.getMessage() + "\n");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(inputStream);
                        String buildName=jsonNode.get("data").get("buildName").asText();
                        String buildType = jsonNode.get("data").get("buildType").asInt()==1?"IOS": "Android";
                        String buildVersion=jsonNode.get("data").get("buildVersion").asText();
                        int buildBuildVersion=jsonNode.get("data").get("buildBuildVersion").asInt();
                        String buildUpdated=jsonNode.get("data").get("buildUpdated").asText();
                        int buildFileSize=jsonNode.get("data").get("buildFileSize").asInt();
                        String buildShortcutUrl=jsonNode.get("data").get("buildShortcutUrl").asText();
                        String buildUpdateDescription=jsonNode.get("data").get("buildUpdateDescription").asText();
                        String buildQRCodeURL=jsonNode.get("data").get("buildQRCodeURL").asText();
                        String buildIcon=jsonNode.get("data").get("buildIcon").asText();
                        try {
                            if(dialog.isDingDingSelected()) {
                                RobotMessage.sendMessage(dialog, false, "应用更新", "**应用更新提醒**\n\n应用名称：" + buildName + "\n\n" +
                                        "应用类型：" + buildType + "\n\n" +
                                        "版本信息：" + buildVersion + "(Build " + buildBuildVersion + ")\n\n" +
                                        "应用大小：" + String.format("%.2f", buildFileSize / 1024. / 1024.0) + " MB\n\n" +
                                        "更新时间：" + buildUpdated + "\n\n" +
                                        "更新内容：" + buildUpdateDescription.replaceAll("\n", "\n\n") + "\n\n" +
                                        "![screenshot](" + buildQRCodeURL + ") " + "\n\n" +
                                        "## [点击下载](https://www.pgyer.com/" + buildShortcutUrl + ")");
                            }
                            FlutterConsoles.displayMessage(project, module, "\u4e0a\u4f20\u6210\u529f.\n");
                        } catch (Exception e) {
                            FlutterConsoles.displayMessage(project, module, e.getMessage());
                        }
                    } else {
                        FlutterConsoles.displayMessage(project, module, "\u4e0a\u4f20\u5931\u8d25: " + response.message() + "\n");
                    }
                }
            });
        } else {
            FlutterConsoles.displayMessage(project, module, "上传文件未找到\n");
        }
    }

    private static String getVersionNameFromPubspecYaml(Project project, com.intellij.openapi.module.Module module) {
        File pubspecFile = new File(project.getBasePath(), "pubspec.yaml");
        if (!pubspecFile.exists()) {
            FlutterConsoles.displayMessage(project, module, "pubspec.yaml 文件不存在.\n");
            return "无法获取版本号";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(pubspecFile, Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("version: ")) {
                    String[] parts = line.split(":")[1].split("\\+");
                    return parts[0].trim();
                }
            }
        } catch (IOException e) {
            FlutterConsoles.displayMessage(project, module, "读取 pubspec.yaml 文件失败: " + e.getMessage() + "\n");
        }

        return "无法获取版本号";
    }
}
