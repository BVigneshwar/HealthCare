package com.vignesh.healthcare.entity;


import java.util.HashMap;
import java.util.List;

public class DoctorEntity {

    public static class Available{
        private long check_in;
        private long check_out;

        public Available(){}

        public Available(long check_in, long check_out){
            this.check_in = check_in;
            this.check_out = check_out;
        }

        public long getCheck_in() {
            return check_in;
        }

        public long getCheck_out() {
            return check_out;
        }

        public void setCheck_in(long check_in) {
            this.check_in = check_in;
        }

        public void setCheck_out(long check_out) {
            this.check_out = check_out;
        }
    }

    private String name;
    private long contact;
    private String email;
    private String gender;
    private String qualification;
    private String speciality;
    private String address;
    private String city;
    private String country;
    private HashMap<String, Available> available;
    private HashMap<String, DoctorConsultEntity> consult;

    public String getName() {
        return name;
    }

    public long getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getQualification() {
        return qualification;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public HashMap<String, Available> getAvailable() {
        return available;
    }

    public HashMap<String, DoctorConsultEntity> getConsult() {
        return consult;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAvailable(HashMap<String, Available> available) {
        this.available = available;
    }

    public void setConsult(HashMap<String, DoctorConsultEntity> consult) {
        this.consult = consult;
    }
}
