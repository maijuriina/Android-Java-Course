package com.example.testapplication.ui.notifications;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    public NotificationsViewModel() {
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

// --Commented out by Inspection START (9.12.2020 11.41):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.41)
}