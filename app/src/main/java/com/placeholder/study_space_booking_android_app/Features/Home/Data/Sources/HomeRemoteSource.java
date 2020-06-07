package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

public class HomeRemoteSource {
    private static volatile HomeRemoteSource instance;

    public static HomeRemoteSource getInstance() {
        if(instance == null) {
            instance = new HomeRemoteSource();
        }
        return instance;
    }
}
