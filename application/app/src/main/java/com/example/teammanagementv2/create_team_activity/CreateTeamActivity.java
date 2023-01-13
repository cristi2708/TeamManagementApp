package com.example.teammanagementv2.create_team_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.teammanagementv2.databinding.ActivityCreateTeamBinding;
import com.example.teammanagementv2.entities.UserProfile;
import com.example.teammanagementv2.models.MembersViewModel;

import java.util.ArrayList;

public class CreateTeamActivity extends AppCompatActivity {

    private ActivityCreateTeamBinding binding;
    private final ArrayList<String> toAddMembers = new ArrayList<>();
    private MembersViewModel possibleMembersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("activity_create_team", "create team button clicked, will add members: " + String.join(",", toAddMembers));
            }
        });

        possibleMembersViewModel = new ViewModelProvider(this).get(MembersViewModel.class);

        ArrayList<UserProfile> possibleMembersArrayList = new ArrayList<>();

        ArrayAdapter<UserProfile> membersArrayAdapter = new TeamMembersAdapter(CreateTeamActivity.this, possibleMembersArrayList, (user) -> {
            // what happens when checkbox's onClick method is clicked
            // update a local list of members with the usernames of the members that will be added to the team
            toAddMembers.add(user.getUsername());
        }, ListItemLayout.ADD_MEMBER_LIST_ITEM);

        possibleMembersViewModel.getPossibleMembers().observe(this, members -> {
            possibleMembersArrayList.clear();
            possibleMembersArrayList.addAll(members);
            membersArrayAdapter.notifyDataSetChanged();
        });
        possibleMembersViewModel.loadPossibleMembers();

        binding.listview.setAdapter(membersArrayAdapter);
        membersArrayAdapter.notifyDataSetChanged();
    }
}