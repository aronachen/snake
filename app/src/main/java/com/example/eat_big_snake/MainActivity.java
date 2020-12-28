package com.example.eat_big_snake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity  {
    private GamePanel mv;
    private Button start;
    private Button gameContinue;
    private Button gameStop;
    Sensor b;
    public int lastDirection;
    private DirectionThread lt = new DirectionThread(mv, 1);
    private DirectionThread ut = new DirectionThread(mv, 2);
    private DirectionThread rt = new DirectionThread(mv, 3);
    private DirectionThread dt = new DirectionThread(mv, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        b= sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(listener, b, SensorManager.SENSOR_DELAY_NORMAL);

        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        mv = (GamePanel)findViewById(R.id.mv);
        mv.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mv.getLayoutParams();
        params.width = width;
        params.height = width;
        mv.setLayoutParams(params);
//        left=(Button)findViewById(R.id.left);
//        up=(Button)findViewById(R.id.up);
//        right=(Button)findViewById(R.id.right);
//        down=(Button)findViewById(R.id.down);
//        left.setWidth(width/4);
//        left.setHeight(width/5);
//        up.setWidth(width/4);
//        up.setHeight(width/5);
//        right.setWidth(width/4);
//        right.setHeight(width/5);
//        down.setWidth(width/4);
//        down.setHeight(width/5);
//        left.setX(width/2-width/8-width/5);
//        left.setY(width+(height-width)/3+width/10);
//        up.setX(width/2-width/8);
//        up.setY(width+(height-width)/3-width/20);
//        right.setX(width/2-width/8+width/5);
//        right.setY(width+(height-width)/3+width/10);
//        down.setX(width/2-width/8);
//        down.setY(width+(height-width)/3+width/5+width/20);
        start=(Button)findViewById(R.id.start);
        start.setOnTouchListener((View.OnTouchListener) mv);
        gameContinue=(Button)findViewById(R.id.gameContinue);
        gameContinue.setOnTouchListener(mv);
        gameStop=(Button)findViewById(R.id.gameStop);
        gameStop.setOnTouchListener(mv);
//        left.setOnTouchListener(mv);
//        up.setOnTouchListener(mv);
//        right.setOnTouchListener(mv);
//        down.setOnTouchListener(mv);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mv.start=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z =event.values[2];
            if(mv!=null){
                if (lastDirection!=4){
                if (y>20&&y>z){
                    ut = new DirectionThread(mv, 2);
                    ut.canRun = true;
                    rt.canRun = false;
                    dt.canRun = false;
                    lt.canRun = false;
                    ut.start();
                }
                } else{
                    ut.canRun = false;
                }
                if (lastDirection!=2) {
                    if (y <-20&&y<z) {
                        dt = new DirectionThread(mv, 4);
                        ut.canRun = false;
                        rt.canRun = false;
                        dt.canRun = true;
                        lt.canRun = false;
                        dt.start();
                    }
                }else{
                    dt.canRun = false;
                }
                if (lastDirection!=3) {
                    if (z>20&&z>y) {  //佐/
                        lt = new DirectionThread(mv, 1);
                        ut.canRun = false;
                        rt.canRun = false;
                        dt.canRun = false;
                        lt.canRun = true;
                        lt.start();
                    }
                }else{
                    lt.canRun = false;
                }
                if (lastDirection!=1) {
                    if (z<-20&&z<y) {   //右
                        rt = new DirectionThread(mv, 3);
                        ut.canRun = false;
                        rt.canRun = true;
                        dt.canRun = false;
                        lt.canRun = false;
                        rt.start();

                }
                }else{
                    rt.canRun = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}