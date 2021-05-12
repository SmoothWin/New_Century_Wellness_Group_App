package com.example.newwellnesscenturygroupapplication.ui.AddPatientView;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.newwellnesscenturygroupapplication.ui.AddReportView.AddReportFragment;

import java.sql.Date;

public class AddPatientFragment extends Fragment {


    //------------- Database parameters --------------\\
    private MyDBHelper myDBHelper;
    private SQLiteDatabase sqLiteDatabase;



    //------------- EditText parameters --------------\\
    EditText nameEdt,
            phoneEdt,
            dobEdt,
            emailEdt,
            addressEdt,
            minEdt;


    //------------- Button parameters --------------\\
    Button addPatient, selectDateButton;

    private DatePickerDialog datePickerDialog;

    Toast toast;

    private AddPatientViewModel AddPatientViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AddPatientViewModel = new ViewModelProvider(this).get(AddPatientViewModel.class);



        //------------- Get the root link --------------\\
        View root = inflater.inflate(R.layout.fragment_addnewpatient, container, false);

        //------------- Get the TextView --------------\\
        final TextView textView = root.findViewById(R.id.text_gallery);

        //------------- Set the database connection --------------\\
        myDBHelper = new MyDBHelper(this.getContext());
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        //myDBHelper.onUpgrade(sqLiteDatabase, 1, 2);


        //------------- Set the EditText --------------\\
        nameEdt = root.findViewById(R.id.name_edt);
        phoneEdt = root.findViewById(R.id.phone_edt);
        dobEdt = root.findViewById(R.id.dateOfBirthEdt);
        emailEdt = root.findViewById(R.id.email_edt);
        addressEdt = root.findViewById(R.id.address_edt);
        minEdt = root.findViewById(R.id.min_edt);

        //------------- Set the button --------------\\
        addPatient = root.findViewById(R.id.add_patient);
        selectDateButton = root.findViewById(R.id.selectDateButton);

        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = nameEdt.getText().toString();
                String pEmail = emailEdt.getText().toString();
                String pPhone = phoneEdt.getText().toString();
                Date pDob = Date.valueOf(dobEdt.getText().toString());
                String pAddress = addressEdt.getText().toString();
                String pMin = minEdt.getText().toString();

                Patient patient = new Patient(pName, pEmail, pPhone, pDob, pAddress, pMin);

                int pId = myDBHelper.createPatient(pName, pDob.toString(), pPhone, pEmail, pAddress, pMin);



                if(pId != -1){
                    clearInputs();
                    //textView.setText("69");

                    Report report = new Report(pId, "");

                    myDBHelper.createReport(report);

                    AddReportFragment addReportFragment = new AddReportFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pId", pId);
                    addReportFragment.setArguments(bundle);
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction().replace(R.id.nav_host_fragment, addReportFragment, addReportFragment.getTag())
                            .commit();
                }
            }
        });

        dobEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePicker datePicker = new DatePicker(getContext());
                    int currentDay = datePicker.getDayOfMonth();
                    int currentMonth = datePicker.getMonth();
                    int currentYear = datePicker.getYear();

                    datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                    dobEdt.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                }
                            }, currentYear, currentMonth, currentDay);

                    datePickerDialog.show();
                }
            }
        });



        AddPatientViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    private void clearInputs(){
        nameEdt.setText("");
        dobEdt.setText("");
        phoneEdt.setText("");
        emailEdt.setText("");
        addressEdt.setText("");
        minEdt.setText("");
    }

    private void showToast(String message)
    {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }



}