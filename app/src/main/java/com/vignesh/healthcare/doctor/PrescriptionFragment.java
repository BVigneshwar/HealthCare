package com.vignesh.healthcare.doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.vignesh.healthcare.MainActivity;
import com.vignesh.healthcare.R;
import com.vignesh.healthcare.common.FirebaseHandler;
import com.vignesh.healthcare.entity.DoctorConsultEntity;
import com.vignesh.healthcare.entity.DoctorEntity;
import com.vignesh.healthcare.entity.MedicineEntity;
import com.vignesh.healthcare.entity.PrescriptionEntity;
import com.vignesh.healthcare.entity.UserEntity;
import com.vignesh.healthcare.validator.MedicineValidator;
import com.vignesh.healthcare.validator.PrescriptionValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PrescriptionFragment extends Fragment {
    TextView doctor_textView, speciality_textView, patient_textView, age_textView, gender_textView, consult_date_textView;
    EditText analysis_editText;
    Button add_medicine_button;
    RecyclerView medicine_recyclerView;
    CheckBox refer_checkBox;
    LinearLayout refer_speciality_container, refer_doctor_container;
    Spinner speciality_spinner, doctor_spinner;
    Button cancel_button, proceed_button;
    HashMap<String, DoctorEntity> doctor_entity_map;
    List<MedicineEntity> medicine_entity_list;
    MedicineRecyclerViewAdapter medicineRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prescription_layout, container, false);

        doctor_textView = rootView.findViewById(R.id.doctor_textView);
        speciality_textView = rootView.findViewById(R.id.speciality_textView);
        patient_textView = rootView.findViewById(R.id.patient_textView);
        age_textView = rootView.findViewById(R.id.age_textView);
        gender_textView = rootView.findViewById(R.id.gender_textView);
        consult_date_textView = rootView.findViewById(R.id.consult_date_textView);
        analysis_editText = rootView.findViewById(R.id.analysis_editText);
        add_medicine_button = rootView.findViewById(R.id.add_medicine_button);
        medicine_recyclerView = rootView.findViewById(R.id.medicine_recyclerView);
        refer_checkBox = rootView.findViewById(R.id.refer_checkBox);
        refer_speciality_container = rootView.findViewById(R.id.refer_speciality_container);
        refer_doctor_container = rootView.findViewById(R.id.refer_doctor_container);
        speciality_spinner = rootView.findViewById(R.id.speciality_spinner);
        doctor_spinner = rootView.findViewById(R.id.doctor_spinner);
        cancel_button = rootView.findViewById(R.id.cancel_button);
        proceed_button = rootView.findViewById(R.id.proceed_button);

        DoctorEntity doctorEntity = ((MainActivity)getActivity()).getDoctorEntity();
        DoctorConsultEntity doctorConsultEntity = ((MainActivity)getActivity()).getDoctorConsultEntity();

        doctor_textView.setText(doctorEntity.getName());
        speciality_textView.setText(doctorEntity.getSpeciality());
        patient_textView.setText(doctorConsultEntity.getUser_name());
        age_textView.setText(doctorConsultEntity.getUser_age());
        gender_textView.setText(doctorConsultEntity.getUser_gender());
        consult_date_textView.setText(doctorConsultEntity.getConsult_date());

        medicine_entity_list = new ArrayList<>();
        medicineRecyclerViewAdapter = new MedicineRecyclerViewAdapter(medicine_entity_list, getContext());
        medicine_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicine_recyclerView.setAdapter(medicineRecyclerViewAdapter);

        refer_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    refer_speciality_container.setVisibility(View.VISIBLE);
                    refer_doctor_container.setVisibility(View.VISIBLE);
                    modifyReferDoctorList(speciality_spinner.getSelectedItem().toString());
                }else{
                    refer_speciality_container.setVisibility(View.GONE);
                    refer_doctor_container.setVisibility(View.GONE);
                }
            }
        });

        speciality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modifyReferDoctorList(speciality_spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_medicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_medicine_layout, null, false);
                final EditText medicine_editText = dialogView.findViewById(R.id.medicine_editText);
                final EditText description_editText = dialogView.findViewById(R.id.description_editText);
                final EditText duration_editText = dialogView.findViewById(R.id.duration_editText);
                final Spinner duration_spinner = dialogView.findViewById(R.id.duration_spinner);
                final CheckBox morning_checkBox = dialogView.findViewById(R.id.morning_checkBox);
                final CheckBox afternoon_checkBox = dialogView.findViewById(R.id.afternoon_checkBox);
                final CheckBox night_checkBox = dialogView.findViewById(R.id.night_checkBox);
                final RadioButton after_meal_radioButton = dialogView.findViewById(R.id.after_meal_radioButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.medicine);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MedicineEntity medicineEntity = new MedicineEntity();
                        MedicineValidator medicineValidator = new MedicineValidator(medicineEntity);
                        medicineValidator.validate_and_SetName(medicine_editText.getText().toString());
                        medicineValidator.validate_and_SetDescription(description_editText.getText().toString());
                        medicineValidator.validate_and_SetDuration(duration_editText.getText().toString(), duration_spinner.getSelectedItem().toString());
                        medicineValidator.validate_and_SetMorning(morning_checkBox.isChecked());
                        medicineValidator.validate_and_SetAfternoon(afternoon_checkBox.isChecked());
                        medicineValidator.validate_and_SetNight(night_checkBox.isChecked());
                        medicineValidator.validate_and_SetAfterMeal(after_meal_radioButton.isChecked());
                        List<Integer> error_list = medicineValidator.getError_list();
                        if(error_list.isEmpty()){
                            medicine_entity_list.add(medicineEntity);
                            dialog.cancel();
                            medicineRecyclerViewAdapter.notifyDataSetChanged();
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
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        proceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long milli_second = ((MainActivity)getActivity()).getDoctorConsultEntity().getMilli_second();
                UserEntity userEntity = ((MainActivity)getActivity()).getUserEntity();

                PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
                PrescriptionValidator prescriptionValidator = new PrescriptionValidator(prescriptionEntity);
                prescriptionValidator.validate_and_SetAnalysis(analysis_editText.getText().toString());
                prescriptionValidator.validate_and_SetMedicine(medicine_entity_list);
                prescriptionValidator.validate_and_SetRefer(refer_checkBox.isChecked());
                if(refer_checkBox.isChecked()){
                    DoctorEntity referDoctorEntity = doctor_entity_map.get(doctor_spinner.getSelectedItem().toString());
                    prescriptionValidator.validate_and_SetRefer_doctor(referDoctorEntity.getName());
                    prescriptionValidator.validate_and_SetRefer_speciality(referDoctorEntity.getSpeciality());
                    prescriptionValidator.validate_and_SetRefer_contact(referDoctorEntity.getContact());
                }
                List<Integer> error_list = prescriptionValidator.getError_list();
                if(error_list.isEmpty()){
                    FirebaseHandler.setPrescription(userEntity.getContact(), milli_second, prescriptionEntity);
                    getFragmentManager().popBackStack();
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

    void modifyReferDoctorList(String selected_speciality){
        FirebaseHandler.getSpecialityList(selected_speciality, new FirebaseHandler.FirebaseInterface() {
            @Override
            public void onGetCallback(DataSnapshot dataSnapshot) {
                List<String> doctor_list = new LinkedList<>();
                doctor_entity_map = new HashMap<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    DoctorEntity doctorEntity = ds.getValue(DoctorEntity.class);
                    String doctor_name = doctorEntity.getName();
                    doctor_list.add(doctor_name);
                    doctor_entity_map.put(doctor_name, doctorEntity);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, doctor_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctor_spinner.setAdapter(arrayAdapter);
            }
        });
    }
}
