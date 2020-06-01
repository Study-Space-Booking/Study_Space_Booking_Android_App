package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import com.placeholder.study_space_booking_android_app.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;

public class HomeRemoteSource {
    private static volatile HomeRemoteSource instance;

    public static HomeRemoteSource getInstance() {
        if(instance == null) {
            instance = new HomeRemoteSource();
        }
        return instance;
    }
}
