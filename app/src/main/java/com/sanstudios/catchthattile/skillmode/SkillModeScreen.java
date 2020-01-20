package com.sanstudios.catchthattile.skillmode;
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

public class SkillModeScreen extends AppCompatActivity {

    private RadioGroup chooseLevel;
    private long level = 2000;
    private int currentPressedButtonId = 0;
    private int colorId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_mode_screen_layout);

        chooseLevel = (RadioGroup)findViewById(R.id.SkillModeRadioGroupLevel);
        chooseLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_skill_easy:  level = 2000;break;
                    case R.id.radio_skill_normal:level = 1000;break;
                    case R.id.radio_skill_hard:  level = 500;break;
                }
            }
        });

    }


    public void playSkillMode(View v){
        if(colorId !=0) {
            Intent intent = new Intent(SkillModeScreen.this, SkillModePlay.class);
            intent.putExtra("level", level);
            intent.putExtra("color", colorId);
            startActivity(intent);
        }else{
            Toast.makeText(this, "You have to choose tile color!", Toast.LENGTH_SHORT).show();
        }
    }


    public void showTutorial(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("Skill mode").setMessage(R.string.text_skill_mode).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    public void tutorialBtn(View v){
        showTutorial();
    }

    public void goToSettings(View v){
        Intent intent = new Intent(SkillModeScreen.this, SettingsActivity.class);
        startActivity(intent);
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
            case R.id.color_BLUE_skill:colorId = R.drawable.square_blue_border;break;
            case R.id.color_GREEN_skill:colorId = R.drawable.square_green_border;break;
            case R.id.color_YELLOW_skill:colorId = R.drawable.square_yellow_border;break;
            case R.id.color_RED_skill:colorId = R.drawable.square_red_border;break;
            case R.id.color_GRAY_skill:colorId = R.drawable.square_gray_border;break;
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
        return getSharedPreferences("switchSkill", MODE_PRIVATE).getBoolean("switchSkill",true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returnSwitchState()){
            showTutorial();
        }
    }
}
