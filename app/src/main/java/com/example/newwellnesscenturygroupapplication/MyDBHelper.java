package com.example.newwellnesscenturygroupapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDBHelper extends SQLiteOpenHelper {


    private SQLiteDatabase sqLiteDatabase;

    //------------- DATABASE NAME --------------\\
    private static final String DATABASE_NAME = "wellness.db";


    //------------- TABLE NAMES --------------\\
    private static final String PATIENT_TABLE ="patient_table";
    private static final String REPORT_TABLE ="report_table";


    //------------- Column name for PATIENT_TABLE --------------\\
    private static final String ID = "patient_ID";
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PHONE_NUMBER = "Phone_Number";
    private static final String DOB = "DOB";
    private static final String ADDRESS = "Address";
    private static final String MIN = "MIN";

    //SCRIPT FOR CREATING PATIENT_TABLE
    private static final String CREATE_PATIENT_TABLE = "CREATE TABLE " + PATIENT_TABLE + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " + EMAIL + " VARCHAR(50) UNIQUE, " + PHONE_NUMBER + " VARCHAR(50) UNIQUE, " + DOB + " VARCHAR(50), " + ADDRESS + " VARCHAR(125) , " + MIN + " VARCHAR(125))";


    //------------- Column name for REPORT_TABLE --------------\\
    private static final String report_ID = "report_ID";
    private static final String patient_ID = "patient_ID";
    private static final String DATE_CREATED = "Date_Created";
    private static final String DATE_MODIFIED = "Date_Modified";
    private static final String DETAILS = "Details";

    //SCRIPT FOR CREATING REPORT_TABLE
    private static final String CREATE_REPORT_TABLE = "CREATE TABLE " + REPORT_TABLE + "("+ report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + patient_ID + " INTEGER, " + DATE_CREATED + " DATE, " + DATE_MODIFIED + " DATE, " + DETAILS + " TEXT, FOREIGN KEY (" + patient_ID + ") REFERENCES " + PATIENT_TABLE + "(" + ID + "))";


    //------------- VERSION NUMBER --------------\\
    private static final int VERSION_NUMBER = 1;
    private Context context;


    //------------- SCRIPTS FOR DROPPING TABLES --------------\\
    private static final String DROP_TABLE_PATIENT = "DROP TABLE IF EXISTS " + PATIENT_TABLE;
    private static final String DROP_TABLE_REPORT = "DROP TABLE IF EXISTS " + REPORT_TABLE;

    //SQL SCRIPT FOR CREATING PATIENT_TABLE AND REPORT_TABLE ON UPGRADE
    private static final String CREATE_PATIENT_TABLE_UPD = "CREATE TABLE " + PATIENT_TABLE + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " + EMAIL + " VARCHAR(50) UNIQUE, " + PHONE_NUMBER + " VARCHAR(50) UNIQUE, " + DOB + " DATE, " + ADDRESS + " VARCHAR(125) , " + MIN + " VARCHAR(125))";
    private static final String CREATE_REPORT_TABLE_UPD = "CREATE TABLE " + REPORT_TABLE + "("+ report_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + patient_ID + " INTEGER, " + DATE_CREATED + " DATE, " + DATE_MODIFIED + " DATE, " + DETAILS + " TEXT, FOREIGN KEY (" + patient_ID + ") REFERENCES " + PATIENT_TABLE + "(" + ID + "))";

    //------------- OTHER SCRIPTS --------------\\
    private static final String SELECT_ALL = "SELECT * FROM " + PATIENT_TABLE;

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
        Toast toast;
        toast = Toast.makeText(this.context, message, Toast.LENGTH_LONG);
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

    public long insertTable(String name, String dob, String phone, String email, String address, String min)
    {
        try {

            ContentValues patientValues = new ContentValues();
            patientValues.put("Name", name);
            patientValues.put("Email", email);
            patientValues.put("Phone_Number", phone);
            patientValues.put("DOB", dob);
            patientValues.put("Address", address);
            patientValues.put("MIN", min);

            showToast(patientValues.toString());
            sqLiteDatabase = this.getWritableDatabase();
            long patientRowId = sqLiteDatabase.insert(PATIENT_TABLE, null, patientValues);

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
            showToast("Exception: " + e);
            Long exception = Long.parseLong(e.toString());
            return exception;
        }
    }

    public Cursor displayAllData()
    {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);
        return cursor;
    }
}
