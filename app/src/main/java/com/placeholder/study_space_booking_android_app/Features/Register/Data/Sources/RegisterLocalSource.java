package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import android.database.Cursor;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Model.RegisterListener;
import com.placeholder.study_space_booking_android_app.db.DBAdminManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;


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
    public boolean hasExistingUser(String userName, RegisterListener registerListener) {
        try {
            boolean check = false;
            Cursor cursorOne = dbUserInformationManager.getUserInformation(userName, null, null);
            Cursor cursorTwo = dbAdminManager.getAdmin(userName, null, null);
            if (cursorOne.getCount() + cursorTwo.getCount() > 0) {
                check = true;
                registerListener.onExistingUser();
            } else {
                registerListener.onNewUser();
            }
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public Result<NormalUser> register(String userName, String password, RegisterListener registerListener) {
        try{
            boolean check = dbUserInformationManager.insertUserInformation(
                    new NormalUser(0, INITIAL_CREDIT, userName, password, 0)
            );
            if(check == false) {
                registerListener.onCheckRegistration();
                return new Result.Accepted<>(null);
            } else {
                registerListener.onRegistered();
                return new Result.Accepted<NormalUser>(null);
            }
        } catch (Exception e) {
            return new Result.Handle(e);
        }
    }
}
