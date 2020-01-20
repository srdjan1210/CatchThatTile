package com.sanstudios.catchthattile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class CountdownActivity extends Activity {
    int countdown = 3;
    TextView countdownTxt;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown_layout);

        countdownTxt = findViewById(R.id.countdownTxt);

        Handler h = new Handler();
        h.postDelayed(countdownTime(h),1000);

    }

    public Runnable countdownTime(final Handler h){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                countdown--;
                checkTime(h);

            }
        };
        return run;
    }

    public void checkTime(Handler h){
        if(countdown == 0){
            countdownTxt.setText("GO!");
            finish();
        }else{
            countdownTxt.setText(""+countdown);
            h.postDelayed(countdownTime(h),1000);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
