package com.vignesh.healthcare.user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.entity.UserEntity;
import com.vignesh.healthcare.validator.UserValidator;

import java.util.Calendar;
import java.util.List;

public class UserSignupFragment extends Fragment {
    Button cancel_button, signup_button;
    TextView dob_textView;
    EditText name_editText, contact_editText, email_editText, password_editText, re_password_editText, address_editText, city_editText, country_editText;
    RelativeLayout dob_datePicker;
    RadioButton male_radioButton, female_radioButton;
    UserEntity userEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_signup_layout, container, false);
        name_editText = rootView.findViewById(R.id.name_editText);
        contact_editText = rootView.findViewById(R.id.contact_editText);
        email_editText = rootView.findViewById(R.id.email_editText);
        dob_textView = rootView.findViewById(R.id.dob_textView);
        dob_datePicker = rootView.findViewById(R.id.dob_datePicker);
        password_editText = rootView.findViewById(R.id.password_editText);
        re_password_editText = rootView.findViewById(R.id.re_password_editText);
        address_editText = rootView.findViewById(R.id.address_editText);
        male_radioButton = rootView.findViewById(R.id.male_radioButton);
        female_radioButton = rootView.findViewById(R.id.female_radioButton);
        city_editText = rootView.findViewById(R.id.city_editText);
        country_editText = rootView.findViewById(R.id.country_editText);

        cancel_button = rootView.findViewById(R.id.cancel_button);
        signup_button = rootView.findViewById(R.id.signup_button);

        dob_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob_textView.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEntity = new UserEntity();
                UserValidator userValidator = new UserValidator(userEntity);
                userValidator.validate_and_SetName(name_editText.getText().toString());
                userValidator.validate_and_SetContact(contact_editText.getText().toString());
                userValidator.validate_and_SetEmail(email_editText.getText().toString());
                userValidator.validate_and_SetDob(dob_textView.getText().toString());
                userValidator.validate_and_SetAddress(address_editText.getText().toString());
                userValidator.validate_and_SetGender(male_radioButton.isChecked()? "Male" : "Female");
                userValidator.validate_and_SetCity(city_editText.getText().toString());
                userValidator.validate_and_SetCountry(country_editText.getText().toString());
                userValidator.validatePassword(password_editText.getText().toString(), re_password_editText.getText().toString());

                List<Integer> error_list = userValidator.getError_list();
                if(error_list.isEmpty()){
                    FirebaseHandler.getLoginDetails(userEntity.getContact() + "@user", new FirebaseHandler.FirebaseInterface() {
                        @Override
                        public void onGetCallback(DataSnapshot dataSnapshot) {
                            LoginEntity loginEntity = dataSnapshot.getValue(LoginEntity.class);
                            if(loginEntity == null){
                                FirebaseHandler.saveUserEntity(userEntity.getContact(), userEntity);

                                loginEntity = new LoginEntity();
                                loginEntity.setLogin_id(userEntity.getContact()+"@user");
                                loginEntity.setLogin_password(password_editText.getText().toString());
                                FirebaseHandler.saveLogin(userEntity.getContact()+"@user", loginEntity);

                                registrationSuccessDialog(userEntity.getContact()+"@user");
                            }else{
                                Toast.makeText(getContext(), getString(R.string.account_already_exists), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    String error_fields = "";
                    for(Integer i : error_list){
                        error_fields = error_fields + getString(i) + ", ";
                    }
                    error_fields.substring(0, error_fields.length()-2);
                    Toast.makeText(getContext(), getString(R.string.invalid_value_alert, error_fields), Toast.LENGTH_LONG).show();
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
