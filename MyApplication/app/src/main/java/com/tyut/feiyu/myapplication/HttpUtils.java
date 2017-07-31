package com.tyut.feiyu.myapplication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by feiyu on 2017/5/10.
 */


public class HttpUtils {
    String url;
    String sendMsg;
    String recMsg;

    public HttpUtils(String url, String sendData) {
        this.url ="http://192.168.1.115:8080/Myserver/servlet/"+url;
        this.sendMsg = sendData;
    }
    public  String  doHttpPost(){
        HttpClient client = new DefaultHttpClient();
        HttpPost post=new HttpPost(url);
        ArrayList<BasicNameValuePair> data=new ArrayList<BasicNameValuePair>();
        data.add(new BasicNameValuePair("sendMsg",sendMsg));
        try {
            post.setEntity(new UrlEncodedFormEntity(data,"utf-8"));
            HttpResponse response=client.execute(post);

            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
                recMsg = EntityUtils.toString(response.getEntity());

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recMsg;
    }

}
