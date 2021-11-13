package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Set;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 10:06 下午 2021/11/7
 */
public class JsoupFirstTest {

    String filePath = "";

    // URL 解析
    @Test
    public void testUrl() throws Exception {
        // 解析url地址，第一个参数是访问的url，第二个参数是访问时候的超时时间
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);

        // 使用标签选择器，获取title标签内的内容
        String title = document.getElementsByTag("title").first().text();
        System.out.println(title);

    }

    // 解析字符串
    @Test
    public void testString() throws Exception {
        // 使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File(filePath), "utf8");
        // 解析字符串
        Document doc = Jsoup.parse(content);
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception {
        // 解析文件
        Document doc = Jsoup.parse(new File(filePath), "utf8");
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);

    }

    @Test
    public void testDOM() throws Exception {
        Document doc = Jsoup.parse(new File(filePath), "utf8");

        // 获取元素
        // 1 根据id查询元素
//        Element element= doc.getElementById("city_bj");
        // 2 根据标签获取元素
//        Element element = doc.getElementsByTag("span").first();
        // 3 根据class获取元素
//        Element element = doc.getElementsByClass("class_a").first();
        // 4 根据属性获取元素
        Elements elementsByAttribute = doc.getElementsByAttribute("");
        Elements elementsByAttributeValue = doc.getElementsByAttributeValue("", "");

    }

    @Test
    public void testDomData() throws Exception {
        Document doc = Jsoup.parse(new File(filePath), "utf8");
        Element element = doc.getElementById("test");
        String str = "";
        // 从元素中获取id
        str = element.id();
        // 从元素中获取className
        str = element.className();
        Set<String> classNames = element.classNames();
        for (String className : classNames) {
            System.out.println("");
        }
        // 从元素中获取属性的值
        str = element.attr("class");

        // 从元素中获取所有属性的值
        Attributes attributes = element.attributes();
        System.out.println(attributes.toString());


        // 从元素中获取文本内容text
        str = element.text();
    }

}
