package com.placeholder.study_space_booking_android_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.placeholder.study_space_booking_android_app.Core.Beans.ProbRepo;

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

    public void initialize(Context context) {
        prContext = context.getApplicationContext();
        prDbhelper = new DatabaseHelper(prContext, DatabaseHelper.DB_NAME);
        prInitialize = true;
    }

    private boolean valid() {
        if (!prInitialize) {
            return false;
        }
        return true;
    }

    public Boolean setProbRepo(ProbRepo pr) throws SQLiteException {
        if (!valid()) return false;
        SQLiteDatabase database = prDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_TITLE, pr.getTitle());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_PLACE_ID, pr.getPlaceId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_SEAT_ID, pr.getSeatId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_DESC, pr.getDescription());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_CONTENT, pr.getImage());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_TIME, pr.getTime());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORTER_ID, pr.getReporterId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REMARKS, pr.getRemarks());
        long result = database.insert(DatabaseHelper.TABLE_PROBREPORT_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getProblemReport(Integer reportId) {
        if (!valid()) return null;

        SQLiteDatabase database = prDbhelper.open();
        String strSQL = "select * from " + DatabaseHelper.TABLE_PROBREPORT_NAME + " where " +
                DatabaseHelper.TABLE_SEAT_SEAT_ID + "=?";
        Cursor res = database.rawQuery(strSQL, new String[] {String.valueOf(reportId)});

        return res;
    }

    public boolean updateProbRepo(ProbRepo pr) {
        if (!valid()) return false;
        SQLiteDatabase database = prDbhelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_ID, pr.getId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORT_TITLE, pr.getTitle());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_PLACE_ID, pr.getPlaceId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_SEAT_ID, pr.getSeatId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_DESC, pr.getDescription());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_CONTENT, pr.getImage());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_TIME, pr.getTime());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REPORTER_ID, pr.getReporterId());
        contentValues.put(DatabaseHelper.TABLE_PROBREPORT_REMARKS, pr.getRemarks());
        database.update(DatabaseHelper.TABLE_SEAT_NAME, contentValues, "Tab_Seat_SeatId = ?", new String[] { String.valueOf(pr.getId()) });
        return true;
    }

    public Integer deleteProbRepo(Integer id) {
        if (!valid()) return 0;
        SQLiteDatabase database = prDbhelper.open();
        return database.delete(DatabaseHelper.TABLE_PROBREPORT_NAME, "Tab_ProbReport_ReportId = ?", new String[] {String.valueOf(id)});
    }

}