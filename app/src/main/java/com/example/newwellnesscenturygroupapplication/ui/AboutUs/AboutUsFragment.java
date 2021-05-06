package com.example.newwellnesscenturygroupapplication.ui.AboutUs;

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

public class AboutUsFragment extends Fragment {

    private AbousUsViewModel abousUsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        abousUsViewModel =
                new ViewModelProvider(this).get(AbousUsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        abousUsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}