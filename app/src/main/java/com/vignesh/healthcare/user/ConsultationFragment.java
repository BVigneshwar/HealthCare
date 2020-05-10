package com.vignesh.healthcare.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConsultationFragment extends Fragment {
    RecyclerView date_recyclerView, time_recyclerView;
    TextView name_textView, speciality_textView;
    ConsultationTimeRecyclerViewAdapter consultationTimeRecyclerViewAdapter;
    ConsultationDateRecyclerViewAdapter consultationDateRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_consultation_layout, container, false);

        name_textView = rootView.findViewById(R.id.name_textView);
        speciality_textView = rootView.findViewById(R.id.speciality_textView);
        date_recyclerView = rootView.findViewById(R.id.date_recyclerView);
        time_recyclerView = rootView.findViewById(R.id.time_recyclerView);

        name_textView.setText(((MainActivity)getActivity()).getDoctorEntity().getName());
        speciality_textView.setText(((MainActivity)getActivity()).getDoctorEntity().getSpeciality());

        List<Long> consult_date_list = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
        for(int i=0; i<7; i++){
            consult_date_list.add(calendar.getTimeInMillis());
            calendar.add(Calendar.HOUR, 24);
        }
        consultationDateRecyclerViewAdapter = new ConsultationDateRecyclerViewAdapter(this, consult_date_list, 0);
        date_recyclerView.setHasFixedSize(true);
        date_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        date_recyclerView.setAdapter(consultationDateRecyclerViewAdapter);

        consultationTimeRecyclerViewAdapter = new ConsultationTimeRecyclerViewAdapter(this);
        time_recyclerView.setHasFixedSize(true);
        time_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        time_recyclerView.setAdapter(consultationTimeRecyclerViewAdapter);

        modifyConsultationTimeList(CommonUtil.getDateStringForMilliSeconds(CommonUtil.date_day_format, consult_date_list.get(0)));
        return rootView;
    }

    void modifyConsultationTimeList(String consult_date_day){
        DoctorEntity doctorEntity = ((MainActivity)getActivity()).getDoctorEntity();
        String consult[] = consult_date_day.split(" ");
        String consult_date = consult[0];
        String consult_day = consult[1];

        DoctorEntity.Available doctorEntityAvailable = doctorEntity.getAvailable().get(consult_day);
        long check_in = doctorEntityAvailable == null ? 0 : doctorEntityAvailable.getCheck_in();
        long check_out = doctorEntityAvailable == null ? 0 : doctorEntityAvailable.getCheck_out();

        check_in = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_in));
        check_out = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_out));

        List<Long> available_time_list = new LinkedList<>();
        List<Long> consult_time_list = new LinkedList<>();
        long time = check_in;
        while(time < check_out){
            available_time_list.add(time);
            time = time + 1800000L;
        }
        if(doctorEntity.getConsult() != null){
            for(HashMap.Entry<String, DoctorConsultEntity> entry : doctorEntity.getConsult().entrySet()){
                long ms = entry.getValue().getMilli_second();
                if(ms >= check_in && ms <= check_out){
                    consult_time_list.add(ms);
                }
            }
        }
        consultationTimeRecyclerViewAdapter.available_time_list = available_time_list;
        consultationTimeRecyclerViewAdapter.consult_time_list = consult_time_list;
        consultationTimeRecyclerViewAdapter.notifyDataSetChanged();
    }

    boolean isAvailable(String consult_date, long milli_second){
        String consult_day = CommonUtil.getDateStringForMilliSeconds(CommonUtil.day_format, milli_second);
        DoctorEntity doctorEntity = ((MainActivity)getActivity()).getDoctorEntity();
        DoctorEntity.Available doctorEntityAvailable = doctorEntity.getAvailable().get(consult_day);
        long check_in = doctorEntityAvailable == null ? 0 : doctorEntityAvailable.getCheck_in();
        long check_out = doctorEntityAvailable == null ? 0 : doctorEntityAvailable.getCheck_out();

        check_in = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_in));
        check_out = CommonUtil.getMilliSecondsForDate(CommonUtil.date_time_format, consult_date+" "+CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, check_out));

        return milli_second >= check_in && milli_second <= check_out && (doctorEntity.getConsult() == null || (doctorEntity.getConsult() != null && !doctorEntity.getConsult().containsKey(""+milli_second)));
    }

}
