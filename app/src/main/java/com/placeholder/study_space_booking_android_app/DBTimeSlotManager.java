package com.placeholder.study_space_booking_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;


public class DBTimeSlotManager {
    public static final String TAG = DBTimeSlotManager.class.getSimpleName();

    private volatile static DBTimeSlotManager instance = null;
    private DatabaseHelper tsDbhelper;
    private Context tsContext;

    private boolean tsInitialize;

    private DBTimeSlotManager() {}

    public static DBTimeSlotManager getInstance() {
        if (instance == null) {
            synchronized (DBTimeSlotManager.class) {
                if (instance == null) {
                    instance = new DBTimeSlotManager();
                }
            }
        }
        return instance;
    } // get the instance of DBTimeSlotManager

    public void initialize(Context context) {
        tsContext = context.getApplicationContext();
        tsDbhelper = new DatabaseHelper(tsContext, DatabaseHelper.DB_NAME);
        tsInitialize = true;
    }

    private boolean valid() {
        if (!tsInitialize) {
            return false;
        }
        return true;
    }

    public Boolean setTimeSlot(TimeSlot t) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = tsDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID, t.getPlaceId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID, t.getSeatId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_USER_ID, t.getUserId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME, t.getBookStartTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME, t.getBookEndTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_IN_TIME, t.getInTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME, t.getOutTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME, t.getTempLeaveTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME, t.getTempBackTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_STATE, t.getState());
        long result = database.insert(DatabaseHelper.TABLE_TIMESLOT_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getTimeSlot(Integer tsId) {
        if (!valid()) return null;

        TimeSlot t;
        SQLiteDatabase database = tsDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_TIMESLOT_NAME + " where " +
                DatabaseHelper.TABLE_TIMESLOT_ID + "=?";
        Cursor res = database.rawQuery(strSQL, new String[] { String.valueOf(tsId)});

        return res;
    }

    public Cursor getUserTimeSlot(Integer userId) {
        if(!valid()) return null;

        SQLiteDatabase database = tsDbhelper.open();
        String stringSQL = "select * from " + DatabaseHelper.TABLE_TIMESLOT_NAME + " where " +
                DatabaseHelper.TABLE_TIMESLOT_USER_ID + " =?";
        Cursor result = database.rawQuery(stringSQL, new String[]{userId.toString()});
        return result;
    }

    public Cursor getInBetweenTimeSlot(Integer startTime, Integer endTime, Integer placeId) {
        if(!valid()) return null;
        //Log.d("debug", "debgug can see?");
        SQLiteDatabase database = tsDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_TIMESLOT_NAME + " where " +
                DatabaseHelper.TABLE_TIMESLOT_PLACE_ID + " =? " + " and " +
                DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME + " <?" + " and " +
                DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME + " >?";
        Cursor result = database.rawQuery(strSQL, new String[]{placeId.toString(), endTime.toString(), startTime.toString()});
        return result;
    }

    public boolean updateTimeSlot(TimeSlot t) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = tsDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_ID, t.getId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID, t.getPlaceId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID, t.getSeatId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_USER_ID, t.getUserId());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME, t.getBookStartTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME, t.getBookEndTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_IN_TIME, t.getInTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME, t.getOutTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME, t.getTempLeaveTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME, t.getTempBackTime());
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_STATE, t.getState());
        database.update(DatabaseHelper.TABLE_TIMESLOT_NAME, contentValues, "Tab_Timeslot_Id = ?", new String[] { String.valueOf(t.getId()) });
        return true;
    }

    public Integer deleteTimeSlot(Integer id) {
        if (!valid()) return 0;
        SQLiteDatabase database = tsDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_TIMESLOT_NAME, "Tab_Timeslot_ReportId = ?", new String[] {String.valueOf(id)});
    }

    public Cursor getTimeSlotToUpdate(Integer startTime, Integer gap) { // gap is in minute
        if(!valid()) return null;
        //Log.d("debug", "debgug can see?");
        SQLiteDatabase database = tsDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_TIMESLOT_NAME + " where " +
                DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME + " >?";
        Cursor result = database.rawQuery(strSQL, new String[]{ String.valueOf(startTime - gap * 60)});
        return result;
    }


}