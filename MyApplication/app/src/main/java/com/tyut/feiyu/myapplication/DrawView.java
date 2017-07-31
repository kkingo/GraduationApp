package com.tyut.feiyu.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by feiyu on 2017/4/19.
 */

public class DrawView extends View {
    public DrawView(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        int canvashight=canvas.getHeight();
        int canvaswidth=canvas.getWidth();
        int canvasdensity=canvas.getDensity();
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setAntiAlias(true);

        p.setColor(Color.RED);
        canvas.drawLine(10,10,canvaswidth,10,p);
        p.setColor(Color.BLUE);
        canvas.drawLine(10,10,10,canvashight,p);





    }
}
