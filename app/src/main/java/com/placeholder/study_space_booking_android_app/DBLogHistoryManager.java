package com.placeholder.study_space_booking_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

public class DBLogHistoryManager {
    public static final String TAG = DBLogHistoryManager.class.getSimpleName();
    private volatile static DBLogHistoryManager instance = null;
    private DatabaseHelper tsDbHelper;
    private Context tsContext;
    private boolean tsInitialized;

    private DBLogHistoryManager() {}

    public static DBLogHistoryManager getInstance() {
        if(instance == null) {
            synchronized (DBUserInformationManager.class) {
                if(instance == null) {
                    instance = new DBLogHistoryManager();
                }
            }
        }
        return instance;
    }

    public void initialize(Context context) {
        tsContext = context.getApplicationContext();
        tsDbHelper = new DatabaseHelper(tsContext, DatabaseHelper.DB_NAME);
        tsInitialized = true;
    }

    public boolean valid() {
        boolean result;
        if(!tsInitialized) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public boolean insertHistory(TimeSlot timeSlot) throws SQLException {
        if(!valid()) {
            return false;
        } else {
            SQLiteDatabase db = tsDbHelper.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_PLACE_ID, timeSlot.getPlaceId().toString());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_SEAT_ID, timeSlot.getSeatId().toString());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_USER_ID, timeSlot.getUserId().toString());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME, timeSlot.getBookStartTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_END_TIME, timeSlot.getBookEndTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_ARRIVAL_TIME, timeSlot.getInTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_SIGNOUTIME, timeSlot.getOutTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_LEAVE_TIME, timeSlot.getTempLeaveTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME, timeSlot.getTempBackTime());
            contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOKING_STATE, timeSlot.getState());
            long result = db.insert(DatabaseHelper.TABLE_LOG_HISTORY, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Cursor getUserHistory(NormalUser user) {
        SQLiteDatabase db = tsDbHelper.open();
        Cursor result = null;
        result = db.rawQuery("select * from " + DatabaseHelper.TABLE_LOG_HISTORY + " where " +
                        DatabaseHelper.HISTORY_COLUMN_USER_ID + " = ?",
                new String[]{user.getId().toString()});

        return result;
    }

    public boolean updateHistory(TimeSlot timeSlot) {
        SQLiteDatabase db = tsDbHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_PLACE_ID, timeSlot.getPlaceId().toString());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_SEAT_ID, timeSlot.getSeatId().toString());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_USER_ID, timeSlot.getUserId().toString());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME, timeSlot.getBookStartTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_END_TIME, timeSlot.getBookEndTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_ARRIVAL_TIME, timeSlot.getInTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_SIGNOUTIME, timeSlot.getOutTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_LEAVE_TIME, timeSlot.getTempLeaveTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME, timeSlot.getTempBackTime());
        contentValues.put(DatabaseHelper.HISTORY_COLUMN_BOOKING_STATE, timeSlot.getState());
        db.update(
                DatabaseHelper.TABLE_LOG_HISTORY, contentValues,
                DatabaseHelper.HISTORY_COLUMN_ID + " = ?",
                new String[] {timeSlot.getId().toString()}
        );
        return true;
    }

    public int deleteUserHistory(NormalUser user) {
        SQLiteDatabase db = tsDbHelper.open();
        return db.delete(
                DatabaseHelper.TABLE_LOG_HISTORY,
                DatabaseHelper.HISTORY_COLUMN_USER_ID + " = ?",
                new String[]{user.getId().toString()}
        );
    }
}