package com.sanstudios.catchthattile.fabActivies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.sanstudios.catchthattile.R;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener,RatingBar.OnRatingBarChangeListener {
        private EditText feedbackText;
        private Button sendFeedback;
        private RatingBar ratingBar;
        private TextView starValue;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.ratingActionBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rate app!!");

        ratingBar = findViewById(R.id.ratingBar);
        starValue = findViewById(R.id.starValue);
        feedbackText = findViewById(R.id.editText);
        sendFeedback = findViewById(R.id.sendFeedback);

        sendFeedback.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(this);



    }

    @Override
    public void onClick(View v) {
        if (feedbackText.getText().toString().isEmpty()) {
            Toast.makeText(RatingActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "stjepanovicsrdjan2000@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            emailIntent.putExtra(Intent.EXTRA_TEXT, starValue.getText().toString()+"\n\n"+feedbackText.getText().toString());
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

            startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch((int)rating) {
            case 1:
                starValue.setText("Very bad!");
                break;
            case 2:
                starValue.setText("Need some improvement!");
                break;
            case 3:
                starValue.setText("Good!");
                break;
            case 4:
                starValue.setText("Great!");
                break;
            case 5:
                starValue.setText("Awesome,I love it!");
                break;
            default:
                ratingBar.setRating(1);
        }
    }
}
