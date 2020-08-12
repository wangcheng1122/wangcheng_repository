package com.example.WXTest.util;

import com.example.WXTest.po.*;
import com.example.WXTest.template.*;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

public class MessageUtil {
    private static String INDUSTRY_URL ="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
    private static String GET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    private static String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //XML转化成map集合
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //创建xml解析器
        SAXReader reader = new SAXReader();
        //获取输入流
        InputStream ins = request.getInputStream();
        //通过解析器从输入流中读取xml
        Document doc = reader.read(ins);
        //获取xml根元素
        Element root = doc.getRootElement();
        //通过根元素获取所有节点元素
        List<Element> list = root.elements();
        //将所有根节点的信息放入map集合中
        for(Element e:list)
        {
            map.put(e.getName(),e.getText());
        }
        ins.close();

        return map;
    }
    //将文本消息对象转化为xml类型
    public static String testMessageToXml(TextMessage textMessage) throws Exception {

        XStream xstream = new XStream();
        xstream.alias("xml",textMessage.getClass());
       String xml = xstream.toXML(textMessage);
       return xml;

    }
    ////将图文消息对象转化为xml类型
    public static String newsMessageToXml(NewsMessage newsMessage) throws Exception {

        XStream xstream = new XStream();
        xstream.alias("xml",newsMessage.getClass());
         xstream.alias("item", new News().getClass());
        String xml = xstream.toXML(newsMessage);
        return xml;

    }
    //初始化图文消息
    public static String initNewsMessage(String FromUserName , String ToUserName) throws Exception{
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(FromUserName);
        newsMessage.setToUserName(ToUserName);

        newsMessage.setCreateTime(new Date().toString());
        newsMessage.setMsgType("news");
        ArrayList<News> arrayList = new ArrayList<>();
        News news = new News();
        news.setDescription("这是图片描述");
        news.setTitle("图片标题");
        System.out.println("加载图片");
        news.setPicUrl("http://wangc.vip.qydev.com/2.jpg");
        news.setUrl("www.baidu.com");
        arrayList.add(news);
        newsMessage.setArticleCount(""+arrayList.size());
        newsMessage.setArticles(arrayList);
        String xml = newsMessageToXml(newsMessage);
        return  xml;

    }
    //初始化图片消息
    public static String initImageMessage(String FromUserName , String ToUserName) throws Exception{
        Image image = new Image();
        image.setMediaId("OeWqCRXYi3RDPYkMynGGS1j1k_lshEkuV8ZKX42ED0ymr6oTdaR1O122l15La19p");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(FromUserName);
        imageMessage.setToUserName(ToUserName);
        imageMessage.setCreateTime(new Date().toString());
        imageMessage.setMsgType("image");
        imageMessage.setImage(image);

        XStream xstream = new XStream();
        xstream.alias("xml",imageMessage.getClass());
        String xml = xstream.toXML(imageMessage);
        return xml;
    }

    //设置模板消息行业
    public static void setIndusry(String token,String industry_id1,String industry_id2) throws IOException, ParseException {
        String url = INDUSTRY_URL.replace("ACCESS_TOKEN",token);
        Industry industry = new Industry();
        industry.setIndustry_id1(industry_id1);
        industry.setIndustry_id2(industry_id2);
        String industry_json = JSONObject.fromObject(industry).toString();
        SubmitUtil.doPostStr(url,industry_json);

    }
    //获取模板消息行业
    public static String getIndusry(String token) throws IOException, ParseException {
        String url = GET_INDUSTRY_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = SubmitUtil.doGetStr(url);
        Iterator iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey().toString());
            System.out.println(entry.getValue().toString());
        }

        String message = jsonObject.getJSONObject("primary_industry").getString("first_class");
        return message;

    }
    //发送模板消息
    public static String sendTemplateMessage(String token) throws IOException, ParseException {
        //创建模板消息对象
        User user = new User();
        user.setColor("#173177");
        user.setValue("王成同学");

        Money money = new Money();
        money.setColor("#173177");
        money.setValue("100元");

        Data data = new Data();
        data.setMoney(money);
        data.setUsername(user);

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setData(data);
        templateMessage.setTemplate_id("vcJNXKXzxj2JmiXALEYFZUv1Y48gF1ZzDIVK1PbHqak");
        templateMessage.setUrl("http://www.baidu.com");
        templateMessage.setTouser("oEMaL6kgS5vjsRrNDlaWE1kGrXOA");

        //转化为json字符串
        String json = JSONObject.fromObject(templateMessage).toString();
        System.out.println(json);

        //发送到微信后台
        String url = SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = SubmitUtil.doPostStr(url,json);
        String message = "消息返回码为：" + jsonObject.getString("errcode");

        return message;
    }


}
