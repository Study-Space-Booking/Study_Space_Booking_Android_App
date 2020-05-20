package com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository;

import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;


import java.util.Optional;

public interface SignInRepository {
    //boolean isExistingUser(String userName, String password);
    Result getUserInformation(String userName, String password);
}
