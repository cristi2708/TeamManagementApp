package com.example.teammanagementv2.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.teammanagementv2.APIClient;
import com.example.teammanagementv2.entities.Team;
import com.example.teammanagementv2.entities.UserProfile;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//view model used for fetching the profile of the logged in user and the team it coordinates or it belongs to (if it has one)

public class UserViewModel extends ViewModel {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final APIClient apiClient = new APIClient();

    // Expose screen UI state
    private final MutableLiveData<UserProfile> userProfile;
    private final MutableLiveData<Team> userTeam;
    private final MutableLiveData<Boolean> errorFetching;   // used to show a toast and disable the button when an error is encountered

    public UserViewModel() {
        this.errorFetching = new MutableLiveData<Boolean>();;
        this.userTeam = new MutableLiveData<Team>(Team.NULL_TEAM);
        this.userProfile = new MutableLiveData<UserProfile>(UserProfile.NULL_PROFILE);
    }

    public LiveData<Team> getUserTeam() {
        return userTeam;
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public LiveData<Boolean> getErrorFetching() {
        return errorFetching;
    }

    // Handle business logic
    public void loadUserProfile(String username) {
        executor.execute(() -> {
            try {
                userProfile.postValue(apiClient.fetchUserProfile(username));
            } catch (IOException e) {
                errorFetching.postValue(Boolean.TRUE);
                e.printStackTrace();
            }
        });
    }

    public void loadUserTeam(String username) {
        executor.execute(() -> {
            try {
                userTeam.postValue(apiClient.fetchUserTeam(username));
            } catch (IOException e) {
                errorFetching.postValue(Boolean.TRUE);
                e.printStackTrace();
            }
        });
    }
}
