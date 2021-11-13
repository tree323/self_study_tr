package com.tangran.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 5:00 下午 2021/11/7
 */
public class HttpConfigTest {
    public static void main(String[] args) throws Exception {
        //1、 打开一个浏览器，创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2、输入网址，创建httpget对象
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");

        // 配置请求信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)  // 创建连接的最长时间，单位是毫秒
                .setConnectionRequestTimeout(500) // 设置获取连接的最长时间，单位是毫秒
                .setSocketTimeout(10_1000) // 设置数据传输的最长时间，单位是毫秒
                .build();

        // 给请求设置请求信息
        httpGet.setConfig(config);
        // 在传智官网搜索视频：url http://yun.itheima.com/search?keys=Java
        // 带参数的GET请求
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        // 链式编程，可以无限add下去
        URI build = uriBuilder.addParameter("keys", "java").build();
        HttpGet httpGetWithParam = new HttpGet(build);
        System.out.println("url:" + httpGetWithParam);
        //3、按回车，发起请求，返回响应，使用httpclient对象发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4、解析响应，获取数据
        // 判断状态码是否200
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity, "utf8");
            System.out.println(resp);
        }
    }
}
