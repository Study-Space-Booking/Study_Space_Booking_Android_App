package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;


import java.util.Optional;

public interface Source {
    Result getUserInformation(String password, String userName);
}
