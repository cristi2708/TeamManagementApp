package com.example.teammanagementv2.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.teammanagementv2.APIClient;
import com.example.teammanagementv2.entities.UserProfile;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TeamMemberViewModel extends ViewModel {
    private final Executor executor = Executors.newSingleThreadExecutor();

    // Expose screen UI state
    private final APIClient apiClient = new APIClient();
    private final MutableLiveData<UserProfile> userProfile;

    public TeamMemberViewModel() {
        this.userProfile = new MutableLiveData<UserProfile>(UserProfile.NULL_PROFILE);;
    }

    public LiveData<UserProfile> getUserProfile( ) {
        return userProfile;
    }

    // Handle business logic
    public void loadUserProfile(String username) {
        executor.execute(() -> {
            try {
                userProfile.postValue(apiClient.fetchUserProfile(username));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
