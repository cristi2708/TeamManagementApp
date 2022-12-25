package com.example.teammanagementv2.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.teammanagementv2.APIClient;
import com.example.teammanagementv2.entities.Task;
import com.example.teammanagementv2.entities.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TaskViewModel extends ViewModel {
    private final Executor executor = Executors.newSingleThreadExecutor();

    // Expose screen UI state
    private final APIClient apiClient = new APIClient();
    private MutableLiveData<ArrayList<Task>> tasks;

    public TaskViewModel() {
        this.tasks =  new MutableLiveData<ArrayList<Task>>(new ArrayList<>());;
    }


    public LiveData<ArrayList<Task>> getTasks( ) {
        return tasks;
    }

    // Handle business logic
    public void loadUserTasks(String username) {
        executor.execute(() -> {
            try {
                ArrayList<Task> taskFromApi = apiClient.fetchUserTasks(username);
                tasks.postValue(taskFromApi);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("taskViewModel", "exception in task view model", e);
            }
        });
    }
}
