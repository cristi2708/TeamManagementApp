package com.example.teammanagementv2.task_list_activity;

import android.view.View;

import com.example.teammanagementv2.entities.Task;

public interface TaskListActivityCallback {
    void executeAction(View view, Task task);
}
