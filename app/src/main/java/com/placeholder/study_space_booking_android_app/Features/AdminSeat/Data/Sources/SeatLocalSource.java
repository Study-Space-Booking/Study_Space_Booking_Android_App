package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources.HomeSource;
import com.placeholder.study_space_booking_android_app.db.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.db.DBPlaceManager;
import com.placeholder.study_space_booking_android_app.db.DBSeatManager;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SeatLocalSource {
    private static volatile SeatLocalSource instance;
    private final DBLogHistoryManager dbLogHistoryManager;
    private final DBTimeSlotManager dbTimeSlotManager;
    private final DBPlaceManager dbPlaceManager;
    private final DBUserInformationManager dbUserInformationManager;
    private final DBSeatManager dbSeatManager;


    private SeatLocalSource(DBLogHistoryManager dbLogHistoryManager,
                            DBTimeSlotManager dbTimeSlotManager, DBPlaceManager dbPlaceManager,
                            DBUserInformationManager dbUserInformationManager, DBSeatManager dbSeatManager) {
        this.dbLogHistoryManager = dbLogHistoryManager;
        this.dbTimeSlotManager = dbTimeSlotManager;
        this.dbPlaceManager = dbPlaceManager;
        this.dbUserInformationManager = dbUserInformationManager;
        this.dbSeatManager = dbSeatManager;
    }

    public static SeatLocalSource getInstance() {
        if (instance == null) {
            instance = new SeatLocalSource(
                    DBLogHistoryManager.getInstance(),
                    DBTimeSlotManager.getInstance(),
                    DBPlaceManager.getInstance(),
                    DBUserInformationManager.getInstance(),
                    DBSeatManager.getInstance()
            );
        }
        return instance;
    }

    public Result<List<Seat>> getAllSeats() {
        Cursor cursor = dbSeatManager.getAllSeats();
        List<Seat> list = new ArrayList<>();
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user"));
            } else {

                while (cursor.moveToNext()) {
                    list.add(
                            new Seat(
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_SEAT_SEAT_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_SEAT_PLACE_ID))
                            )
                    );
                }
                return new Result.Accepted<>(list);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id) {
        Cursor cursor = dbLogHistoryManager.getSeatTimeSlot(id);
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No Seat history"));
            } else {
                List<TimeSlot> list = new ArrayList<>();
                while (cursor.moveToNext()) {
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


}
