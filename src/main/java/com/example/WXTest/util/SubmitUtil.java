package com.example.WXTest.util;

import com.example.WXTest.po.News;
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
import java.util.ArrayList;
import java.util.List;

public class SubmitUtil {



    //创建一个从指定地址获取json对象方法（get方式）
    public static JSONObject doGetStr(String url) throws ParseException, IOException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        //通过cilent对象执行httpGet获取response对象
        HttpResponse httpResponse = client.execute(httpGet);
        //从response对象中获取实体对象
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null){
            //通过工具类将实体对象转化为String
            String result = EntityUtils.toString(entity,"UTF-8");
            //将String转化为JSONObject对象
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }

    /**
     * POST请求
     * @param url
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr,"UTF-8"));
        HttpResponse response = client.execute(httpost);
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    //通过传递的参数确定返回类型
    public  static  <T> T getListFisrt(List<T> data) {
        //T t = new T();语法错误
            T t = data.get(0);
            return t;
    }

    public static void main(String[] args) {
        ArrayList<News> arrayList = new ArrayList<>();
        News news1 =  SubmitUtil.getListFisrt(arrayList);
    }

}
