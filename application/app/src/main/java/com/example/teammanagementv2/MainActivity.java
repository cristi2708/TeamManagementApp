package com.example.teammanagementv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teammanagementv2.create_team_activity.ActivityCreateTeam;
import com.example.teammanagementv2.entities.UserProfile;
import com.example.teammanagementv2.models.TeamMemberViewModel;
import com.example.teammanagementv2.task_list_activity.TaskListActivity;

public class MainActivity extends AppCompatActivity {

    private Button redirectButton;
    private Button logOutButton;

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView roleTV;

    private Boolean isLead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member);

        redirectButton = findViewById(R.id.redirectButton);
        logOutButton = findViewById(R.id.logoutButton);

        firstNameTV = findViewById(R.id.firstNameTV);
        lastNameTV = findViewById(R.id.lastNameTV);
        roleTV = findViewById(R.id.roleTV);

        TeamMemberViewModel teamMemberViewModel = new ViewModelProvider(this).get(TeamMemberViewModel.class);
        SharedPreferences sharedPref = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        String usernameSp = sharedPref.getString("username", null);

        teamMemberViewModel.loadUserProfile(usernameSp);
        teamMemberViewModel.getUserProfile().observe(this, new Observer<UserProfile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(UserProfile userProfile) {
                if (!userProfile.equals(UserProfile.NULL_PROFILE)) {
                    firstNameTV.setText(userProfile.getFirstName());
                    lastNameTV.setText(userProfile.getLastName());
                    roleTV.setText(userProfile.getRole());
                    if (userProfile.getRole().equals("lead")) {
                        redirectButton.setText("Manage Team");
                        isLead = true;
                    }
                }
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete from sp
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (MainActivity.this.isLead) {
                    intent = new Intent(getApplicationContext(), ActivityCreateTeam.class);
                } else
                    intent = new Intent(getApplicationContext(), TaskListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}