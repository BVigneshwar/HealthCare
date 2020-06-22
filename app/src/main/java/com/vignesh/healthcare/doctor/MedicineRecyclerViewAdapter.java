package com.vignesh.healthcare.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.MedicineEntity;

import java.util.List;

public class MedicineRecyclerViewAdapter extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder> {
    List<MedicineEntity> medicine_list;
    Context context;
    boolean hideRemoveButton;

    public MedicineRecyclerViewAdapter(List<MedicineEntity> medicine_list, Context context){
        this.medicine_list = medicine_list;
        this.context = context;
        hideRemoveButton = false;
    }

    public void setHideRemoveButton(boolean hideRemoveButton) {
        this.hideRemoveButton = hideRemoveButton;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_medicine_card_layout, parent, false);
        return new MedicineRecyclerViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MedicineEntity medicineEntity = medicine_list.get(position);
        holder.name_textView.setText(medicineEntity.getName());
        holder.description_textView.setText(medicineEntity.getDescription());
        holder.meal_textView.setText(medicineEntity.isAfter_meal() ? context.getString(R.string.after_meal) : context.getString(R.string.before_meal));
        if(!medicineEntity.isMorning()){
            holder.morning_textView.setVisibility(View.INVISIBLE);
        }
        if(!medicineEntity.isAfternoon()){
            holder.afternoon_textView.setVisibility(View.INVISIBLE);
        }
        if(!medicineEntity.isNight()){
            holder.night_textView.setVisibility(View.INVISIBLE);
        }
        if(!hideRemoveButton){
            holder.remove_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    medicine_list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }else{
            holder.remove_button.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return medicine_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name_textView, description_textView, meal_textView, morning_textView, afternoon_textView, night_textView;
        ImageButton remove_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_textView = itemView.findViewById(R.id.name_textView);
            description_textView = itemView.findViewById(R.id.description_textView);
            meal_textView = itemView.findViewById(R.id.meal_textView);
            morning_textView = itemView.findViewById(R.id.morning_textView);
            afternoon_textView = itemView.findViewById(R.id.afternoon_textView);
            night_textView = itemView.findViewById(R.id.night_textView);
            remove_button = itemView.findViewById(R.id.remove_button);
        }
    }
}
