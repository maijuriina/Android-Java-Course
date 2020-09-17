package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button; // introduce Finish-button
    Button gameButton; // introduce Play game-button
    TextView textView; // introduce textView for Hello World
    public static final String TAG = "TestApplicationMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button); // find finish-button
        button.setOnClickListener(this); // this meaning this class

        gameButton = (Button) findViewById(R.id.gameButton); // find play-button
        gameButton.setOnClickListener(this); // this meaning this class

        Log.d(TAG, "App activated successfully");
        Log.i(TAG, "Log sent from onCreate()");
        Log.w(TAG, "Running out of things to log!");
        Log.e(TAG, "Fatal error! Could not find more things to log.");
    }

        public void startActivity(View view) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Log.e("test", "Button has been clicked!");
                textView = findViewById(R.id.textView);
                if (textView.getVisibility() == View.VISIBLE) {
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                };
                break;
            case R.id.gameButton:
                startActivity(view);
                break;
        }
    }
}