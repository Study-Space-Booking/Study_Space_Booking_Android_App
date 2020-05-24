package com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.sql.Time;
import java.util.List;

public interface HomeRepository {
    Result<List<TimeSlot>> getAllBookings(NormalUser user);
    Result<List<TimeSlot>> getHistory(NormalUser user);
    Result<TimeSlot> callOffBooking(TimeSlot timeSlot);
}
