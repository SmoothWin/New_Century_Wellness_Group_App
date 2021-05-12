package com.example.newwellnesscenturygroupapplication.ui.AddReportView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.newwellnesscenturygroupapplication.R;
import com.example.newwellnesscenturygroupapplication.da.MyDBHelper;
import com.example.newwellnesscenturygroupapplication.da.Report;
import com.example.newwellnesscenturygroupapplication.ui.Home.HomeFragment;

public class AddReportFragment extends Fragment {


    //------------- Database parameters --------------\\
    private MyDBHelper myDBHelper;

    //------------- EditText parameters --------------\\
    EditText patientIdEditText, detailsEditText;
    Button createReportButton;


    private int patientId = 0;
    private String reportDetails;
    private int reportId = 0;

    private AddReportViewModel AddReportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AddReportViewModel = new ViewModelProvider(this).get(AddReportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addnewreport, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        patientIdEditText = root.findViewById(R.id.patientID_edt);
        detailsEditText = root.findViewById(R.id.details_edt);

        createReportButton = root.findViewById(R.id.add_report);

        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportDetails = detailsEditText.getText().toString();
                Report report = new Report(patientId,reportDetails);
                reportId = myDBHelper.updateReport(report);

                if(reportId != -1){
                    showToast("New report created with reportId: " + reportId + " for patientId: " + patientId);

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction().replace(R.id.nav_host_fragment,
                            homeFragment, homeFragment.getTag())
                            .commit();

                }
                else{
                    showToast("An error occurred while creating the report");
                }
            }
        });

        myDBHelper = new MyDBHelper(this.getContext());

        //showToast("AddReport : onCreate");


        Bundle bundle = this.getArguments();
        if(bundle != null) {
            patientId = bundle.getInt("pId");
        }



        patientIdEditText.setText(String.valueOf(patientId));



        AddReportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return root;
    }

    private void showToast(String message)
    {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }


}