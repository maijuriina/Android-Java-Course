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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.w3c.dom.Text;

import java.util.function.IntToLongFunction;

public class NotificationsFragment extends Fragment implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    private TextClock textClock;
    private NumberPicker noPickerMin;
    private NumberPicker noPickerSec;
    private String[] pickerValsSec;
    private String[] pickerValsMin;
    private TextView pickedTimeSec;
    private TextView pickedTimeMin;
    private int minPicked;
    private int secPicked;
    private long timePicked;
    private MaterialButtonToggleGroup toggleGroup;
    private Button startPauseButton;
    private Button stopButton;
    private TextView timerText;
    private CountDownTimer userTimer;
    private long millisUntilFinished;
    private String formattedTime;
    private boolean clicked = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        noPickerMin = root.findViewById(R.id.numberPickerMins); // numberpickers introduced
        noPickerSec = root.findViewById(R.id.numberPickerSecs);
        pickedTimeSec = root.findViewById(R.id.pickedTimeSec); // these are for displaying the selected times
        pickedTimeMin = root.findViewById(R.id.pickedTimeMin);
        toggleGroup = root.findViewById(R.id.toggleButton);
        startPauseButton = root.findViewById(R.id.startPauseButton); // pair the buttons
        stopButton = root.findViewById(R.id.stopButton);
        timerText = root.findViewById(R.id.timerText);
        setMaxValuesSec();
        setMaxValuesMin();
        noPickerSec.setOnValueChangedListener(this);
        noPickerMin.setOnValueChangedListener(this);
        toggleGroup.setOnClickListener(this);
        startPauseButton.setOnClickListener(this);
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
                    pickedTimeSec.setText(String.valueOf(secPicked));
                }
                break;
            case R.id.numberPickerMins:
                if (noPickerMin != null) {
                    minPicked = noPickerMin.getValue();
                    pickedTimeMin.setText(String.valueOf(minPicked));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggleButton:
            case R.id.startPauseButton:
            case R.id.stopButton:
                int toggledButton = toggleGroup.getCheckedButtonId();
                if (toggledButton == R.id.startPauseButton) {
                    if(!clicked) {
                        startPauseButton.setText(getString(R.string.pauseTimer));
                        clicked = true;
                        startTimer();
                    } else {
                        startPauseButton.setText(getString(R.string.continueTimer));
                        pauseTimer();
                    }
                }
                else {
                    startPauseButton.setText(getString(R.string.startTimer));
                    stopTimer();
                }
                break;
        }
    }

    private void startTimer() {
        long lMinPicked = (long) minPicked * 60000; // minutes to milliseconds
        long lSecPicked = (long) secPicked * 1000; // seconds to milliseconds
        timePicked = lMinPicked + lSecPicked;
        userTimer = new CountDownTimer(timePicked, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setVisibility(View.VISIBLE);
                pickedTimeMin.setText(String.valueOf((millisUntilFinished/1000)/60));
                pickedTimeSec.setText(String.valueOf((millisUntilFinished/1000)%60));
                // timerText.setText(getString(R.string.timerRunning) + millisUntilFinished / 1000);
                Log.e("TIMER", "TIMER TICKING");
            }
            public void onFinish() {
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
        }
        pickedTimeSec.setText(getString(R.string.pickedTimeSec));
        pickedTimeSec.setText(getString(R.string.pickedTimeMin));
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