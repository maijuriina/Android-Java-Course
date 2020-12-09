package com.example.testapplication.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    public SearchViewModel() {
        // TODO: Implement the ViewModel
        MutableLiveData<String> mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

// --Commented out by Inspection START (9.12.2020 11.41):
//    public LiveData<String> getText() {
//        return mText;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.41)
}