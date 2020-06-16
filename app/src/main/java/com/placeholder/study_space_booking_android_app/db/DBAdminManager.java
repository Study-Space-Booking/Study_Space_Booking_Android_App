package com.placeholder.study_space_booking_android_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;


public class DBAdminManager {
    public static final String TAG = DBAdminManager.class.getSimpleName();
    private volatile static DBAdminManager instance = null;
    private DatabaseHelper tsDbHelper;
    private Context tsContext;
    private boolean tsInitialized;

    private DBAdminManager() {}

    public static DBAdminManager getInstance() {
        if(instance == null) {
            synchronized (DBAdminManager.class) {
                if(instance == null) {
                    instance = new DBAdminManager();
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

    public boolean insertAdmin(Admin a) throws SQLException {
        if(!valid()) {
            return false;
        } else {
            SQLiteDatabase db = tsDbHelper.open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.TABLE_ADMIN_USERNAME, a.getUserName());
            contentValues.put(DatabaseHelper.TABLE_ADMIN_PASSWORD, a.getPassword());

            long result = db.insert(DatabaseHelper.TABLE_ADMIN_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Cursor getAdmin(String userName, String password, String userId) {
        SQLiteDatabase db = tsDbHelper.open();
        Cursor result = null;
        if(userId != null) {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_ADMIN_NAME + " where " +
                            DatabaseHelper.TABLE_ADMIN_ID + " = ?",
                    new String[]{userId});
        } else if(password != null) {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_ADMIN_NAME + " where " +
                            DatabaseHelper.TABLE_ADMIN_USERNAME + " = ?" + " and " + DatabaseHelper.TABLE_ADMIN_PASSWORD + " = ?",
                    new String[]{userName, password});
        } else {
            result = db.rawQuery("select * from " + DatabaseHelper.TABLE_ADMIN_NAME + " where " +
                            DatabaseHelper.TABLE_ADMIN_USERNAME + " = ?" ,
                    new String[]{userName});
        }
        return result;
    }

    public boolean updateAdmin(Admin a) {
        SQLiteDatabase db = tsDbHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TABLE_ADMIN_USERNAME, a.getUserName());
        contentValues.put(DatabaseHelper.TABLE_ADMIN_PASSWORD, a.getPassword());
        //contentValues.put(DatabaseHelper.USER_COLUMN_ROLE, user.isAdministrator().toString());
        db.update(
                DatabaseHelper.TABLE_ADMIN_NAME, contentValues,
                DatabaseHelper.TABLE_ADMIN_ID + " = ?",
                new String[] {a.getId().toString()}
        );
        return true;
    }

    public int deleteAdmin(Admin user) {
        SQLiteDatabase db = tsDbHelper.open();
        return db.delete(
                DatabaseHelper.TABLE_ADMIN_NAME,
                DatabaseHelper.TABLE_ADMIN_ID + " = ?",
                new String[]{user.getId().toString()}
        );
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = tsDbHelper.open();
        Cursor result = db.rawQuery("select * from " + DatabaseHelper.TABLE_ADMIN_NAME, null);
        return result;
    }

}
