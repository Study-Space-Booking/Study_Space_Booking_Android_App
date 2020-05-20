package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.os.Build;

import androidx.annotation.RequiresApi;


import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;

import java.util.Optional;

public class RemoteSourceImplementation implements Source {
    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result getUserInformation(String password, String userName) {
        return null;
    }
}
