package com.placeholder.study_space_booking_android_app.api;

import com.placeholder.study_space_booking_android_app.Core.Beans.User;

public class RemoteServerApiImpl implements Api {

    @Override
    public Result<User> login(String account, String password) {
        // request remote server synchronize
        // get data
        // parse data
        // to bean
        User p = new User();
        Result<User> ret = new Result<>(p);
        return ret;
    }

}
