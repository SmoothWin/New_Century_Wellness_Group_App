package com.example.newwellnesscenturygroupapplication.ui.AddPatientView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPatientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddPatientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add new patient");
    }

    public LiveData<String> getText() {
        return mText;
    }
}