package com.vignesh.healthcare.entity;

import android.content.Context;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.vignesh.healthcare.R;

public class DoctorEntity {
    private String name;
    private long contact;
    private String email;
    private String address;
    private String qualification;
    private String specialist;
    private String password;

    public String getName() {
        return name;
    }

    public long getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getQualification() {
        return qualification;
    }

    public String getSpecialist() {
        return specialist;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Need To change
    private String getDisplayNameForField(Context context, String field_name){
        return "name";
    }

    public boolean validateAndSetName(String name){
        return true;
    }

}
