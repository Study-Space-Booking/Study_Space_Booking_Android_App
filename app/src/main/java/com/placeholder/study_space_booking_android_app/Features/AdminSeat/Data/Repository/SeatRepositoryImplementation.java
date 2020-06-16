package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Sources.SeatLocalSource;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Repository.SeatRepository;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.util.List;

public class SeatRepositoryImplementation implements SeatRepository {
    private static volatile SeatRepositoryImplementation instance;
    private final SeatLocalSource seatLocalSource;
    // private final  ;

    private SeatRepositoryImplementation(SeatLocalSource seatLocalSource) {
        this.seatLocalSource = seatLocalSource;
      //  this.homeRemoteSource = homeRemoteSource;
    }
    public static SeatRepositoryImplementation getInstance() {
        if(instance == null) {
            instance = new SeatRepositoryImplementation(SeatLocalSource.getInstance());
        }
        return instance;
    }
    @Override
    public Result<List<Seat>> getAllSeats() {
        return seatLocalSource.getAllSeats();
    }

    @Override
    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id) {
        return seatLocalSource.getSeatTimeSlot(id);
    }

//    @Override
//    public Result<List<TimeSlot>> getAllBookings(NormalUser user) {
//        return seatLocalSource.getAllBookings(user);
//    }
//
//    @Override
//    public Result<List<User>> getAllUsers() {
//        return homeLocalSource.getAllUsers();
//    }
//
//    @Override
//    public Result<List<TimeSlot>> getHistory(NormalUser user) {
//        return homeLocalSource.getHistory(user);
//    }
//
//    @Override
//    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot) {
//        return homeLocalSource.callOffBooking(timeSlot);
//    }
//
//    @Override
//    public Result<String> getPlaceName(Integer placeId) {
//        return homeLocalSource.getPlaceName(placeId);
//    }
//
//    @Override
//    public Result<List<TimeSlot>> getUserTimeSlot(String name) {
//        return homeLocalSource.getUserTimeSlot(name);
//    }
//
//    @Override
//    public Result<NormalUser> getUserInfo(String name) {
//        return homeLocalSource.getUserInfo(name);
//    }
//
//    @Override
//    public void updateUser(NormalUser user) {
//        homeLocalSource.updateUser(user);
//    }
}
