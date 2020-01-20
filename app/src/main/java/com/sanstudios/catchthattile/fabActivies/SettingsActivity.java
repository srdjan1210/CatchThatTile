package com.sanstudios.catchthattile.fabActivies;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.sanstudios.catchthattile.R;

public class SettingsActivity extends AppCompatActivity {


    private Switch showClassicTut;
    private Switch showTimeTut;
    private Switch showSkillTut;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);


        showClassicTut = findViewById(R.id.cla_tut_set);
        showTimeTut = findViewById(R.id.tim_tut_set);
        showSkillTut = findViewById(R.id.ski_tut_set);

        showClassicTut.setChecked(returnSwitchState("switchClassic"));
        showTimeTut.setChecked(returnSwitchState("switchTime"));
        showSkillTut.setChecked(returnSwitchState("switchSkill"));

    }

    private void setPrefs(Switch switcho, String name){


            if (switcho.isChecked())
            {
                SharedPreferences.Editor editor = getSharedPreferences(name, MODE_PRIVATE).edit();
                editor.putBoolean(name, true);
                editor.commit();
            }
            else
            {
                SharedPreferences.Editor editor = getSharedPreferences(name, MODE_PRIVATE).edit();
                editor.putBoolean(name, false);
                editor.commit();
            }

    }

    private boolean returnSwitchState(String name){
        return getSharedPreferences(name, MODE_PRIVATE).getBoolean(name,true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setPrefs(showClassicTut,"switchClassic");
        setPrefs(showSkillTut,"switchSkill");
        setPrefs(showTimeTut,"switchTime");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
