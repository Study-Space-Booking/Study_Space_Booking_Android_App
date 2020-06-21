package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.UserInfoListener;

import java.util.List;

public interface SeatRepository {
    Result<List<Seat>> getAllSeats(SeatListener seatListener);
    Result<List<TimeSlot>> getSeatTimeSlot(Integer id, final SeatListener seatListener);
    Result<NormalUser> getUserInfo(Integer id, UserInfoListener userInfoListener);
}
