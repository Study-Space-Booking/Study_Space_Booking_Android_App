package com.placeholder.study_space_booking_android_app.api;

import com.placeholder.study_space_booking_android_app.Core.Beans.User;

public interface Api {
    Result<User> login(String account, String password);
}
