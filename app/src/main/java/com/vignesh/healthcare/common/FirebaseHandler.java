package com.vignesh.healthcare.common;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.PrescriptionEntity;
import com.vignesh.healthcare.entity.UserConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.entity.UserEntity;

public class FirebaseHandler {
    public interface FirebaseInterface{
        void onGetCallback(DataSnapshot dataSnapshot);
    }

    public static void getLoginDetails(String login_id, final FirebaseHandler.FirebaseInterface firebaseInterface){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/login/"+login_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseInterface.onGetCallback(dataSnapshot);
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setConsultationInDoctor(String doctor_speciality, long doctor_contact, long consult_millisecond, DoctorConsultEntity doctorConsultEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/doctor/"+doctor_speciality+"/"+doctor_contact+"/consult/"+consult_millisecond);
        databaseReference.setValue(doctorConsultEntity);
    }

    public static void setConsultationInUser(long user_contact, long consult_millisecond, UserConsultEntity userConsultEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/user/"+user_contact+"/consult/"+consult_millisecond);
        databaseReference.setValue(userConsultEntity);
    }

    public static void getSpecialityList(String speciality, final FirebaseInterface firebaseInterface){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/doctor/"+speciality);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseInterface.onGetCallback(dataSnapshot);
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getUserDetails(long user_contact, final FirebaseInterface firebaseInterface){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/user/"+user_contact);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseInterface.onGetCallback(dataSnapshot);
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  static void getDoctorDetails(String doctor_speciality, long doctor_contact, final FirebaseInterface firebaseInterface){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/doctor/"+doctor_speciality+"/"+doctor_contact);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseInterface.onGetCallback(dataSnapshot);
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setPrescription(long user_contact, long milli_second, PrescriptionEntity prescriptionEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/user/"+user_contact+"/consult/"+milli_second+"/prescription");
        databaseReference.setValue(prescriptionEntity);
    }

    public static void saveLogin(String login_id, LoginEntity loginEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/login/"+login_id);
        databaseReference.setValue(loginEntity);
    }

    public static void saveDoctorEntity(String doctor_speciality, long doctor_contact, DoctorEntity doctorEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/doctor/"+doctor_speciality+"/"+doctor_contact);
        databaseReference.setValue(doctorEntity);
    }

    public static void saveUserEntity(long user_contact, UserEntity userEntity){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/user/"+user_contact);
        databaseReference.setValue(userEntity);
    }
}
