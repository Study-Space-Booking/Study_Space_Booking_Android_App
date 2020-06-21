package com.placeholder.study_space_booking_android_app.Core.Beans;

public class User {
    private Integer id;
    private String name;
    private String password;

    public User() {

    }

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUserName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
}