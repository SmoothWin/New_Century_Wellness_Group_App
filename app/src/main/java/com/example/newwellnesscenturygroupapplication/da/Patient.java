package com.example.newwellnesscenturygroupapplication.da;

import java.sql.Date;

public class Patient {

    private int patientId;
    private String name;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String address;
    private String MIN;

    public Patient() {
    }

    public Patient(int patientId, String name, String email, String phoneNumber, Date dateOfBirth, String address, String MIN) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.MIN = MIN;
    }

    public Patient(String name, String email, String phoneNumber, Date dateOfBirth, String address, String MIN) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.MIN = MIN;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMIN() {
        return MIN;
    }

    public void setMIN(String MIN) {
        this.MIN = MIN;
    }
}
