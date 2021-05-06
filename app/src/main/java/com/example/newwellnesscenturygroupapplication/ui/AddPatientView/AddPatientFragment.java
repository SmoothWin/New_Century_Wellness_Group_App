package com.example.newwellnesscenturygroupapplication.ui.AddPatientView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newwellnesscenturygroupapplication.R;

public class AddPatientFragment extends Fragment {

    private AddPatientViewModel AddPatientViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AddPatientViewModel =
                new ViewModelProvider(this).get(AddPatientViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addnewpatient, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        AddPatientViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}