package com.example.teammanagementv2.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class Task {
    private String description;
    private String assignee;
    private String reporter;
    private LocalDateTime dueDate;

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("assignee")
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @JsonSetter("reporter")
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSetter("due_date")
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @JsonGetter("description")
    public String getDesc() {
        return description;
    }

    @JsonGetter("assignee")
    public String getAssignee() {
        return assignee;
    }

    @JsonGetter("reporter")
    public String getReporter() {
        return reporter;
    }

    @JsonGetter("due_date")
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Task() {

    }

    public Task(String desc, String assignee, String reporter, LocalDateTime dueDate) {
        this.description = desc;
        this.assignee = assignee;
        this.reporter = reporter;
        this.dueDate = dueDate;
    }
}
