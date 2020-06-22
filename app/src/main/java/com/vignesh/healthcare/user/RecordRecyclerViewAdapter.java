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

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.UserConsultEntity;

import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    public List<UserConsultEntity> user_consult_entity_list;
    Fragment fragment;

    public RecordRecyclerViewAdapter(List<UserConsultEntity> user_consult_entity_list, Fragment fragment){
        this.user_consult_entity_list = user_consult_entity_list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecordRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cardView = layoutInflater.inflate(R.layout.user_record_card_layout, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserConsultEntity userConsultEntity = user_consult_entity_list.get(position);
        holder.reason_textView.setText(userConsultEntity.getReason());
        holder.doctor_name_textView.setText(userConsultEntity.getDoctor_name());
        holder.speciality_textView.setText(userConsultEntity.getDoctor_speciality());
        holder.consult_date_textView.setText(userConsultEntity.getConsult_date()+" "+userConsultEntity.getConsult_time());
        holder.record_card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("consult_milli_second", userConsultEntity.getMilli_second()+"");
                PrescriptionFragment prescriptionFragment = new PrescriptionFragment();
                prescriptionFragment.setArguments(bundle);

                fragment.getFragmentManager().beginTransaction().replace(R.id.fragment_layout, prescriptionFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return user_consult_entity_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView reason_textView, doctor_name_textView, speciality_textView, consult_date_textView;
        RelativeLayout record_card_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            record_card_container = itemView.findViewById(R.id.record_card_container);
            reason_textView = itemView.findViewById(R.id.reason_textView);
            doctor_name_textView = itemView.findViewById(R.id.doctor_name_textView);
            speciality_textView = itemView.findViewById(R.id.speciality_textView);
            consult_date_textView = itemView.findViewById(R.id.consult_date_textView);
        }
    }
}
