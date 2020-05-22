package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;


public interface RegisterSource {
    boolean hasExistingUser(String userName);
    Result<NormalUser> register(String userName, String password);
}
