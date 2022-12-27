package com.example.teammanagementv2.create_team_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.teammanagementv2.R;
import com.example.teammanagementv2.databinding.ActivityCreateTeamBinding;
import com.example.teammanagementv2.databinding.ActivityTaskListBinding;
import com.example.teammanagementv2.entities.UserProfile;

import java.util.ArrayList;

public class ActivityCreateTeam extends AppCompatActivity {

    private ActivityCreateTeamBinding binding;
    private final ArrayList<String> toAddMembers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //dummy data
        UserProfile u1  = new UserProfile("mihai", "vlaicu", "mvlaicu", "aaa", "aaa", "aaa", "employee");
        UserProfile u2  = new UserProfile("mihai", "vlaicu", "mvlaicu", "aaa", "aaa", "aaa", "employee");
        UserProfile u3  = new UserProfile("mihai", "vlaicu", "mvlaicu", "aaa", "aaa", "aaa", "employee");
        UserProfile u4  = new UserProfile("mihai", "vlaicu", "mvlaicu", "aaa", "aaa", "aaa", "employee");
        UserProfile u5  = new UserProfile("mihai", "vlaicu", "mvlaicu", "aaa", "aaa", "aaa", "employee");
        ArrayList<UserProfile> userArrayList = new ArrayList<>();
        userArrayList.add(u1);
        userArrayList.add(u2);
        userArrayList.add(u3);
        userArrayList.add(u4);
        userArrayList.add(u5);

        ArrayAdapter<UserProfile> membersArrayAdapter = new TeamMemberAdapter(ActivityCreateTeam.this, userArrayList, (v, user) -> {
            // what happens when checkbox's onClick method is clicked
            // update a local list of members with the usernames of the members that will be added to the team
            toAddMembers.add(user.getUsername());
        });
        binding.listview.setAdapter(membersArrayAdapter);
        membersArrayAdapter.notifyDataSetChanged();
        Log.i("activity_create_team", "binding");
    }
}