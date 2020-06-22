package com.vignesh.healthcare.common;


import android.content.Context;

import com.vignesh.healthcare.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public final static String time_format = "HH:mm";
    public final static String day_format = "EEE";
    public final static String date_format = "dd-MM-yyyy";
    public final static String date_day_format = "dd-MM-yyy EEE";
    public final static String date_time_format = "dd-MM-yyyy HH:mm";

    public static String getAge(String dob, Context context){
        String month_string = context.getString(R.string.month);
        String year_string = context.getString(R.string.year);

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(CommonUtil.date_format);
        String current_date = dateFormat.format(calendar.getTime());

        String dob_arr[] = dob.split("-");
        String current_arr[] = current_date.split("-");
        int dob_year = Integer.parseInt(dob_arr[2]);
        int dob_month = Integer.parseInt(dob_arr[1]);
        int current_year = Integer.parseInt(current_arr[2]);
        int current_month = Integer.parseInt(current_arr[1]);
        int years_diff = current_year - dob_year;
        int month_diff = Math.abs(current_month - dob_month);
        String age = null;

        if(years_diff == 0){
            age = month_diff + " " + month_string;
        }else if(years_diff == 1){
            if(current_month > dob_month){
                age = years_diff + " " + year_string;
            }else{
                age = month_diff + " " + month_string;
            }
        }else{
            if(dob_month > current_month){
                age = years_diff + " " + year_string;
            }else{
                age = (years_diff+1) + " " + year_string;
            }
        }
        return age;
    }

    public static String getDateStringForMilliSeconds(String format, long milliseconds){
        Calendar calendar = Calendar.getInstance();
        if(milliseconds != -1){
            calendar.setTime(new Date(milliseconds));
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(calendar.getTime());
    }

    public static  long getMilliSecondsForDate(String format, String date){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }
}
