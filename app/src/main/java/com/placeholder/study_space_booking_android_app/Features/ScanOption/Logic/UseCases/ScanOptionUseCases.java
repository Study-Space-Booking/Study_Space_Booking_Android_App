package com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources.ScanOptionLocalSource;

import java.util.ArrayList;
import java.util.List;

public class ScanOptionUseCases {
    private static volatile ScanOptionUseCases instance;
    private final ScanOptionLocalSource scanOptionLocalSource;
    public static User user = null;

    private ScanOptionUseCases(ScanOptionLocalSource scanOptionRepository) {
        this.scanOptionLocalSource = scanOptionRepository;
    }

    public static ScanOptionUseCases getInstance() {
        if(instance == null) {
            instance = new ScanOptionUseCases(ScanOptionLocalSource.getInstance());
        }
        return instance;
    }

    public TimeSlot getCurrentBooking(NormalUser user) {

        return scanOptionLocalSource.getCurrentTimeSlot(user);
    }

    public void updateTimeSlot() {
    }

}
