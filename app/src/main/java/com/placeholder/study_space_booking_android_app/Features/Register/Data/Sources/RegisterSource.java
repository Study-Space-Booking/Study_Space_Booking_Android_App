package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean.Result;

public interface RegisterSource {
    boolean hasExistingUser(String userName, String password);
    Result register(String userName, String password);
}
