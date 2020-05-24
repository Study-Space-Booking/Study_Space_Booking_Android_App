package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeLocalSource implements HomeSource{
    private static volatile HomeLocalSource instance;
    private final DBLogHistoryManager dbLogHistoryManager;
    private final DBTimeSlotManager dbTimeSlotManager;


    private HomeLocalSource(DBLogHistoryManager dbLogHistoryManager, DBTimeSlotManager dbTimeSlotManager) {
        this.dbLogHistoryManager = dbLogHistoryManager;
        this.dbTimeSlotManager = dbTimeSlotManager;
    }
    public static HomeLocalSource getInstance() {
        if(instance == null) {
            instance = new HomeLocalSource(DBLogHistoryManager.getInstance(), DBTimeSlotManager.getInstance());
        }
        return instance;
    }

    @Override
    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        Cursor cursor = dbLogHistoryManager.getUserHistory(user);
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user history"));
            } else {
                List<TimeSlot> list = new ArrayList<>();
                while(cursor.moveToNext()) {
                    list.add(
                            new TimeSlot(
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_PLACE_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_SEAT_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_USER_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOK_END_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_ARRIVAL_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_SIGNOUTIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_LEAVE_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_BACK_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOKING_STATE))
                            )
                    );
                }
                return new Result.Accepted<>(list);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<List<TimeSlot>> getAllBookings(NormalUser user) {
        Cursor cursor = dbTimeSlotManager.getUserTimeSlot(user.getId());
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No booking"));
            } else {
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
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot) {
        try{
            int result = dbTimeSlotManager.deleteTimeSlot(timeSlot.getId());
            if(result == 0) {
                return new Result.Handle(new IllegalArgumentException("The booking is not found"));
            } else {
                return new Result.Accepted<>(timeSlot);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }
}
