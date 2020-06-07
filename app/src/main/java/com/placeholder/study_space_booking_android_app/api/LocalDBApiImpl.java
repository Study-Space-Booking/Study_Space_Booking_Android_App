package com.placeholder.study_space_booking_android_app.api;

import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.Source;

public class LocalDBApiImpl implements Api {

    private final Source mLocalSource;

    public LocalDBApiImpl(){
        mLocalSource = LocalSourceImplementation.getInstance();
    }

    @Override
    public Result<User> login(String account, String password) {
        // query db synchronize
        // get data
        // convert to bean
//      Result<User> result = mLocalSource.getUserInformation(account, password);
//        return result;
        return null;
    }

}
