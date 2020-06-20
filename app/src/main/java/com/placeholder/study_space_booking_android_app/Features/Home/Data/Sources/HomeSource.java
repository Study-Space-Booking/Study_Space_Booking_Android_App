package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;

import java.sql.Time;
import java.util.List;

public interface HomeSource {
    Result<List<TimeSlot>> getAllBookings(List<TimeSlot> bookings, NormalUser user, HomeListener homeListener);
    Result<List<TimeSlot>> getHistory(NormalUser user);
    Result<TimeSlot> callOffBooking(TimeSlot timeSlot, HomeListener homeListener);
    Result<List<User>> getAllUsers();
    Result<NormalUser> getUserInfo(String name);
    void updateUser(NormalUser user);
}
