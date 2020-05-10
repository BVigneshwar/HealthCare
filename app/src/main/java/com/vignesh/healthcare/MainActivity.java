package com.vignesh.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vignesh.healthcare.common.LoginFragment;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.UserConsultEntity;
import com.vignesh.healthcare.entity.UserEntity;

public class MainActivity extends AppCompatActivity{
    DoctorEntity doctorEntity;
    UserEntity userEntity;
    DoctorConsultEntity doctorConsultEntity;
    UserConsultEntity userConsultEntity;
    String reason;
    long user_contact, doctor_contact;
    String speciality;
    long consult_milli_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new LoginFragment()).commit();
    }

    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setDoctorConsultEntity(DoctorConsultEntity doctorConsultEntity) {
        this.doctorConsultEntity = doctorConsultEntity;
    }

    public void setUserConsultEntity(UserConsultEntity userConsultEntity) {
        this.userConsultEntity = userConsultEntity;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setUser_contact(long user_contact) {
        this.user_contact = user_contact;
    }

    public void setDoctor_contact(long doctor_contact) {
        this.doctor_contact = doctor_contact;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setConsult_milli_second(long consult_milli_second) {
        this.consult_milli_second = consult_milli_second;
    }

    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public DoctorConsultEntity getDoctorConsultEntity() {
        return doctorConsultEntity;
    }

    public UserConsultEntity getUserConsultEntity() {
        return userConsultEntity;
    }

    public String getReason() {
        if(reason == null){
            return getString(R.string.general_checkup);
        }else{
            return reason;
        }
    }

    public long getUser_contact() {
        return user_contact;
    }

    public long getDoctor_contact() {
        return doctor_contact;
    }

    public String getSpeciality() {
        return speciality;
    }

    public long getConsult_milli_second() {
        return consult_milli_second;
    }
}
