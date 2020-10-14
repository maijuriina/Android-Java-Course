package com.example.testapplication.ui.notifications;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;

public class NotificationsFragment extends Fragment implements NumberPicker.OnValueChangeListener {
    private TextClock textClock;
    private NumberPicker noPickerMin;
    private NumberPicker noPickerSec;
    private String[] pickerVals;
    private TextView pickedTimeSec;
    private TextView pickedTimeMin;
    private int minPicked;
    private int secPicked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        noPickerMin = root.findViewById(R.id.numberPickerMins); // numberpickers introduced
        noPickerSec = root.findViewById(R.id.numberPickerSecs);
        pickedTimeSec = root.findViewById(R.id.pickedTimeSec); // these are for displaying the selected times
        pickedTimeMin = root.findViewById(R.id.pickedTimeMin);
        setMaxValuesSec();
        setMaxValuesMin();
        noPickerSec.setOnValueChangedListener(this);
        noPickerMin.setOnValueChangedListener(this);


        return root;
    }

    public void setMaxValuesSec() {
        int i;
        if (noPickerSec != null) {
            noPickerSec.setMinValue(0);
            noPickerSec.setMaxValue(59);
            pickerVals = new String[60];
            for (i = 0; i < 60; i++) {
                pickerVals[i] = i + " s";
            }
            noPickerSec.setDisplayedValues(pickerVals);
        }
    }

    public void setMaxValuesMin() {
        int i;
        if (noPickerMin != null) {
            noPickerMin.setMinValue(0);
            noPickerMin.setMaxValue(60);
            pickerVals = new String[61];
            for (i = 0; i < 61; i++) {
                pickerVals[i] = i + " m";
            }
            noPickerMin.setDisplayedValues(pickerVals);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.numberPickerSecs:
                if(noPickerSec != null) {
                    secPicked = noPickerSec.getValue();
                    pickedTimeSec.setText(String.valueOf(secPicked));
                }
                break;
            case R.id.numberPickerMins:
                if(noPickerMin != null) {
                    minPicked = noPickerMin.getValue();
                    pickedTimeMin.setText(String.valueOf(minPicked));
                }
                break;
        }

    }

    /*public void countDown() {
        new CountDownTimer(30000, 1000) {
        public void onTick(long millisUntilFinished) {
            mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
        }
        public void onFinish() {
            mTextField.setText("done!");
        }
    }.start();*/
}