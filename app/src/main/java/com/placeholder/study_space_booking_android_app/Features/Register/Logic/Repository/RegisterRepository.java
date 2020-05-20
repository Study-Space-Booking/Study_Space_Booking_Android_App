package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository;

import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean.Result;

public interface RegisterRepository {
    boolean hasExistingUser(String userName, String password);
    Result register(String userName, String password);
}
