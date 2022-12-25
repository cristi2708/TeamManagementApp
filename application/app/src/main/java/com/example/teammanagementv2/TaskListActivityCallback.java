package com.example.teammanagementv2;

import android.view.View;

import com.example.teammanagementv2.entities.Task;

public interface TaskListActivityCallback {
    void executeAction(View view, Task task);
}
