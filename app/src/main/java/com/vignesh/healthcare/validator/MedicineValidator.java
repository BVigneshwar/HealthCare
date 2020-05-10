package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.MedicineEntity;

import java.util.LinkedList;
import java.util.List;

public class MedicineValidator {
    MedicineEntity medicineEntity;
    List<Integer> error_list;

    public MedicineValidator(MedicineEntity medicineEntity){
        this.medicineEntity = medicineEntity;
        error_list = new LinkedList<>();
    }

    public List<Integer> getError_list(){
        return error_list;
    }

    public boolean validate_and_SetName(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.name);
            return false;
        }
        medicineEntity.setName(value);
        return true;
    }

    public boolean validate_and_SetDescription(String value){
        medicineEntity.setDescription(value);
        return true;
    }

    public boolean validate_and_SetDuration(String value, String unit){
        if(value == null || value.equals("") || unit == null || unit.equals("")){
            error_list.add(R.string.duration);
            return false;
        }
        medicineEntity.setDuration(value+" "+unit);
        return true;
    }

    public boolean validate_and_SetMorning(boolean value){
        medicineEntity.setMorning(value);
        return true;
    }

    public boolean validate_and_SetAfternoon(boolean value){
        medicineEntity.setAfternoon(value);
        return true;
    }

    public boolean validate_and_SetNight(boolean value){
        medicineEntity.setNight(value);
        return true;
    }

    public boolean validate_and_SetAfterMeal(boolean value){
        medicineEntity.setAfter_meal(value);
        return true;
    }
}
