package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface HomeSource {
    Result<List<TimeSlot>> getAllBookings(NormalUser user);
    Result<List<TimeSlot>> getHistory(NormalUser user);
    Result<TimeSlot> callOffBooking(TimeSlot timeSlot);
}
