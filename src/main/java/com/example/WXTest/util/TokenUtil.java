package com.example.WXTest.util;

import com.example.WXTest.po.AccessToken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;

import static com.example.WXTest.util.SubmitUtil.doGetStr;

public class TokenUtil {
    private static final String APPID="wx0bb02d104d0bab17";
    private static final String APPSECRET="de0622a502d30ed294b78cd3e5314e5b";
    private static final String URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * 获取accessToken
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static AccessToken getAccessToken() throws ParseException, IOException{
        AccessToken token = new AccessToken();
        String url = URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject!=null){
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }





    //将token方法里的json取出放入TokenAccess对象中

}
