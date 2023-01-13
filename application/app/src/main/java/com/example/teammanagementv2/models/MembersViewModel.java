package com.example.teammanagementv2.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.teammanagementv2.APIClient;
import com.example.teammanagementv2.entities.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MembersViewModel extends ViewModel {
    private final Executor executor = Executors.newSingleThreadExecutor();

    // Expose screen UI state
    private final APIClient apiClient = new APIClient();
    private final MutableLiveData<ArrayList<UserProfile>> possibleMembers;

    public MembersViewModel() {
        this.possibleMembers =  new MutableLiveData<ArrayList<UserProfile>>(new ArrayList<>());;
    }

    public LiveData<ArrayList<UserProfile>> getPossibleMembers( ) {
        return possibleMembers;
    }

    // Handle business logic
    public void loadPossibleMembers() {
        executor.execute(() -> {
            try {
                ArrayList<UserProfile> taskFromApi = apiClient.fetchEmployees();
                possibleMembers.postValue(taskFromApi);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("possibleMembersViewModel", "exception in members view model", e);
            }
        });
    }
}
