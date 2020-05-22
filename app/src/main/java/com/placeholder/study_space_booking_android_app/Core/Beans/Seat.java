package com.placeholder.study_space_booking_android_app.Core.Beans;

public class Seat {
    Integer id;
    Integer placeId;

    public Seat(Integer id, Integer placeId) {
        this.id = id;
        this.placeId = placeId;
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
