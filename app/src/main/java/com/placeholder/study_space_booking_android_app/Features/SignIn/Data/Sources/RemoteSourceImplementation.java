package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.os.Build;

import androidx.annotation.RequiresApi;


import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;

import java.util.Optional;

public class RemoteSourceImplementation implements Source {
    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result<UserBean> getUserInformation(String password, String userName) {
        return null;
    }
}
