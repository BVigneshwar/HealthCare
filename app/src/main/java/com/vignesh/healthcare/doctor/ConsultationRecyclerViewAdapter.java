package com.vignesh.healthcare.doctor;

import android.content.Context;
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
import com.vignesh.healthcare.entity.DoctorConsultEntity;

import java.util.List;

public class ConsultationRecyclerViewAdapter extends RecyclerView.Adapter<ConsultationRecyclerViewAdapter.ViewHolder> {
    List<DoctorConsultEntity> consult_entity_list;
    Context context;
    Fragment fragment;

    public ConsultationRecyclerViewAdapter(List<DoctorConsultEntity> consult_entity_list, Context context){
        this.consult_entity_list = consult_entity_list;
        this.context = context;
    }

    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_consultation_card_layout, parent, false);
        return new ConsultationRecyclerViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DoctorConsultEntity doctorConsultEntity = consult_entity_list.get(position);
        holder.consult_time_textView.setText(doctorConsultEntity.getConsult_time());
        holder.name_textView.setText(doctorConsultEntity.getUser_name());
        holder.age_textView.setText(doctorConsultEntity.getUser_age());
        holder.gender_textView.setText(doctorConsultEntity.getUser_gender());
        holder.reason_textView.setText(doctorConsultEntity.getReason());
        holder.consult_card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)fragment.getActivity()).setDoctorConsultEntity(doctorConsultEntity);
                UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
                fragment.getFragmentManager().beginTransaction().replace(R.id.fragment_layout, userDetailsFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return consult_entity_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView consult_time_textView, name_textView, gender_textView, age_textView, reason_textView;
        RelativeLayout consult_card_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consult_card_container = (RelativeLayout) itemView.findViewById(R.id.consult_card_container);
            consult_time_textView = (TextView) itemView.findViewById(R.id.consult_time_textView);
            name_textView = (TextView) itemView.findViewById(R.id.name_textView);
            gender_textView = (TextView) itemView.findViewById(R.id.gender_textView);
            age_textView = (TextView) itemView.findViewById(R.id.age_textView);
            reason_textView = (TextView) itemView.findViewById(R.id.reason_textView);
        }
    }
}
