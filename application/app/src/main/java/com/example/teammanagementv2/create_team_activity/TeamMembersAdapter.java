package com.example.teammanagementv2.create_team_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teammanagementv2.R;
import com.example.teammanagementv2.entities.UserProfile;

import java.util.ArrayList;

// TODO pass callbacks for the 2 buttons in constructor

public class TeamMembersAdapter extends ArrayAdapter<UserProfile> {

    private LayoutInflater layoutInflater;
    private final AddMemberActivityCallback memberActivityCallback;
    private ListItemLayout listItemLayout;

    public TeamMembersAdapter(@NonNull Context context, ArrayList<UserProfile> membersArrayList, AddMemberActivityCallback memberActivityCallback, ListItemLayout listItemLayout) {
        super(context, R.layout.task_list_item, membersArrayList);
        this.memberActivityCallback = memberActivityCallback;
        this.listItemLayout = listItemLayout;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserProfile currentMember = getItem(position);

        if (convertView == null) {
            if (listItemLayout == ListItemLayout.ADD_MEMBER_LIST_ITEM) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_member_list_item, parent, false);
                convertView.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memberActivityCallback.executeAction(currentMember);
                    }
                });
            }
            else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_member_list_item, parent, false);
                convertView.findViewById(R.id.selectMemberButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memberActivityCallback.executeAction(currentMember);
                    }
                });
            }
        }

        // Button checkBox = convertView.findViewById(R.id.checkBox);  // use button type to match both checkbox and button
        TextView firstName = convertView.findViewById(R.id.firstName);
        TextView lastName = convertView.findViewById(R.id.lastName);

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                memberActivityCallback.executeAction(currentMember);
//            }
//        });
        firstName.setText(currentMember.getFirstName());
        lastName.setText(currentMember.getLastName());

        return convertView;
    }
}
