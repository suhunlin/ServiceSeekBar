package com.suhun.serviceseekbar;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private String tag = MyService.class.getSimpleName();
    private Timer timer;
    private UIHandler uiHandler;
    private int counter, maxCounter;
    private class MyTask extends TimerTask{
        @Override
        public void run() {
            if(counter>=maxCounter){
                cancel();
            }else{
                uiHandler.sendEmptyMessage(0);
            }
        }
    }
    private class UIHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            counter++;
            Intent intent = new Intent("happy");
            intent.putExtra("counter", counter);
            sendBroadcast(intent);
        }
    }

    public MyService(){
        uiHandler = new UIHandler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        counter = 0;maxCounter = 100;
        Intent intent = new Intent("happy");
        intent.putExtra("maxCounter", maxCounter);
        sendBroadcast(intent);
        timer.schedule(new MyTask(), 100, 100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int newCounter = intent.getIntExtra("newCounter", -1);
        if(newCounter>0){
            counter = newCounter;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}