package com.example.newwellnesscenturygroupapplication.ui.AddReportView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddReportViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddReportViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add new report");
    }

    public LiveData<String> getText() {
        return mText;
    }
}