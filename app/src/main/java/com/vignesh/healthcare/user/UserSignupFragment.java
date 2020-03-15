package com.vignesh.healthcare.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FieldNameMapping;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.entity.UserEntity;
import com.vignesh.healthcare.validator.UserValidator;

import java.util.List;

public class UserSignupFragment extends Fragment {
    Button cancel_button, signup_button;
    EditText name_editText, email_editText, contact_editText, age_editText, password_editText, re_password_editText, address_editText, city_editText, country_editText;
    RadioButton male_radioButton, female_radioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_signup_layout, container, false);
        name_editText = (EditText)rootView.findViewById(R.id.name_editText);
        email_editText = (EditText)rootView.findViewById(R.id.email_editText);
        contact_editText = (EditText)rootView.findViewById(R.id.contact_editText);
        age_editText = (EditText)rootView.findViewById(R.id.age_textEdit);
        password_editText = (EditText)rootView.findViewById(R.id.password_editText);
        re_password_editText = (EditText)rootView.findViewById(R.id.re_password_editText);
        address_editText = (EditText)rootView.findViewById(R.id.address_editText);
        male_radioButton = (RadioButton)rootView.findViewById(R.id.male_radioButton);
        female_radioButton = (RadioButton)rootView.findViewById(R.id.female_radioButton);
        city_editText = (EditText)rootView.findViewById(R.id.city_editText);
        country_editText = (EditText)rootView.findViewById(R.id.country_editText);

        cancel_button = (Button)rootView.findViewById(R.id.cancel_button);
        signup_button = (Button)rootView.findViewById(R.id.signup_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();
                UserValidator userValidator = new UserValidator(userEntity);
                userValidator.validate_and_SetName(name_editText.getText().toString());
                userValidator.validate_and_SetEmail(email_editText.getText().toString());
                userValidator.validate_and_SetContact(contact_editText.getText().toString());
                userValidator.validate_and_setAge(age_editText.getText().toString());
                userValidator.validate_and_SetAddress(address_editText.getText().toString());
                userValidator.validate_and_SetGender(male_radioButton.isChecked()? "Male" : "Female");
                userValidator.validate_and_SetCity(city_editText.getText().toString());
                userValidator.validate_and_SetCountry(country_editText.getText().toString());
                if(!CommonUtil.validatePassword(password_editText.getText().toString(), re_password_editText.getText().toString())){
                    userValidator.add_To_Error_List("password");
                }

                List<String> error_list = userValidator.getError_list();
                if(error_list.size() > 0){
                    Toast.makeText(getContext(), getString(R.string.invalid_value_alert, getString(FieldNameMapping.fieldNameMapping.get(error_list.get(0)))), Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/user");
                    databaseReference.child(userEntity.getContact()+"").setValue(userEntity);

                    LoginEntity loginEntity = new LoginEntity();
                    loginEntity.setLogin_id(userEntity.getContact()+"@user");
                    loginEntity.setLogin_password(password_editText.getText().toString());

                    databaseReference = FirebaseDatabase.getInstance().getReference("healthcare/login");
                    databaseReference.child(userEntity.getContact()+"@user").setValue(loginEntity);

                    registrationSuccessDialog(userEntity.getContact()+"@user");
                }
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
