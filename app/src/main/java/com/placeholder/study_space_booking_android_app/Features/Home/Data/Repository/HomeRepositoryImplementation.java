package com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.util.List;

public class HomeRepositoryImplementation implements HomeRepository {
    private static volatile HomeRepositoryImplementation instance;
    private final HomeLocalSource homeLocalSource;
    private final HomeRemoteSource homeRemoteSource;

    private HomeRepositoryImplementation(HomeLocalSource homeLocalSource, HomeRemoteSource homeRemoteSource) {
        this.homeLocalSource = homeLocalSource;
        this.homeRemoteSource = homeRemoteSource;
    }
    public static HomeRepositoryImplementation getInstance() {
        if(instance == null) {
            instance = new HomeRepositoryImplementation(HomeLocalSource.getInstance(), HomeRemoteSource.getInstance());
        }
        return instance;
    }

    @Override
    public Result<List<TimeSlot>> getAllBookings(List<TimeSlot> bookings, NormalUser user, HomeListener homeListener) {
        //return homeLocalSource.getAllBookings(bookings, user, homeListener);
        return homeRemoteSource.getAllBookings(bookings, user, homeListener);
    }

    @Override
    public Result<List<User>> getAllUsers() {
        return homeLocalSource.getAllUsers();
    }

    @Override
    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        return homeLocalSource.getHistory(user);
    }

    @Override
    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot, HomeListener homeListener) {
        //return homeLocalSource.callOffBooking(timeSlot, homeListener);
        return homeRemoteSource.callOffBooking(timeSlot, homeListener);
    }

    @Override
    public Result<List<TimeSlot>> getUserTimeSlot(String name) {
        return homeLocalSource.getUserTimeSlot(name);
    }

    @Override
    public Result<NormalUser> getUserInfo(String name) {
        return homeLocalSource.getUserInfo(name);
    }

    @Override
    public void updateUser(NormalUser user) {
        homeLocalSource.updateUser(user);
    }
}
