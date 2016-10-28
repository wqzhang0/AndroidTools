package com.wqzhang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.wqzhang.myapplication.listener.NaviAnimationListener;


/**
 * Created by wqzhang on 16-10-28.
 *
 * 自定义View 实现环形动画倒计时
 *
 * 提供listener 处理动画结束的事件
 * 解决： surfaceView动画 隐藏布局时继续显示的情况 ；多次调用view倒计时Thread 同步
 * 隐藏后不listener 的结束回调方法
 *
 */

public class CicularSeekBarView extends View {

    public static final String TAG = "CicularSeekBarView";
    static Paint paint = null;
    static Rect rectCircle = null;
    static RectF rectCircleF = null;
    static int allTime = 10 * 10;
    static int currentTime = allTime;
    static Paint numPaint = null;
    private NaviThread thread = null;

    public NaviAnimationListener mNaviAnimationListener;

    public CicularSeekBarView(Context context) {
        super(context);
        Log.d(TAG, "SurfaceViewDemo one param");
    }

    public CicularSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "SurfaceViewDemo two param");
        init();
    }

    public CicularSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        Log.d(TAG, "init");
        rectCircle = new Rect(27, 48, 149, 170);
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
        numPaint.setTextSize(16);
        numPaint.setAntiAlias(true);

    }


    @Override
    public ViewTreeObserver getViewTreeObserver() {
        return super.getViewTreeObserver();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        if (currentTime <= 0) {
            //透明背景
            canvas.drawColor(Color.TRANSPARENT);

        } else {
            //透明背景
            canvas.drawColor(Color.TRANSPARENT);
            Log.d(TAG, "bili: " + (360 * ((float) currentTime / (float) allTime)));
            canvas.drawText((currentTime / 10 + 1) + "s", 80, 20, numPaint);
            canvas.drawArc(rectCircleF, -90, -(360 * ((float) currentTime / (float) allTime)), false, paint);
        }
    }

    public void setTime() {
        allTime = currentTime = 30;
        start();
    }

    public void setNaviAnimationListener(NaviAnimationListener naviAnimationListener) {
        mNaviAnimationListener = naviAnimationListener;
    }

    Handler handler = new Handler();


    private void start() {
        if (thread == null) {
            thread = new NaviThread();
            thread.start();
        } else {
            Log.d(TAG, thread.getState() + "");
            if (Thread.State.TERMINATED != thread.getState()) {
                return;
            } else {
                thread = new NaviThread();
                thread.start();
            }
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.d("visi", visibility + "");
        super.onWindowVisibilityChanged(visibility);
    }

    class NaviThread extends Thread {
        @Override
        public void run() {
            while (currentTime > 0) {
                Log.d(TAG, "thread run" + currentTime);
                postInvalidate();
                currentTime = currentTime - 1;
                try {
                    Thread.sleep(1000 / 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (currentTime == 0) {
                    if (mNaviAnimationListener != null) {
                        Log.d(TAG, "listener ");

                        handler.getLooper().prepare();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(isShown()){
                                    if (View.VISIBLE == getWindowVisibility()) {
                                        mNaviAnimationListener.end();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }


}
