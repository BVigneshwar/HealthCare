package com.vignesh.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vignesh.healthcare.common.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new LoginFragment()).commit();
    }

}
