package com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository.HomeRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

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

    public Result<List<User>> getAllUsers() {
        return homeEepository.getAllUsers();
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

    public Result<List<TimeSlot>> getUserTimeSlot(String username) {
        return homeEepository.getUserTimeSlot(username);
    }

    public Result<NormalUser> getUserInfo(String username) {
        return homeEepository.getUserInfo(username);
    }
    public void updateUser(NormalUser user){
        homeEepository.updateUser(user);
    }
}
