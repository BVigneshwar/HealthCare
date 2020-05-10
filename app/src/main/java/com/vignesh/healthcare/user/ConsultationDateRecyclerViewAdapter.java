package com.vignesh.healthcare.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;

import java.util.List;

public class ConsultationDateRecyclerViewAdapter extends RecyclerView.Adapter<ConsultationDateRecyclerViewAdapter.ViewHolder> {
    List<Long> consult_date_list;
    ConsultationFragment fragment;
    int selected;

    public ConsultationDateRecyclerViewAdapter(ConsultationFragment fragment, List<Long> consult_date_list, int selected){
        this.fragment = fragment;
        this.consult_date_list = consult_date_list;
        this.selected = selected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_consultation_date_card_layout, parent, false);
        return new ConsultationDateRecyclerViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final long milli_second = consult_date_list.get(position);
        holder.consult_date_textView.setText(CommonUtil.getDateStringForMilliSeconds("dd", milli_second));
        holder.consult_day_textView.setText(CommonUtil.getDateStringForMilliSeconds("EEE", milli_second));
        holder.consult_date_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected != position){
                    fragment.modifyConsultationTimeList(CommonUtil.getDateStringForMilliSeconds(CommonUtil.date_day_format, milli_second));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return consult_date_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout consult_date_container;
        TextView consult_date_textView, consult_day_textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consult_date_container = itemView.findViewById(R.id.consult_date_container);
            consult_date_textView = itemView.findViewById(R.id.consult_date_textView);
            consult_day_textView = itemView.findViewById(R.id.consult_day_textView);
        }
    }
}
