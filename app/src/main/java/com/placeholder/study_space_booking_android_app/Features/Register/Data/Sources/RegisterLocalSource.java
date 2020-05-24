package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.DBAdminManager;
import com.placeholder.study_space_booking_android_app.DBUserInformationManager;


public class RegisterLocalSource implements RegisterSource {
    public static final Integer INITIAL_CREDIT = 10;
    private final DBUserInformationManager dbUserInformationManager;
    private final DBAdminManager dbAdminManager;
    public static volatile RegisterLocalSource instance;

    private RegisterLocalSource(DBUserInformationManager dbUserInformationManager,
                                DBAdminManager dbAdminManager) {
        this.dbUserInformationManager = dbUserInformationManager;
        this.dbAdminManager = dbAdminManager;
    }

    public static RegisterLocalSource getInstance() {
        if(instance == null) {
            instance = new RegisterLocalSource(DBUserInformationManager.getInstance(), DBAdminManager.getInstance());
        }
        return instance;
    }

    @Override
    public boolean hasExistingUser(String userName) {
        boolean check = false;
        Cursor cursorOne = dbUserInformationManager.getUserInformation(userName, null, null);
        Cursor cursorTwo = dbAdminManager.getAdmin(userName, null, null);
        if(cursorOne.getCount() + cursorTwo.getCount() > 0) {
            check = true;
        }
        return check;
    }

    @Override
    public Result<NormalUser> register(String userName, String password) {
        try{
            boolean check = dbUserInformationManager.insertUserInformation(
                    new NormalUser(0, INITIAL_CREDIT, userName, password, 0)
            );
            if(check == false) {
                return new Result.Handle(new Exception("check insertion"));
            } else {
                return new Result.Accepted<NormalUser>(null);
            }
        } catch (Exception e) {
            return new Result.Handle(e);
        }
    }
}
