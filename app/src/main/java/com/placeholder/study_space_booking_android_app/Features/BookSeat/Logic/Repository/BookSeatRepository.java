package com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository;

import android.content.Intent;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.sql.Time;

public interface BookSeatRepository {
    public Integer CurrStateOfSeat(TimeSlot t);
    public Result<TimeSlot> chooseTimeSlot(Integer bookTime, Integer UserId, Integer SeatId, Integer PlaceId);
}