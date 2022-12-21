package com.example.teammanagementapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class CustomerModel {
    @PrimaryKey(autoGenerate = true)
    public int uid;
}
