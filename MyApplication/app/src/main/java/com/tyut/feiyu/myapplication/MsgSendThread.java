package com.tyut.feiyu.myapplication;

/**
 * Created by feiyu on 2017/5/7.
 */

public class MsgSendThread extends Thread {

    private String sendMsg;
    private String url;
    private String content;


    public MsgSendThread( String url,String sendMsg) {
        this.sendMsg = sendMsg;
        this.url = url;
    }
    public void run(){

        HttpUtils httpconnection=new HttpUtils(url,sendMsg);
        content=httpconnection.doHttpPost();

    }
    public String getContent(){
        return  this.content;
    }
}
