package com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases;

import android.util.Log;

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

    private ScanOptionUseCases(ScanOptionLocalSource scanOptionLocalSource) {
        this.scanOptionLocalSource = scanOptionLocalSource;
    }

    public static ScanOptionUseCases getInstance() {
        Log.d("debug", "scan user cases get instance");
        if(instance == null) {
            instance = new ScanOptionUseCases(ScanOptionLocalSource.getInstance());
        }
        return instance;
    }

    public TimeSlot getSigninTimeSlot(NormalUser user) {

        return scanOptionLocalSource.getSigninTimeSlot(user);
    }

    public TimeSlot getSignOutTimeSlot(NormalUser user) {
        return scanOptionLocalSource.getSignOutTimeSlot(user);
    }

    public TimeSlot getTmpBackTimeSlot(NormalUser user) {
        return scanOptionLocalSource.getTmpBackTimeSlot(user);
    }
    public TimeSlot getTmpLeaveTimeSlot(NormalUser user) {
        return scanOptionLocalSource.getTmpLeaveTimeSlot(user);
    }

}
