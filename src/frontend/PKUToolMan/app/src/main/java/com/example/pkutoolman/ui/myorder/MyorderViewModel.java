package com.example.pkutoolman.ui.myorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyorderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyorderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is myorder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}