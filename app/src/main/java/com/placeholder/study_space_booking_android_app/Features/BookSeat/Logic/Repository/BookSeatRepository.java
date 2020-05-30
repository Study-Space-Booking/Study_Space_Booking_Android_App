package com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository;

import android.content.Intent;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.sql.Time;
import java.util.List;

public interface BookSeatRepository {
    public Integer CurrStateOfSeat(TimeSlot t);
    public Result<TimeSlot> chooseTimeSlot(Integer bookTime, Integer UserId, Integer SeatId, Integer PlaceId);
    Result<List<Integer>> getAllSeatId(Integer placeId);
    Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId);
    Result<TimeSlot> insertBooking(TimeSlot timeSlot);

}