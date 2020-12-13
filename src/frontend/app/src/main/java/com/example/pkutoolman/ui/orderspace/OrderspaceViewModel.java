package com.example.pkutoolman.ui.orderspace;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderspaceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderspaceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is orderspace fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}