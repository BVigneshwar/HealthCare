package com.vignesh.healthcare.doctor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DoctorHomePage extends Fragment{

    TextView prev_date_textView, date_textView, next_date_textView;
    RecyclerView consultation_recyclerView;
    String doctor_speciality;
    long doctor_contact;
    ConsultationRecyclerViewAdapter consultationRecyclerViewAdapter;
    List<DoctorConsultEntity> consult_entity_list;
    String consult_date;
    long consult_millisecond;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_homepage_layout, container, false);
        prev_date_textView = (TextView)rootView.findViewById(R.id.prev_date_textView);
        date_textView = (TextView)rootView.findViewById(R.id.date_textView);
        next_date_textView = (TextView)rootView.findViewById(R.id.next_date_textView);
        consultation_recyclerView = (RecyclerView)rootView.findViewById(R.id.consultation_recyclerView);

        doctor_speciality = ((MainActivity)getActivity()).getSpeciality();
        doctor_contact = ((MainActivity)getActivity()).getDoctor_contact();

        consult_date = CommonUtil.getDateStringForMilliSeconds(CommonUtil.date_format, -1);
        consult_millisecond = CommonUtil.getMilliSecondsForDate(CommonUtil.date_format, consult_date);

        FirebaseHandler.getDoctorDetails(doctor_speciality, doctor_contact, new FirebaseHandler.FirebaseInterface() {
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                DoctorEntity doctorEntity = dataSnapshot.getValue(DoctorEntity.class);
                ((MainActivity)getActivity()).setDoctorEntity(doctorEntity);
                setConsultationDate();
            }
        });

        date_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePicker();
            }
        });

        prev_date_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            modifyConsultDateToconsecutiveDate(-1);
            }
        });

        next_date_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            modifyConsultDateToconsecutiveDate(1);
            }
        });

        return rootView;
    }

    void modifyConsultData(){
        DoctorEntity doctorEntity = ((MainActivity)getActivity()).getDoctorEntity();
        consult_entity_list = new LinkedList<>();
        long check_in = 0, check_out = 0;
        String day = CommonUtil.getDateStringForMilliSeconds(CommonUtil.day_format, consult_millisecond);

        if(doctorEntity.getAvailable().get(day) == null){
            return;
        }
        check_in = doctorEntity.getAvailable().get(day).getCheck_in();
        check_out = doctorEntity.getAvailable().get(day).getCheck_out();
        check_in = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_in));
        check_out = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_out));

        for(HashMap.Entry<String, DoctorConsultEntity> entry : doctorEntity.getConsult().entrySet()){
            long consult_time = Long.parseLong(entry.getKey());
            if(consult_time >= check_in && consult_time <= check_out){
                consult_entity_list.add(entry.getValue());
            }
        }
        if(consultationRecyclerViewAdapter == null){
            consultationRecyclerViewAdapter = new ConsultationRecyclerViewAdapter(consult_entity_list, getContext());
            consultationRecyclerViewAdapter.setFragment(getFragment());
            consultation_recyclerView.setHasFixedSize(true);
            consultation_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            consultation_recyclerView.setAdapter(consultationRecyclerViewAdapter);
        }else{
            consultationRecyclerViewAdapter.consult_entity_list = consult_entity_list;
            consultationRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    void displayDatePicker(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonUtil.date_format);
        try {
            calendar.setTime(simpleDateFormat.parse(consult_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                consult_date = dayOfMonth+"-"+(month+1)+"-"+year;
                consult_millisecond = CommonUtil.getMilliSecondsForDate(CommonUtil.date_format, consult_date);
                setConsultationDate();
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    void modifyConsultDateToconsecutiveDate(int consecutive_value){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(CommonUtil.date_format);
        try {
            calendar.setTime(dateFormat.parse(consult_date));
            calendar.add(Calendar.DAY_OF_YEAR, consecutive_value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        consult_date = dateFormat.format(calendar.getTime());
        consult_millisecond = CommonUtil.getMilliSecondsForDate(CommonUtil.date_format, consult_date);
        setConsultationDate();
    }

    void setConsultationDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonUtil.date_format);
        long diff = 0;
        try {
            Date today = calendar.getTime();
            Date new_consult_date = simpleDateFormat.parse(consult_date);
            long diffInMilliSec = today.getTime() - new_consult_date.getTime();
            diff = TimeUnit.DAYS.convert(diffInMilliSec, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String display_date = consult_date;
        if(diff == 0){
            display_date = getString(R.string.today);
        }else if(diff == -1){
            display_date = getString(R.string.yesterday);
        }else if(diff == 1){
            display_date = getString(R.string.tomorrow);
        }
        date_textView.setText(display_date);
        modifyConsultData();
    }

    Fragment getFragment(){
        return this;
    }
}
