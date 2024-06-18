package com.github.jlcool.shorebird.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jlcool.shorebird.ui.MyDialog;
import com.github.jlcool.shorebird.ui.MydialogForm;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RobotMessage {
    public static void sendMessage(MydialogForm dialog, boolean isAtAll, String title, String text) throws Exception {
        String urlStr = "https://oapi.dingtalk.com/robot/send?access_token=" + dialog.getDingToken();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        MarkdownMessage message = new MarkdownMessage(title,text);
        message.setAt(isAtAll,new ArrayList<>());

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(message);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        System.out.println("Response Code : " + code);

        if (code != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
    }
    /**
     * 将包含逗号或中文逗号分隔的字符串转换为List<String>
     * @param input 输入的字符串
     * @return 分割后的字符串列表
     */
    public static List<String> splitDingAt(String input) {
        // 正则表达式匹配英文逗号和中文逗号
        String regex = "[,，]";
        // 使用split方法分割字符串，并使用stream去除空白后收集到List中
        return Arrays.stream(input.split(regex))
                .map(String::trim) // 去除每个元素前后的空白
                .collect(Collectors.toList());
    }
    static class MarkdownMessage {
        private String msgtype = "markdown";
        private Markdown markdown;
        private At at;

        public MarkdownMessage(String title, String text) {
            this.markdown = new Markdown(title, text);
        }

        public void setAt(boolean isAtAll, List<String> atUserIds) {
            this.at = new At(isAtAll, atUserIds);
        }

        public String getMsgtype() {
            return msgtype;
        }

        public Markdown getMarkdown() {
            return markdown;
        }

        public At getAt() {
            return at;
        }
    }
    static class Markdown {
        private String title;
        private String text;
        public Markdown(String title, String text) {
            this.title = title;
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }
    }

    static class ActionCardMessage {
        private String msgtype = "actionCard";
        private ActionCard actionCard;
        private At at;

        public ActionCardMessage(String title, String text,String singleURL) {
            this.actionCard = new ActionCard(title, text,singleURL);
        }

        public void setAt(boolean isAtAll, List<String> atUserIds) {
            this.at = new At(isAtAll, atUserIds);
        }

        public String getMsgtype() {
            return msgtype;
        }

        public ActionCard getActionCard() {
            return actionCard;
        }

        public At getAt() {
            return at;
        }
    }

    static class ActionCard {
        private String title;
        private String text;
        private String btnOrientation="1";
        private String singleTitle="下载";
        private String singleURL;

        public ActionCard(String title, String text,String singleURL) {
            this.title = title;
            this.text = text;
            this.singleURL=singleURL;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getBtnOrientation() {
            return btnOrientation;
        }

        public String getSingleTitle() {
            return singleTitle;
        }

        public String getSingleURL() {
            return singleURL;
        }
    }

    static class At {
        private boolean isAtAll;
        private List<String> atUserIds;

        public At(boolean isAtAll, List<String> atUserIds) {
            this.isAtAll = isAtAll;
            this.atUserIds = atUserIds;
        }

        public boolean isAtAll() {
            return isAtAll;
        }

        public List<String> getAtUserIds() {
            return atUserIds;
        }
    }
}
