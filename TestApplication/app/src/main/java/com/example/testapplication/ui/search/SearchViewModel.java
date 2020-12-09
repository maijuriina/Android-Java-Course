package com.example.testapplication.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

// --Commented out by Inspection START (9.12.2020 11.41):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.41)
}