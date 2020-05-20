package com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Repository;

import com.placeholder.bookingapplication.R;

import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.LocalSourceImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.RemoteSourceImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Sources.Source;

import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;

import java.util.Optional;

public class RepositoryImplementation implements SignInRepository {
    private final Source localSource;
    private final Source remoteSource;
    public static volatile SignInRepository instance;

    public RepositoryImplementation(LocalSourceImplementation localSource, RemoteSourceImplementation remoteSource) {
        this.localSource = localSource;
        this.remoteSource = remoteSource;
    }

    public SignInRepository getInstance(LocalSourceImplementation localSource, RemoteSourceImplementation remoteSource) {
        if(instance == null) {
            instance = new RepositoryImplementation(localSource, remoteSource);
        }
        return instance;
    }

    /*
    @Override
    public boolean isExistingUser(String userName, String password) {
        return localSource;
    }
    */

    @Override
    public Result getUserInformation(String userName, String password) {
        return localSource.getUserInformation(userName, password);
    }
}
