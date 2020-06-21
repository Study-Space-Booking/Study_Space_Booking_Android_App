package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Sources.SeatLocalSource;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Sources.SeatRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.UserInfoListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Repository.SeatRepository;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository.HomeRepository;

import java.util.List;

public class SeatRepositoryImplementation implements SeatRepository {
    private static volatile SeatRepositoryImplementation instance;
    private final SeatLocalSource seatLocalSource;
    private final SeatRemoteSource seatRemoteSource;

    private SeatRepositoryImplementation(SeatLocalSource seatLocalSource, SeatRemoteSource seatRemoteSource) {
        this.seatLocalSource = seatLocalSource;
        this.seatRemoteSource = seatRemoteSource;
    }

    public static SeatRepositoryImplementation getInstance() {
        if (instance == null) {
            instance = new SeatRepositoryImplementation(SeatLocalSource.getInstance(), SeatRemoteSource.getInstance());
        }
        return instance;
    }

    @Override
    public Result<List<Seat>> getAllSeats(SeatListener seatListener) {
        return seatRemoteSource.getAllSeats(seatListener);
    }

    @Override
    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id, final SeatListener seatListener) {
        return seatRemoteSource.getSeatTimeSlot(id, seatListener);
    }

    @Override
    public Result<NormalUser> getUserInfo(Integer id, UserInfoListener userInfoListener) {
        return seatRemoteSource.getUserInfo(id, userInfoListener);
    }
}