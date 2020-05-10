package com.vignesh.healthcare.entity;

public class UserConsultEntity implements Comparable<UserConsultEntity> {
    private String doctor_name;
    private long doctor_contact;
    private String user_age;
    private String user_gender;
    private String reason;
    private String doctor_speciality;
    private String consult_date;
    private String consult_time;
    private long milli_second;
    private PrescriptionEntity prescription;

    public String getDoctor_name() {
        return doctor_name;
    }

    public long getDoctor_contact() {
        return doctor_contact;
    }

    public String getUser_age() {
        return user_age;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getReason() {
        return reason;
    }

    public String getDoctor_speciality() {
        return doctor_speciality;
    }

    public String getConsult_date() {
        return consult_date;
    }

    public String getConsult_time() {
        return consult_time;
    }

    public long getMilli_second() {
        return milli_second;
    }

    public PrescriptionEntity getPrescription() {
        return prescription;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setDoctor_contact(long doctor_contact) {
        this.doctor_contact = doctor_contact;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDoctor_speciality(String doctor_speciality) {
        this.doctor_speciality = doctor_speciality;
    }

    public void setConsult_date(String consult_date) {
        this.consult_date = consult_date;
    }

    public void setConsult_time(String consult_time) {
        this.consult_time = consult_time;
    }

    public void setMilli_second(long milli_second) {
        this.milli_second = milli_second;
    }

    public void setPrescription(PrescriptionEntity prescription) {
        this.prescription = prescription;
    }

    @Override
    public int compareTo(UserConsultEntity o) {
        return (int)(this.milli_second - o.getMilli_second());
    }
}
