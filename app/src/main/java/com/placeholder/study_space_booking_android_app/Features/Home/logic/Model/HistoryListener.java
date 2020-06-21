package com.placeholder.study_space_booking_android_app.Features.Home.logic.Model;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;

import java.util.List;

public interface HistoryListener {
    void onNoHistoryFound();
    void onGetHistorySuccess(List<TimeSlot> list);
    void onGetHistoryFail(DatabaseError databaseError);
    void onGetUserInfoSuccess(NormalUser user);
    void onGetUserInfoFail(DatabaseError databaseError);
    void onFoundNoUserInfo();
    void onUpdateUserSuccess();
    void onUpdateUserFail(Exception e);
}
