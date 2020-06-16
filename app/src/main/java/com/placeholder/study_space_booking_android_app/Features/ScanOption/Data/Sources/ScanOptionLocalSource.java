package com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.State;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScanOptionLocalSource {
    private static volatile ScanOptionLocalSource instance;
    private final DBTimeSlotManager dbTimeSlotManager;


    private ScanOptionLocalSource(DBTimeSlotManager dbTimeSlotManager) {
        this.dbTimeSlotManager = dbTimeSlotManager;
    }

    public static ScanOptionLocalSource getInstance() {
        if (instance == null) {
            instance = new ScanOptionLocalSource(
                    DBTimeSlotManager.getInstance()
            );
        }
        return instance;
    }

    @SuppressLint("NewApi")
    public TimeSlot getSigninTimeSlot(NormalUser user) {
        List<TimeSlot> list = getAllBookings(user);
        if (list.isEmpty()) {
            Log.d("debug", "local source is null");
            return null;
        }

        list.sort(new Comparator<TimeSlot>() {
            @Override
            public int compare(TimeSlot a, TimeSlot b) {
                return a.getBookStartTime() - b.getBookStartTime();
            }
        });

        for (TimeSlot t : list) {
            if (t.getState() == State.BOOKED) {
                return t;
            }
        }
        return null;
    }

    public TimeSlot getSignOutTimeSlot(NormalUser user) {
        List<TimeSlot> list = getAllBookings(user);
        if (list.isEmpty()) {
            Log.d("debug", "local source is null");
            return null;
        }

        for (TimeSlot t : list) {
            if (t.getState() == State.SIGNIN) {
                return t;
            }
        }
        return null;
    }

    public TimeSlot getTmpBackTimeSlot(NormalUser user) {
        List<TimeSlot> list = getAllBookings(user);
        if (list.isEmpty()) {
            Log.d("debug", "local source is null");
            return null;
        }

        for (TimeSlot t : list) {
            if (t.getState() == State.TMPLEAVE) {
                return t;
            }
        }
        return null;
    }

    public TimeSlot getTmpLeaveTimeSlot(NormalUser user) {
        List<TimeSlot> list = getAllBookings(user);
        if (list.isEmpty()) {
            Log.d("debug", "local source is null");
            return null;
        }

        for (TimeSlot t : list) {
            if (t.getState() == State.SIGNIN) {
                return t;
            }
        }
        return null;
    }



    public List<TimeSlot> getAllBookings(NormalUser user) {
        Log.d("in scan local source", " create a new cursor");
        Cursor cursor = dbTimeSlotManager.getUserTimeSlot(user.getId());
        boolean check = cursor == null;
        Log.d("in scan local source", " check if cursor is null: " + check);

        if (cursor.getCount() == 0) {
            Log.d("debug", "no booking found");
            return Collections.emptyList();
        } else {
            List<TimeSlot> list = new ArrayList<>();
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
            }
            return list;
        }
    }
}
