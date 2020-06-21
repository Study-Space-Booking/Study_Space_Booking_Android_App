package com.placeholder.study_space_booking_android_app.Features.Home.logic.Model;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;

import java.util.List;

public interface AdminHistoryListener {
    void onGetAllUserSuccess(List<NormalUser> list);
    void onGetAllUserFail(DatabaseError databaseError);
}
