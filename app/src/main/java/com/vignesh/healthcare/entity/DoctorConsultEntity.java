package com.vignesh.healthcare.entity;

import java.util.Comparator;

public class DoctorConsultEntity implements Comparable<DoctorConsultEntity>{
    private String user_name;
    private long user_contact;
    private String user_age;
    private String user_gender;
    private String reason;
    private String consult_date;
    private String consult_time;
    private long milli_second;

    public String getUser_name() {
        return user_name;
    }

    public long getUser_contact() {
        return user_contact;
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

    public String getConsult_date() {
        return consult_date;
    }

    public String getConsult_time() {
        return consult_time;
    }

    public long getMilli_second() {
        return milli_second;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_contact(long user_contact) {
        this.user_contact = user_contact;
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

    public void setConsult_date(String consult_date) {
        this.consult_date = consult_date;
    }

    public void setConsult_time(String consult_time) {
        this.consult_time = consult_time;
    }

    public void setMilli_second(long milli_second) {
        this.milli_second = milli_second;
    }

    @Override
    public int compareTo(DoctorConsultEntity doctorConsultEntity) {
        return (int)(this.milli_second - doctorConsultEntity.getMilli_second());
    }
}
