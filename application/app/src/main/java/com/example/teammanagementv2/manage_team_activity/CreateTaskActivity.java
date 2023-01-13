package com.example.teammanagementv2.manage_team_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.teammanagementv2.APIClient;
import com.example.teammanagementv2.create_team_activity.ListItemLayout;
import com.example.teammanagementv2.create_team_activity.TeamMembersAdapter;
import com.example.teammanagementv2.databinding.ActivityCreateTaskBinding;
import com.example.teammanagementv2.entities.Task;
import com.example.teammanagementv2.entities.UserProfile;
import com.example.teammanagementv2.models.MembersViewModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateTaskActivity extends AppCompatActivity {
    private ActivityCreateTaskBinding activityCreateTaskBinding;
    private String lead;
    private String assignee;
    private APIClient apiClient = new APIClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateTaskBinding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        setContentView(activityCreateTaskBinding.getRoot());

        // get current team lead from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lead = extras.getString("team_lead_name");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // use view model to load all members of a team
        MembersViewModel possibleMembersViewModel = new ViewModelProvider(this).get(MembersViewModel.class);

        ArrayList<UserProfile> possibleMembersArrayList = new ArrayList<>();

        ArrayAdapter<UserProfile> membersArrayAdapter = new TeamMembersAdapter(CreateTaskActivity.this, possibleMembersArrayList, (userProfile) -> {
            // what happens when checkbox's onClick method is clicked
            // block so only one member can be selected
            // activityCreateTaskBinding.tv1.setText("selected: " + assignee);
            assignee = userProfile.getUsername();
        }, ListItemLayout.SELECT_MEMBER_LIST_ITEM);

        possibleMembersViewModel.getPossibleMembers().observe(this, members -> {
            possibleMembersArrayList.clear();
            possibleMembersArrayList.addAll(members);
            membersArrayAdapter.notifyDataSetChanged();
        });
        possibleMembersViewModel.loadPossibleMembers();

        activityCreateTaskBinding.listview.setAdapter(membersArrayAdapter);
        membersArrayAdapter.notifyDataSetChanged();

        // send request to create task

        activityCreateTaskBinding.dueDateButton.setOnClickListener(new View.OnClickListener() {  // on click open a calendar dialog
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                StringBuilder dayOfMonthStr = new StringBuilder();
                                StringBuilder monthOfYearStr = new StringBuilder();
                                if (dayOfMonth < 10) {
                                    dayOfMonthStr.append('0');
                                }
                                dayOfMonthStr.append(Integer.toString(dayOfMonth));
                                if (monthOfYear < 10) {
                                    monthOfYearStr.append('0');
                                }
                                monthOfYearStr.append(Integer.toString(monthOfYear+1));
                                activityCreateTaskBinding.dueDateButton.setText(dayOfMonthStr + "/" + monthOfYearStr + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityCreateTaskBinding.createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = activityCreateTaskBinding.taskDescTV.getText().toString();
                String dateString = activityCreateTaskBinding.dueDateButton.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US);
                LocalDate localDate = LocalDate.parse(dateString, formatter);
                LocalDateTime localDateTime = localDate.atStartOfDay();
                // pt moment presupunem ca am bagat date in toate input placeholder-ele cand apasam pe buton
                try {
                    apiClient.createTaskAsync(new Task(desc, assignee, lead, localDateTime), new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() != 200) {
                                        Toast.makeText(getApplicationContext(), "unable to create task",
                                                Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    Toast.makeText(getApplicationContext(), "task created successfully",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}