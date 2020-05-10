package com.vignesh.healthcare.entity;


import java.util.HashMap;

public class UserEntity {
    private String name;
    private long contact;
    private String email;
    private String dob;
    private String gender;
    private String address;
    private String city;
    private String country;
    private HashMap<String, UserConsultEntity> consult;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getContact() {
        return contact;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getCity(){ return city; }

    public String getCountry(){ return country; }

    public HashMap<String, UserConsultEntity> getConsult() {
        return consult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setConsult(HashMap<String, UserConsultEntity> consult) {
        this.consult = consult;
    }
}
