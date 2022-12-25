package com.example.teammanagementv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.teammanagementv2.databinding.ActivityMainBinding;
import com.example.teammanagementv2.databinding.ActivityTaskListBinding;
import com.example.teammanagementv2.entities.Task;
import com.example.teammanagementv2.entities.UserProfile;
import com.example.teammanagementv2.models.TaskViewModel;
import com.example.teammanagementv2.models.TeamMemberViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskListActivity extends AppCompatActivity {

    ActivityTaskListBinding binding;
    APIClient apiClient = new APIClient();
    TaskViewModel tasksViewModel;
    private Boolean popupDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        tasksViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        setContentView(R.layout.activity_task_list);
        View view = binding.getRoot();
        setContentView(view);


        ArrayList<Task> taskOverviewArrayList = new ArrayList<>();
        ArrayAdapter<Task> listAdapter = new TaskListAdapter(TaskListActivity.this,
                taskOverviewArrayList, (v, task) -> {
                    // details callback
                    if (popupDisplayed) {
                        return;
                    }
                    popupDisplayed = true;

                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.task_details_popup, null);

                    TextView assignee = container.findViewById(R.id.desc);
                    TextView reporter = container.findViewById(R.id.reporter);
                    TextView dueDate = container.findViewById(R.id.dueDate);

                    assignee.setText(task.getDesc());
                    reporter.setText(task.getReporter());
                    dueDate.setText(task.getDueDate().toString());

                    PopupWindow popupWindow = new PopupWindow(container, 600, 600);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            popupDisplayed = false;
                            return true;
                        }
                    });
                }
        );

        tasksViewModel.getTasks().observe(this, tasks -> {
            taskOverviewArrayList.clear();
            taskOverviewArrayList.addAll(tasks);
            listAdapter.notifyDataSetChanged();
        });
        tasksViewModel.loadUserTasks("mvlaicu");
        binding.listview.setAdapter(listAdapter);
    }
}