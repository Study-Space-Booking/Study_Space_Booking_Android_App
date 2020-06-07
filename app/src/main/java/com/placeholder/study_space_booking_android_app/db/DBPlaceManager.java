package com.placeholder.study_space_booking_android_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.placeholder.study_space_booking_android_app.Core.Beans.Place;


public class DBPlaceManager {
    public static final String TAG = DBPlaceManager.class.getSimpleName();

    private volatile static DBPlaceManager instance = null;
    private DatabaseHelper pDbhelper;
    private Context pContext;

    private boolean pInitialize;

    private DBPlaceManager() {}

    public static DBPlaceManager getInstance() {
        if (instance == null) {
            synchronized (DBPlaceManager.class) {
                if (instance == null) {
                    instance = new DBPlaceManager();
                }
            }
        }
        return instance;
    } // get the instance of DBPlaceManager

    public void initialize(Context context) {
        pContext = context.getApplicationContext();
        pDbhelper = new DatabaseHelper(pContext, DatabaseHelper.DB_NAME);
        pInitialize = true;
    }

    private boolean valid() {
        if (!pInitialize) {
            return false;
        }
        return true;
    }

    public Boolean setPlaceName(Place p) {
        if (!valid()) return false;
        SQLiteDatabase database = pDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PLACE_PlACE_NAME, p.getName());
        long result = database.insert(DatabaseHelper.TABLE_PLACE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getPlaceName(Integer placeId) {
        if (!valid()) return String.valueOf(0);
        String placeName = "";
        try {
            SQLiteDatabase database = pDbhelper.open();
            String strSQL = "select * from " + DatabaseHelper.TABLE_PLACE_PlACE_NAME + " where " +
                    DatabaseHelper.TABLE_PLACE_PlACE_ID + " = ?";
            Cursor res = database.rawQuery(strSQL, new String[] { String.valueOf(placeId) });
            if (res.moveToFirst()) {
                placeName = res.getString(0);
            }
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pDbhelper.close();
        }
        return placeName;
    }

    public boolean updatePlace(Place p) {
        if (!valid()) return false;
        SQLiteDatabase database = pDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PLACE_PlACE_ID, p.getId());
        contentValues.put(DatabaseHelper.TABLE_PLACE_PlACE_NAME, p.getName());
        database.update(DatabaseHelper.TABLE_PLACE_NAME, contentValues, "Tab_Place_PlaceId = ?",
                new String[] { String.valueOf(p.getId()) });
        return true;
    }

    public Integer deletePlace(Place p) {
        if (!valid()) return 0;
        SQLiteDatabase database = pDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_PLACE_NAME, "Tab_Place_PlaceId = ?", new String[] { String.valueOf(p.getId())});
    }

}
