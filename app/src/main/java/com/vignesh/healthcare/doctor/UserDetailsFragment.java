package com.vignesh.healthcare.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.DoctorConsultEntity;

public class UserDetailsFragment extends Fragment {
    TabLayout user_tabLayout;
    ViewPager user_viewPager;
    TextView name_textView, age_textView, gender_textView;
    Button prescribe_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_user_details_layout, container, false);
        user_tabLayout = rootView.findViewById(R.id.user_tab);
        user_viewPager = rootView.findViewById(R.id.user_viewPager);
        name_textView = rootView.findViewById(R.id.name_textView);
        age_textView = rootView.findViewById(R.id.age_textView);
        gender_textView = rootView.findViewById(R.id.gender_textView);
        prescribe_button = rootView.findViewById(R.id.prescribe_button);

        DoctorConsultEntity doctorConsultEntity = ((MainActivity)getActivity()).getDoctorConsultEntity();
        name_textView.setText(doctorConsultEntity.getUser_name());
        age_textView.setText(doctorConsultEntity.getUser_age());
        gender_textView.setText(doctorConsultEntity.getUser_gender());

        UserDetailsTabAdapter userTabAdapter = new UserDetailsTabAdapter(getContext(), getFragmentManager());
        user_viewPager.setAdapter(userTabAdapter);
        user_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(user_tabLayout));
        user_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                user_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        prescribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescriptionFragment prescriptionFragment = new PrescriptionFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_layout, prescriptionFragment).addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}
