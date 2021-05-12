package com.example.newwellnesscenturygroupapplication.ui.Home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newwellnesscenturygroupapplication.da.MyDBHelper;
import com.example.newwellnesscenturygroupapplication.R;
import com.example.newwellnesscenturygroupapplication.da.Patient;
import com.example.newwellnesscenturygroupapplication.da.Report;
import com.example.newwellnesscenturygroupapplication.ui.PatientAggregateView.PatientAggregateFragment;

import java.sql.Date;
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
        //myDBHelper.onUpgrade(sqLiteDatabase, 1, 2);


        //------------- Setup ArrayList --------------\\

        PatientList = new ArrayList<String>();
        Cursor resultSet = myDBHelper.getAllPatients();
        if (resultSet.getCount()==0){
            showToast("Called getAllPatients() got 0 results");

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientAggregateFragment patientAggregateFragment = new PatientAggregateFragment();

                String patientIdStr = (String)parent.getItemAtPosition(position);
                int patientId = Integer.valueOf(patientIdStr.substring(0, patientIdStr.indexOf('|') - 1));
                //showToast(String.valueOf(patientId));

                Patient patient = myDBHelper.getPatient(patientId);
                Report report  = myDBHelper.getReport(patientId);

                Bundle bundle = new Bundle();
                bundle.putInt("pId", patient.getPatientId());
                bundle.putString("pName", patient.getName());
                bundle.putString("pDob", patient.getDateOfBirth().toString());
                bundle.putString("dModified", report.getDateModified().toString());
                bundle.putString("dCreated", report.getDateCreated().toString());
                bundle.putString("details", report.getDetails());
                bundle.putInt("rId", report.getReportId());

                patientAggregateFragment.setArguments(bundle);
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,
                        patientAggregateFragment, patientAggregateFragment.getTag())
                        .commit();

            }
        });

        return root;
    }


    @Override
    public void onResume() {
        //showToast("Home : onResume");
        super.onResume();
    }

    @Override
    public void onDestroy() {

        myDBHelper.close();
        super.onDestroy();
    }


    private void showToast(String message)
    {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}