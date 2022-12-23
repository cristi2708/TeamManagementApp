package com.example.teammanagementv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<TaskOverview> {

    public TaskListAdapter(@NonNull Context context, ArrayList<TaskOverview> taskOverviewArrayList) {
        super(context, R.layout.task_list_item, taskOverviewArrayList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaskOverview taskOverview = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView taskNr = convertView.findViewById(R.id.taskNr);
        TextView taskDesc = convertView.findViewById(R.id.taskDesc);

        taskNr.setText(Integer.toString(taskOverview.taskNr));
        taskDesc.setText(taskOverview.taskDesc);

        return convertView;
    }
}
