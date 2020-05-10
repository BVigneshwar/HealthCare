package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.DoctorEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class DoctorValidator {
    DoctorEntity doctorEntity;
    List<Integer> error_list;

    public DoctorValidator(DoctorEntity doctorEntity){
        this.doctorEntity = doctorEntity;
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
        doctorEntity.setName(value);
        return true;
    }

    public boolean validate_and_SetContact(String value){
        if(value == null || value.equals("") || !Pattern.compile("[\\d]+").matcher(value).matches()){
            error_list.add(R.string.contact);
            return false;
        }
        doctorEntity.setContact(Long.parseLong(value));
        return true;
    }

    public boolean validate_and_SetEmail(String value){
        if(value == null || value.equals("") || !Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(value).matches()){
            error_list.add(R.string.email);
            return false;
        }
        doctorEntity.setEmail(value);
        return true;
    }

    public boolean validate_and_SetGender(String value){
        if(value == null){
            error_list.add(R.string.gender);
            return false;
        }else{
            doctorEntity.setGender(value);
        }
        return true;
    }

    public boolean validate_and_SetQualification(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.qualification);
            return false;
        }
        doctorEntity.setQualification(value);
        return true;
    }

    public boolean validate_and_SetSpeciality(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.speciality);
            return false;
        }
        doctorEntity.setSpeciality(value);
        return true;
    }

    public boolean validate_and_SetAddress(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.address);
            return false;
        }
        doctorEntity.setAddress(value);
        return true;
    }

    public boolean validate_and_SetCity(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.city);
            return false;
        }
        doctorEntity.setCity(value);
        return true;
    }

    public boolean validate_and_SetCountry(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.country);
            return false;
        }
        doctorEntity.setCountry(value);
        return true;
    }

    public boolean validate_and_SetAvailable(List<String> days, String check_in, String check_out){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        long check_in_val=0, check_out_val=0;
        try {
            calendar.setTime(sdf.parse(check_in));
            check_in_val = calendar.getTimeInMillis();
            calendar.setTime(sdf.parse(check_out));
            check_out_val = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(days.size() == 0 || check_out_val <= check_in_val) {
            error_list.add(R.string.available);
            return false;
        }
        HashMap<String, DoctorEntity.Available> availableHashMap = new HashMap<>();
        for(String day : days){
            availableHashMap.put(day, new DoctorEntity.Available(check_in_val, check_out_val));
        }
        doctorEntity.setAvailable(availableHashMap);
        return true;
    }

    public boolean validatePassword(String value1, String value2){
        if(value1 == null || value1.equals("")){
            error_list.add(R.string.password);
            return false;
        }
        if(value2 == null || value2.equals("")){
            error_list.add(R.string.password);
            return false;
        }
        if(!value1.equals(value2)){
            error_list.add(R.string.password);
            return false;
        }
        return true;
    }
}
