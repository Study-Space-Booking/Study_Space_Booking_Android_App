package com.placeholder.study_space_booking_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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

    public void initialize(Context context, String strLoginId) {
        tsContext = context.getApplicationContext();
        tsDbhelper = new DatabaseHelper(tsContext, DatabaseHelper.DB_NAME);
        tsInitialize = true;
    }

    private boolean valid() {
        if (!tsInitialize) {
            // ??? TLog.d("DB", "DB Manager Not Initialize.");
            return false;
        }
        return true;
    }

    public Boolean setProbRepo(Integer placeId, Integer seatId,
                               Integer bookStartTime, Integer bookEndTime, Integer inTime, Integer outTime,
                               Integer tempLeaveTime, Integer tempBackTime) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = tsDbhelper.open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID, placeId);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID, seatId);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME, bookStartTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME, bookEndTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_IN_TIME, inTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME, outTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME, tempLeaveTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME, tempBackTime);
        long result = database.insert(DatabaseHelper.TABLE_TIMESLOT_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public TimeSlot getTimeSlot(String tsId) {
        if (!valid()) return null;

        SQLiteDatabase database = tsDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_TIMESLOT_NAME + " where " +
                DatabaseHelper.TABLE_TIMESLOT_ID.equals(tsId);
        Cursor res = database.rawQuery(strSQL, null);

        return res;
    }

    public boolean updateProbRepo(String id, Integer placeId, Integer seatId,
                                  Integer bookStartTime, Integer bookEndTime, Integer inTime, Integer outTime,
                                  Integer tempLeaveTime, Integer tempBackTime) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = tsDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_ID, id);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID, placeId);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID, seatId);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME, bookStartTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME, bookEndTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_IN_TIME, inTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME, outTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME, tempLeaveTime);
        contentValues.put(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME, tempBackTime);
        database.update(DatabaseHelper.TABLE_TIMESLOT_NAME, contentValues, "Tab_Timeslot_ReportId = ?", new String[] { id });
        return true;
    }

    public Integer deleteTimeSlot(String id) {
        if (!valid()) return 0;
        SQLiteDatabase database = tsDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_TIMESLOT_NAME, "Tab_Timeslot_ReportId = ?", new String[] {id});
    }

}