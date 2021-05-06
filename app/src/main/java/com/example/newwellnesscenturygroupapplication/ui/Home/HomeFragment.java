package com.example.newwellnesscenturygroupapplication.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.newwellnesscenturygroupapplication.R;

public class HomeFragment extends Fragment {

    private ListView listView;
    private String[] CarNames;
    private SearchView searchView;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState)
    {


        //Populate the country names from strings.xml file
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home2);
        searchView = root.findViewById(R.id.searchViewID);
        listView = root.findViewById(R.id.list_view);
        CarNames = getResources().getStringArray(R.array.car_names);

        //Declare ArrayAdapter for String data with destination, intermediate buffer, source
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>
                        (this.getContext(), R.layout.sample_view, R.id.textViewID, CarNames);
        //Attaching the adapter to the ListView for processing
        listView.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });
        return root;
    }
}