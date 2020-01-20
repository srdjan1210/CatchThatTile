package com.sanstudios.catchthattile.timemode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.fabActivies.SettingsActivity;

public class TimeModeScreen extends AppCompatActivity {
    public RadioGroup chooseTime;
    public RadioGroup chooseLevel;
    public int choosenTime = 30;
    public long choosenLevel = 1000;
    private int currentPressedButtonId = 0;
    private int colorId;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_mode_screen_layout);


        chooseTime = (RadioGroup)findViewById(R.id.timeModeRadioGroup);
        chooseTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_30s  : choosenTime = 30;break;
                    case R.id.radio_60s : choosenTime = 60;break;
                    case R.id.radio_90s : choosenTime = 90;break;
                }
            }
        });

        chooseLevel = (RadioGroup)findViewById(R.id.timeModeRadioGroupLevel);
        chooseLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_easy:choosenLevel = 1000;break;
                    case R.id.radio_normal:choosenLevel = 600;break;
                    case R.id.radio_hard:choosenLevel = 450;break;
                }
            }
        });
    }



    public void playTimeMode(View v){
            if(colorId != 0) {
                Intent intent = new Intent(TimeModeScreen.this, TimeModePlay.class);
                Bundle paket = new Bundle();
                paket.putInt("choosenTime", choosenTime);
                paket.putLong("choosenLevel", choosenLevel);
                paket.putInt("color", colorId);
                intent.putExtras(paket);
                startActivity(intent);
            }else{
                Toast.makeText(this, "You have to choose tile color!", Toast.LENGTH_SHORT).show();
            }
    }


    public void showTutorial(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("Time mode").setMessage(R.string.text_time_mode).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    public void goToSettings(View v){
        Intent intent = new Intent(TimeModeScreen.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void tutorialBtn(View v){
        showTutorial();
    }

    public void chooseColor(View v)
    {
        if(currentPressedButtonId == 0) {
            Button b = (Button) findViewById(v.getId());
            b.startAnimation(animate());
            currentPressedButtonId = b.getId();
        }
        Button b = (Button) findViewById(currentPressedButtonId);
        b.getAnimation().setFillAfter(false);
        currentPressedButtonId = v.getId();
        v.startAnimation(animate());



        switch(v.getId())
        {
            case R.id.color_BLUE_time:colorId = R.drawable.square_blue_border;break;
            case R.id.color_GREEN_time:colorId = R.drawable.square_green_border;break;
            case R.id.color_YELLOW_time:colorId = R.drawable.square_yellow_border;break;
            case R.id.color_RED_time:colorId = R.drawable.square_red_border;break;
            case R.id.color_GRAY_time:colorId = R.drawable.square_gray_border;break;
        }
    }

    private Animation animate()
    {
        Animation anim = new ScaleAnimation(
                1f, 1.2f, // Start and end values for the X axis scaling
                1f, 1.2f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.25f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        return anim;
    }

    private boolean returnSwitchState(){
        return getSharedPreferences("switchTime", MODE_PRIVATE).getBoolean("switchTime",true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returnSwitchState()){
            showTutorial();
        }
    }


}
