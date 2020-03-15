package com.vignesh.healthcare.entity;


public class UserEntity {
    private String name;
    private String email;
    private long contact;
    private int age;
    private String gender;
    private String address;
    private String city;
    private String country;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getContact() {
        return contact;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getCity(){ return city; }

    public String getCountry(){ return country; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public void setAge(int age) {
        this.age = age;
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

}
