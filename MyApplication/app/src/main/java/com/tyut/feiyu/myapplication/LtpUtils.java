package com.tyut.feiyu.myapplication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class LtpUtils {
/*	String url="http://api.ltp-cloud.com/analysis/";
	String api_key="M1m0o6X3A5PmuzpGiRAMGV5rPsHZPrsJFkdJQmtu";*/
	String url="http://ltpapi.voicecloud.cn/analysis/";
	String api_key="L1D4M9y4w844u107w9X6bfVoiMHWKXIQ2BSRtlIi";
	String text;
	String format;
	String pattern;
	
	public LtpUtils(String text, String format, String pattern) {
		super();
		this.text = text;
		this.format = format;
		this.pattern = pattern;
	}
	public LtpUtils(String format, String pattern) {
		super();
		this.format = format;
		this.pattern = pattern;
	}
	
	public static LtpUtils GetDefaultLtpObject(String text) {
		String format = "json";
		String pattern = "all";
		return new LtpUtils(text,format, pattern);
	}
	public LtpUtils(String str) {
		super();
		this.text=str;
		this.format="json";
		this.pattern="all";
	}
	public String WordSplit(){
		this.pattern="ws";
		String results=null;
		HttpClient client=HttpClients.createDefault();
		HttpPost post=new HttpPost(this.url);
		ArrayList<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("api_key", api_key));
		parameters.add(new BasicNameValuePair("text", text));
		parameters.add(new BasicNameValuePair("format", format));
		parameters.add(new BasicNameValuePair("pattern", pattern));
		System.out.println("ready");
		try {
			post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
			System.out.println(post.getEntity().toString());
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String temp=EntityUtils.toString(response.getEntity());
				System.out.println(temp.substring(7,temp.length()-4));
				results=temp.substring(7,temp.length()-4);
			}
			else{
				System.out.println("the server not response");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	public  String getFullResult(){
		String jsondata=null;
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		ArrayList<BasicNameValuePair> parameters=new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("api_key", api_key));
		parameters.add(new BasicNameValuePair("text", text));
		parameters.add(new BasicNameValuePair("format", format));
		parameters.add(new BasicNameValuePair("pattern", pattern));
		System.out.println("ready");
		try {
			post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
			System.out.println(post.getEntity().toString());
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String temp=EntityUtils.toString(response.getEntity());
				jsondata=temp.substring(7,temp.length()-4);
			}
			else{
				System.out.println("the server not response");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsondata;
	}

}
