package com.sanstudios.catchthattile.skillmode;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sanstudios.catchthattile.CountdownActivity;
import com.sanstudios.catchthattile.R;


import java.util.Random;

public class SkillModePlay extends AppCompatActivity {

    private long switchTime = 2000;
    private int score = 0;
    private int currentId = 0;
    private int life = 3;
    private TableLayout table;
    private Random random = new Random();
    private Handler switchSquare = new Handler();
    private TextView txtScore;
    private MediaPlayer mp;
    private MediaPlayer wmp;
    private Toolbar toolbar;
    private int frameColor = R.drawable.square_border;
    private int squareColor;
    private AlertDialog dialog;
    private SharedPreferences goldenTileNum;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_mode_play_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        startActivity(new Intent(SkillModePlay.this, CountdownActivity.class));


        switchSquare.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchSquare.postDelayed(changeRateOfSquares(),switchTime);
            }
        },3000);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        squareColor = getSquareColor();

        table = (TableLayout) findViewById(R.id.tableSkillMode);
        txtScore = (TextView)findViewById(R.id.txtScore_skill);


        mp = MediaPlayer.create(this,R.raw.point_sound);
        wmp = MediaPlayer.create(this,R.raw.wrong);

        setSquare();
        switchTime = getLevel();

    }

    private Long getLevel() { return getIntent().getExtras().getLong("level"); }
    private int getSquareColor() { return getIntent().getExtras().getInt("color"); }

    public void btnClicked(View v)
    {
        if(currentId == v.getId()){
            score++;
            playTheBeep();
            txtScore.setText(""+score);
            setSquare();

        }else {
            playTheWrongBeep();
            life--;
            checkLife();
        }
    }

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

    private void checkLife()
    {
        switch (life){
            case 0:setHeart(R.id.lives3);displayEnd();break;
            case 1:setHeart(R.id.lives2);break;
            case 2:setHeart(R.id.lives1);break;
        }

    }

    private Runnable changeRateOfSquares()
    {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                if(life != 0) {
                    setSquare();
                    switchSquare.postDelayed(changeRateOfSquares(), switchTime);
                }else{
                    displayEnd();
                }
            }
        };

        return run;
    }

    public void displayEnd()
    {
        Toast.makeText(this, "You have no more lives!", Toast.LENGTH_SHORT).show();
        switchSquare.removeCallbacksAndMessages(null);
        //ZA  PRIKAZIVANJE EKRANA SCORE
        askForContinuing();
    }

    private void goToScoreActivity()
    {
        Intent intent = new Intent(SkillModePlay.this,SkillModeScore.class);
        intent.putExtra("score",score);
        intent.putExtra("level",getLevel());
        startActivity(intent);
        finish();
    }

    private void playTheBeep()
    {
        mp.start();
    }
    private void playTheWrongBeep()
    {
        wmp.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.skill_mode_lives_menu,menu);
        return true;
    }

    private void setHeart(int id)
    {
        MenuItem item = toolbar.getMenu().findItem(id);
        item.setIcon(R.drawable.heart_nofill);

    }

    private void setHeart(int id,int dravableid){
        MenuItem item = toolbar.getMenu().findItem(id);
        item.setIcon(dravableid);
    }

    /*
    *********
    Nije jos odradjeno
    *********
     */
    private void askForContinuing(){
        AlertDialog.Builder dialogb = new AlertDialog.Builder(this);
        dialog = dialogb.create();
        dialog.setView(getLayoutInflater().inflate(R.layout.continue_playing_skill,null));
        dialog.setCancelable(false);
        dialog.show();



    }

    public void continuePlaying(View v){
        if(v.getId() == R.id.continuePlayingSkill) {
            if( checkGtNumber()) {
                life = 1;
                setHeart(R.id.lives3,R.drawable.heart_red);
                switchSquare.postDelayed(changeRateOfSquares(), switchTime);
                dialog.cancel();
            }else{
                Toast.makeText(this, "Not enough Golden Tiles!", Toast.LENGTH_SHORT).show();
                goToScoreActivity();
            }
        }else{
            goToScoreActivity();
        }
    }

    private boolean checkGtNumber(){
        goldenTileNum = getSharedPreferences("gems",MODE_PRIVATE);
        int numGT = goldenTileNum.getInt("gems",MODE_PRIVATE);

        if((numGT-10) >= 0){
            SharedPreferences.Editor gte = getSharedPreferences("gems",MODE_PRIVATE).edit() ;
            gte.putInt("gems",numGT-10);
            gte.apply();
            gte.commit();
            return true;
        }


        return false;
    }

    private void cancelCallbacks(){
        switchSquare.removeCallbacksAndMessages(null);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        cancelCallbacks();
    }



}
