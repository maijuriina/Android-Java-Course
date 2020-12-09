package com.example.testapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapplication.R;
import com.example.testapplication.ui.search.CompanySearchActivity;

public class GuessActivity extends AppCompatActivity  implements View.OnClickListener {
    TextView xamk;
    TextView laurea;
    TextView lab;
    TextView jamk;
    TextView tamk;
    SeekBar seekBar;
    int guessPoints = 0;
    String scoreToDisplay;
    Button showScore;
    TextView correct;
    boolean clickedXamk = false;
    boolean clickedLaurea = false;
    boolean clickedJamk = false;
    boolean clickedTamk = false;
    boolean clickedLab = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        xamk = findViewById(R.id.xamk);
        laurea = findViewById(R.id.laurea);
        lab = findViewById(R.id.lab);
        jamk = findViewById(R.id.jamk);
        tamk = findViewById(R.id.tamk);
        correct = findViewById(R.id.correct);
        showScore = findViewById(R.id.showScore);
        seekBar = findViewById(R.id.seekBarGuess);
        seekBar.setMax(5);
        seekBar.setProgress(0);
        xamk.setOnClickListener(this);
        laurea.setOnClickListener(this);
        lab.setOnClickListener(this);
        jamk.setOnClickListener(this);
        tamk.setOnClickListener(this);
        showScore.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // makes the seekbar not greyed out, even though is not changeable
        seekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xamk:
                if (!clickedXamk && !clickedLab) {
                    clickedXamk = true;
                    guessPoints++;
                    seekBar.setProgress(guessPoints);
                    xamk.setAlpha((float) 0.7);
                }
                break;
            case R.id.laurea:
                if (!clickedLaurea && !clickedLab) {
                    clickedLaurea = true;
                    guessPoints++;
                    seekBar.setProgress(guessPoints);
                    laurea.setAlpha((float) 0.7);
                }
                break;
            case R.id.lab:
                if (!clickedLab) {
                    clickedLab = true;
                    guessPoints++;
                    seekBar.setProgress(guessPoints);
                    setVisibility();
                    scoreToDisplay = String.valueOf(addUp());
                    resetAlphasAndScore();
                }
                break;
            case R.id.jamk:
                if (!clickedJamk && !clickedLab) {
                    clickedJamk = true;
                    guessPoints++;
                    seekBar.setProgress(guessPoints);
                    jamk.setAlpha((float) 0.7);
                }
                break;
            case R.id.tamk:
                if (!clickedTamk && !clickedLab) {
                    clickedTamk = true;
                    guessPoints++;
                    seekBar.setProgress(guessPoints);
                    tamk.setAlpha((float) 0.7);
                }
                break;
            case R.id.showScore:
                Intent intent = new Intent(this, ScoreDisplayActivity.class); // create new intent
                intent.putExtra("score", scoreToDisplay); // give the intent the search terms  with key "searchTerms"
                startActivity(intent);
        }
    }

    public int addUp() {
        int finalScore = guessPoints;
        return finalScore;
    }

    public void setVisibility() {
        showScore.setVisibility(View.VISIBLE);
        correct.setVisibility(View.VISIBLE);
    }

    public void resetAlphasAndScore() {
        xamk.setAlpha(1);
        laurea.setAlpha(1);
        jamk.setAlpha(1);
        tamk.setAlpha(1);
    }
}