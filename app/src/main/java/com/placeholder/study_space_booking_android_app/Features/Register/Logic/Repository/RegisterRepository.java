package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Model.RegisterListener;


public interface RegisterRepository {
    boolean hasExistingUser(String userName, RegisterListener registerListener);
    Result<NormalUser> register(String userName, String password, RegisterListener registerListener);

}
