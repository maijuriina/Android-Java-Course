package com.example.testapplication.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    private NumberPicker noPickerMin;
    private NumberPicker noPickerSec;
    private TextView remainingTimeSec;
    private TextView remainingTimeMin;
    private int minPicked;
    private int secPicked;
    private MaterialButtonToggleGroup toggleGroup;
    private MaterialButton playButton;
    private MaterialButton pauseButton;
    private MaterialButton stopButton;
    private TextView timerText;
    private CountDownTimer userTimer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        noPickerMin = root.findViewById(R.id.numberPickerMins); // numberPickers introduced
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

    private void setMaxValuesSec() { // sets min and max-values for second numberPicker
        int i;
        if (noPickerSec != null) {
            noPickerSec.setMinValue(0);
            noPickerSec.setMaxValue(59);
            String[] pickerValsSec = new String[60]; // local array for 59 seconds
            for (i = 0; i < 60; i++) {
                pickerValsSec[i] = i + " s"; // add second's " s" to the end of i, e.g. 5 s
            }
            noPickerSec.setDisplayedValues(pickerValsSec);
        }
    }

    private void setMaxValuesMin() { // sets min and max-values for minute numberPicker
        int i;
        if (noPickerMin != null) {
            noPickerMin.setMinValue(0);
            noPickerMin.setMaxValue(60);
            String[] pickerValsMin = new String[61]; // local array for 60 minutes
            for (i = 0; i < 61; i++) {
                pickerValsMin[i] = i + " m"; // add minute's " m" to the end of i, e.g. 5 m
            }
            noPickerMin.setDisplayedValues(pickerValsMin);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) { // checks every value change on both sec and min numberPickers and sets it on counter
        switch (picker.getId()) {
            case R.id.numberPickerSecs:
                if (noPickerSec != null) {
                    secPicked = noPickerSec.getValue();
                    remainingTimeSec.setText(String.valueOf(formatTime(secPicked)));
                }
                break;
            case R.id.numberPickerMins:
                if (noPickerMin != null) {
                    minPicked = noPickerMin.getValue();
                    remainingTimeMin.setText(String.valueOf(formatTime(minPicked)));
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

                else { // if toggleButton == R.id.stopButton
                    stopTimer();
                }
                break;
        }
    }

    private Long milliSecondConverter(int minPicked, int secPicked) { // converts minutes and seconds into milliseconds to be used by the CountDownTimer
            long lMinPicked = (long) minPicked * 60000; // minutes to milliseconds
            long lSecPicked = (long) secPicked * 1000; // seconds to milliseconds
        long timePicked; // local variable for combination of values
        return timePicked = lMinPicked + lSecPicked;
    }

    private void startTimer() {
        if (minPicked==0 & secPicked==0) { // check that timer has values set in at least one field with non-short-circuit evaluation "&"
            timerAnimate(getString(R.string.cannotTime)); // notify user of missing time
        } else {
            userTimer = new CountDownTimer(milliSecondConverter(minPicked, secPicked), 1000) {
                public void onTick(long millisUntilFinished) {
                    minPicked = (int) millisUntilFinished / 1000 / 60;
                    secPicked = (int) (millisUntilFinished / 1000) % 60;
                    remainingTimeMin.setText(String.valueOf(formatTime(minPicked))); // updates the TextView with the running times every second
                    remainingTimeSec.setText(String.valueOf(formatTime(secPicked)));
                }

                public void onFinish() {
                    try {
                        timerAnimate(getString(R.string.timerDone)); // send timer done message to timerAnimate-function
                        playAlarm();
                        resetAllValues(0, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void pauseTimer() {
        if (userTimer != null) {
            userTimer.cancel();
        } else {
            pauseButton.setChecked(false); // reset pause-button selection
        }
    }

    private void stopTimer() {
        if (userTimer != null) {
            userTimer.cancel();
            userTimer = null;
            resetAllValues(0, 0);
        }
        stopButton.setChecked(false); // reset stop-button selection
    }

    private void resetAllValues(int valueSec, int valueMin) {
        noPickerSec.setValue(valueSec); // set picker values back to given value
        noPickerMin.setValue(valueMin);
        secPicked = valueSec; // set parameter values for userTimer to given value
        minPicked = valueMin;
        remainingTimeSec.setText(getString(R.string.pickedTimeSec)); // set TextViews that count time down as zeroes
        remainingTimeMin.setText(getString(R.string.pickedTimeMin));
    }

    private void timerAnimate(String message) { // message is given to animator and displays that message
        Animation timesUpAnimationFade = AnimationUtils.loadAnimation(getContext(), R.anim.fadeanimationend); // introduce fade animation
        timerText.setVisibility(View.VISIBLE);
        timerText.setText(message);
        timerText.startAnimation(timesUpAnimationFade);
        playButton.setChecked(false); // reset play-button selection
    }

    private void playAlarm() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // get notification ringtone ready for use
        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
        r.play();
    }

    private String formatTime(int time) {  // adds zeroes to all displays of less than 10 in time
        String formattedTime; // local variable for formatting the time
        if (time < 10) {
            return formattedTime = '0' + String.valueOf(time);
        } else {
            return formattedTime = String.valueOf(time);
        }
    }
}