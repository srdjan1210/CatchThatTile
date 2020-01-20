package com.sanstudios.catchthattile.loadingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.sanstudios.catchthattile.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.splash_screen_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },1000);




    }
}
