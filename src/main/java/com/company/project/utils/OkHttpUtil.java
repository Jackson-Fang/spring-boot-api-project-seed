package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * http工具类
 *
 * @author weiteng
 * @date 2018-04-16
 */
@Slf4j
public class OkHttpUtil {
    /**
     * 处理url，拼接token和时间戳
     *
     * @param url
     * @return
     */
    private static String handleUrl(String url, Map<String, String> urlParams) {
        if (urlParams == null || urlParams.isEmpty()) {
            return url;
        }
        if (url.lastIndexOf("?") == -1) {
            url = url + "?";
        }
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            String v = entry.getValue();
            if (v.lastIndexOf(";") != -1) {
                v = v.substring(0, v.lastIndexOf(";"));
            }
            url += "&" + entry.getKey() + "=" + v;
        }

        return url;
    }

    /**
     * 获取请求对象
     *
     * @return
     */
    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder().build();
    }

    private static OkHttpClient getClient(long timeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS);
        return builder.build();
    }

    /**
     * get请求
     *
     * @return
     */
    public static String requestOfGet(String url, Map<String, String> headers, Map<String, String> urlParams) {
        url = handleUrl(url, urlParams);
        OkHttpClient okHttpClient = getClient(5000);
        Request request = getRequest(url, null, headers);
        Call call = okHttpClient.newCall(request);
        try (Response response = call.execute()) {
//            response = call.execute();
            if (response.code() == 200) {
                String result = response.body().string();
                return result;
            }
            return "";
        } catch (IOException e) {
            log.error("OkHttpUtil.requestOfGet请求异常：", e);
        } catch (Exception ex) {
            log.error("OkHttpUtil.requestOfGet其他异常信息：", ex);
        }
        return "";
    }

    /**
     * get请求
     *
     * @param timeout 超时时间，单位毫秒
     * @return
     */
    public static String requestOfGet(String url, Map<String, String> headers, Map<String, String> urlParams, long timeout) {
        url = handleUrl(url, urlParams);
        OkHttpClient okHttpClient = getClient(timeout);
        Request request = getRequest(url, null, headers);
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response.code() == 200) {
                String result = response.body().string();
                return result;
            }
            return "";
        } catch (IOException e) {
            log.error("OkHttpUtil.requestOfGet请求异常：", e);
        } catch (Exception ex) {
            log.error("OkHttpUtil.requestOfGet其他异常信息：", ex);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ex) {
                }
            }
        }
        return "";
    }

//    /**
//     * 获取请求体
//     * @param requestBodies
//     * @return
//     */
//    private static okhttp3.RequestBody getRequestBody(List<HttpRequestBody> requestBodies){
//        FormBody.Builder builder=new FormBody.Builder();
//        requestBodies.stream().forEach(requestBody->{
//            builder.add(requestBody.getKey(),requestBody.getValue());
//        });
//        return builder.build();
//    }

    /**
     * 获取最终的请求对象
     *
     * @param url
     * @param requestBody
     * @return
     */
    private static Request getRequest(String url, okhttp3.RequestBody requestBody, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        builder.addHeader("Connection", "keep-alive");
        if (requestBody != null) {
            builder.post(requestBody);
        }
        return builder.build();
    }

//    /**
//     * post请求，form表单提交
//     * @param url
//     * @param params
//     * @return
//     */
//    public static String requestOfPostByForm(String url, List<HttpRequestBody> params, Map<String,String> headers){
//        okhttp3.RequestBody body = null;
//        if(CollectionUtils.isNotEmpty(params)){
//            body=getRequestBody(params);
//        }
//        OkHttpClient okHttpClient = getClient();
//        Request request=getRequest(url,body,headers);
//        try{
//            Response response=okHttpClient.newCall(request).execute();
//            if(response.code()==200){
//                return response.body().string();
//            }
//            return "";
//        }catch (Exception ex){
//            log.error("OkHttpUtil.requestOfPostByForm请求异常：",ex);
//        }
//        return "";
//    }

    /**
     * 下载远程文件
     *
     * @param url
     * @param headers
     * @param urlParams
     * @return
     */
    public static InputStream downLoadFile(String url, Map<String, String> headers, Map<String, String> urlParams) {
        url = handleUrl(url, urlParams);
        OkHttpClient okHttpClient = getClient(5000);
        Request request = getRequest(url, null, headers);
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response.code() == 200) {
                return response.body().byteStream();
            }
        } catch (IOException e) {
            log.error("OkHttpUtil.downLoadFile请求异常：", e);
        } catch (Exception ex) {
            log.error("OkHttpUtil.downLoadFile其他异常信息：", ex);
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

}
