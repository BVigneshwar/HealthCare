package com.vignesh.healthcare.validator;

import com.vignesh.healthcare.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {
    UserEntity userEntity;
    List<String> error_list;

    public UserValidator(UserEntity userEntity){
        this.userEntity = userEntity;
        error_list = new LinkedList<>();
    }

    public List<String> getError_list(){
        return error_list;
    }

    public void add_To_Error_List(String field){
        error_list.add(field);
    }

    public boolean validate_and_SetName(String value){
        if(value == null || value.equals("")){
            error_list.add("name");
            return false;
        }
        userEntity.setName(value);
        return true;
    }

    public boolean validate_and_SetEmail(String value){
        if(value == null || value.equals("")){
            error_list.add("email");
            return false;
        }
        userEntity.setEmail(value);
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
        userEntity.setContact(Long.parseLong(value));
        return true;
    }

    public boolean validate_and_setAge(String value){
        if(value == null || value.equals("")){
            error_list.add("age");
            return false;
        }
        if(!Pattern.compile("[\\d]+").matcher(value).matches()){
            error_list.add("age");
            return false;
        }
        userEntity.setAge(Integer.parseInt(value));
        return true;
    }

    public boolean validate_and_SetAddress(String value){
        if(value == null || value.equals("")){
            error_list.add("address");
            return false;
        }
        userEntity.setAddress(value);
        return true;
    }

    public boolean validate_and_SetGender(String value){
        if(value == null){
            error_list.add("gender");
            return false;
        }else if(value.equals("Male")){
            userEntity.setGender(value);
        }else if(value.equals("Female")){
            userEntity.setGender(value);
        }else{
            error_list.add("gender");
            return false;
        }
        return true;
    }

    public boolean validate_and_SetCountry(String value){
        if(value == null || value.equals("")){
            error_list.add("country");
            return false;
        }
        userEntity.setCountry(value);
        return true;
    }

    public boolean validate_and_SetCity(String value){
        if(value == null || value.equals("")){
            error_list.add("city");
            return false;
        }
        userEntity.setCity(value);
        return true;
    }
}
