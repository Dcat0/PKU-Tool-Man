package com.example.pkutoolman.ui.myinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyinfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyinfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is myinfo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}