package com.sanstudios.catchthattile.timemode;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.sanstudios.catchthattile.CountdownActivity;
import com.sanstudios.catchthattile.R;

import java.util.Random;

public class TimeModePlay extends AppCompatActivity {

    private int countdownTime, points = 0, currentId = 0;
    private Random random = new Random();
    private Handler changingSquareRate = new Handler();
    private Handler countdownChanger = new Handler();
    private TableLayout table;
    public TextView txtScore;
    public TextView txtTime;
    private long level  = 0;
    private MediaPlayer mp;
    private int frameColor = R.drawable.square_border;
    private int squareColor;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode_play_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        startActivity(new Intent(TimeModePlay.this, CountdownActivity.class));
        changingSquareRate.postDelayed(new Runnable() {
            @Override
            public void run() {
                changingSquareRate.postDelayed(changeRateOfSquares(),level);
                startCountingDown();
            }
        },3000);


        squareColor = getSquareColor();

        countdownTime = getTime();
        level = getLevel();

        table = (TableLayout)findViewById(R.id.tableTimeMode);
        txtScore = (TextView)findViewById(R.id.txtScore);
        txtTime = (TextView)findViewById(R.id.txtTime);

        txtScore.setText(""+points);
        txtTime.setText(""+countdownTime);

        mp = MediaPlayer.create(this,R.raw.point_sound);

        setSquare();


    }




    private int getTime(){
        return getIntent().getExtras().getInt("choosenTime");
    }

    private long getLevel(){
        return getIntent().getExtras().getLong("choosenLevel");
    }

    private int getSquareColor(){
        return getIntent().getExtras().getInt("color");
    }

    //FUNKCIJA ZA ODBROJAVANJE
    private void startCountingDown()
    {
        if(checkTime()) {
            countdownChanger.postDelayed(new Runnable() {
                @Override
                public void run() {
                    countdownTime--;
                    startCountingDown();
                    txtTime.setText("" + countdownTime);
                }
            }, 1000);
        }else{
            displayEnd();
        }
    }

    //Promjena svakih level sekundi
    private Runnable changeRateOfSquares()
    {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                if(checkTime()) {
                    setSquare();
                    changingSquareRate.postDelayed(changeRateOfSquares(), level);
                }
            }
        };

        return run;
    }

    public void btnClicked(View v)
    {
       if(currentId == v.getId()){
           points++;
           playTheBeep();
           setSquare();
           txtScore.setText(""+points);
           resetSquareCallback();

       }else {
       }

    }
    //FUNKCIJA ZA POSTAVLJANJE KVADRATA
    private void setSquare()
    {
        if(currentId != 0){
            Button btn = (Button)findViewById(currentId);
            btn.setBackgroundResource(frameColor);
        }

        TableRow tr = (TableRow) table.getChildAt(random.nextInt(table.getChildCount()));
        Button btn = (Button)tr.getChildAt(random.nextInt(tr.getChildCount()));
        btn.setBackgroundResource(squareColor);
        currentId = btn.getId();

    }

    private boolean checkTime()
    {
        if(countdownTime == 0){
            return false;
        }
        return true;
    }

    private void goToScoreActivity()
    {
        Intent intent = new Intent(TimeModePlay.this,TimeModeScore.class);
        intent.putExtra("score",points);
        intent.putExtra("level",getLevel());
        intent.putExtra("time",getTime());
        startActivity(intent);
        finish();
    }

    private void cancelCallbacks()
    {
        changingSquareRate.removeCallbacksAndMessages(null);
        countdownChanger.removeCallbacksAndMessages(null);
    }

    private void resetSquareCallback()
    {
        changingSquareRate.removeCallbacksAndMessages(null);
        changingSquareRate.postDelayed(changeRateOfSquares(),level);
    }
    @Override
    public void onBackPressed()
    {
        changingSquareRate.removeCallbacksAndMessages(null);
        countdownChanger.removeCallbacksAndMessages(null);
        finish();
    }

    private void playTheBeep()
    {
        mp.start();
    }

    private void displayEnd()
    {
        Toast.makeText(TimeModePlay.this, "Time has ended!", Toast.LENGTH_SHORT).show();
        cancelCallbacks();
        goToScoreActivity();

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cancelCallbacks();
    }
}
