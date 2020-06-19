package com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Model;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface BookSeatListener {
    void onGetBookingsFailure(DatabaseError databaseError);
    void onGetBookingsSuccess(List<TimeSlot> timeSlots);
    void onGetSeatFailure(DatabaseError databaseError);
    void onAddBookingsSuccess();
    void onGetSeatSuccess(List<Integer> seats);
}
