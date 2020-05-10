package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.MedicineEntity;
import com.vignesh.healthcare.entity.PrescriptionEntity;

import java.util.LinkedList;
import java.util.List;

public class PrescriptionValidator {
    PrescriptionEntity prescriptionEntity;
    List<Integer> error_list;

    public PrescriptionValidator(PrescriptionEntity prescriptionEntity){
        this.prescriptionEntity = prescriptionEntity;
        this.error_list = new LinkedList<>();
    }

    public List<Integer> getError_list(){
        return error_list;
    }

    public boolean validate_and_SetAnalysis(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.analysis);
            return false;
        }
        prescriptionEntity.setAnalysis(value);
        return true;
    }

    public boolean validate_and_SetMedicine(List<MedicineEntity> value){
        prescriptionEntity.setMedicine(value);
        return true;
    }

    public boolean validate_and_SetRefer(Boolean value){
        prescriptionEntity.setRefer(value);
        return true;
    }

    public boolean validate_and_SetRefer_doctor(String value){
        if(!prescriptionEntity.isRefer()){
            error_list.add(R.string.refer_doctor);
            return false;
        }
        prescriptionEntity.setRefer_doctor(value);
        return true;
    }

    public boolean validate_and_SetRefer_speciality(String value){
        if(!prescriptionEntity.isRefer()){
            error_list.add(R.string.refer_doctor);
            return false;
        }
        prescriptionEntity.setRefer_speciality(value);
        return true;
    }

    public boolean validate_and_SetRefer_contact(long value){
        if(!prescriptionEntity.isRefer()){
            error_list.add(R.string.refer_doctor);
            return false;
        }
        prescriptionEntity.setRefer_contact(value);
        return true;
    }
}
