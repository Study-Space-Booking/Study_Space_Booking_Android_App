package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface SeatRepository {
    Result<List<Seat>> getAllSeats();
    Result<List<TimeSlot>> getSeatTimeSlot(Integer id);
}
