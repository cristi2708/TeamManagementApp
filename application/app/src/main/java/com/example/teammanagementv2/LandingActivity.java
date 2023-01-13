package com.example.teammanagementv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teammanagementv2.databinding.ActivityCreateTaskBinding;
import com.example.teammanagementv2.databinding.ActivityLandingBinding;

import java.util.Objects;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLandingBinding activityLandingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(activityLandingBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        activityLandingBinding.joinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        activityLandingBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }
}