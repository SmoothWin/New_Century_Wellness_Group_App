package com.example.newwellnesscenturygroupapplication.da;

import java.sql.Date;

public class Report {

    private int reportId;
    private int patientId;
    private String dateCreated;
    private String dateModified;
    private String details;

    public Report() {
    }

    public Report(int reportId, int patientId, String dateCreated, String dateModified, String details) {
        this.reportId = reportId;
        this.patientId = patientId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.details = details;
    }

    public Report(int reportId, int patientId, String details) {
        this.reportId = reportId;
        this.patientId = patientId;
        this.details = details;
    }

    public Report(int patientId, String details) {
        this.patientId = patientId;
        this.details = details;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
