package com.pangmaobao.bestsign.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @author he.feng
 */
public class HttpsClientUtil {

    /** The Constant SUCCESS. */
    private static final Integer SUCCESS = 200;

    /** The constant logger. */
    private static Logger logger = Logger.getLogger(HttpsClientUtil.class);

    public static HttpResp httpPost(String url, Map<String, String> dataMap) throws IOException {
        logger.info("开始执行https请求,请求地址url=[" + url + "],数据集合map = [" + dataMap + "]");
        HttpResp httpResp = new HttpResp();
        CloseableHttpClient httpclient = createSSLClientDefault();
        HttpPost httppost = new HttpPost(url);
        // 设置可消费编码格式
        httppost.setHeader("Accept-Charset", "utf-8");
        // 构造请求数据
        UrlEncodedFormEntity myEntity = new UrlEncodedFormEntity(generateFormParams(dataMap), Consts.UTF_8);
        // 设置请求体
        httppost.setEntity(myEntity);
        // 响应内容
        String responseContent = null;

        CloseableHttpResponse response = null;
        try {
            // post 请求
            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                httpResp.setTransSuccess(true);
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, Consts.UTF_8);
                httpResp.setResponseContext(responseContent);
            } else {
                httpResp.setTransSuccess(false);
                logger.error("HTTP访问失败：" + response.toString());
                responseContent = response.toString();
                httpResp.setResponseContext(responseContent);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
            }
        }
        return httpResp;
    }

    public static HttpResp httpPostByJSON(String url, Object json) throws IOException {
        logger.info("开始执行https请求,请求地址url=[" + url + "],数据json = [" + json + "]");
        HttpResp httpResp = new HttpResp();
        CloseableHttpClient httpclient = createSSLClientDefault();
        HttpPost httppost = new HttpPost(url);
        // 设置可消费编码格式
        httppost.setHeader("Accept-Charset", "utf-8");
        httppost.setHeader("Content-Type","application/json");
        // 构造请求数据
        String jsonData = toJson(json);
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(jsonData.getBytes("UTF-8"));
        //StringEntity myEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
        // 设置请求体
        httppost.setEntity(byteArrayEntity);
        // 响应内容
        String responseContent = null;

        CloseableHttpResponse response = null;
        try {
            // post 请求
            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                httpResp.setTransSuccess(true);
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, Consts.UTF_8);
                httpResp.setResponseContext(responseContent);
            } else {
                httpResp.setTransSuccess(false);
                httpResp.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
                logger.error("HTTP访问失败：" + response.toString());
                responseContent = response.toString();
                httpResp.setResponseContext(responseContent);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
            }
        }
        return httpResp;
    }

    /**
     *
     * @param url url
     * @param authorizationKey 授权key
     * @return HttpResp
     * @throws IOException
     */
    public static HttpResp httpGetByBasicAuthentication(String url, String authorizationKey) throws IOException {
        logger.info("开始执行https请求,请求地址url=[" + url + "]");
        HttpResp httpResp = new HttpResp();
        CloseableHttpClient httpclient = createSSLClientDefault();
        HttpGet httpGet = new HttpGet(url);
        // 设置可消费编码格式
        httpGet.setHeader("Accept-Charset", "utf-8");
        // Authorization: Basic 加密代码(注Basic和加密代码之间有空格)
        httpGet.setHeader("Authorization", "Basic " + authorizationKey);
        // 响应内容
        String responseContent = null;

        CloseableHttpResponse response = null;
        try {
            // get 请求
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                httpResp.setTransSuccess(true);
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, Consts.UTF_8);
                httpResp.setResponseContext(responseContent);
            } else {
                httpResp.setTransSuccess(false);
                httpResp.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
                logger.error("HTTP访问失败：" + response.toString());
                responseContent = response.toString();
                httpResp.setResponseContext(responseContent);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
            }
        }
        return httpResp;
    }

    /**
     * Http get for text string.
     *
     * @param url the url
     * @return the string
     */
    public static HttpResp httpPostForText(String url) throws IOException {
        HttpResp httpResp = new HttpResp();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept-Charset", "utf-8");// 设置可消费编码格式
        // 构造请求数据 使用ContentType.APPLICATION_JSON 默认编码格式为UTF-8
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            // get 请求
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                httpResp.setTransSuccess(true);
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, Consts.UTF_8);
                httpResp.setResponseContext(responseContent);
            } else {
                httpResp.setTransSuccess(false);
                httpResp.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
                logger.error("HTTP访问失败：" + response.toString());
                responseContent = response.toString();
                httpResp.setResponseContext(responseContent);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
            }
        }
        return httpResp;
    }

    /**
     * object to json
     * @param value
     * @return
     */
    private static String toJson(Object value) {
        if (value != null) {
            try {
                return JSONObject.toJSONString(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Create ssl client default closeable http client.
     *
     * @return the closeable http client
     */
    private static CloseableHttpClient createSSLClientDefault() {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            logger.error(e);
        }
        return httpClient;
    }

    private static List<? extends NameValuePair> generateFormParams(Map<String, String> dataMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry: dataMap.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

}
