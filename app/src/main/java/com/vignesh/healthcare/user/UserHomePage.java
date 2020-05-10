package com.vignesh.healthcare.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.UserConsultEntity;
import com.vignesh.healthcare.entity.UserEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class UserHomePage extends Fragment implements View.OnClickListener{
    GridLayout speciality_gridLayout;
    RecyclerView record_recyclerView;
    RecordRecyclerViewAdapter recordRecyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_homepage_layout, container, false);
        speciality_gridLayout = rootView.findViewById(R.id.specialities_gridLayout);
        record_recyclerView = rootView.findViewById(R.id.record_recyclerView);

        recordRecyclerViewAdapter = new RecordRecyclerViewAdapter(new LinkedList<UserConsultEntity>());
        record_recyclerView.setHasFixedSize(true);
        record_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        record_recyclerView.setAdapter(recordRecyclerViewAdapter);

        long user_contact = ((MainActivity)getActivity()).getUser_contact();

        FirebaseHandler.getUserDetails(user_contact, new FirebaseHandler.FirebaseInterface() {
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                ((MainActivity)getActivity()).setUserEntity(userEntity);

                if(userEntity.getConsult() != null){
                    List<UserConsultEntity> user_consult_entity_list = new LinkedList<>();
                    for(HashMap.Entry<String, UserConsultEntity> entry : userEntity.getConsult().entrySet()){
                        user_consult_entity_list.add(entry.getValue());
                    }
                    Collections.sort(user_consult_entity_list);
                    recordRecyclerViewAdapter.user_consult_entity_list = user_consult_entity_list;
                    recordRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int speciality_count = speciality_gridLayout.getChildCount();
        for(int i=0; i<speciality_count; i++){
            LinearLayout speciality_container = (LinearLayout) speciality_gridLayout.getChildAt(i);
            speciality_container.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String speciality = v.getTag().toString();
        ((MainActivity)getActivity()).setSpeciality(speciality);
        SpecialityRecyclerViewFragment specialityRecyclerViewFragment = new SpecialityRecyclerViewFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, specialityRecyclerViewFragment).addToBackStack(null).commit();
    }
}
