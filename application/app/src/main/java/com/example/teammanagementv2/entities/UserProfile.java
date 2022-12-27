package com.example.teammanagementv2.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserProfile {
    private String firstName;
    private String lastName;
    private String username; // username
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String role;
    public static final UserProfile NULL_PROFILE = new UserProfile();

    public UserProfile(){}

    public UserProfile(String firstName, String lastName, String username, String passwordHash, String email, String phoneNumber, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password_hash='" + passwordHash + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                "role='" + role + '\'' +
                '}';
    }

    @JsonGetter("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonSetter("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonGetter("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonSetter("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonGetter("username")
    public String getUsername() {
        return username;
    }

    @JsonSetter("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonGetter("password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @JsonSetter("password_hash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonGetter("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonSetter("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonGetter("role")
    public String getRole() {
        return role;
    }

    @JsonSetter("role")
    public void setRole(String role) {
        this.role = role;
    }
}
