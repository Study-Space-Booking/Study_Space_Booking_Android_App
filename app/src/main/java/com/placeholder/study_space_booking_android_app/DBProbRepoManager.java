package com.placeholder.study_space_booking_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBProbRepoManager {
    public static final String TAG = DBProbRepoManager.class.getSimpleName();

    private volatile static DBProbRepoManager instance = null;
    private DatabaseHelper prDbhelper;
    private Context prContext;

    private boolean prInitialize;

    private DBProbRepoManager() {}

    public static DBProbRepoManager getInstance() {
        if (instance == null) {
            synchronized (DBProbRepoManager.class) {
                if (instance == null) {
                    instance = new DBProbRepoManager();
                }
            }
        }

        return instance;
    } // get the instance of DBPlaceManager

    public void initialize(Context context, String strLoginId) {
        prContext = context.getApplicationContext();
        prDbhelper = new DatabaseHelper(prContext, DatabaseHelper.DB_NAME);
        prInitialize = true;
    }

    private boolean valid() {
        if (!prInitialize) {
            // ??? TLog.d("DB", "DB Manager Not Initialize.");
            return false;
        }
        return true;
    }

    public Boolean setProbRepo(Integer id, String title, Integer placeId, Integer seatId,
                               String description, String image, Integer time, Integer reporterId,
                               String remarks) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = prDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_TITLE, title);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_PLACE_ID, placeId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_SEAT_ID, seatId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_DESC, description);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_CONTENT, image);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_TIME, time);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORTER_ID, reporterId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REMARKS, remarks);
        long result = database.insert(DatabaseHelper.TABLE_PROBREPORT_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getProblemReport(String reportId) {
        if (!valid()) return null;

        SQLiteDatabase database = prDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_PROBREPORT_NAME + " where " +
                DatabaseHelper.TABLE_SEAT_SEAT_ID.equals(reportId);
        Cursor res = database.rawQuery(strSQL, null);

        return res;
    }

    public boolean updateProbRepo(String id, String title, Integer placeId, Integer seatId,
                                  String description, byte[] image, Integer time, Integer reporterId,
                                  String remarks) {
        if (!valid()) return false;
        SQLiteDatabase database = prDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_ID, id);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_TITLE, title);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_PLACE_ID, placeId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_SEAT_ID, seatId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_DESC, description);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_CONTENT, image);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_TIME, time);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORTER_ID, reporterId);
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REMARKS, remarks);
        database.update(DatabaseHelper.TABLE_SEAT_NAME, contentValues, "Tab_Seat_SeatId = ?", new String[] { id });
        return true;
    }

    public Integer deleteProbRepo(String id) {
        if (!valid()) return 0;
        SQLiteDatabase database = prDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_PROBREPORT_NAME, "Tab_ProbReport_ReportId = ?", new String[] {id});
    }

}