package com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository.HomeRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.util.List;

public class HomeUseCases {
    private static volatile HomeUseCases instance;
    private final HomeRepository homeRepository;
    private HomeUseCases(HomeRepository repository) {
        this.homeRepository = repository;
    }

    public static HomeUseCases getInstance() {
        if(instance == null) {
            instance = new HomeUseCases(HomeRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<List<TimeSlot>> getAllBookings(List<TimeSlot> bookings, NormalUser user, HomeListener homeListener) {

        return homeRepository.getAllBookings(bookings, user, homeListener);
    }

    public Result<List<User>> getAllUsers() {
        return homeRepository.getAllUsers();
    }

    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        return homeRepository.getHistory(user);
    }

    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot, HomeListener homeListener) {
        return homeRepository.callOffBooking(timeSlot, homeListener);
    }



    public Result<List<TimeSlot>> getUserTimeSlot(String username) {
        return homeRepository.getUserTimeSlot(username);
    }

    public Result<NormalUser> getUserInfo(String username) {
        return homeRepository.getUserInfo(username);
    }
    public void updateUser(NormalUser user){
        homeRepository.updateUser(user);
    }
}
