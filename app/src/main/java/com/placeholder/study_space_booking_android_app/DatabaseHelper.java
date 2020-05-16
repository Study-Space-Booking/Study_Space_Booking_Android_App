package com.placeholder.study_space_booking_android_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "SSB.db";

    // region TABLE PLACE
    public static final String TABLE_PLACE_NAME = "Tab_Place";
    public static final String TABLE_PLACE_PlACE_ID = "Tab_Place_PlaceId";

    private static final String SQL_CREATE_TABLE_PLACE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACE_NAME + " (" +
            TABLE_PLACE_PlACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
            ");";
    private static final String SQL_DROP_TABLE_PLACE = "DROP TABLE IF EXISTS " + TABLE_PLACE_NAME;


    // region TABLE SEAT
    public static final String TABLE_PLACE_SEAT = "Tab_Seat";
    public static final String TABLE_PLACE_SEAT_ID = "Tab_Seat_SeatId";
    public static final String  TABLE_PLACE_SEAT_STATE = "Tab_Seat_SeatState";

    private static final String SQL_CREATE_SEAT_PLACE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACE_NAME + " (" +
            TABLE_PLACE_PlACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
            ");";
    private static final String SQL_DROP_SEAT_PLACE = "DROP TABLE IF EXISTS " + TABLE_PLACE_NAME;




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
