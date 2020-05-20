package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean.Result;


public class RegisterLocalSource implements RegisterSource {
    public static final Integer INITIAL_CREDIT = 10;
    private final DBUserInformationManager dbUserInformationManager;
    public static volatile RegisterLocalSource instance;

    private RegisterLocalSource(DBUserInformationManager dbUserInformationManager) {
        this.dbUserInformationManager = dbUserInformationManager;
    }

    public static RegisterLocalSource getInstance(DBUserInformationManager dbUserInformationManager) {
        if(instance == null) {
            instance = new RegisterLocalSource(dbUserInformationManager);
        }
        return instance;
    }

    @Override
    public boolean hasExistingUser(String userName, String password) {
        boolean check = false;
        Cursor cursor = dbUserInformationManager.getUserInformation(userName, null, null);
        if(cursor.getCount() > 0) {
            check = true;
        }
        return check;
    }

    @Override
    public Result register(String userName, String password) {
        try{
            boolean check = dbUserInformationManager.insertUserInformation(
                    new UserBean(0, INITIAL_CREDIT, userName, password, 0, 0)
            );
            if(check == false) {
                return new Result.Handle(new Exception("check insertion"));
            } else {
                return new Result.Accepted();
            }
        } catch (Exception e) {
            return new Result.Handle(e);
        }
    }
}
