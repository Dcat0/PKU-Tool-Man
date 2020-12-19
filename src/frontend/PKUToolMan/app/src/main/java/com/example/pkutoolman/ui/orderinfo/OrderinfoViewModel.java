package com.example.pkutoolman.ui.orderinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderinfoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public OrderinfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is orderinfo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
