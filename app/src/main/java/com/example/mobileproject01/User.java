package com.example.mobileproject01;

public class User {
    private int id;
    private String userName;
    private int password;
    private String emailAddress;
    private int isInUse;

    public int getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getIsInUse() {
        return isInUse;
    }

    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setIsInUse(int isInUse) {
        this.isInUse = isInUse;
    }

    public void setId(int id) {
        this.id = id;
    }
}
