package com.example.testapplication.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    public DashboardViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

// --Commented out by Inspection START (9.12.2020 11.40):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.40)
}