package com.sanstudios.catchthattile.classicmode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.ads.AdsInterstitial;

public class ClassicModeScore extends AppCompatActivity {
    private int score = 0;
    private TextView txtScore;
    private TextView txtHighScore;
    private TextView txtClassicLevel;
    private TextView txtNewHighScore;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_mode_score_layout);
        score = getScore();

        txtScore = (TextView)findViewById(R.id.txt_SCORE);
        txtHighScore = (TextView)findViewById(R.id.classic_mode_current_highscore);
        txtClassicLevel = (TextView)findViewById(R.id.txtClassicLevel);

        txtScore.setText(""+score);
        txtClassicLevel();

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
        private long getLevel(){return getIntent().getExtras().getLong("level");}

        public void confirm(View v)
        {
            finish();
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


    private void txtClassicLevel(){
        switch((int)getLevel()){
            case 1500:txtClassicLevel.setText("Difficulty: Easy");break;
            case 1000:txtClassicLevel.setText("Difficulty: Normal");break;
            case 500:txtClassicLevel.setText("Difficulty: Hard");break;
        }
    }
        private void setHighScoreInPrefs(int score){
            SharedPreferences.Editor highScorePrefs = getSharedPreferences("",MODE_PRIVATE).edit();

            switch((int)getLevel()){
                case 1500:
                    highScorePrefs = getSharedPreferences("highScoreClassicModeEasy",MODE_PRIVATE).edit();
                    highScorePrefs.putInt("highScoreClassicModeEasy",score);break;
                case 1000:
                    highScorePrefs = getSharedPreferences("highScoreClassicModeNormal",MODE_PRIVATE).edit();
                    highScorePrefs.putInt("highScoreClassicModeNormal",score);break;
                case 500:
                    highScorePrefs = getSharedPreferences("highScoreClassicModeHard",MODE_PRIVATE).edit();
                    highScorePrefs.putInt("highScoreClassicModeHard",score);break;
            }
            highScorePrefs.apply();
            highScorePrefs.commit();
        }

        private int getHighScoreFromPrefs(){
            SharedPreferences sp;
            switch((int)getLevel()){
                case 1500:
                    sp = getSharedPreferences("highScoreClassicModeEasy",MODE_PRIVATE);
                    return sp.getInt("highScoreClassicModeEasy",0);
                case 1000:
                    sp = getSharedPreferences("highScoreClassicModeNormal",MODE_PRIVATE);
                    return sp.getInt("highScoreClassicModeNormal",0);
                case 500:
                    sp = getSharedPreferences("highScoreClassicModeHard",MODE_PRIVATE);
                    return sp.getInt("highScoreClassicModeHard",0);
            }

            return 0;
        }


    }



