package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;

import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Model.SignInListener;
import com.placeholder.study_space_booking_android_app.db.DBAdminManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.db.DatabaseHelper;


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

    public static LocalSourceImplementation getInstance() {
        if(instance == null) {
            instance = new LocalSourceImplementation(DBUserInformationManager.getInstance(), DBAdminManager.getInstance());
        }
        return instance;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public Result<User> getUserInformation(String userName, String password, SignInListener signInListener) {
        try {
            Cursor cursorOne = dbUserInformationManager.getUserInformation(userName, password, null);
            Cursor cursorTwo = dbAdminManager.getAdmin(userName, password, null);
            if (cursorOne.getCount() + cursorTwo.getCount() == 0) {
                signInListener.onNoUserFound();
                return new Result.Accepted<>(null);
            } else if (cursorOne.getCount() + cursorTwo.getCount() >= 2) {
                signInListener.onDuplicateUserFound();
                return new Result.Accepted<>(null);
            } else {
                if (cursorOne.getCount() == 1) {
                    //System.out.println(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_ID));
                    //System.out.println(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT));
                    //System.out.println(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME));
                    //System.out.println(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD));
                    //System.out.println(cursorOne.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED));
                    cursorOne.moveToFirst();
                    NormalUser normalUser = new NormalUser(
                            Integer.parseInt(cursorOne.getString(0)),
                            Integer.parseInt(cursorOne.getString(3)),
                            cursorOne.getString(1),
                            cursorOne.getString(2),
                            Integer.parseInt(cursorOne.getString(4))
                    );
                    signInListener.onUserFound(normalUser);
                    return new Result.Accepted<User>(normalUser);
                } else {
                    cursorTwo.moveToFirst();
                    Admin administrator = new Admin(
                            cursorTwo.getInt(cursorTwo.getColumnIndex(DatabaseHelper.TABLE_ADMIN_ID)),
                            cursorTwo.getString(cursorTwo.getColumnIndex(DatabaseHelper.TABLE_ADMIN_USERNAME)),
                            cursorTwo.getString(cursorTwo.getColumnIndex(DatabaseHelper.TABLE_ADMIN_PASSWORD))
                    );
                    signInListener.onUserFound(administrator);
                    return new Result.Accepted<User>(administrator);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result.Handle(e);
        }
    }
}
