package com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources;

import android.database.Cursor;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBSeatManager;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Activity.MacBookSeatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookSeatLocalSource {
    private final DBTimeSlotManager dbTimeSlotManager;
    private static volatile BookSeatLocalSource instance;
    private final DBSeatManager dbSeatManager;

    BookSeatLocalSource(DBTimeSlotManager dbTimeSlotManager, DBSeatManager dbSeatManager) {
        this.dbTimeSlotManager = dbTimeSlotManager;
        this.dbSeatManager = dbSeatManager;
    }

    public static BookSeatLocalSource getInstance() {
        if(instance == null) {
            instance = new BookSeatLocalSource(DBTimeSlotManager.getInstance(), DBSeatManager.getInstance());
        }
        return instance;
    }

    public Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId) {
        try {
            Cursor cursor = dbTimeSlotManager.getInBetweenTimeSlot(startTime, endTime, placeId);
            List<TimeSlot> list = new ArrayList<>();
            int i = 1;
            while (cursor.moveToNext()) {
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
                Log.d("debug", "show debugging!!" + String.valueOf(i));
                i++;
            }
            return new Result.Accepted<>(list);
        } catch (Exception exception) {
            //Log.d("debug", "show debugging!!");
            return new Result.Handle(exception);
        }
    }

        public Result<List<Integer>> getAllSeatId (Integer placeId) {
            try {
                Cursor cursor = dbSeatManager.getSeatId(placeId);
                List<Integer> list = new ArrayList<>();
                int i = 1;
                while(cursor.moveToNext()) {
                    list.add(
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_SEAT_SEAT_ID))
                    );
                    Log.d("debug", "show newly created method to get all seat!!" + String.valueOf(i));
                    i++;
                }
                return new Result.Accepted<>(list);
            } catch (Exception exception) {
                //Log.d("debug", "show debugging!!");
                return new Result.Handle(exception);
            }
    }
}
