package com.sanstudios.catchthattile.classicmode;

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
import androidx.appcompat.widget.SwitchCompat;
import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.fabActivies.SettingsActivity;

public class ClassicModeScreen extends AppCompatActivity
{
    private RadioGroup group;
    private long level = 1500;
    private int colorId = R.drawable.square_gray_border;
    private int currentPressedButtonId;
    private SwitchCompat switchCompat;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_mode_screen_layout);

        group = (RadioGroup)findViewById(R.id.SkillModeRadioGroupLevel);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_classic_easy:level = 1500;break;
                    case R.id.radio_classic_normal:level = 1000;break;
                    case R.id.radio_classic_hard:level = 500;break;
                }
            }
        });


    }

    public void playClassicMode(View v)
    {
        if(currentPressedButtonId != 0) {
            Intent i = new Intent(ClassicModeScreen.this, ClassicModePlay.class);
            i.putExtra("level", level);
            i.putExtra("squareColor", colorId);
            startActivity(i);
        }else{
            Toast.makeText(this, "You have to choose tile color!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showTutorial()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("Classic mode").setMessage(R.string.text_classic_mode).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    public void goToSettings(View v){
        Intent intent = new Intent(ClassicModeScreen.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void tutorialBtn(View v) { showTutorial(); }

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
            case R.id.color_BLUE:colorId = R.drawable.square_blue_border;break;
            case R.id.color_GREEN:colorId = R.drawable.square_green_border;break;
            case R.id.color_YELLOW:colorId = R.drawable.square_yellow_border;break;
            case R.id.color_RED:colorId = R.drawable.square_red_border;break;
            case R.id.color_GRAY:colorId = R.drawable.square_gray_border;break;
        }
    }

    private Animation animate()
    {
        Animation anim = new ScaleAnimation(
                1f, 1.2f, // Start and end values for the X axis scaling
                1f, 1.2f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.3f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        return anim;
    }

    private boolean returnSwitchState(){
        return getSharedPreferences("switchClassic", MODE_PRIVATE).getBoolean("switchClassic",true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returnSwitchState()){
            showTutorial();
        }
    }


}
