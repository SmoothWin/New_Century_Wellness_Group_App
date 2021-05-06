package com.example.newwellnesscenturygroupapplication.ui.AboutUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbousUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbousUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("About Us");
    }

    public LiveData<String> getText() {
        return mText;
    }
}