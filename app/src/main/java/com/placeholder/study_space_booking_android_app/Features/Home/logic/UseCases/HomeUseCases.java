package com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository.HomeRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.sql.Time;
import java.util.List;

public class HomeUseCases {
    private static volatile HomeUseCases instance;
    private final HomeRepository homeEepository;
    private HomeUseCases(HomeRepository repository) {
        this.homeEepository = repository;
    }

    public static HomeUseCases getInstance() {
        if(instance == null) {
            instance = new HomeUseCases(HomeRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<List<TimeSlot>> getAllBookings(NormalUser user) {

        return homeEepository.getAllBookings(user);
    }

    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        return homeEepository.getHistory(user);
    }

    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot) {
        return homeEepository.callOffBooking(timeSlot);
    }

    public Result<String> getPlaceName(Integer placeId) {
        return homeEepository.getPlaceName(placeId);
    }
}
