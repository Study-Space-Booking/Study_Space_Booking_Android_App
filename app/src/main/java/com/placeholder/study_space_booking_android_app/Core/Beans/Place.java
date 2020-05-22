package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Place {
    Integer id;
    String name;

    public Place(Integer id, String name) {
        this.id = id;
        this.name = name;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
