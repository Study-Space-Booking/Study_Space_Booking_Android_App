package com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;

public interface UserInfoListener {
    void onGetUserInfoSuccess(NormalUser user);
    void onGetUserInfoFail(DatabaseError databaseError);
    void onFoundNoUserInfo();
}
