package com.vignesh.healthcare.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.UserEntity;

public class UserDetailsTabFragment extends Fragment {
    TextView name_textView, contact_textView, email_textView, dob_textView, gender_textView, address_textView, city_textView, country_textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_details_tab_layout, container, false);
        name_textView = rootView.findViewById(R.id.name_textView);
        contact_textView = rootView.findViewById(R.id.contact_textView);
        email_textView = rootView.findViewById(R.id.email_textView);
        dob_textView = rootView.findViewById(R.id.dob_textView);
        gender_textView = rootView.findViewById(R.id.gender_textView);
        address_textView = rootView.findViewById(R.id.address_textView);
        city_textView = rootView.findViewById(R.id.city_textView);
        country_textView = rootView.findViewById(R.id.country_textView);

        long user_contact = ((MainActivity)getActivity()).getDoctorConsultEntity().getUser_contact();
        FirebaseHandler.getUserDetails(user_contact, new FirebaseHandler.FirebaseInterface(){
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                name_textView.setText(userEntity.getName());
                contact_textView.setText(""+userEntity.getContact());
                email_textView.setText(userEntity.getEmail());
                dob_textView.setText(userEntity.getDob());
                gender_textView.setText(userEntity.getGender());
                address_textView.setText(userEntity.getAddress());
                city_textView.setText(userEntity.getCity());
                country_textView.setText(userEntity.getCountry());

                ((MainActivity)getActivity()).setUserEntity(userEntity);
            }
        });

        return rootView;
    }
}
