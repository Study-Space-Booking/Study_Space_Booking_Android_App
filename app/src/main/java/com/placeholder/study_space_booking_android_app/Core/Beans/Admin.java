package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Admin {
    Integer id;
    String name;
    String Password;

    public Admin(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        Password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
