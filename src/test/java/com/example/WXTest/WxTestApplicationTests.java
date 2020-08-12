package com.example.WXTest;

import com.example.WXTest.menu.Menu;
import com.example.WXTest.po.AccessToken;
import com.example.WXTest.util.MenuUtil;
import com.example.WXTest.util.MessageUtil;
import com.example.WXTest.util.TokenUtil;
import com.example.WXTest.util.UploadUtil;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;

@SpringBootTest
class WxTestApplicationTests {

	@Test
	void contextLoads() throws IOException, ParseException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		AccessToken accessToken = TokenUtil.getAccessToken();
		String accesstoken = accessToken.getToken();

		/*//素材上传
		String mediaId = UploadUtil.upload("D:\\临时文件\\2020.6.4\\2.jpg",accesstoken,"image");
		System.out.println("mediaId的值为："+mediaId);*/

	/*	//菜单创建
		String menu = JSONObject.fromObject(MenuUtil.initMenu()).toString();
		int code = MenuUtil.createMenu(accesstoken,menu);
		if(code == 0){
			System.out.println("菜单创建成功");}
		else {
			System.out.println("菜单创建失败");
		}*/
		//设置模板消息行业
	/*	MessageUtil.setIndusry(accesstoken,"1","4");*/
		//获取模板消息行业
	/*	System.out.println(MessageUtil.getIndusry(accesstoken));*/
		//发送模板消息
		System.out.println(MessageUtil.sendTemplateMessage(accesstoken));

	}

}
