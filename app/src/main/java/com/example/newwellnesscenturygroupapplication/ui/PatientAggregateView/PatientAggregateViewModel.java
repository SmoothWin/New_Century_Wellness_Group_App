package com.example.newwellnesscenturygroupapplication.ui.PatientAggregateView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PatientAggregateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PatientAggregateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aggregate Patient");


    }

    public LiveData<String> getText() {
        return mText;
    }

}
