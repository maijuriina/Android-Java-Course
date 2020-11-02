package com.example.testapplication.ui.notifications;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    private TextClock textClock;
    private NumberPicker noPickerMin;
    private NumberPicker noPickerSec;
    private String[] pickerValsSec;
    private String[] pickerValsMin;
    private TextView remainingTimeSec;
    private TextView remainingTimeMin;
    private int minPicked;
    private int secPicked;
    private long timePicked;
    private MaterialButtonToggleGroup toggleGroup;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private TextView timerText;
    private CountDownTimer userTimer;
    private boolean timerRunning = false;
    private boolean paused = false;
    private long millisUntilFinished;
    private String formattedTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        noPickerMin = root.findViewById(R.id.numberPickerMins); // numberpickers introduced
        noPickerSec = root.findViewById(R.id.numberPickerSecs);
        remainingTimeSec = root.findViewById(R.id.pickedTimeSec); // these are for displaying the selected and remaining time
        remainingTimeMin = root.findViewById(R.id.pickedTimeMin);
        toggleGroup = root.findViewById(R.id.toggleButton); // find toggle group
        playButton = root.findViewById(R.id.playButton); // pair the buttons
        pauseButton = root.findViewById(R.id.pauseButton);
        stopButton = root.findViewById(R.id.stopButton);
        timerText = root.findViewById(R.id.timerText);
        setMaxValuesSec();
        setMaxValuesMin();
        noPickerSec.setOnValueChangedListener(this); // set listeners
        noPickerMin.setOnValueChangedListener(this);
        toggleGroup.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        return root;
    }

    private void setMaxValuesSec() {
        int i;
        if (noPickerSec != null) {
            noPickerSec.setMinValue(0);
            noPickerSec.setMaxValue(59);
            pickerValsSec = new String[60];
            for (i = 0; i < 60; i++) {
                pickerValsSec[i] = i + " s";
            }
            noPickerSec.setDisplayedValues(pickerValsSec);
        }
    }

    private void setMaxValuesMin() {
        int i;
        if (noPickerMin != null) {
            noPickerMin.setMinValue(0);
            noPickerMin.setMaxValue(60);
            pickerValsMin = new String[61];
            for (i = 0; i < 61; i++) {
                pickerValsMin[i] = i + " m";
            }
            noPickerMin.setDisplayedValues(pickerValsMin);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.numberPickerSecs:
                if (noPickerSec != null) {
                    secPicked = noPickerSec.getValue();
                    remainingTimeSec.setText(String.valueOf(secPicked));
                }
                break;
            case R.id.numberPickerMins:
                if (noPickerMin != null) {
                    minPicked = noPickerMin.getValue();
                    remainingTimeMin.setText(String.valueOf(minPicked));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) { // match id of toggled button in group inside switch case
            case R.id.toggleButton:
            case R.id.playButton:
            case R.id.pauseButton:
            case R.id.stopButton:
                int toggledButton = toggleGroup.getCheckedButtonId();

                if (toggledButton == R.id.playButton) {
                    startTimer();
                }

                else if (toggledButton == R.id.pauseButton) {
                    pauseTimer();
                }

                else { // if togglebutton is stop button
                    stopTimer();
                }
                break;
        }
    }

    private Long milliSecondConverter(int minPicked, int secPicked) {
            long lMinPicked = (long) minPicked * 60000; // minutes to milliseconds
            long lSecPicked = (long) secPicked * 1000; // seconds to milliseconds
            return timePicked = lMinPicked + lSecPicked;
    }

    private void startTimer() {
            userTimer = new CountDownTimer(milliSecondConverter(minPicked, secPicked), 1000) {
                public void onTick(long millisUntilFinished) {
                    minPicked = (int) millisUntilFinished /1000 / 60;
                    secPicked = (int) (millisUntilFinished /1000) % 60;
                    remainingTimeMin.setText(String.valueOf(minPicked));
                    remainingTimeSec.setText(String.valueOf(secPicked));
                    Log.e("TIMER", "TIMER TICKING");
                }

                public void onFinish() {
                    timerText.setVisibility(View.VISIBLE);
                    timerText.setText(getString(R.string.timerDone));
                    Log.e("TIMER", "TIMER FINISHED");
                }
            }.start();
    }

    private void pauseTimer() {
        if (userTimer != null) {
            userTimer.cancel();
        }
    }

    private void stopTimer() {
        if (userTimer != null) {
            userTimer.cancel();
            userTimer = null;
            // nollaa ajat
            remainingTimeSec.setText(getString(R.string.pickedTimeSec)); // set TextViews as zeroes
            remainingTimeMin.setText(getString(R.string.pickedTimeMin));
        }
    }

    private String formatTime(double time) {
        return formattedTime = '0' + String.valueOf(time);
    }

    /*public void countDown() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerText.setText("done!");
            }
        }.start();
    }*/
}