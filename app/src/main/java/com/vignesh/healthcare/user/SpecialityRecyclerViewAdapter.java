package com.vignesh.healthcare.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.entity.DoctorEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SpecialityRecyclerViewAdapter extends RecyclerView.Adapter<SpecialityRecyclerViewAdapter.ViewHolder> {

    List<DoctorEntity> doctor_entity_list;
    Fragment fragment;

    public SpecialityRecyclerViewAdapter(Fragment fragment, List<DoctorEntity> doctor_entity_list){
        this.fragment = fragment;
        this.doctor_entity_list = doctor_entity_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cardView = layoutInflater.inflate(R.layout.user_doctor_card_layout, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.doctor_name.setText(doctor_entity_list.get(position).getName());
        holder.doctor_qualification.setText(doctor_entity_list.get(position).getQualification());
        holder.doctor_contact.setText(doctor_entity_list.get(position).getContact()+"");

        holder.doctor_card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultationFragment consultationFragment = new ConsultationFragment();

                long consult_millisecond = Calendar.getInstance().getTimeInMillis();
                ((MainActivity)fragment.getActivity()).setDoctorEntity(doctor_entity_list.get(position));
                ((MainActivity)fragment.getActivity()).setConsult_milli_second(consult_millisecond);

                fragment.getFragmentManager().beginTransaction().replace(R.id.fragment_layout, consultationFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctor_entity_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView doctor_name, doctor_qualification, doctor_contact;
        RelativeLayout doctor_card_container;

        public ViewHolder(View view){
            super(view);
            doctor_name = view.findViewById(R.id.doctor_name);
            doctor_qualification = view.findViewById(R.id.doctor_qualification);
            doctor_contact = view.findViewById(R.id.doctor_contact);
            doctor_card_container = view.findViewById(R.id.doctor_card_container);
        }
    }
}
