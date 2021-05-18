package com.example.newwellnesscenturygroupapplication.da;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyDBHelper extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    //------------- DATABASE NAME --------------\\
    private static final String DATABASE_NAME = "wellness.db";


    //------------- TABLE NAMES --------------\\
    private static final String PATIENT_TABLE ="patient_table";
    private static final String REPORT_TABLE ="report_table";


    //------------- Column name for PATIENT_TABLE --------------\\
    private static final String patient_ID = "patient_ID";
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PHONE_NUMBER = "Phone_Number";
    private static final String DOB = "DOB";
    private static final String ADDRESS = "Address";
    private static final String MIN = "MIN";

    //SCRIPT FOR CREATING PATIENT_TABLE
    private static final String CREATE_PATIENT_TABLE = "CREATE TABLE " + PATIENT_TABLE + "("+ patient_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " + EMAIL + " VARCHAR(50) UNIQUE, " + PHONE_NUMBER + " VARCHAR(50) UNIQUE, " + DOB + " VARCHAR(50), " + ADDRESS + " VARCHAR(125) , " + MIN + " VARCHAR(125))";


    //------------- Column name for REPORT_TABLE --------------\\
    private static final String report_ID = "report_ID";
    private static final String DATE_CREATED = "Date_Created";
    private static final String DATE_MODIFIED = "Date_Modified";
    private static final String DETAILS = "Details";

    //SCRIPT FOR CREATING REPORT_TABLE
    private static final String CREATE_REPORT_TABLE = "CREATE TABLE " + REPORT_TABLE + "("+ report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + patient_ID + " INTEGER, " + DATE_CREATED + " DATE  DEFAULT (DATE('now')), " + DATE_MODIFIED + " DATE DEFAULT (DATE('now')), " + DETAILS + " TEXT, FOREIGN KEY (" + patient_ID + ") REFERENCES " + PATIENT_TABLE + "(" + patient_ID + "))";


    //------------- VERSION NUMBER --------------\\
    private static final int VERSION_NUMBER = 1;
    private Context context;


    //------------- SCRIPTS FOR DROPPING TABLES --------------\\
    private static final String DROP_TABLE_PATIENT = "DROP TABLE IF EXISTS " + PATIENT_TABLE;
    private static final String DROP_TABLE_REPORT = "DROP TABLE IF EXISTS " + REPORT_TABLE;

    //SQL SCRIPT FOR CREATING PATIENT_TABLE AND REPORT_TABLE ON UPGRADE
    private static final String CREATE_PATIENT_TABLE_UPD = "CREATE TABLE " + PATIENT_TABLE + "("+ patient_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " + EMAIL + " VARCHAR(50) UNIQUE, " + PHONE_NUMBER + " VARCHAR(50) UNIQUE, " + DOB + " DATE, " + ADDRESS + " VARCHAR(125) , " + MIN + " VARCHAR(125))";
    private static final String CREATE_REPORT_TABLE_UPD = "CREATE TABLE " + REPORT_TABLE + "("+ report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + patient_ID + " INTEGER UNIQUE, " + DATE_CREATED + " DATE DEFAULT (DATE('now')), " + DATE_MODIFIED + " DATE DEFAULT (DATE('now')), " + DETAILS + " TEXT, FOREIGN KEY (" + patient_ID + ") REFERENCES " + PATIENT_TABLE + "(" + patient_ID + "))";

    //------------- OTHER SCRIPTS --------------\\
    private static final String SELECT_ALL_PATIENTS = "SELECT * FROM " + PATIENT_TABLE;
    private static final String SELECT_PATIENT = "SELECT * FROM " + PATIENT_TABLE + " WHERE " + patient_ID + "=?";
    private static final String SELECT_REPORT_BY_PATIENT_ID = "SELECT * FROM " + REPORT_TABLE + " WHERE " + patient_ID + "=?";


    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;

    }

    @Override

    //Create table here
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            //Creates PATIENT_TABLE
            sqLiteDatabase.execSQL(CREATE_PATIENT_TABLE);
            showToast("PATIENT_TABLE created using onCreate()");


            //Creates REPORT_TABLE
            sqLiteDatabase.execSQL(CREATE_REPORT_TABLE);
            showToast("REPORT_TABLE created using onCreate()");



        //Table creation script without using String value
        /*
        sqLiteDatabase.execSQL("CREATE TABLE "
                + PATIENT_TABLE
                + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " + EMAIL + " VARCHAR(50) UNIQUE, " + PHONE_NUMBER + " VARCHAR(50) UNIQUE, " + DOB + " DATE, " + ADDRESS + " VARCHAR(125) , " + MIN + " VARCHAR(125))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + REPORT_TABLE
                + "("+ report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + patient_ID + " INTEGER, " + DATE_CREATED + " DATE, " + DATE_MODIFIED + " DATE, " + DETAILS + " TEXT, FOREIGN KEY (" + patient_ID + ") REFERENCES " + PATIENT_TABLE + "(" + ID + "))");
         */


        }
        catch (Exception e) {
            showToast("Exception: " + e);
        }
    }

    private void showToast(String message)
    {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            //Drop old table
            sqLiteDatabase.execSQL(DROP_TABLE_PATIENT);
            showToast("PATIENT_TABLE dropped");


            //Create new REPORT_TABLE
            sqLiteDatabase.execSQL(CREATE_PATIENT_TABLE_UPD);
            showToast("New PATIENT_TABLE created using onUpgrade()");
        }
        catch (Exception e) {
            showToast("Exception: " + e);
        }

        try {
            //Drop old table
            sqLiteDatabase.execSQL(DROP_TABLE_REPORT);
            showToast("REPORT_TABLE dropped");


            //Create new REPORT_TABLE
            sqLiteDatabase.execSQL(CREATE_REPORT_TABLE_UPD);
            showToast("New REPORT_TABLE created using onUpgrade()");
        }
        catch (Exception e) {
            showToast("Exception: " + e);
        }
    }

    public int createPatient(String name, String dob, String phone, String email, String address, String min)
    {
        try {

            ContentValues patientValues = new ContentValues();
            patientValues.put("Name", name);
            patientValues.put("Email", email);
            patientValues.put("Phone_Number", phone);
            patientValues.put("DOB", dob);
            patientValues.put("Address", address);
            patientValues.put("MIN", min);

            //showToast(patientValues.toString());
            sqLiteDatabase = this.getWritableDatabase();
            int patientRowId = (int)sqLiteDatabase.insert(PATIENT_TABLE, null, patientValues);

/*
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            ContentValues reportValues = new ContentValues();
            reportValues.put("Date_Created", date);
            reportValues.put("Date_Modified", date);
            long reportRowId = sqLiteDatabase.insert(REPORT_TABLE, null, reportValues);

            */


            return patientRowId;
        }
        catch (SQLException e)
        {

            showToast("SQL Exception caught: " + e.getMessage()); //for some reason does not show up????
            return -1;
        }
    }


    public int updatePatient(Patient patient){
        try{

            ContentValues patientValues = new ContentValues();
            patientValues.put(NAME, patient.getName());
            patientValues.put(EMAIL, patient.getEmail());
            patientValues.put(PHONE_NUMBER, patient.getPhoneNumber());
            patientValues.put(DOB, patient.getDateOfBirth().toString());
            patientValues.put(ADDRESS, patient.getAddress());
            patientValues.put(MIN, patient.getMIN());

            sqLiteDatabase = this.getWritableDatabase();

            int patientRowId = sqLiteDatabase.update(PATIENT_TABLE, patientValues, patient_ID + "=?", new String[] {String.valueOf(patient.getPatientId())});

            return patientRowId;

        }
        catch(SQLException e){
            showToast("SQL Exception caught: " + e.getMessage());
            return -1;
        }
    }

    public Cursor getAllPatients()
    {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_PATIENTS, null);
        return cursor;
    }

    public int createReport(Report report) {
        try {

            ContentValues reportValues = new ContentValues();
            reportValues.put(patient_ID, report.getPatientId());
            reportValues.put(DETAILS, report.getDetails());

            //showToast(reportValues.toString());
            sqLiteDatabase = this.getWritableDatabase();
            int reportRowId = (int)sqLiteDatabase.insert(REPORT_TABLE, null, reportValues);

/*
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            ContentValues reportValues = new ContentValues();
            reportValues.put("Date_Created", date);
            reportValues.put("Date_Modified", date);
            long reportRowId = sqLiteDatabase.insert(REPORT_TABLE, null, reportValues);

            */


            return reportRowId;
        }
        catch (SQLException e)
        {

            showToast("SQL Exception caught: " + e.getMessage()); //for some reason does not show up????
            return -1;
        }

    }

    public Patient getPatient(int patientId) {



        try{

            Patient patient = new Patient();
            sqLiteDatabase = this.getReadableDatabase();

            Cursor result = sqLiteDatabase.rawQuery(SELECT_PATIENT, new String[] {String.valueOf(patientId)});

            while(result.moveToNext()){
                patient = new Patient(result.getInt(result.getColumnIndex(patient_ID)),
                        result.getString(result.getColumnIndex(NAME)),
                        result.getString(result.getColumnIndex(EMAIL)),
                        result.getString(result.getColumnIndex(PHONE_NUMBER)) ,
                        result.getString(result.getColumnIndex(DOB)),
                        result.getString(result.getColumnIndex(ADDRESS)),
                        result.getString(result.getColumnIndex(MIN)));
            }


            return patient;


        }
        catch(SQLException e){
            showToast("SQL Exception caught: " + e.getMessage());
            return new Patient();
        }

    }

    public Report getReport(int patientId) {

        try{

            Report report = new Report();

            sqLiteDatabase = this.getReadableDatabase();

            Cursor result = sqLiteDatabase.rawQuery(SELECT_REPORT_BY_PATIENT_ID, new String[] {String.valueOf(patientId)});

            while (result.moveToNext()){
                report = new Report(result.getInt(result.getColumnIndex(report_ID)),
                        result.getInt(result.getColumnIndex(patient_ID)),
                        result.getString(result.getColumnIndex(DATE_CREATED)),
                        result.getString(result.getColumnIndex(DATE_MODIFIED)),
                        result.getString(result.getColumnIndex(DETAILS)));
            }



            return report;


        }
        catch(SQLException e){
            showToast("SQL Exception caught: " + e.getMessage());
            return new Report();
        }

    }

    public int updateReport(Report report) {

        try{

            long millis = System.currentTimeMillis();

            ContentValues reportValues = new ContentValues();
            reportValues.put(DETAILS, report.getDetails());
            reportValues.put(DATE_MODIFIED, String.valueOf(new Date(millis)));

            if(report.getDetails().length() == 0){
                reportValues.put(DATE_CREATED, String.valueOf(new Date(millis)));
            }

            sqLiteDatabase = this.getWritableDatabase();

            int reportRowId = sqLiteDatabase.update(REPORT_TABLE, reportValues, report_ID + "=?", new String[] {String.valueOf(report.getReportId())});

            return reportRowId;

        }
        catch(SQLException e){
            showToast("SQL Exception caught: " + e.getMessage());
            return -1;
        }

    }


}
