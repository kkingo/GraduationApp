package com.tyut.feiyu.myapplication;

public class queryThread extends Thread {
	boolean isContinue = true;
	LtpUtils ltpObject;
	String text;
	public queryThread(LtpUtils ltpObject) {
		super();
		this.ltpObject = ltpObject;
	}
	public void run() {
		int times=20;
		while(isContinue){
			text=ltpObject.getFullResult();
			if(text==null){
				times--;
				isContinue=true;
			}
			else{
				isContinue=false;	
			}
			if(times==0)
				isContinue=false;
		}
	}
	public String getResults() {
		if(isContinue==false&&this.text!=null)
			return text;
		else
			return null;
	}
}
