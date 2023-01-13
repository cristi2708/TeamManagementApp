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
import android.widget.Toast;

import com.example.teammanagementv2.create_team_activity.CreateTeamActivity;
import com.example.teammanagementv2.databinding.ActivityCreateTeamBinding;
import com.example.teammanagementv2.databinding.ActivityMainBinding;
import com.example.teammanagementv2.entities.Team;
import com.example.teammanagementv2.entities.UserProfile;
import com.example.teammanagementv2.manage_team_activity.CreateTaskActivity;
import com.example.teammanagementv2.models.UserViewModel;
import com.example.teammanagementv2.task_list_activity.TaskListActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private UserViewModel userViewModel;
    private boolean isLead;
    private boolean hasTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // retrieve username of logged in user, (was saved in shared prefs when the user logged in)
        SharedPreferences sharedPref = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        String usernameSp = sharedPref.getString("username", null);

        userViewModel.loadUserProfile(usernameSp);
        userViewModel.loadUserTeam(usernameSp);

        userViewModel.getUserProfile().observe(this, new Observer<UserProfile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(UserProfile userProfile) {
                if (!userProfile.equals(UserProfile.NULL_PROFILE)) {
                    activityMainBinding.firstNameTV.setText(userProfile.getFirstName());
                    activityMainBinding.lastNameTV.setText(userProfile.getLastName());
                    activityMainBinding.roleTV.setText(userProfile.getRole());
                    if (userProfile.getRole().equals("lead")) {
                        activityMainBinding.redirectButton.setText("Manage Team");
                        isLead = true;
                    } else {
                        isLead = false;
                    }
                }
            }
        });

        userViewModel.getErrorFetching().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getApplicationContext(), "Error fetching data from the API",
                        Toast.LENGTH_SHORT).show();
                activityMainBinding.redirectButton.setEnabled(false);
            }
        });

        userViewModel.getUserTeam().observe(this, new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                hasTeam = !team.equals(Team.NULL_TEAM);
            }
        });

        activityMainBinding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete from shared prefs
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        activityMainBinding.redirectButton.setOnClickListener(new View.OnClickListener() {
            /*
            will redirect the user to:
                -> an activity to create team, if the user is a lead with no team created
                -> an activity to manage its team, if the user is a lead that has already created a team
                -> an activity to view it's tasks if the the user is a team member
                -> if the user does not belong to any team, a toast is displayed
            */
            @Override
            public void onClick(View view) {
                if (isLead && hasTeam) {
                    Toast.makeText(getApplicationContext(), "start manage team activity",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), CreateTaskActivity.class);
                    intent.putExtra("team_lead_name", usernameSp);
                    startActivity(intent);
                }
                if (isLead && !hasTeam) {
                    Toast.makeText(getApplicationContext(), "start create team activity",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CreateTeamActivity.class));
                }
                if (!isLead && hasTeam) {
                    Toast.makeText(getApplicationContext(), "start task list activity",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), TaskListActivity.class));
                }
            }
        });
    }
}
