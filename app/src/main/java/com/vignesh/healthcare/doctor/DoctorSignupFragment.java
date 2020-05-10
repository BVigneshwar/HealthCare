package com.vignesh.healthcare.doctor;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.CommonUtil;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.LoginEntity;
import com.vignesh.healthcare.validator.DoctorValidator;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class DoctorSignupFragment extends Fragment {
    Button signup_button, cancel_button;
    EditText name_editText, contact_editText, email_editText, qualification_editText, password_editText, re_password_editText, address_editText, country_editText, city_editText;
    Spinner speciality_spinner;
    RadioButton male_radioButton, female_radioButton;
    RelativeLayout check_in_timePicker, check_out_timePicker;
    CheckBox monday_checkBox, tuesday_checkBox, wednesday_checkBox, thursday_checkBox, friday_checkBox, saturday_checkBox, sunday_checkBox;
    TextView check_in_textView, check_out_textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_signup_layout, container, false);
        name_editText = (EditText)rootView.findViewById(R.id.name_editText);
        contact_editText = (EditText)rootView.findViewById(R.id.contact_editText);
        email_editText = (EditText)rootView.findViewById(R.id.email_editText);
        male_radioButton = (RadioButton)rootView.findViewById(R.id.male_radioButton);
        female_radioButton = (RadioButton)rootView.findViewById(R.id.female_radioButton);
        qualification_editText = (EditText)rootView.findViewById(R.id.qualification_editText);
        speciality_spinner = (Spinner)rootView.findViewById(R.id.speciality_spinner);
        check_in_timePicker = (RelativeLayout)rootView.findViewById(R.id.check_in_timePicker);
        check_out_timePicker = (RelativeLayout)rootView.findViewById(R.id.check_out_timePicker);
        check_in_textView = (TextView)rootView.findViewById(R.id.check_in_textView);
        check_out_textView = (TextView)rootView.findViewById(R.id.check_out_textView);
        password_editText = (EditText)rootView.findViewById(R.id.password_editText);
        re_password_editText = (EditText)rootView.findViewById(R.id.re_password_editText);
        monday_checkBox = (CheckBox)rootView.findViewById(R.id.monday_checkBox);
        tuesday_checkBox = (CheckBox)rootView.findViewById(R.id.tuesday_checkBox);
        wednesday_checkBox = (CheckBox)rootView.findViewById(R.id.wednesday_checkBox);
        thursday_checkBox = (CheckBox)rootView.findViewById(R.id.thursday_checkBox);
        friday_checkBox = (CheckBox)rootView.findViewById(R.id.friday_checkBox);
        saturday_checkBox = (CheckBox)rootView.findViewById(R.id.saturday_checkBox);
        sunday_checkBox = (CheckBox)rootView.findViewById(R.id.sunday_checkBox);
        address_editText = (EditText)rootView.findViewById(R.id.address_editText);
        city_editText = (EditText)rootView.findViewById(R.id.city_editText);
        country_editText = (EditText)rootView.findViewById(R.id.country_editText);

        signup_button = (Button)rootView.findViewById(R.id.signup_button);
        cancel_button = (Button)rootView.findViewById(R.id.cancel_button);

        check_in_timePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         check_in_textView.setText(hourOfDay+":"+minute);
                     }
                 }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        check_out_timePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        check_out_textView.setText(hourOfDay+":"+minute);
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DoctorEntity doctorEntity = new DoctorEntity();
                DoctorValidator doctorValidator = new DoctorValidator(doctorEntity);

                doctorValidator.validate_and_SetName(name_editText.getText().toString());
                doctorValidator.validate_and_SetContact(contact_editText.getText().toString());
                doctorValidator.validate_and_SetEmail(email_editText.getText().toString());
                doctorValidator.validate_and_SetGender(male_radioButton.isChecked()? getString(R.string.male) : getString(R.string.female));
                doctorValidator.validate_and_SetQualification(qualification_editText.getText().toString());
                doctorValidator.validate_and_SetSpeciality(speciality_spinner.getSelectedItem().toString());
                doctorValidator.validate_and_SetAddress(address_editText.getText().toString());
                doctorValidator.validate_and_SetCity(city_editText.getText().toString());
                doctorValidator.validate_and_SetCountry(country_editText.getText().toString());
                List<String> available_days = getAvailableDays();
                doctorValidator.validate_and_SetAvailable(available_days, check_in_textView.getText().toString(), check_out_textView.getText().toString());
                doctorValidator.validatePassword(password_editText.getText().toString(), re_password_editText.getText().toString());

                List<Integer> error_list = doctorValidator.getError_list();
                if(error_list.size() == 0){
                    FirebaseHandler.getLoginDetails(doctorEntity.getContact() + "@doctor", new FirebaseHandler.FirebaseInterface() {
                        @Override
                        public void onGetCallback(DataSnapshot dataSnapshot) {
                            LoginEntity loginEntity = dataSnapshot.getValue(LoginEntity.class);
                            if(loginEntity == null){
                                FirebaseHandler.saveDoctorEntity(doctorEntity.getSpeciality(), doctorEntity.getContact(), doctorEntity);

                                loginEntity = new LoginEntity();
                                loginEntity.setLogin_id(doctorEntity.getContact()+"@"+doctorEntity.getSpeciality());
                                loginEntity.setLogin_password(password_editText.getText().toString());
                                FirebaseHandler.saveLogin(doctorEntity.getContact()+"@doctor", loginEntity);

                                registrationSuccessDialog(doctorEntity.getContact()+"@doctor");
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

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(speciality_spinner);
            popupWindow.setHeight(500);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

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

    List<String> getAvailableDays(){
        List<String> available_days = new LinkedList<>();
        if(monday_checkBox.isChecked()){
            available_days.add("Mon");
        }
        if(tuesday_checkBox.isChecked()){
            available_days.add("Tue");
        }
        if(wednesday_checkBox.isChecked()){
            available_days.add("Wed");
        }
        if(thursday_checkBox.isChecked()){
            available_days.add("Thu");
        }
        if(friday_checkBox.isChecked()){
            available_days.add("Fri");
        }
        if(saturday_checkBox.isChecked()){
            available_days.add("Sat");
        }
        if(sunday_checkBox.isChecked()){
            available_days.add("Sun");
        }
        return available_days;
    }
}
