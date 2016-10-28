package com.wqzhang.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wqzhang on 16-10-27.
 * 一个倒计时的环形动画效果
 * 在xml 里面调用  两个参数的构造方法
 * 在code 里调用 调用一个参数的构造方法
 *
 * surface 动画  在view/父类被隐藏时  还能继续绘画/显示
 * 所以又自定义了VIEW 去实现透明动画 ../CicularSeekBarView
 */

public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {
    public static final String TAG = "SurfaceViewDemo";
    static Paint paint = null;
    static Canvas canvas = null;
    static SurfaceHolder surfaceHolder = null;
    static Rect rectCircle = null;
    static RectF rectCircleF = null;
    static int allTime = 10 * 10;
    static int currentTime = allTime;
    static Paint numPaint = null;

    public SurfaceViewDemo(Context context) {
        super(context);
        Log.d(TAG, "SurfaceViewDemo one param");
        surfaceHolder = this.getHolder();
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);

    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = this.getHolder();
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);

        Log.d(TAG, "SurfaceViewDemo two param");
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "SurfaceViewDemo three param");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        Log.d(TAG, "SurfaceViewDemo four param");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
    }

    public void init() {
        Log.d(TAG, "init");

        rectCircle = new Rect(20, 40, 156, 176);
        rectCircleF = new RectF(rectCircle);

        //画圆 的paint
        paint = new Paint();
        paint.setColor(Color.parseColor("#f76e1f"));
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        //圆上方数字 的paint
        numPaint = new Paint();
        numPaint.setColor(Color.parseColor("#f76e1f"));
        numPaint.setTextSize(18);
        numPaint.setAntiAlias(true);


        start();
    }


    public static void setTime(int x) {
        Log.d(TAG, "setTime" + x);
        canvas = surfaceHolder.lockCanvas(rectCircle);
        if (x == 0) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        } else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            Log.d(TAG, "bili: " + (360 * ((float) x / (float) allTime)));
            canvas.drawText((currentTime / 10 + 1) + "s", 80, 20, numPaint);
            canvas.drawArc(rectCircleF, -90, -(360 * ((float) x / (float) allTime)), false, paint);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);


    }

    public static void start() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (currentTime >= 0) {
                            setTime(currentTime);
                            currentTime = currentTime - 1;

                            try {
                                Thread.sleep(1000 / 10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
    }
}
