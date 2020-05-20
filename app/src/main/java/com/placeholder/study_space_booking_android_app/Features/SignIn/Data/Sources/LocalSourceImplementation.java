package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;

import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;


import java.util.Optional;

/*
interface LocalSource {
    Optional<UserBean> getUserInformation(String userName, String password);
}
*/

public class LocalSourceImplementation implements Source {
    private final DBUserInformationManager dbUserInformationManager;
    public static volatile LocalSourceImplementation instance;

    public LocalSourceImplementation(DBUserInformationManager dbUserInformationManager) {
        this.dbUserInformationManager = dbUserInformationManager;
    }

    public static LocalSourceImplementation getInstance(DBUserInformationManager dbUserInformationManager) {
        if(instance == null) {
            instance = new LocalSourceImplementation(dbUserInformationManager);
        }
        return instance;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public Result getUserInformation(String userName, String password) {
        try {
            Cursor cursor = dbUserInformationManager.getUserInformation(userName, password, null);
            if (cursor.getCount() == 0 || cursor.getCount() >= 2) {
                return new Result.Handle(new IllegalArgumentException("No such user"));
            } else {
                return new Result.Accepted(
                    new UserBean(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ROLE)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED))
                    )
                );
            }
        } catch (Exception e) {
            return new Result.Handle(e);
        }
    }
}
