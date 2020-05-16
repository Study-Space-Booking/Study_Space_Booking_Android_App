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
    // endregion

    // region TABLE SEAT
    public static final String TABLE_SEAT_NAME = "Tab_Seat";
    public static final String TABLE_SEAT_SEAT_ID = "Tab_Seat_SeatId";
    public static final String  TABLE_SEAT_SEAT_STATE = "Tab_Seat_SeatState";

    private static final String SQL_CREATE_TABLE_SEAT = "CREATE TABLE IF NOT EXISTS " + TABLE_SEAT_NAME + " (" +
            TABLE_SEAT_SEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_SEAT_SEAT_STATE + " Text " +
            ");";
    private static final String SQL_DROP_SEAT_PLACE = "DROP TABLE IF EXISTS " + TABLE_SEAT_NAME;
    // endregion

    // region TABLE ProbReport
    public static final String TABLE_PROBREPORT_NAME = "Tab_ProbReport";
    public static final String TABLE_PROBREPORT_REPORT_ID = "Tab_ProbReport_ReportId";
    public static final String TABLE_PROBREPORT_PLACE_ID = "Tab_ProbReport_PlaceId";
    public static final String TABLE_PROBREPORT_SEAT_ID = "Tab_ProbReport_SeatId";
    public static final String TABLE_PROBREPORT_DESC = "Tab_ProbReport_Desc";
    public static final String TABLE_PROBREPORT_CONTENT = "Tab_ProbReport_Content"; // May contain pics
    public static final String TABLE_PROBREPORT_TIME = "Tab_ProbReport_Time";
    public static final String TABLE_PROBREPORT_REPORTER_ID = "Tab_ProbReport_ReporterId";
    public static final String TABLE_PROBREPORT_REMARKS = "Tab_ProbReport_Remarks";

    private static final String SQL_CREATE_TABLE_PROBREPORT = "CREATE TABLE IF NOT EXISTS " + TABLE_PROBREPORT_NAME + " (" +
            TABLE_PROBREPORT_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_PROBREPORT_PLACE_ID + " TEXT, " +
            TABLE_PROBREPORT_SEAT_ID + " TEXT, " +
            TABLE_PROBREPORT_DESC + " TEXT, " +
            TABLE_PROBREPORT_CONTENT + " TEXT, " +
            TABLE_PROBREPORT_TIME + " TEXT, " +         // Use text for time?
            TABLE_PROBREPORT_REPORTER_ID + " TEXT, " +
            TABLE_PROBREPORT_REMARKS + " TEXT " +
            ");";
    private static final String SQL_DROP_TABLE_PROBREPORT = "DROP TABLE IF EXISTS " + TABLE_PROBREPORT_NAME;
    //endregion


    // region TABLE TIMESLOT
    public static final String TABLE_TIMESLOT_NAME = "Tab_Timeslot";
    public static final String TABLE_TIMESLOT_ID = "Tab_Timeslot_ReportId";
    public static final String TABLE_TIMESLOT_PLACE_ID = "Tab_Timeslot_PlaceId";
    public static final String TABLE_TIMESLOT_SEAT_ID = "Tab_Timeslot_SeatId";
    public static final String TABLE_TIMESLOT_BOOK_TIME = "Tab_Timeslot_BookTime";
    public static final String TABLE_TIMESLOT_IN_TIME = "Tab_Timeslot_InTime"; // May contain pics
    public static final String TABLE_TIMESLOT_OUT_TIME = "Tab_Timeslot_OutTime";
    public static final String TABLE_TIMESLOT_TEMPLEAVE_TIME = "Tab_Timeslot_TempLeaveTime";
    public static final String TABLE_TIMESLOT_TEMPBACK_TIME = "Tab_Timeslot_TempBackTime";

    private static final String SQL_CREATE_TABLE_TIMESLOT = "CREATE TABLE IF NOT EXISTS " + TABLE_TIMESLOT_NAME + " (" +
            TABLE_TIMESLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_TIMESLOT_PLACE_ID + " TEXT, " +
            TABLE_TIMESLOT_SEAT_ID + " TEXT, " +
            TABLE_TIMESLOT_BOOK_TIME + " TEXT, " +
            TABLE_TIMESLOT_IN_TIME + " TEXT, " +
            TABLE_TIMESLOT_OUT_TIME + " TEXT, " +         // Use text for time?
            TABLE_TIMESLOT_TEMPLEAVE_TIME + " TEXT, " +
            TABLE_TIMESLOT_TEMPBACK_TIME + " TEXT " +
            ");";
    private static final String SQL_DROP_TABLE_TIMESLOT = "DROP TABLE IF EXISTS " + TABLE_TIMESLOT_NAME;
    //endregion


    public DatabaseHelper(Context context, String strDBName) {
        super(context, strDBName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PLACE);
        db.execSQL(SQL_CREATE_TABLE_SEAT);
        db.execSQL(SQL_CREATE_TABLE_PROBREPORT);
        db.execSQL(SQL_CREATE_TABLE_TIMESLOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_TABLE_PLACE);
        db.execSQL(SQL_CREATE_TABLE_SEAT);
        db.execSQL(SQL_CREATE_TABLE_PROBREPORT);
        db.execSQL(SQL_CREATE_TABLE_TIMESLOT);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase == null){
            writableDatabase.close();
        }
    }
}