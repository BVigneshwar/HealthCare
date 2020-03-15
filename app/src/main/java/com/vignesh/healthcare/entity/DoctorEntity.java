package com.vignesh.healthcare.entity;


public class DoctorEntity {
    private String name;
    private long contact;
    private String email;
    private String address;
    private String qualification;
    private String specialist;
    private String city;
    private String country;

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

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getSpecialist() {
        return specialist;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
