package com.vignesh.healthcare.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.UserConsultEntity;
import com.vignesh.healthcare.entity.UserEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConsultationTimeRecyclerViewAdapter extends RecyclerView.Adapter<ConsultationTimeRecyclerViewAdapter.ViewHolder>{
    List<Long> available_time_list, consult_time_list;
    Fragment fragment;
    UserEntity userEntity;

    public ConsultationTimeRecyclerViewAdapter(Fragment fragment){
        this.fragment = fragment;
        available_time_list = new LinkedList<>();
        consult_time_list = new LinkedList<>();
        userEntity = ((MainActivity)fragment.getActivity()).getUserEntity();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_consultation_time_card_layout, parent, false);
        return new ConsultationTimeRecyclerViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final long milli_second = available_time_list.get(position);
        holder.time_textView.setText(CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, milli_second));
        if(consult_time_list.contains(milli_second)){
            holder.consultation_time_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(fragment.getContext(), fragment.getString(R.string.not_available), Toast.LENGTH_LONG).show();
                }
            });
        }else if(userEntity.getConsult() != null && userEntity.getConsult().containsKey(""+milli_second)){
            holder.consultation_time_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(fragment.getContext(), fragment.getString(R.string.already_made_appointment, userEntity.getConsult().get(""+milli_second).getDoctor_name()), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            holder.consultation_time_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.user_consult_confirmation_layout, null, false);
                    final DoctorEntity doctorEntity = ((MainActivity)fragment.getActivity()).getDoctorEntity();
                    final String reason = ((MainActivity)fragment.getActivity()).getReason();
                    final String consult_time = CommonUtil.getDateStringForMilliSeconds(CommonUtil.time_format, milli_second);
                    final String consult_date = CommonUtil.getDateStringForMilliSeconds(CommonUtil.date_format, milli_second);

                    ((TextView)dialogView.findViewById(R.id.doctor_textView)).setText(doctorEntity.getName());
                    ((TextView)dialogView.findViewById(R.id.speciality_textView)).setText(doctorEntity.getSpeciality());
                    ((EditText)dialogView.findViewById(R.id.reason_textView)).setText(reason);
                    ((TextView)dialogView.findViewById(R.id.date_textView)).setText(consult_date);
                    ((TextView)dialogView.findViewById(R.id.time_textView)).setText(consult_time);

                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
                    builder.setView(dialogView);
                    builder.setTitle(R.string.confirmation);
                    builder.setCancelable(false);
                    builder.setPositiveButton(fragment.getString(R.string.proceed), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            final String age = CommonUtil.getAge(userEntity.getDob(), fragment.getContext());
                            FirebaseHandler.getDoctorDetails(doctorEntity.getSpeciality(), doctorEntity.getContact(), new FirebaseHandler.FirebaseInterface() {
                                @Override
                                public void onGetCallback(DataSnapshot dataSnapshot) {
                                    ((MainActivity)fragment.getActivity()).setDoctorEntity(dataSnapshot.getValue(DoctorEntity.class));
                                    if(((ConsultationFragment)fragment).isAvailable(consult_date, milli_second)){
                                        DoctorConsultEntity doctorConsultEntity = new DoctorConsultEntity();
                                        doctorConsultEntity.setUser_name(userEntity.getName());
                                        doctorConsultEntity.setUser_contact(userEntity.getContact());
                                        doctorConsultEntity.setUser_gender(userEntity.getGender());
                                        doctorConsultEntity.setUser_age(age);
                                        doctorConsultEntity.setReason(reason);
                                        doctorConsultEntity.setConsult_time(consult_time);
                                        doctorConsultEntity.setConsult_date(consult_date);
                                        doctorConsultEntity.setMilli_second(milli_second);

                                        UserConsultEntity userConsultEntity = new UserConsultEntity();
                                        userConsultEntity.setDoctor_name(doctorEntity.getName());
                                        userConsultEntity.setDoctor_contact(doctorEntity.getContact());
                                        userConsultEntity.setUser_age(age);
                                        userConsultEntity.setUser_gender(userEntity.getGender());
                                        userConsultEntity.setReason(reason);
                                        userConsultEntity.setDoctor_speciality(doctorEntity.getSpeciality());
                                        userConsultEntity.setConsult_date(consult_date);
                                        userConsultEntity.setConsult_time(consult_time);
                                        userConsultEntity.setMilli_second(milli_second);

                                        FirebaseHandler.setConsultationInDoctor(doctorEntity.getSpeciality(), doctorEntity.getContact(), milli_second, doctorConsultEntity);
                                        FirebaseHandler.setConsultationInUser(userEntity.getContact(), milli_second, userConsultEntity);

                                        HashMap<String, DoctorConsultEntity> doctorConsult = ((MainActivity)fragment.getActivity()).getDoctorEntity().getConsult();
                                        if(doctorConsult == null)
                                            doctorConsult = new HashMap<>();
                                        doctorConsult.put(""+milli_second, doctorConsultEntity);

                                        HashMap<String, UserConsultEntity> userConsult = userEntity.getConsult();
                                        if(userConsult == null)
                                            userConsult = new HashMap<>();
                                        userConsult.put(""+milli_second, userConsultEntity);

                                        Toast.makeText(fragment.getContext(), fragment.getString(R.string.consultation_booking_confirmed), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(fragment.getContext(), fragment.getString(R.string.not_available), Toast.LENGTH_LONG).show();
                                    }
                                    fragment.getFragmentManager().popBackStack();
                                    dialog.cancel();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton(fragment.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return available_time_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView time_textView;
        RelativeLayout consultation_time_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_textView = itemView.findViewById(R.id.time_textView);
            consultation_time_container = itemView.findViewById(R.id.consultation_time_container);
        }
    }
}
