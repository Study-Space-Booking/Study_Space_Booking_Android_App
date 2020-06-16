package com.placeholder.study_space_booking_android_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 44;
    public static final String DB_NAME = "SSB.db";

    // region TABLE PLACE

    public static final String TABLE_PLACE_NAME = "Tab_Place";
    public static final String TABLE_PLACE_PlACE_NAME = "Tab_Place_PlaceName";
    public static final String TABLE_PLACE_PlACE_ID = "Tab_Place_PlaceId";

    private static final String SQL_CREATE_TABLE_PLACE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACE_NAME + " (" +
            TABLE_PLACE_PlACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_PLACE_PlACE_NAME + " TEXT " +
            ");";
    private static final String SQL_DROP_TABLE_PLACE = "DROP TABLE IF EXISTS " + TABLE_PLACE_NAME;
    // endregion

    // region TABLE SEAT
    public static final String TABLE_SEAT_NAME = "Tab_Seat";
    public static final String TABLE_SEAT_SEAT_ID = "Tab_Seat_SeatId";
    public static final String TABLE_SEAT_PLACE_ID = "Tab_Seat_PlaceId";

    private static final String SQL_CREATE_TABLE_SEAT = "CREATE TABLE IF NOT EXISTS " + TABLE_SEAT_NAME + " (" +
            TABLE_SEAT_SEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_SEAT_PLACE_ID + " INTEGER " +
            ");";
    private static final String SQL_DROP_TABLE_SEAT = "DROP TABLE IF EXISTS " + TABLE_SEAT_NAME;
    // endregion

    // region TABLE ProbReport
    public static final String TABLE_PROBREPORT_NAME = "Tab_ProbReport";
    public static final String TABLE_PROBREPORT_REPORT_TITLE = "Tab_ProbReport_ReportTitle";
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
            TABLE_PROBREPORT_REPORT_TITLE + "Text, " +
            TABLE_PROBREPORT_PLACE_ID + " INTEGER, " +
            TABLE_PROBREPORT_SEAT_ID + " INTEGER, " +
            TABLE_PROBREPORT_DESC + " TEXT, " +
            TABLE_PROBREPORT_CONTENT + " Text, " + // using blob for images?
            TABLE_PROBREPORT_TIME + " INTEGER, " +         // Use text for time?
            TABLE_PROBREPORT_REPORTER_ID + " INTEGER, " +
            TABLE_PROBREPORT_REMARKS + " TEXT " +
            ");";
    private static final String SQL_DROP_TABLE_PROBREPORT = "DROP TABLE IF EXISTS " + TABLE_PROBREPORT_NAME;
    //endregion

    // region TABLE TIMESLOT
    public static final String TABLE_TIMESLOT_NAME = "Tab_Timeslot";
    public static final String TABLE_TIMESLOT_ID = "Tab_Timeslot_Id";
    public static final String TABLE_TIMESLOT_PLACE_ID = "Tab_Timeslot_PlaceId";
    public static final String TABLE_TIMESLOT_SEAT_ID = "Tab_Timeslot_SeatId";
    public static final String TABLE_TIMESLOT_USER_ID = "Tab_Timeslot_UserId";
    public static final String TABLE_TIMESLOT_BOOKSTART_TIME = "Tab_Timeslot_BookStartTime";
    public static final String TABLE_TIMESLOT_BOOKEND_TIME = "Tab_Timeslot_BookEndTime";
    public static final String TABLE_TIMESLOT_IN_TIME = "Tab_Timeslot_InTime"; // May contain pics
    public static final String TABLE_TIMESLOT_OUT_TIME = "Tab_Timeslot_OutTime";
    public static final String TABLE_TIMESLOT_TEMPLEAVE_TIME = "Tab_Timeslot_TempLeaveTime";
    public static final String TABLE_TIMESLOT_TEMPBACK_TIME = "Tab_Timeslot_TempBackTime";
    public static final String TABLE_TIMESLOT_STATE = "Tab_Timeslot_State";

    private static final String SQL_CREATE_TABLE_TIMESLOT = "CREATE TABLE IF NOT EXISTS " + TABLE_TIMESLOT_NAME + " (" +
            TABLE_TIMESLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_TIMESLOT_PLACE_ID + " INTEGER, " +
            TABLE_TIMESLOT_SEAT_ID + " INTEGER, " +
            TABLE_TIMESLOT_USER_ID + " INTEGER, " +
            TABLE_TIMESLOT_BOOKSTART_TIME + " INTEGER, " +
            TABLE_TIMESLOT_BOOKEND_TIME + " INTEGER, " +
            TABLE_TIMESLOT_IN_TIME + " INTEGER, " +
            TABLE_TIMESLOT_OUT_TIME + " INTEGER, " +
            TABLE_TIMESLOT_TEMPLEAVE_TIME + " INTEGER, " +
            TABLE_TIMESLOT_TEMPBACK_TIME + " INTEGER, " +
            TABLE_TIMESLOT_STATE + " INTEGER " +
            ");";
    private static final String SQL_DROP_TABLE_TIMESLOT = "DROP TABLE IF EXISTS " + TABLE_TIMESLOT_NAME;
    //endregion

    // region Table Admin

    public static final String TABLE_ADMIN_NAME = "Tab_Admin";
    public static final String TABLE_ADMIN_ID = "Tab_Admin_Id";
    public static final String TABLE_ADMIN_PASSWORD = "Tab_Admin_Password";
    public static final String TABLE_ADMIN_USERNAME = "Tab_Admin_Username";

    private static final String SQL_CREATE_TABLE_ADMIN = "CREATE TABLE IF NOT EXISTS " + TABLE_ADMIN_NAME + " (" +
            TABLE_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_ADMIN_PASSWORD + " TEXT, " +
            TABLE_ADMIN_USERNAME + " TEXT " +
            ");";
    private static final String SQL_DROP_TABLE_ADMIN = "DROP TABLE IF EXISTS " + TABLE_ADMIN_NAME;
    // end region
    //region Table User
    public static final String TABLE_USER = "Table_User";

    public static final String USER_COLUMN_ID = "ID";
    public static final String USER_COLUMN_USERNAME = "USERNAME";
    public static final String USER_COLUMN_PASSWORD = "PASSWORD";
    public static final String USER_COLUMN_CREDIT = "CREDIT";
    //public static final String USER_COLUMN_ROLE = "ROLE";
    public static final String USER_COLUMN_ISBLOCKED = "ISBLOCKED";

    private static final String SQL_CREATE_TABLE_USER = "create table if not exists " + TABLE_USER + " (" +
            USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_COLUMN_USERNAME + " TEXT, " +
            USER_COLUMN_PASSWORD + " TEXT," +
            USER_COLUMN_CREDIT + " INTEGER, " +
            USER_COLUMN_ISBLOCKED + " INTEGER)";
    private static final String SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    //endregion

    //region Table History
    public static final String TABLE_LOG_HISTORY = "Table_Log_History";

    public static final String HISTORY_COLUMN_ID = "HISTORY_ID";
    public static final String HISTORY_COLUMN_USER_ID = "USER_ID";
    public static final String HISTORY_COLUMN_PLACE_ID = "PLACE_ID";
    public static final String HISTORY_COLUMN_SEAT_ID = "SEAT_ID";
    public static final String HISTORY_COLUMN_BOOK_START_TIME = "BOOKSTARTTIME";
    public static final String HISTORY_COLUMN_BOOK_END_TIME = "BOOKENDTIME";
    public static final String HISTORY_COLUMN_ARRIVAL_TIME = "ARRIVALTIME";
    public static final String HISTORY_COLUMN_SIGNOUTIME = "SIGNOUTTIME";
    public static final String HISTORY_COLUMN_TEMPORARY_LEAVE_TIME = "TEMPORARY_LEAVE_TIME";
    public static final String HISTORY_COLUMN_TEMPORARY_BACK_TIME = "TEMPORARY_BACK_TIME";
    //public static final String HISTORY_COLUMN_CREDIT = "CREDIT";
    public static final String HISTORY_COLUMN_BOOKING_STATE = "BOOKINGSTATE";

    private static final String SQL_CREATE_TABLE_LOG_HISTORY = "create table if not exists " + TABLE_LOG_HISTORY + " (" +
            HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HISTORY_COLUMN_USER_ID + " INTEGER, " +
            HISTORY_COLUMN_PLACE_ID + " INTEGER, " +
            HISTORY_COLUMN_SEAT_ID + " INTEGER, " +
            HISTORY_COLUMN_BOOK_START_TIME + " INTEGER, " +
            HISTORY_COLUMN_BOOK_END_TIME + " INTEGER, " +
            HISTORY_COLUMN_ARRIVAL_TIME + " INTEGER, " +
            HISTORY_COLUMN_SIGNOUTIME + " INTEGER, " +
            HISTORY_COLUMN_TEMPORARY_LEAVE_TIME + " INTEGER, " +
            HISTORY_COLUMN_TEMPORARY_BACK_TIME + " INTEGER, " +
            HISTORY_COLUMN_BOOKING_STATE + " INTEGER)";
    private static final String SQL_DROP_TABLE_LOG_HISTORY = "DROP TABLE IF EXISTS " + TABLE_LOG_HISTORY;
    //endregion

    public DatabaseHelper(Context context, String strDBName) {
        super(context, strDBName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_LOG_HISTORY);
        db.execSQL(SQL_CREATE_TABLE_PLACE);
        db.execSQL(SQL_CREATE_TABLE_SEAT);
        db.execSQL(SQL_CREATE_TABLE_PROBREPORT);
        db.execSQL(SQL_CREATE_TABLE_TIMESLOT);
        db.execSQL(SQL_CREATE_TABLE_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_USER);
        db.execSQL(SQL_DROP_TABLE_LOG_HISTORY);
        db.execSQL(SQL_DROP_TABLE_PLACE);
        db.execSQL(SQL_DROP_TABLE_SEAT);
        db.execSQL(SQL_DROP_TABLE_PROBREPORT);
        db.execSQL(SQL_DROP_TABLE_TIMESLOT);
        db.execSQL(SQL_CREATE_TABLE_ADMIN);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase != null){
            writableDatabase.close();
        }
    }
}