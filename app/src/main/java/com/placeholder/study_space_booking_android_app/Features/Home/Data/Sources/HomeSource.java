package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.AdminHistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;

import java.sql.Time;
import java.util.List;

public interface HomeSource {
    Result<List<TimeSlot>> getAllBookings(List<TimeSlot> bookings, NormalUser user, HomeListener homeListener);
    Result<List<TimeSlot>> getHistory(NormalUser user, final HistoryListener historyListener);
    Result<TimeSlot> callOffBooking(TimeSlot timeSlot, HomeListener homeListener);
    Result<List<NormalUser>> getAllUsers(AdminHistoryListener adminHistoryListener);
    Result<NormalUser> getUserInfo(String name, final HistoryListener historyListener);
    Result<NormalUser> getUserInfoID(Integer name, final HistoryListener historyListener);
    void updateUser(NormalUser user, final HistoryListener historyListener);
}
