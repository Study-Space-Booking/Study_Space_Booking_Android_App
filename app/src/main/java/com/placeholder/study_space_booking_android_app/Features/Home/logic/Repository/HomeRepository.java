package com.placeholder.study_space_booking_android_app.Features.Home.logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;

import java.util.List;

public interface HomeRepository {
    Result<List<TimeSlot>> getAllBookings(List<TimeSlot> bookings, NormalUser user, HomeListener homeListener);
    Result<List<TimeSlot>> getHistory(NormalUser user);
    Result<TimeSlot> callOffBooking(TimeSlot timeSlot, HomeListener homeListener);
    Result<List<User>> getAllUsers();
    Result<List<TimeSlot>> getUserTimeSlot(String name);
    Result<NormalUser> getUserInfo(String name);
    void updateUser(NormalUser user);
}
