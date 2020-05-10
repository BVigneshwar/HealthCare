package com.vignesh.healthcare.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

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
import com.vignesh.healthcare.entity.DoctorEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SpecialityRecyclerViewFragment extends Fragment {
    Spinner speciality_spinner;
    RecyclerView specility_recyclerView;
    SpecialityRecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_doctor_list_layout, container, false);
        speciality_spinner = rootView.findViewById(R.id.speciality_spinner);
        specility_recyclerView = rootView.findViewById(R.id.speciality_recyclerView);

        String doctor_speciality = ((MainActivity)getActivity()).getSpeciality();

        speciality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String doctor_speciality = speciality_spinner.getSelectedItem().toString();
                ((MainActivity)getActivity()).setSpeciality(doctor_speciality);
                modifyDoctorList(doctor_speciality);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        recyclerViewAdapter = new SpecialityRecyclerViewAdapter(this, new LinkedList<DoctorEntity>());
        specility_recyclerView.setHasFixedSize(true);
        specility_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        specility_recyclerView.setAdapter(recyclerViewAdapter);

        String speciality_array[] = getResources().getStringArray(R.array.speciality);
        int position = Arrays.asList(speciality_array).indexOf(doctor_speciality);
        speciality_spinner.setSelection(position);

        return rootView;
    }

    void modifyDoctorList(String doctor_speciality){
        FirebaseHandler.getSpecialityList(doctor_speciality, new FirebaseHandler.FirebaseInterface() {
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                List<DoctorEntity> doctor_entity_list = new LinkedList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    doctor_entity_list.add(ds.getValue(DoctorEntity.class));
                }
                recyclerViewAdapter.doctor_entity_list = doctor_entity_list;
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
