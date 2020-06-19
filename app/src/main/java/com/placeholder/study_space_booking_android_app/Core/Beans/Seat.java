package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Seat {
    String key;
    Integer id;
    Integer placeId;

    public Seat() {}

    public Seat(Integer id, Integer placeId) {
        this.id = id;
        this.placeId = placeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
