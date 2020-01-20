package com.sanstudios.catchthattile.timemode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.ads.AdsInterstitial;

public class TimeModeScore extends AppCompatActivity {

    private int score = 0;
    private TextView txtScore;
    private TextView txtHighScore;
    private TextView newHighScore;
    private TextView timeLevel;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode_score_layout);

        score = getScore();

        newHighScore = (TextView)findViewById(R.id.newHighScore);
        txtScore = (TextView)findViewById(R.id.txt_SCORE);
        txtHighScore = (TextView)findViewById(R.id.time_mode_current_highscore);
        txtScore.setText(""+score);
        timeLevel = (TextView)findViewById(R.id.txtTimeLevel);

        txtTimeLevel();

        if(determineHighScore()){
            txtHighScore.setText("HIGHSCORE : "+getHighScoreFromPrefs());
        }else{
            txtHighScore.setText("HIGHSCORE : "+getHighScoreFromPrefs());
        }
        AdsInterstitial.showAdd();
    }


    private int getScore(){
        return getIntent().getExtras().getInt("score");
    }
    private long getLevel(){ return getIntent().getExtras().getLong("level"); }
    private int getTime(){ return getIntent().getExtras().getInt("time"); }

    public void confirm(View v)
    {
        finish();
    }

    private void txtTimeLevel(){
        switch((int)getLevel()){
            case 1000:timeLevel.setText("Difficulty: Easy");break;
            case 600:timeLevel.setText("Difficulty: Normal");break;
            case 450:timeLevel.setText("Difficulty: Hard");break;
        }
    }
    private boolean determineHighScore(){
        if(getHighScoreFromPrefs() < score){
            setHighScoreInPrefs(score);
            newHighScore.setVisibility(View.VISIBLE);
            return true;
        }

        return false;
    }

    private void setHighScoreInPrefs(int score){
        switch(getTime()){

            case 30:
                switch((int)getLevel()){
                    case 1000:executePrefs("highScoreTimeModeHard30s");break;
                    case 600:executePrefs("highScoreTimeModeNormal30s");break;
                    case 450:executePrefs("highScoreTimeModeEasy30s");break;
                }
                break;
            case 60:
                switch((int)getLevel()){
                    case 1000:executePrefs("highScoreTimeModeHard60s");break;
                    case 600:executePrefs("highScoreTimeModeNormal60s");break;
                    case 450:executePrefs("highScoreTimeModeEasy60s");break;
                }
                break;
            case 90:
                switch((int)getLevel()){
                    case 1000:executePrefs("highScoreTimeModeHard90s");break;
                    case 600:executePrefs("highScoreTimeModeNormal90s");break;
                    case 450:executePrefs("highScoreTimeModeEasy90s");break;
                }
                break;
        }

    }

    private int getHighScoreFromPrefs(){
        switch(getTime()){

            case 30:
                switch((int)getLevel()){
                    case 1000:return getExecutedPrefs("highScoreTimeModeHard30s");
                    case 600:return getExecutedPrefs("highScoreTimeModeNormal30s");
                    case 450:return getExecutedPrefs("highScoreTimeModeEasy30s");
                }
                break;
            case 60:
                switch((int)getLevel()){
                    case 1000:return getExecutedPrefs("highScoreTimeModeHard60s");
                    case 600:return getExecutedPrefs("highScoreTimeModeNormal60s");
                    case 450:return getExecutedPrefs("highScoreTimeModeEasy60s");
                }
                break;
            case 90:
                switch((int)getLevel()){
                    case 1000:return getExecutedPrefs("highScoreTimeModeHard90s");
                    case 600:return getExecutedPrefs("highScoreTimeModeNormal90s");
                    case 450:return getExecutedPrefs("highScoreTimeModeEasy90s");
                }
                break;
        }

        return 0;
    }

    private void executePrefs(String prefName){
        SharedPreferences.Editor highScorePrefs = getSharedPreferences(prefName,MODE_PRIVATE).edit();
        highScorePrefs.putInt(prefName,score);
        highScorePrefs.apply();
        highScorePrefs.commit();
    }

    private int getExecutedPrefs(String prefName){
        SharedPreferences sp = getSharedPreferences(prefName,MODE_PRIVATE);
        return sp.getInt(prefName,0);
    }




}
