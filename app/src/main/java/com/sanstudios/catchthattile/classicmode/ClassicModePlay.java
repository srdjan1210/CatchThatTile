package com.sanstudios.catchthattile.classicmode;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sanstudios.catchthattile.CountdownActivity;
import com.sanstudios.catchthattile.R;


import java.util.Random;

public class ClassicModePlay extends AppCompatActivity {
    private int countdownTime = 30, points = 0, currentId = 0;
    private long level;
    private Random random = new Random();
    private Handler changingSquareRate = new Handler();
    private Handler countdownChanger = new Handler();
    private TableLayout table;
    public TextView txtScore;
    public TextView txtTime;
    private MediaPlayer mp;
    private int frameColor = R.drawable.square_border;
    private int squareColor = R.drawable.square_gray_border;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_mode_play_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        startActivity(new Intent(ClassicModePlay.this, CountdownActivity.class));

        changingSquareRate.postDelayed(new Runnable() {
            @Override
            public void run() {
                changingSquareRate.postDelayed(changeRateOfSquares(),level);
                startCountingDown();
            }
        },3000);

        level = getLevel();
        squareColor = getColor();

        table = (TableLayout)findViewById(R.id.tableClassicMode);
        txtScore = (TextView)findViewById(R.id.txtScoreClassic);
        txtTime = (TextView)findViewById(R.id.txtTimeClassic);

        txtTime.setText(""+countdownTime);

        mp = MediaPlayer.create(this,R.raw.point_sound);

        setSquare();


    }


    @NonNull
    private Long getLevel(){return getIntent().getExtras().getLong("level");}
    private int getColor(){return getIntent().getExtras().getInt("squareColor");}

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
            if(points > 0){
                points--;
                txtScore.setText(""+points);
            }
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
        Intent intent = new Intent(ClassicModePlay.this,ClassicModeScore.class);
        intent.putExtra("score",points);
        intent.putExtra("level",level);
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
        Toast.makeText(ClassicModePlay.this, "Time has ended!", Toast.LENGTH_SHORT).show();
        cancelCallbacks();
        goToScoreActivity();

    }
    private void restartMode(){
        resetSquareCallback();
        countdownTime = 30;
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cancelCallbacks();
    }

}
