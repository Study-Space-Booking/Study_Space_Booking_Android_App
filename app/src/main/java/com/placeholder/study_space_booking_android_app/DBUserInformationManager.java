package com.placeholder.study_space_booking_android_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;


public class DBUserInformationManager {
    public static final String TAG = DBUserInformationManager.class.getSimpleName();
    private volatile static DBUserInformationManager instance = null;
    private DatabaseHelper tsDbHelper;
    private Context tsContext;
    private boolean tsInitialized;

    private DBUserInformationManager() {}

    public static DBUserInformationManager getInstance() {
        if(instance == null) {
            synchronized (DBUserInformationManager.class) {
                if(instance == null) {
                    instance = new DBUserInformationManager();
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

    public boolean insertUserInformation(NormalUser user) throws SQLException {
        if(!valid()) {
            return false;
        } else {
            SQLiteDatabase db = tsDbHelper.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.USER_COLUMN_USERNAME, user.getUserName());
            contentValues.put(DatabaseHelper.USER_COLUMN_PASSWORD, user.getPassword());
            contentValues.put(DatabaseHelper.USER_COLUMN_CREDIT, user.getCredit());
            contentValues.put(DatabaseHelper.USER_COLUMN_ISBLOCKED, user.isBlocked());
            long result = db.insert(DatabaseHelper.TABLE_USER, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Cursor getUserInformation(String userName, String password, String userId) {
        SQLiteDatabase db = tsDbHelper.open();
        Cursor result = null;
        if(userId != null) {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_USER + " where " +
                            DatabaseHelper.USER_COLUMN_ID + " = ?",
                    new String[]{userId});
        } else if(password != null) {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_USER + " where " +
                    DatabaseHelper.USER_COLUMN_USERNAME + " = ?" + " and " + DatabaseHelper.USER_COLUMN_PASSWORD + " = ?",
                    new String[]{userName, password});
        } else {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_USER + " where " +
                            DatabaseHelper.USER_COLUMN_USERNAME + " = ?" ,
                    new String[]{userName});
        }
        return result;
    }

    public boolean updateUserInformation(NormalUser user) {
        SQLiteDatabase db = tsDbHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER_COLUMN_USERNAME, user.getUserName());
        contentValues.put(DatabaseHelper.USER_COLUMN_PASSWORD, user.getPassword());
        contentValues.put(DatabaseHelper.USER_COLUMN_CREDIT, user.getCredit().toString());
        contentValues.put(DatabaseHelper.USER_COLUMN_ISBLOCKED, user.isBlocked().toString());
        db.update(
                DatabaseHelper.TABLE_USER, contentValues,
                DatabaseHelper.USER_COLUMN_ID + " = ?",
                new String[] {user.getId().toString()}
            );
        return true;
    }

    public int deleteUser(NormalUser user) {
        SQLiteDatabase db = tsDbHelper.open();
        return db.delete(
                DatabaseHelper.TABLE_USER,
                DatabaseHelper.USER_COLUMN_ID + " = ?",
                new String[]{user.getId().toString()}
            );
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = tsDbHelper.open();
        Cursor result = db.rawQuery("select * from " + DatabaseHelper.TABLE_USER, null);
        return result;
    }

}
