package com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases;

import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources.ScanOptionLocalSource;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources.ScanOptionRemoteSource;

import java.util.ArrayList;
import java.util.List;

public class ScanOptionUseCases {
    private static volatile ScanOptionUseCases instance;
    private final ScanOptionLocalSource scanOptionLocalSource;
    private final ScanOptionRemoteSource scanOptionRemoteSource;
    public static User user = null;

    private ScanOptionUseCases(ScanOptionLocalSource scanOptionLocalSource, ScanOptionRemoteSource scanOptionRemoteSource) {
        this.scanOptionLocalSource = scanOptionLocalSource;
        this.scanOptionRemoteSource = scanOptionRemoteSource;
    }

    public static ScanOptionUseCases getInstance() {
        Log.d("debug", "scan user cases get instance");
        if(instance == null) {
            instance = new ScanOptionUseCases(ScanOptionLocalSource.getInstance(),
                    ScanOptionRemoteSource.getInstance());
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
