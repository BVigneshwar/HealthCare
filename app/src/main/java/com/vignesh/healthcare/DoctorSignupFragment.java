package com.vignesh.healthcare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vignesh.healthcare.common.FieldNameMapping;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.validator.DoctorValidator;

import java.util.List;

public class DoctorSignupFragment extends Fragment {
    Button signup_button, cancel_button;
    EditText name_editText, email_editText, contact_editText, qualification_editText, specialist_editText, password_editText, re_password_editText, address_editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_signup_layout, container, false);
        name_editText = (EditText)rootView.findViewById(R.id.name_editText);
        email_editText = (EditText)rootView.findViewById(R.id.email_editText);
        contact_editText = (EditText)rootView.findViewById(R.id.contact_editText);
        qualification_editText = (EditText)rootView.findViewById(R.id.qualification_editText);
        specialist_editText = (EditText)rootView.findViewById(R.id.specialist_editText);
        password_editText = (EditText)rootView.findViewById(R.id.password_editText);
        re_password_editText = (EditText)rootView.findViewById(R.id.re_password_editText);
        address_editText = (EditText)rootView.findViewById(R.id.address_editText);

        signup_button = (Button)rootView.findViewById(R.id.signup_button);
        cancel_button = (Button)rootView.findViewById(R.id.cancel_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorEntity doctorEntity = new DoctorEntity();
                DoctorValidator doctorValidator = new DoctorValidator(doctorEntity);

                doctorValidator.validate_and_SetName(name_editText.getText().toString());
                doctorValidator.validate_and_SetEmail(email_editText.getText().toString());
                doctorValidator.validate_and_SetContact(contact_editText.getText().toString());
                doctorValidator.validate_and_SetQualification(qualification_editText.getText().toString());
                doctorValidator.validate_and_SetSpecialist(specialist_editText.getText().toString());
                doctorValidator.validate_and_setPassword(password_editText.getText().toString(), re_password_editText.getText().toString());
                doctorValidator.validate_and_SetAddress(address_editText.getText().toString());

                List<String> error_list = doctorValidator.getError_list();
                if(error_list.size() > 0){
                    Toast.makeText(getContext(), getString(R.string.invalid_value_alert, getString(FieldNameMapping.fieldNameMapping.get(error_list.get(0)))), Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare");
                    databaseReference.child("doctor").child(doctorEntity.getContact()+"").setValue(doctorEntity);
                    Toast.makeText(getContext(), getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return rootView;
    }
}
