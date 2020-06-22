package com.vignesh.healthcare.common;

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
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.doctor.DoctorHomePage;
import com.vignesh.healthcare.doctor.DoctorSignupFragment;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.user.UserHomePage;
import com.vignesh.healthcare.user.UserSignupFragment;
;

public class LoginFragment extends Fragment {
    EditText login_id_editText, login_password_editText;
    Button signup_button, login_button;
    RadioButton user_radio_btn, doctor_radio_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        login_id_editText = (EditText)rootView.findViewById(R.id.login_id);
        login_password_editText = (EditText)rootView.findViewById(R.id.login_password);
        signup_button = (Button)rootView.findViewById(R.id.signup_button);
        login_button = (Button)rootView.findViewById(R.id.login_button);
        user_radio_btn = (RadioButton)rootView.findViewById(R.id.user_radio_btn);
        doctor_radio_btn = (RadioButton)rootView.findViewById(R.id.doctor_radio_btn);

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
                final String login_id = login_id_editText.getText().toString();
                final String login_password = login_password_editText.getText().toString();
                if(Pattern.compile("[\\d]+@[a-z]+").matcher(login_id).matches()){
                    FirebaseHandler.getLoginDetails(login_id, new FirebaseHandler.FirebaseInterface() {
                        @Override
                        public void onGetCallback(DataSnapshot dataSnapshot) {
                            LoginEntity loginEntity = dataSnapshot.getValue(LoginEntity.class);
                            if(loginEntity != null && loginEntity.getLogin_password() != null && loginEntity.getLogin_password().equals(login_password)){
                                if(login_id.contains("user")){
                                    UserHomePage userHomePage = new UserHomePage();
                                    ((MainActivity)getActivity()).setUser_contact(Long.parseLong(loginEntity.getLogin_id().split("@")[0]));
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, userHomePage).commit();
                                }else{
                                    DoctorHomePage doctorHomePage = new DoctorHomePage();
                                    ((MainActivity)getActivity()).setDoctor_contact(Long.parseLong(loginEntity.getLogin_id().split("@")[0]));
                                    ((MainActivity)getActivity()).setSpeciality(loginEntity.getLogin_id().split("@")[1]);
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, doctorHomePage).commit();
                                }
                            }else{
                                Toast.makeText(getContext(), getString(R.string.invalid_login_credentials), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), getString(R.string.invalid_login_credentials), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
