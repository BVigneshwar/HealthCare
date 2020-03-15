package com.vignesh.healthcare.common;

public class CommonUtil {

    public static boolean validatePassword(String value1, String value2){
        if(value1 == null || value1.equals("")){
            return false;
        }
        if(value2 == null || value2.equals("")){
            return false;
        }
        if(!value1.equals(value2)){
            return false;
        }
        return true;
    }

}
