package com.suhun.serviceseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView counterShow;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyReceiver myReceiver = new MyReceiver();
        registerReceiver(myReceiver, new IntentFilter("happy"));
        counterShow = findViewById(R.id.showCounter);
        seekBar = findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    intent.putExtra("newCounter", progress);
                    startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startServiceFun(View view){
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
    public void stopServiceFun(View view){
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        seekBar.setProgress(0);
        counterShow.setText(""+0);
    }

    private class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int maxCounter = intent.getIntExtra("maxCounter", -1);
            int counter = intent.getIntExtra("counter", -1);
            if(maxCounter > 0) seekBar.setMax(maxCounter);
            if(counter > 0){
                seekBar.setProgress(counter);
                counterShow.setText(""+counter);
            }
        }
    }
}