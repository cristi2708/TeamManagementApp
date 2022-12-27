package com.example.teammanagementv2.task_list_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teammanagementv2.R;
import com.example.teammanagementv2.entities.Task;

import java.util.ArrayList;

// TODO pass callbacks for the 2 buttons in constructor


public class TaskListAdapter extends ArrayAdapter<Task> {

    private LayoutInflater layoutInflater;
    private TaskListActivityCallback detailsBtnCallback;

    public TaskListAdapter(@NonNull Context context, ArrayList<Task> taskOverviewArrayList, TaskListActivityCallback button1Callback) {
        super(context, R.layout.task_list_item, taskOverviewArrayList);
        this.detailsBtnCallback = button1Callback;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task taskOverview = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView taskNr = convertView.findViewById(R.id.taskNr);
        TextView taskDesc = convertView.findViewById(R.id.taskDesc);

        ImageButton details  = convertView.findViewById(R.id.zoomButton);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBtnCallback.executeAction(v, taskOverview);
            }
        });

        taskNr.setText(Integer.toString(position+1));
        taskDesc.setText(taskOverview.getDesc());


        return convertView;
    }
}
