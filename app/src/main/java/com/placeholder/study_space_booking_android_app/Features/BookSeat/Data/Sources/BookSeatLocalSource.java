package com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookSeatLocalSource {
    private final DBTimeSlotManager dbTimeSlotManager;
    private static volatile BookSeatLocalSource instance;

    BookSeatLocalSource(DBTimeSlotManager dbTimeSlotManager) {
        this.dbTimeSlotManager = dbTimeSlotManager;
    }

    public static BookSeatLocalSource getInstance() {
        if(instance == null) {
            instance = new BookSeatLocalSource(DBTimeSlotManager.getInstance());
        }
        return instance;
    }

    public Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId) {
        try {
            Cursor cursor = dbTimeSlotManager.getInBetweenTimeSlot(startTime, endTime, placeId);
            List<TimeSlot> list = new ArrayList<>();
            while(cursor.moveToNext()) {
                list.add(
                        new TimeSlot(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_USER_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_IN_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_STATE))
                        )
                );
            }
            return new Result.Accepted<>(list);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }
}
