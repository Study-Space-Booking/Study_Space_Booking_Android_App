package com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources.RegisterLocalSource;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources.RegisterRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;

public class RegisterRepositoryImplementation implements RegisterRepository {
    private final RegisterLocalSource registerLocalSource;
    private final RegisterRemoteSource registerRemoteSource;
    public static volatile RegisterRepositoryImplementation instance;

    private RegisterRepositoryImplementation(RegisterLocalSource registerLocalSource,
                                             RegisterRemoteSource registerRemoteSource) {
        this.registerLocalSource = registerLocalSource;
        this.registerRemoteSource = registerRemoteSource;
    }

    public static RegisterRepositoryImplementation getInstance(RegisterLocalSource registerLocalSource,
                                                               RegisterRemoteSource registerRemoteSource) {
        if(instance == null) {
            instance = new RegisterRepositoryImplementation(registerLocalSource, registerRemoteSource);
        }
        return instance;
    }


    @Override
    public boolean hasExistingUser(String userName) {
        return registerLocalSource.hasExistingUser(userName);
    }

    @Override
    public Result<NormalUser> register(String userName, String password) {
        return registerLocalSource.register(userName, password);
    }
}
