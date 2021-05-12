package com.example.newwellnesscenturygroupapplication.ui.AddPatientView;

import android.database.sqlite.SQLiteDatabase;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newwellnesscenturygroupapplication.MyDBHelper;
import com.example.newwellnesscenturygroupapplication.R;

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
    Button addPatient;

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
        myDBHelper.onUpgrade(sqLiteDatabase, 1, 2);


        //------------- Set the EditText --------------\\
        nameEdt = root.findViewById(R.id.name_edt);
        phoneEdt = root.findViewById(R.id.phone_edt);
        dobEdt = root.findViewById(R.id.dob_edt);
        emailEdt = root.findViewById(R.id.email_edt);
        addressEdt = root.findViewById(R.id.address_edt);
        minEdt = root.findViewById(R.id.min_edt);

        //------------- Set the button --------------\\
        addPatient = root.findViewById(R.id.add_patient);




        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pName = nameEdt.getText().toString();
                String pDob = dobEdt.getText().toString();
                String pPhone = phoneEdt.getText().toString();
                String pEmail = emailEdt.getText().toString();
                String pAddress = addressEdt.getText().toString();
                String pMin = minEdt.getText().toString();

                myDBHelper.insertTable(pName, pDob, pPhone, pEmail, pAddress, pMin);

                textView.setText("69");

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

    private void showToast(String message)
    {
        toast = Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
    }



}