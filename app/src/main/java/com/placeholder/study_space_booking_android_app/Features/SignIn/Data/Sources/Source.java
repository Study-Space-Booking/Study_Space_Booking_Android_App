package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;



import java.util.Optional;

public interface Source {
    Result<User> getUserInformation(String password, String userName);
}
