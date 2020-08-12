package com.example.WXTest.controller;

import com.example.WXTest.po.TextMessage;
import com.example.WXTest.util.CheckUtil;
import com.example.WXTest.util.MessageUtil;
import com.sun.imageio.plugins.common.ImageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//@RestController 返回数据,@Controller 则是找相应的页面
@RestController
public class XWController {

    @RequestMapping(value = "/test")
    public String hello() {
        return "Hello Spring Boot";
    }

    @GetMapping(value = "/check")
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in---------");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    @PostMapping(value = "/check")
    public void textMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("UTF-8");
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String FromUserName = map.get("ToUserName");
        String ToUserName = map.get("FromUserName");

        //回复文本消息
        if (map.get("MsgType").equals("text")) {
            if (map.get("Content").equals("11")) {
                String content = "你发送的消息是" + map.get("Content");
                TextMessage testMessage = new TextMessage();
                testMessage.setToUserName(map.get("FromUserName"));
                testMessage.setFromUserName(map.get("ToUserName"));
                testMessage.setContent(content);
                testMessage.setCreateTime(map.get("CreateTime"));
                testMessage.setMsgId(map.get("MsgId"));
                testMessage.setMsgType(map.get("MsgType"));
                String returemessage = MessageUtil.testMessageToXml(testMessage);
                PrintWriter out = response.getWriter();
                System.out.println(returemessage);
                out.print(returemessage);
                out.close();

            }
        }

        //回复图文消息
        if (map.get("MsgType").equals("text")) {
            if (map.get("Content").equals("22")) {
                //通过该map获取FromUser与ToUser

                // String content = map.get("Content");
                String returnxml = MessageUtil.initNewsMessage(FromUserName, ToUserName);
                PrintWriter out = response.getWriter();
                System.out.println(returnxml);
                out.print(returnxml);
                out.close();

            }
        }
        //回复图片消息
        if (map.get("MsgType").equals("text")) {
            if (map.get("Content").equals("33")) {

                String returemessage = MessageUtil.initImageMessage(FromUserName, ToUserName);
                PrintWriter out = response.getWriter();
                // String message1 = returemessage.replace("com.example.WXTest.po.TextMessage","xml");
                System.out.println(returemessage);
                out.print(returemessage);
                out.close();

            }
        }
        if (map.get("MsgType").equals("event")) {

            if (map.get("Event").equals("CLICK")) {
                System.out.println("11");
                TextMessage testMessage = new TextMessage();
                testMessage.setToUserName(map.get("FromUserName"));
                testMessage.setFromUserName(map.get("ToUserName"));
                testMessage.setContent("这是菜单");
                testMessage.setCreateTime(map.get("CreateTime"));
                testMessage.setMsgId(map.get("MsgId"));
                testMessage.setMsgType("text");
                String returemessage = MessageUtil.testMessageToXml(testMessage);
                PrintWriter out = response.getWriter();
                System.out.println(returemessage);
                out.print(returemessage);
                out.close();

            }
            if (map.get("Event").equals("subscribe")) {

                TextMessage testMessage = new TextMessage();
                testMessage.setToUserName(map.get("FromUserName"));
                testMessage.setFromUserName(map.get("ToUserName"));
                testMessage.setContent("欢迎关注！！");
                testMessage.setCreateTime(map.get("CreateTime"));
                testMessage.setMsgId(map.get("MsgId"));
                testMessage.setMsgType("text");
                String returemessage = MessageUtil.testMessageToXml(testMessage);
                PrintWriter out = response.getWriter();
                System.out.println(returemessage);
                out.print(returemessage);
                out.close();

            }

        }
    }


}
