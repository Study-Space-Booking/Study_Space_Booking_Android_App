package com.placeholder.study_space_booking_android_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;


public class DBSeatManager {
    public static final String TAG = DBSeatManager.class.getSimpleName();

    private volatile static DBSeatManager instance = null;
    private DatabaseHelper sDbhelper;
    private Context sContext;

    private boolean sInitialize;

    private DBSeatManager() {}

    public static DBSeatManager getInstance() {
        if (instance == null) {
            synchronized (DBSeatManager.class) {
                if (instance == null) {
                    instance = new DBSeatManager();
                }
            }
        }

        return instance;
    } // get the instance of DBPlaceManager

    public void initialize(Context context) {
        sContext = context.getApplicationContext();
        sDbhelper = new DatabaseHelper(sContext, DatabaseHelper.DB_NAME);
        sInitialize = true;
    }

    private boolean valid() {
        if (!sInitialize) {
            return false;
        }
        return true;
    }

    public Boolean setSeat(Seat s) {
        if (!valid()) return false;
        SQLiteDatabase database = sDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_SEAT_PLACE_ID, s.getPlaceId());
        long result = database.insert(DatabaseHelper.TABLE_SEAT_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Integer getPlaceOfSeat(Integer seatId) {
        if (!valid()) return -1;
        Integer placeId = 0;
        try {
            SQLiteDatabase database = sDbhelper.open();
            String strSQL = "select * from " + DatabaseHelper.TABLE_SEAT_NAME + " where " +
                    DatabaseHelper.TABLE_SEAT_SEAT_ID + "=?";
            Cursor res = database.rawQuery(strSQL, new String[] {String.valueOf(seatId)});
            if (res.moveToFirst()) {
                placeId = res.getInt(0);
            }
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return placeId;
    }

    public Cursor getSeatId(Integer placeId) {
        if (!valid()) return null;
        Log.d("null", "seatmanager not null");
        SQLiteDatabase database = sDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_SEAT_NAME + " where " +
                DatabaseHelper.TABLE_SEAT_PLACE_ID + " =? ";
        Cursor result = database.rawQuery(strSQL, new String[]{placeId.toString()});
        return result;
    }

    public boolean updateSeatState(Integer id, Seat s) {
        if (!valid()) return false;
        SQLiteDatabase database = sDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_SEAT_SEAT_ID, id);
        contentValues.put(DatabaseHelper.TABLE_SEAT_PLACE_ID, s.getPlaceId());
        database.update(DatabaseHelper.TABLE_SEAT_NAME, contentValues, "Tab_Seat_SeatId = ?", new String[] { String.valueOf(id) });
        return true;
    }

    public Integer deleteSeat(Integer id) {
        if (!valid()) return 0;
        SQLiteDatabase database = sDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_SEAT_NAME, "Tab_Seat_SeatId = ?", new String[] {String.valueOf(id)});
    }

}