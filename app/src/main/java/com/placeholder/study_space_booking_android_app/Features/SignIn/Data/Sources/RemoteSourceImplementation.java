package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;

import java.util.Optional;

public class RemoteSourceImplementation implements Source {
    private static volatile RemoteSourceImplementation instance;

    public static RemoteSourceImplementation getInstance() {
        if(instance == null) {
            instance = new RemoteSourceImplementation();
        }
        return instance;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result<User> getUserInformation(String password, String userName) {
        return null;
    }
}
