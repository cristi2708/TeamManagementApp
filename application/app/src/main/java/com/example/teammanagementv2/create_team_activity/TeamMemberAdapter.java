package com.example.teammanagementv2.create_team_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teammanagementv2.R;
import com.example.teammanagementv2.create_team_activity.AddMemberActivityCallback;
import com.example.teammanagementv2.entities.UserProfile;

import java.util.ArrayList;

// TODO pass callbacks for the 2 buttons in constructor


public class TeamMemberAdapter extends ArrayAdapter<UserProfile> {

    private LayoutInflater layoutInflater;
    private final AddMemberActivityCallback addMemberActivityCallback;

    public TeamMemberAdapter(@NonNull Context context, ArrayList<UserProfile> membersArrayList, AddMemberActivityCallback addMemberActivityCallback) {
        super(context, R.layout.task_list_item, membersArrayList);
        this.addMemberActivityCallback = addMemberActivityCallback;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserProfile currentMember = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_member_list_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        TextView firstName = convertView.findViewById(R.id.firstName);
        TextView lastName = convertView.findViewById(R.id.lastName);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemberActivityCallback.executeAction(v, currentMember);
            }
        });
        firstName.setText(currentMember.getFirstName());
        lastName.setText(currentMember.getLastName());

        return convertView;
    }
}
