package com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.AdminHistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HistoryListener;
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
    public Result<List<NormalUser>> getAllUsers(AdminHistoryListener adminHistoryListener) {
        return homeRemoteSource.getAllUsers(adminHistoryListener);
    }

    @Override
    public Result<List<TimeSlot>> getHistory(NormalUser user, final HistoryListener historyListener) {
        return homeRemoteSource.getHistory(user, historyListener);
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
    public Result<NormalUser> getUserInfo(String name, final HistoryListener historyListener) {
        return homeRemoteSource.getUserInfo(name, historyListener);
    }

    @Override
    public Result<NormalUser> getUserInfoID(Integer name, final HistoryListener historyListener) {
        return homeRemoteSource.getUserInfoID(name, historyListener);
    }

    @Override
    public void updateUser(NormalUser user, final HistoryListener historyListener) {
        homeRemoteSource.updateUser(user, historyListener);
    }
}
