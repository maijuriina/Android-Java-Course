package com.example.testapplication.ui.notifications;

import android.os.Bundle;
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
    TextClock textClock;
    NumberPicker noPickerHour;
    NumberPicker noPickerMin;
    int hourPicked;
    int minPicked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        TextClock textClock = root.findViewById(R.id.textClock); // connecting xml counterparts
        NumberPicker noPicker = root.findViewById(R.id.numberPicker);
        noPicker.setMinValue(0);
        noPicker.setMaxValue(59);
        return root;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    }

    /*public void setMaxValues() {
        if(noPicker != null) {
            if (check24hSettings()) {
                noPicker.setMinValue(1);
                noPicker.setMaxValue(1);
                pickerVals = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
                noPicker.setDisplayedValues(pickerVals);
            } else {
                noPicker.setMinValue(1);
                noPicker.setMaxValue(1);
                pickerVals = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                noPicker.setDisplayedValues(pickerVals);
            }
        }
    }*/

    /*public boolean check24hSettings() {
        if(textClock != null) {
            if (textClock.is24HourModeEnabled()) {
                return true;
            }
            return false;
        } return false;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {

    }
    */
}