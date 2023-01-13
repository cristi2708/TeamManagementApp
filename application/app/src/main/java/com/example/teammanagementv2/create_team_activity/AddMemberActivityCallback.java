package com.example.teammanagementv2.create_team_activity;

import android.view.View;

import com.example.teammanagementv2.entities.UserProfile;

public interface AddMemberActivityCallback {
    public void executeAction(UserProfile user);
}
