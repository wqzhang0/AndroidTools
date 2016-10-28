package com.wqzhang.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.wqzhang.myapplication.listener.NaviAnimationListener;

/**
 * Created by wqzhang
 * on 2016/9/27.
 * Reason :
 */

public class DragAndDrawActivity extends Activity {
    private final String TAG = "DragAndDrawActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        surfaceViewDemo.init();
//        SurfaceViewDemo surfaceViewDemo = new SurfaceViewDemo(this);
//        setContentView(surfaceViewDemo);
//        surfaceViewDemo.init();
        CicularSeekBarView cicularSeekBarView = (CicularSeekBarView) findViewById(R.id.cicularSeekBarView);
        cicularSeekBarView.setTime();
        cicularSeekBarView.setNaviAnimationListener(new NaviAnimationListener() {
            @Override
            public void end() {
                Log.d(TAG,"NaviAnimationListener,end");
            }

            @Override
            public void start() {
            }
        });
//        surfaceViewDemo.init();
    }


}
