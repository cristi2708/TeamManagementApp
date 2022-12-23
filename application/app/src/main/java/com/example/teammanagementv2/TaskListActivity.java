package com.example.teammanagementv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;

import com.example.teammanagementv2.databinding.ActivityMainBinding;
import com.example.teammanagementv2.databinding.ActivityTaskListBinding;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    ActivityTaskListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_task_list);
        View view = binding.getRoot();
        setContentView(view);

        // static data for the list items
        int[] taskNrs = {1, 2, 3};
        String[] taskDescs = {"abc", "abd", "aac"};

        ArrayList<TaskOverview> taskOverviewArrayList = new ArrayList<>();
        for(int i=0; i<3; i++) {
            TaskOverview taskOverview = new TaskOverview(taskNrs[i], taskDescs[i]);
            taskOverviewArrayList.add(taskOverview);
        }

        ListAdapter listAdapter = new TaskListAdapter(TaskListActivity.this, taskOverviewArrayList);
        binding.listview.setAdapter(listAdapter);
    }
}