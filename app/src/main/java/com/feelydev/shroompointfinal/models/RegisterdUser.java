package com.feelydev.shroompointfinal.models;

public class RegisterdUser {

    private String userId;
    private String userName;
    private String email;
    private String bio;
    private String role;

    public RegisterdUser(){}

    public RegisterdUser(String userId, String userName, String email, String bio, String role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getUserId() { return userId; }

    public String getRole() { return role; }
}
