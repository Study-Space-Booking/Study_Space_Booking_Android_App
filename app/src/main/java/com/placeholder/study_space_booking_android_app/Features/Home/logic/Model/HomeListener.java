package com.placeholder.study_space_booking_android_app.Features.Home.logic.Model;

import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface HomeListener {
    void onGetBookingsSuccess(List<TimeSlot> bookings);
    void onGetBookingsFailure(Exception exception);
    void onNoBookingFound();
    void onCallOffBookingSuccess();
    void onCallOffBookingFailure(Exception exception);
    void onCallOffBookingsClick(int position);
}
