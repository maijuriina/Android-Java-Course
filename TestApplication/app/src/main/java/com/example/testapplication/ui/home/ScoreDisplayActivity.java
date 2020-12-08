package com.example.testapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testapplication.R;

public class ScoreDisplayActivity extends AppCompatActivity {

    TextView scoreDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        scoreDisplay = findViewById(R.id.scoreDisplay);
        findScore();
    }

    public void findScore() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        String score = extras.getString("score");
        scoreDisplay.setText(score);
    }
}