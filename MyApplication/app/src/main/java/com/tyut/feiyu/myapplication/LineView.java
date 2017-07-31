package com.tyut.feiyu.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiyu on 2017/4/19.
 */

public class LineView extends View {
    private int xPoint=10;
    private int yPoint=10;
    private int xScale=8;
    private int yScale=40;
    private int xLength;
    private int yLength;
 //   private int MaxDataSize=xLength/xScale;
    private List<Point> points=new ArrayList<Point>();
   // private String[] yLabel=new String[yLength/yScale];
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0){    //判断接受消息类型
                Log.i("TAG", "handleMessage: getMessage");
                LineView.this.invalidate();  //刷新View
            }
        };
    };
    public LineView(Context context) {
        super(context);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Point point=new Point();
//                    point.setX(new Random().nextInt(844));
//                    point.setY(new Random().nextInt(1010));
//                    points.add(point);
//                    handler.sendEmptyMessage(0);
//                }
//            }
//        }).start();
        new LineViewThread(handler).start();
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        if(points.size()>1){
            for (int i = 1; i < points.size(); i++) {  //依次取出数据进行绘制
                canvas.drawLine(xPoint+points.get(i-1).getX(),yPoint+points.get(i-1).getY(),
                        xPoint+points.get(i).getX(), yPoint+points.get(i).getY(), paint);

            }
        }

    }
}
