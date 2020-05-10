package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {
    UserEntity userEntity;
    List<Integer> error_list;

    public UserValidator(UserEntity userEntity){
        this.userEntity = userEntity;
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
        userEntity.setName(value);
        return true;
    }

    public boolean validate_and_SetContact(String value){
        if(value == null || value.equals("") || !Pattern.compile("[\\d]+").matcher(value).matches()){
            error_list.add(R.string.contact);
            return false;
        }
        userEntity.setContact(Long.parseLong(value));
        return true;
    }

    public boolean validate_and_SetEmail(String value){
        if(value == null || value.equals("") || !Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(value).matches()){
            error_list.add(R.string.email);
            return false;
        }
        userEntity.setEmail(value);
        return true;
    }

    public boolean validate_and_SetGender(String value){
        if(value == null){
            error_list.add(R.string.gender);
            return false;
        }else if(value.equals("Male")){
            userEntity.setGender(value);
        }else if(value.equals("Female")){
            userEntity.setGender(value);
        }else{
            error_list.add(R.string.gender);
            return false;
        }
        return true;
    }

    public boolean validate_and_SetDob(String value){
        if(value == null || value.equals("") || value.equals("--/--/----")){
            error_list.add(R.string.dob);
        }
        userEntity.setDob(value);
        return true;
    }

    public boolean validate_and_SetAddress(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.address);
            return false;
        }
        userEntity.setAddress(value);
        return true;
    }

    public boolean validate_and_SetCity(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.city);
            return false;
        }
        userEntity.setCity(value);
        return true;
    }

    public boolean validate_and_SetCountry(String value){
        if(value == null || value.equals("")){
            error_list.add(R.string.country);
            return false;
        }
        userEntity.setCountry(value);
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
