package com.example.testapplication.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public HomeViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

// --Commented out by Inspection START (9.12.2020 11.41):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.41)
}