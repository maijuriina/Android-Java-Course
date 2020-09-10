package com.example.testapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button; // introduce button
    public static final String TAG = "TestApplicationMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button); // find button
        button.setOnClickListener(this); // this meaning this class

        Log.d(TAG, "App activated successfully");
        Log.i(TAG, "Log sent from onCreate()");
        Log.w(TAG, "Running out of things to log!");
        Log.e(TAG, "Fatal error! Could not find more things to log.");

        /*Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("test", "Button has been clicked!");
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:
                Log.e("test", "Button has been clicked!");
                break;
        }
    }
}