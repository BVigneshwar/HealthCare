package com.vignesh.healthcare.doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FieldNameMapping;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.validator.DoctorValidator;

import java.util.List;

public class DoctorSignupFragment extends Fragment {
    Button signup_button, cancel_button;
    EditText name_editText, email_editText, contact_editText, qualification_editText, password_editText, re_password_editText, address_editText, country_editText, city_editText;
    Spinner specialist_spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_signup_layout, container, false);
        name_editText = (EditText)rootView.findViewById(R.id.name_editText);
        email_editText = (EditText)rootView.findViewById(R.id.email_editText);
        contact_editText = (EditText)rootView.findViewById(R.id.contact_editText);
        qualification_editText = (EditText)rootView.findViewById(R.id.qualification_editText);
        specialist_spinner = (Spinner)rootView.findViewById(R.id.specialist_spinner);
        password_editText = (EditText)rootView.findViewById(R.id.password_editText);
        re_password_editText = (EditText)rootView.findViewById(R.id.re_password_editText);
        address_editText = (EditText)rootView.findViewById(R.id.address_editText);
        city_editText = (EditText)rootView.findViewById(R.id.city_editText);
        country_editText = (EditText)rootView.findViewById(R.id.country_editText);

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
                doctorValidator.validate_and_SetSpecialist(specialist_spinner.getSelectedItem().toString());
                doctorValidator.validate_and_SetAddress(address_editText.getText().toString());
                doctorValidator.validate_and_SetCity(city_editText.getText().toString());
                doctorValidator.validate_and_SetCountry(country_editText.getText().toString());
                if(!CommonUtil.validatePassword(password_editText.getText().toString(), re_password_editText.getText().toString())){
                    doctorValidator.add_To_Error_List("password");
                }

                List<String> error_list = doctorValidator.getError_list();
                if(error_list.size() > 0){
                    Toast.makeText(getContext(), getString(R.string.invalid_value_alert, getString(FieldNameMapping.fieldNameMapping.get(error_list.get(0)))), Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/doctor");
                    databaseReference.child(doctorEntity.getSpecialist()).child(doctorEntity.getContact()+"").setValue(doctorEntity);

                    LoginEntity loginEntity = new LoginEntity();
                    loginEntity.setLogin_id(doctorEntity.getContact()+"@doctor");
                    loginEntity.setLogin_password(password_editText.getText().toString());

                    databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/login");
                    databaseReference.child(doctorEntity.getContact()+"@doctor").setValue(loginEntity);

                    registrationSuccessDialog(doctorEntity.getContact()+"@doctor");
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

    private void registrationSuccessDialog(String login_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.registration_details, login_id)).setTitle(R.string.registration_success);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
