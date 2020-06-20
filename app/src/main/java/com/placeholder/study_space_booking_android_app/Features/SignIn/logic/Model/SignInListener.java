package com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Model;

import com.placeholder.study_space_booking_android_app.Core.Beans.User;

public interface SignInListener {
    void onNoUserFound();
    void onDuplicateUserFound();
    void onUserFound(User user);
    void onGettingUserInformationException(Exception exception);
}
