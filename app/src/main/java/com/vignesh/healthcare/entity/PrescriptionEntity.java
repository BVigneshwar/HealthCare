package com.vignesh.healthcare.entity;

import java.util.List;

public class PrescriptionEntity{
    private String analysis;
    private List<MedicineEntity> medicine;
    private boolean refer;
    private String refer_doctor;
    private String refer_speciality;
    private long refer_contact;

    public String getAnalysis() {
        return analysis;
    }

    public List<MedicineEntity> getMedicine() {
        return medicine;
    }

    public boolean isRefer() {
        return refer;
    }

    public String getRefer_doctor() {
        return refer_doctor;
    }

    public String getRefer_speciality() {
        return refer_speciality;
    }

    public long getRefer_contact() {
        return refer_contact;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public void setMedicine(List<MedicineEntity> medicine) {
        this.medicine = medicine;
    }

    public void setRefer(boolean refer) {
        this.refer = refer;
    }

    public void setRefer_doctor(String refer_doctor) {
        this.refer_doctor = refer_doctor;
    }

    public void setRefer_speciality(String refer_speciality) {
        this.refer_speciality = refer_speciality;
    }

    public void setRefer_contact(long refer_contact) {
        this.refer_contact = refer_contact;
    }
}
