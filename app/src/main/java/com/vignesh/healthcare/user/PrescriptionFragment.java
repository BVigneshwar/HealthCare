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
import com.vignesh.healthcare.doctor.MedicineRecyclerViewAdapter;
import com.vignesh.healthcare.entity.MedicineEntity;
import com.vignesh.healthcare.entity.UserConsultEntity;
import com.vignesh.healthcare.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;

public class PrescriptionFragment extends Fragment {
    TextView doctor_textView, speciality_textView, patient_textView, age_textView, gender_textView, consult_date_textView;
    TextView analysis_textView, refer_speciality_textView, refer_doctor_textView;
    RecyclerView medicine_recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prescription_layout, container, false);

        doctor_textView = rootView.findViewById(R.id.doctor_textView);
        speciality_textView = rootView.findViewById(R.id.speciality_textView);
        patient_textView = rootView.findViewById(R.id.patient_textView);
        age_textView = rootView.findViewById(R.id.age_textView);
        gender_textView = rootView.findViewById(R.id.gender_textView);
        consult_date_textView = rootView.findViewById(R.id.consult_date_textView);
        analysis_textView = rootView.findViewById(R.id.analysis_textView);
        refer_speciality_textView = rootView.findViewById(R.id.refer_speciality_textView);
        refer_doctor_textView = rootView.findViewById(R.id.refer_doctor_textView);
        medicine_recyclerView = rootView.findViewById(R.id.medicine_recyclerView);

        Bundle bundle = getArguments();
        String millisecond = bundle.getString("consult_milli_second");

        UserEntity userEntity = ((MainActivity)getActivity()).getUserEntity();
        UserConsultEntity userConsultEntity = userEntity.getConsult().get(millisecond);

        doctor_textView.setText(userConsultEntity.getDoctor_name());
        speciality_textView.setText(userConsultEntity.getDoctor_speciality());
        patient_textView.setText(userEntity.getName());
        age_textView.setText(userConsultEntity.getUser_age());
        gender_textView.setText(userConsultEntity.getUser_gender());
        consult_date_textView.setText(userConsultEntity.getConsult_date());

        if(userConsultEntity.getPrescription() != null){
            analysis_textView.setText(userConsultEntity.getPrescription().getAnalysis());
            if(userConsultEntity.getPrescription().isRefer()){
                refer_speciality_textView.setText(userConsultEntity.getPrescription().getRefer_speciality());
                refer_doctor_textView.setText(userConsultEntity.getPrescription().getRefer_doctor());
            }

            List<MedicineEntity> medicine_list = userConsultEntity.getPrescription().getMedicine() == null ? new LinkedList<MedicineEntity>() : userConsultEntity.getPrescription().getMedicine();
            MedicineRecyclerViewAdapter medicineRecyclerViewAdapter = new MedicineRecyclerViewAdapter(medicine_list, getContext());
            medicineRecyclerViewAdapter.setHideRemoveButton(true);
            medicine_recyclerView.setHasFixedSize(true);
            medicine_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            medicine_recyclerView.setAdapter(medicineRecyclerViewAdapter);
        }

        return rootView;
    }
}
