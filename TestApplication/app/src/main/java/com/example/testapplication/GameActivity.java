package com.example.testapplication;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton[] cardButtons = new ImageButton[4]; // introduce array for imagebuttons
    int[] shuffledCards = newShuffle();
    int firstCard = shuffledCards[0];
    int secondCard = shuffledCards[1];
    int thirdCard = shuffledCards[2];
    int fourthCard = shuffledCards[3];

    FloatingActionButton floatingShuffle; // shuffle again button introduction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

        newShuffle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton0:
                Log.e("test", "Button 1 has been clicked!");
                if(0 == shuffledCards[0]) {
                    Log.e("TOIMISAATANA", "Oikein!"); }
                else {
                    Log.e("TOIMISAATANA", "Väärin!"); }
                break;
            case R.id.imageButton1:
                Log.e("test", "Button 2 has been clicked!");
                break;
            case R.id.imageButton2:
                Log.e("test", "Button 3 has been clicked!");
                break;
            case R.id.imageButton3:
                Log.e("test", "Button 4 has been clicked!");
                break;
            case R.id.floatingShuffle:
                Log.e("test", "Shuffle button has been clicked!");
                newShuffle();
                break;
        }
    }

    public void shuffleCards(int card[], int n) {
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
        int deck[] = {0, 1, 2, 3}; // array for all cards from 0 to 3
        shuffleCards(deck, 4); // shuffle array for all its 4 cards
        for (int i = 0; i < 4; i++) {
        System.out.println(deck[i] + " " + "korttiarvo");
        }
        return deck;
    }

    /*public void checkCard(ImageButton cardButton) {
        if(cardButton == (ImageButton) findViewById(R.id.imageButton)) {
            Log.e("CARDS TEST", "Oikein!");
        } else {
            Log.e("CARDS TEST", "Väärin!");
        }
    }*/
}