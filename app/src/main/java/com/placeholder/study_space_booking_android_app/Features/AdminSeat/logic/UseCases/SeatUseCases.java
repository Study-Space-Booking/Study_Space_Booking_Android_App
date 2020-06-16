package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.UseCases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Repository.SeatRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Repository.SeatRepository;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Repository.HomeRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.util.List;

public class SeatUseCases {
    private static volatile SeatUseCases instance;
    private final SeatRepository seatRepository;
    private SeatUseCases(SeatRepository repository) {
        this.seatRepository = repository;
    }

    public static SeatUseCases getInstance() {
        if(instance == null) {
            instance = new SeatUseCases(SeatRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<List<Seat>> getAllSeats() {
        return seatRepository.getAllSeats();
    }

    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id) {
        return seatRepository.getSeatTimeSlot(id);
    }
//    public Result<List<TimeSlot>> getAllBookings(NormalUser user) {
//
//        return homeEepository.getAllBookings(user);
//    }
//
//    public Result<List<User>> getAllUsers() {
//        return homeEepository.getAllUsers();
//    }
//
//
//    public Result<List<TimeSlot>> getHistory(NormalUser user) {
//        return homeEepository.getHistory(user);
//    }
//
//    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot) {
//        return homeEepository.callOffBooking(timeSlot);
//    }
//
//    public Result<String> getPlaceName(Integer placeId) {
//        return homeEepository.getPlaceName(placeId);
//    }
//
//    public Result<List<TimeSlot>> getUserTimeSlot(String username) {
//        return homeEepository.getUserTimeSlot(username);
//    }
//
//    public Result<NormalUser> getUserInfo(String username) {
//        return homeEepository.getUserInfo(username);
//    }
//    public void updateUser(NormalUser user){
//        homeEepository.updateUser(user);
//    }
}