package com.example.newwellnesscenturygroupapplication.ui.Home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newwellnesscenturygroupapplication.MyDBHelper;
import com.example.newwellnesscenturygroupapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //------------- Database parameters --------------\\
    private MyDBHelper myDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ListView listView;
    //private String[] CarNames;
    private List<String> PatientList;
    private SearchView searchView;
    Toast toast;
    TextView textView;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState)
    {


        //Populate the country names from strings.xml file
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home2);
        searchView = root.findViewById(R.id.searchViewID);
        listView = root.findViewById(R.id.list_view);
        //CarNames = getResources().getStringArray(R.array.car_names);

        //------------- Set the database connection --------------\\
        myDBHelper = new MyDBHelper(this.getContext());
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        myDBHelper.onUpgrade(sqLiteDatabase, 1, 2);


        //------------- Setup ArrayList --------------\\

        PatientList = new ArrayList<String>();
        Cursor resultSet = myDBHelper.displayAllData();
        if (resultSet.getCount()==0){
            showToast("Error");

        }
        else
        {
            String patientName_ID;

            while(resultSet.moveToNext())
            {
                patientName_ID = resultSet.getString(0) + " | " + resultSet.getString(1);
                PatientList.add(patientName_ID);


            }

            textView.setText("Yeetus");
        }





        //Declare ArrayAdapter for String data with destination, intermediate buffer, source
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>
                        (this.getContext(), R.layout.sample_view, R.id.textViewID, PatientList);
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



    private void showToast(String message)
    {
        toast = Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
    }
}