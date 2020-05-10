package com.vignesh.healthcare.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.UserConsultEntity;

import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    List<UserConsultEntity> user_consult_entity_list;

    RecordRecyclerViewAdapter(List<UserConsultEntity> user_consult_entity_list){
        this.user_consult_entity_list = user_consult_entity_list;
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
        UserConsultEntity userConsultEntity = user_consult_entity_list.get(position);
        holder.reason_textView.setText(userConsultEntity.getReason());
        holder.doctor_name_textView.setText(userConsultEntity.getDoctor_name());
        holder.speciality_textView.setText(userConsultEntity.getDoctor_speciality());
        holder.consult_date_textView.setText(userConsultEntity.getConsult_date()+" "+userConsultEntity.getConsult_time());
    }

    @Override
    public int getItemCount() {
        return user_consult_entity_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView reason_textView, doctor_name_textView, speciality_textView, consult_date_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reason_textView = itemView.findViewById(R.id.reason_textView);
            doctor_name_textView = itemView.findViewById(R.id.doctor_name_textView);
            speciality_textView = itemView.findViewById(R.id.speciality_textView);
            consult_date_textView = itemView.findViewById(R.id.consult_date_textView);
        }
    }
}
