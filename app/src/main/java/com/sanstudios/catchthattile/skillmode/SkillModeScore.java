package com.sanstudios.catchthattile.skillmode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.ads.AdsInterstitial;

public class SkillModeScore extends AppCompatActivity {

    private int score = 0;
    private TextView txtScore;
    private TextView txtHighScore;
    private TextView txtSkillLevel;
    private TextView txtNewHighScore;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_mode_score_layout);

        score = getScore();

        txtScore = (TextView)findViewById(R.id.txt_SCORE);
        txtHighScore = (TextView)findViewById(R.id.skill_mode_current_highscore);
        txtSkillLevel = (TextView)findViewById(R.id.txtSkillLevel);


        txtScore.setText(""+score);
        txtSkillLevel();

        if(determineHighScore()){
            txtHighScore.setText("HIGHSCORE : "+getHighScoreFromPrefs());
        }else{
            txtHighScore.setText("HIGHSCORE : "+getHighScoreFromPrefs());
        }
        AdsInterstitial.showAdd();
    }

    public void confirm(View v){ finish(); }

    private int getScore(){
        return getIntent().getExtras().getInt("score");
    }
    private long getLevel(){ return getIntent().getExtras().getLong("level"); }

    private void txtSkillLevel(){
        switch((int)getLevel()){
            case 2000:txtSkillLevel.setText("Difficulty: Easy");break;
            case 1000:txtSkillLevel.setText("Difficulty: Normal");break;
            case 500:txtSkillLevel.setText("Difficulty: Hard");break;
        }
    }

    private boolean determineHighScore(){
        if(getHighScoreFromPrefs() < score){
            
            txtNewHighScore = (TextView)findViewById(R.id.newHighScore);
            txtNewHighScore.setVisibility(View.VISIBLE);
            
            setHighScoreInPrefs(score);
            
            return true;
        }

        return false;
    }

    private void setHighScoreInPrefs(int score){
        SharedPreferences.Editor highScorePrefs = getSharedPreferences("",MODE_PRIVATE).edit();

        switch((int)getLevel()){
            case 2000:
                highScorePrefs = getSharedPreferences("highScoreSkillModeEasy",MODE_PRIVATE).edit();
                highScorePrefs.putInt("highScoreSkillModeEasy",score);break;
            case 1000:
                highScorePrefs = getSharedPreferences("highScoreSkillModeNormal",MODE_PRIVATE).edit();
                highScorePrefs.putInt("highScoreSkillModeNormal",score);break;
            case 500:
                highScorePrefs = getSharedPreferences("highScoreSkillModeHard",MODE_PRIVATE).edit();
                highScorePrefs.putInt("highScoreSkillModeHard",score);break;
        }
        highScorePrefs.apply();
        highScorePrefs.commit();
    }

    private int getHighScoreFromPrefs(){
        SharedPreferences sp;
        switch((int)getLevel()){
            case 2000:
                sp = getSharedPreferences("highScoreSkillModeEasy",MODE_PRIVATE);
                return sp.getInt("highScoreSkillModeEasy",0);
            case 1000:
                sp = getSharedPreferences("highScoreSkillModeNormal",MODE_PRIVATE);
                return sp.getInt("highScoreSkillModeNormal",0);
            case 500:
                sp = getSharedPreferences("highScoreSkillModeHard",MODE_PRIVATE);
                return sp.getInt("highScoreSkillModeHard",0);
        }

        return 0;
    }
}
