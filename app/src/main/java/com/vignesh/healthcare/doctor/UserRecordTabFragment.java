package com.vignesh.healthcare.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.vignesh.healthcare.user.RecordRecyclerViewAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class UserRecordTabFragment extends Fragment {
    RecyclerView user_record_recyclerView;
    RecordRecyclerViewAdapter recordRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_record_tab_layout, container, false);

        user_record_recyclerView = rootView.findViewById(R.id.user_record_recyclerView);

        recordRecyclerViewAdapter = new RecordRecyclerViewAdapter(new LinkedList<UserConsultEntity>(), this);
        user_record_recyclerView.setHasFixedSize(true);
        user_record_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user_record_recyclerView.setAdapter(recordRecyclerViewAdapter);

        long user_contact = ((MainActivity)getActivity()).getDoctorConsultEntity().getUser_contact();
        FirebaseHandler.getUserDetails(user_contact, new FirebaseHandler.FirebaseInterface(){
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                LinkedList<UserConsultEntity> user_consult_list = new LinkedList<>();
                if(userEntity.getConsult() != null){
                    for(Map.Entry<String, UserConsultEntity> entry : userEntity.getConsult().entrySet()){
                        user_consult_list.add(entry.getValue());
                    }
                }
                Collections.sort(user_consult_list);
                recordRecyclerViewAdapter.user_consult_entity_list = user_consult_list;
                recordRecyclerViewAdapter.notifyDataSetChanged();
                ((MainActivity)getActivity()).setUserEntity(userEntity);
            }
        });

        return rootView;
    }
}
