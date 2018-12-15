package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class RobotUtil {

    public static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=a5bee81fc6cd9c3a59a7e0ba9f6c93d6f3877b6799c3aac4a9d8a25ce6117cb9";

    public static void chatRebot(String msg) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");

            String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + msg + "\"}}";
            StringEntity se = new StringEntity(textMsg, "utf-8");
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());
//                logger.biz("今日机器人发送数据结果:" + result + ",推送内容:" + msg);
            }

        } catch (Exception e) {
            log.error("通知机器人失败", e);
        }

    }

}
