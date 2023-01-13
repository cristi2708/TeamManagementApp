package com.example.teammanagementv2.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class Team {
    private String teamName;    // _id
    private String teamLeadName;
    private ArrayList<String> employees;
    public static final Team NULL_TEAM = new Team();

    public Team(String teamName, String teamLeadName, ArrayList<String> employees) {
        this.teamName = teamName;
        this.teamLeadName = teamLeadName;
        this.employees = employees;
    }

    public Team() {}

    @JsonGetter("team_name")
    public String getTeamName() {
        return teamName;
    }

    @JsonSetter("team_name")
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @JsonGetter("team_lead_name")
    public String getTeamLeadName() {
        return teamLeadName;
    }

    @JsonSetter("team_lead_name")
    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    @JsonGetter("employees")
    public ArrayList<String> getEmployees() {
        return employees;
    }

    @JsonSetter("employees")
    public void setEmployees(ArrayList<String> employees) {
        this.employees = employees;
    }
}
