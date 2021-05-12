package com.example.newwellnesscenturygroupapplication.ui.PatientAggregateView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.newwellnesscenturygroupapplication.R;
import com.example.newwellnesscenturygroupapplication.da.MyDBHelper;
import com.example.newwellnesscenturygroupapplication.da.Report;
import com.example.newwellnesscenturygroupapplication.ui.Home.HomeFragment;
import com.example.newwellnesscenturygroupapplication.ui.Home.HomeViewModel;



public class PatientAggregateFragment extends Fragment {

    //------------- Database parameters --------------\\
    private MyDBHelper myDBHelper;
    private SQLiteDatabase sqLiteDatabase;


    private TextView patientIdEditText,
            patientNameEditText,
            reportIdEditText,
            reportDetailsEditText,
            dModifiedEditText,
            dCreatedEditTExt;

    private int pId;
    private String pName;
    private int rId;
    private String details;
    private String dModified;
    private String dCreated;

    private Button updateReportButton, deleteReportButton;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_patient_aggregate, container, false);

        //------------- Set the database connection --------------\\
        myDBHelper = new MyDBHelper(this.getContext());
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        //myDBHelper.onUpgrade(sqLiteDatabase, 1, 2);

        patientIdEditText = root.findViewById(R.id.patientID_edt);
        patientNameEditText = root.findViewById(R.id.name_edt);
        reportIdEditText = root.findViewById(R.id.reportId_edt);
        reportDetailsEditText = root.findViewById(R.id.details_edt);
        dModifiedEditText = root.findViewById(R.id.dateModified_edt);
        dCreatedEditTExt = root.findViewById(R.id.dateCreated_edt);

        updateReportButton = root.findViewById(R.id.updateReportButton);
        deleteReportButton = root.findViewById(R.id.deleteReportButton);


        Bundle bundle = this.getArguments();
        if(bundle != null){
            pId = bundle.getInt("pId");
            pName = bundle.getString("pName");
            rId = bundle.getInt("rId");
            details = bundle.getString("details");
            dModified = bundle.getString("dModified");
            dCreated = bundle.getString("dCreated");
        }

        patientIdEditText.setText(String.valueOf(pId));
        patientNameEditText.setText(pName);
        reportIdEditText.setText(String.valueOf(rId));
        reportDetailsEditText.setText(details);
        dModifiedEditText.setText(dModified);
        dCreatedEditTExt.setText(dCreated);

        updateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("PatientAggregate : updateClick");
                Report report = new Report(rId, pId, reportDetailsEditText.getText().toString());

                int affectedRow = myDBHelper.updateReport(report);

                if(affectedRow != -1){
                    showToast("Sucessfully updated Report with reportId: " + affectedRow);

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction().replace(R.id.nav_host_fragment,
                            homeFragment, homeFragment.getTag())
                            .commit();
                }
                else{
                    showToast("An error occured while trying to update the report.");
                }

            }
        });

        deleteReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("PatientAggregate : deleteClick : notImplemented");


            }
        });

        return root;

    }


    @Override
    public void onResume() {
        //showToast(" PatientAggregate: onResume");
        super.onResume();
    }





    private void showToast(String message)
    {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
