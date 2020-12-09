package com.example.testapplication.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    final ImageButton[] cardButtons = new ImageButton[4]; // introduce array for imagebuttons
    int[] shuffledCards; // introduction for storing the shuffled cards
    ImageButton imageButton; // introduction of general imagebutton
    FloatingActionButton floatingShuffle; // shuffle again button introduction
    int currentScore = 0;
    SharedPreferences myPreferences; // Object declaration for saving data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // SharedPreferences object can be used to read and write to preferences-file for saving user data
        myPreferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);

        cardButtons[0] = (ImageButton) findViewById(R.id.imageButton0); // connect to imagebutton's id
        cardButtons[0].setOnClickListener(this); // set a listener
        cardButtons[1] = (ImageButton) findViewById(R.id.imageButton1);
        cardButtons[1].setOnClickListener(this);
        cardButtons[2] = (ImageButton) findViewById(R.id.imageButton2);
        cardButtons[2].setOnClickListener(this);
        cardButtons[3] = (ImageButton) findViewById(R.id.imageButton3);
        cardButtons[3].setOnClickListener(this);
        floatingShuffle = (FloatingActionButton) findViewById(R.id.floatingShuffle); // connect to id
        floatingShuffle.setOnClickListener(this);

        shuffledCards = newShuffle(); // cards are shuffled onCreate and saved to shuffledCards-array

        TextView highScoreField = findViewById(R.id.highscore); // find highscore-field from xml
        highScoreField.setText(String.valueOf(checkHighScore()));
        System.out.println(checkHighScore());
    }

    @Override
    public void onClick(View v) {
        Animation fadeAnimationWrong = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeanimationend); // introduce fadeAnimation when wrong
        // switch case for clicked buttons
        switch (v.getId()) {
            case R.id.imageButton0:
                imageButton = findViewById(R.id.imageButton0); // assign into variable for onClick, ready to be animated
                Log.e("test", "Button 1 has been clicked!");
                System.out.println(Arrays.toString(shuffledCards));
                if(shuffledCards[0] == 0) {
                    currentScore++;
                    setCurrentScore(currentScore); // start function to update currentScore for user
                    if(currentScore>checkHighScore()) {
                        int highScore = currentScore;
                        updateHighScore(highScore);
                        TextView highScoreField = findViewById(R.id.highscore); // find highscore-field from xml
                        highScoreField.setText(String.valueOf(checkHighScore())); // set highscore to xml
                    }
                    Log.e("Correct/False: ", "Correct!");
                    shuffledCards = newShuffle();} // pass current score to newShuffle
                else {
                    currentScore = 0;
                    setCurrentScore(currentScore);
                    Log.e("Correct/False: ", "False!");
                    imageButton.startAnimation(fadeAnimationWrong);}
                break;
            case R.id.imageButton1:
                imageButton = findViewById(R.id.imageButton1);
                Log.e("test", "Button 2 has been clicked!");
                if(shuffledCards[1] == 0) {
                    currentScore++;
                    setCurrentScore(currentScore);
                    if(currentScore>checkHighScore()) {
                        int highScore = currentScore;
                        updateHighScore(highScore);
                        TextView highScoreField = findViewById(R.id.highscore); // find highscore-field from xml
                        highScoreField.setText(String.valueOf(checkHighScore()));
                    }
                    Log.e("Correct/False: ", "Correct!");
                    shuffledCards = newShuffle();}
                else {
                    currentScore = 0;
                    setCurrentScore(currentScore);
                    Log.e("Correct/False: ", "False!");
                    imageButton.startAnimation(fadeAnimationWrong); }
                break;
            case R.id.imageButton2:
                imageButton = findViewById(R.id.imageButton2);
                Log.e("test", "Button 3 has been clicked!");
                if(shuffledCards[2] == 0) {
                    currentScore++;
                    setCurrentScore(currentScore);
                    if(currentScore>checkHighScore()) {
                        int highScore = currentScore;
                        updateHighScore(highScore);
                        TextView highScoreField = findViewById(R.id.highscore); // find highscore-field from xml
                        highScoreField.setText(String.valueOf(checkHighScore()));
                    }
                    Log.e("Correct/False: ", "Correct!");
                    shuffledCards = newShuffle();}
                else {
                    currentScore = 0;
                    setCurrentScore(currentScore);
                    Log.e("Correct/False: ", "False!");
                    imageButton.startAnimation(fadeAnimationWrong); }
                break;
            case R.id.imageButton3:
                imageButton = findViewById(R.id.imageButton3);
                Log.e("test", "Button 4 has been clicked!");
                if(shuffledCards[3] == 0) {
                    currentScore++;
                    setCurrentScore(currentScore);
                    if(currentScore>checkHighScore()) {
                        int highScore = currentScore;
                        updateHighScore(highScore);
                        TextView highScoreField = findViewById(R.id.highscore); // find highscore-field from xml
                        highScoreField.setText(String.valueOf(checkHighScore()));
                    }
                    Log.e("Correct/False: ", "Correct!");
                    shuffledCards = newShuffle();}
                else {
                    currentScore = 0;
                    setCurrentScore(currentScore);
                    Log.e("Correct/False: ", "False!");
                    imageButton.startAnimation(fadeAnimationWrong); }
                break;
            case R.id.floatingShuffle:
                currentScore = 0;
                setCurrentScore(currentScore);
                Log.e("test", "Shuffle button has been clicked!");
                shuffledCards = newShuffle();
                break;
        }
    }

    public void shuffleCards(int[] card, int n) {
        Random rnd = new Random(); // initiate random
        for (int i=0; i<n; i++) {
            int r = i + rnd.nextInt(4-i); // random continued until all are shuffled
            // swapping the elements
            int temp = card[r];
            card[r] = card[i];
            card[i] = temp;
        }
    }

    public int[] newShuffle() {
        Animation fadeAnimationReset = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeanimationreset); // set up alpha-reveal animations

        // loop through ImageButton cards and reset their alpha through animations
        for (int i=0; i<4; i++) {
            ImageButton imageButton = (ImageButton) findViewById(getResources().getIdentifier("imageButton" + i, "id", this.getPackageName()));
            imageButton.startAnimation(fadeAnimationReset);
        }

        int[] deck = {0, 1, 2, 3}; // array "deck" for all cards from 0 to 3
        shuffleCards(deck, 4); // shuffle array for all its 4 cards
        for (int i = 0; i < 4; i++) {
        System.out.println(deck[i] + " " + "korttiarvo"); // loop through deck's cards one by one
        }
        return deck; // return shuffled cards to shuffledCards-array
    }

    public int checkHighScore() {
        return myPreferences.getInt("HIGHSCORE", 0);
    }

    public void updateHighScore(int highScore) {
        SharedPreferences.Editor myEditor = myPreferences.edit(); // call on SP-object's editor to add data to file
            myEditor.putInt("HIGHSCORE", highScore); // add Int to file with key
            myEditor.apply();
    }

    public void setCurrentScore(int currentScore) {
        TextView currentScoreField = findViewById(R.id.current_score); // find current_score from xml
        currentScoreField.setText(String.valueOf(currentScore));
    }
}