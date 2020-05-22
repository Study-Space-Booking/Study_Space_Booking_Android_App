package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;

import com.placeholder.study_space_booking_android_app.DBAdminManager;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;


import java.util.Optional;

/*
interface LocalSource {
    Optional<UserBean> getUserInformation(String userName, String password);
}
*/

public class LocalSourceImplementation implements Source {
    private final DBUserInformationManager dbUserInformationManager;
    private final DBAdminManager dbAdminManager;
    public static volatile LocalSourceImplementation instance;

    public LocalSourceImplementation(DBUserInformationManager dbUserInformationManager,
                                     DBAdminManager dbAdminManager) {
        this.dbUserInformationManager = dbUserInformationManager;
        this.dbAdminManager = dbAdminManager;
    }

    public static LocalSourceImplementation getInstance(DBUserInformationManager dbUserInformationManager,
                                                        DBAdminManager dbAdminManager) {
        if(instance == null) {
            instance = new LocalSourceImplementation(dbUserInformationManager, dbAdminManager);
        }
        return instance;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public Result<User> getUserInformation(String userName, String password) {
        try {
            Cursor cursorOne = dbUserInformationManager.getUserInformation(userName, password, null);
            Cursor cursorTwo = dbAdminManager.getAdmin(userName, password, null);
            if ((cursorOne.getCount() + cursorTwo.getCount() == 0) ||
                    (cursorOne.getCount() + cursorTwo.getCount() >= 2)) {
                return new Result.Handle(new IllegalArgumentException("No such user"));
            } else {
                if(cursorOne.getCount() == 1) {
                    return new Result.Accepted<User>(
                            new NormalUser(
                                    cursorOne.getInt(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_ID)),
                                    cursorOne.getInt(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT)),
                                    cursorOne.getString(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME)),
                                    cursorOne.getString(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD)),
                                    cursorOne.getInt(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED))
                            )
                    );
                } else {
                    return new Result.Accepted<User>(
                            new Admin(
                                    cursorTwo.getInt(cursorTwo.getColumnIndex(DatabaseHelper.TABLE_ADMIN_ID)),
                                    cursorTwo.getString(cursorTwo.getColumnIndex(DatabaseHelper.TABLE_ADMIN_USERNAME)),
                                    cursorTwo.getString(cursorTwo.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD))
                            )
                    );
                }
            }
        } catch (Exception e) {
            return new Result.Handle(e);
        }
    }
}
