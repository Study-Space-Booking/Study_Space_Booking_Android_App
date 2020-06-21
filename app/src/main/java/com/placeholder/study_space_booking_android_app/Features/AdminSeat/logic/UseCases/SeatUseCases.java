package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.UseCases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Repository.SeatRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.UserInfoListener;
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

    public Result<List<Seat>> getAllSeats(SeatListener seatListener) {
        return seatRepository.getAllSeats(seatListener);
    }

    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id, final SeatListener seatListener) {
        return seatRepository.getSeatTimeSlot(id, seatListener);
    }

    public Result<NormalUser> getUserInfo(Integer id, UserInfoListener userInfoListener) {
        return seatRepository.getUserInfo(id, userInfoListener);
    }

}