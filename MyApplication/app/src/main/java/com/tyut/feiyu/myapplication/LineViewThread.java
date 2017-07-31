package com.tyut.feiyu.myapplication;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by feiyu on 2017/4/22.
 */

public class LineViewThread extends Thread {
    private List<Point> points=new ArrayList<Point>();
    private Handler handler;
    public LineViewThread(Handler handler) {
        this.handler = handler;
    }
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point point=new Point();
            point.setX(new Random().nextInt(844));
            point.setY(new Random().nextInt(1010));
            points.add(point);
            Log.d("TAG", "run:getPoint ");
            handler.sendEmptyMessage(0);
            Log.d("TAG", "run: sendMessage");
        }
    }
}
