package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.entity.DoctorEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class DoctorValidator {
    DoctorEntity doctorEntity;
    List<String> error_list;

    public DoctorValidator(DoctorEntity doctorEntity){
        this.doctorEntity = doctorEntity;
        error_list = new LinkedList<>();
    }

    public List<String> getError_list(){
        return error_list;
    }

    public boolean validate_and_SetName(String value){
        if(value == null || value.equals("")){
            error_list.add("name");
            return false;
        }
        doctorEntity.setName(value);
        return true;
    }

    public boolean validate_and_SetQualification(String value){
        if(value == null || value.equals("")){
            error_list.add("qualification");
            return false;
        }
        doctorEntity.setQualification(value);
        return true;
    }

    public boolean validate_and_SetSpecialist(String value){
        if(value == null || value.equals("")){
            error_list.add("specialist");
            return false;
        }
        doctorEntity.setSpecialist(value);
        return true;
    }

    public boolean validate_and_SetEmail(String value){
        if(value == null || value.equals("")){
            error_list.add("email");
            return false;
        }
        doctorEntity.setEmail(value);
        return true;
    }

    public boolean validate_and_SetContact(String value){
        if(value == null || value.equals("")){
            error_list.add("contact");
            return false;
        }
        if(!Pattern.compile("[\\d]+").matcher(value).matches()){
            error_list.add("contact");
            return false;
        }
        doctorEntity.setContact(Long.parseLong(value));
        return true;
    }

    public boolean validate_and_setPassword(String value1, String value2){
        if(value1 == null || value1.equals("")){
            error_list.add("password");
            return false;
        }
        if(value2 == null || value2.equals("")){
            error_list.add("password");
            return false;
        }
        if(!value1.equals(value2)){
            error_list.add("password");
            return false;
        }
        doctorEntity.setPassword(value1);
        return true;
    }

    public boolean validate_and_SetAddress(String value){
        if(value == null || value.equals("")){
            error_list.add("address");
            return false;
        }
        doctorEntity.setAddress(value);
        return true;
    }
}
