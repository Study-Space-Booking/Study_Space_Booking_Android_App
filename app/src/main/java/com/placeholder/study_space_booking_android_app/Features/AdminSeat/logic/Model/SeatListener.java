package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface SeatListener {
    void onGetSeatSuccess(List<Seat> list);
    void onGetSeatFail(DatabaseError databaseError);
    void onSeatTimeSlotNotFound();
    void onSeatTimeSlotSuccess(List<TimeSlot> timeSlots);
    void onSeatTimeSlotFail(DatabaseError databaseError);

}
