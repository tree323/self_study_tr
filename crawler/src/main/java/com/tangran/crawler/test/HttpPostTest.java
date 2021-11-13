package com.tangran.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 7:38 下午 2021/11/7
 */
public class HttpPostTest {
    public static void main(String[] args) throws Exception {
        //1、 打开一个浏览器，创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2、输入网址，创建httpget对象
        HttpPost httpPost = new HttpPost("http://yun.itheima.com/search");
        // 在传智官网搜索视频：url http://yun.itheima.com/search?keys=Java
        // 声明List请求，封装表单中的参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keys", "Java"));
        // 创建表单的Entity对象,第一个参数就是封装好的表单数据，第二个参数就是编码
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params, "utf8");
        // 设置表单的Entity对象到Post请求中
        httpPost.setEntity(encodedFormEntity);
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        // 链式编程，可以无限add下去
        URI build = uriBuilder.addParameter("keys", "java").build();
        HttpGet httpGetWithParam = new HttpGet(build);
        System.out.println("url:" + httpGetWithParam);
        //3、按回车，发起请求，返回响应，使用httpclient对象发起请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //4、解析响应，获取数据
        // 判断状态码是否200
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity, "utf8");
            System.out.println(resp.length());
        }
    }
}
