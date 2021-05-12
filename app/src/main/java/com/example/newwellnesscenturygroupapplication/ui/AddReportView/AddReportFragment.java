package com.example.newwellnesscenturygroupapplication.ui.AddReportView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.newwellnesscenturygroupapplication.R;

public class AddReportFragment extends Fragment {


    /*






    //-----------------------------------DEPRECATED--------------------------------------//








    */

    EditText editText;

    private AddReportViewModel AddReportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AddReportViewModel = new ViewModelProvider(this).get(AddReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addnewreport, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        editText = root.findViewById(R.id.details_edt);

        AddReportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}