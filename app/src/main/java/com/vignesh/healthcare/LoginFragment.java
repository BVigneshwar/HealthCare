package com.vignesh.healthcare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.UserEntity;;

public class LoginFragment extends Fragment {
    EditText login_id, login_password;
    Button signup_button, login_button;
    RadioButton user_radio_btn, doctor_radio_btn;

    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        login_id = (EditText)rootView.findViewById(R.id.login_id);
        login_password = (EditText)rootView.findViewById(R.id.login_password);
        signup_button = (Button)rootView.findViewById(R.id.signup_button);
        login_button = (Button)rootView.findViewById(R.id.login_button);
        user_radio_btn = (RadioButton)rootView.findViewById(R.id.user_radio_btn);
        doctor_radio_btn = (RadioButton)rootView.findViewById(R.id.doctor_radio_btn);

        firebaseDatabase = FirebaseDatabase.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_radio_btn.isChecked()){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new UserSignupFragment()).addToBackStack(null).commit();
                }else if(doctor_radio_btn.isChecked()){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, new DoctorSignupFragment()).addToBackStack(null).commit();
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = login_id.getText().toString();
                String password = login_password.getText().toString();
                if(Pattern.compile("[\\d]+@[a-z]+").matcher(login).matches()){
                    final String str[] = login.split("@");

                    DatabaseReference databaseReference = firebaseDatabase.getReference(str[1]).child(str[0]);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(str[1].equals("doctor")){
                                DoctorEntity doctorEntity = dataSnapshot.getValue(DoctorEntity.class);
                            }else{
                                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getContext(), getString(R.string.invalid_login), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
