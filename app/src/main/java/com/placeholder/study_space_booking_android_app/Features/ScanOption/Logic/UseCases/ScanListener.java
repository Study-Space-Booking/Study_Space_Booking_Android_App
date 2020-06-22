package com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface ScanListener {
    void onGetTimeSlotSuccess(List<TimeSlot> t);
    void onGetTimeSlotNotFound();
    void onGetTimeSlotFail(DatabaseError databaseError);
}
