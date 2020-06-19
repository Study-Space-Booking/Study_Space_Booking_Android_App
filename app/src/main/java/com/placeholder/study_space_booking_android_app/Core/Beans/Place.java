package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Place {
    Integer id;
    String key;

    public Place(Integer id, String name) {
        this.id = id;
        this.key = name;
    }



    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String name) {
        this.key = name;
    }
}
