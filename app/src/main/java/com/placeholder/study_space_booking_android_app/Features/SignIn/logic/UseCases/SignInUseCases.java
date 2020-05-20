package com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases;

import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Bean.Result;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;

import java.util.Optional;

public class SignInUseCases {
    private static volatile SignInUseCases instance;
    private final SignInRepository signInRepository;
    public UserBean userBean = null;

    private SignInUseCases(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    public static SignInUseCases getInstance(SignInRepository signInRepository) {
        if(instance == null) {
            instance = new SignInUseCases(signInRepository);
        }
        return instance;
    }

    public Result signIn(String userName, String password) {
        if(userName == null || password == null) {
            return new Result.Handle(new IllegalArgumentException("Check user information"));
        }
        Result result = getUserInformation(userName, password);
        if(result instanceof Result.Accepted) {
            userBean = ((Result.Accepted)result).getUserInformation();
        }
        return result;
    }

    public void signOut() {
        userBean = null;
    }

    public boolean isSignedIn() {
        return userBean != null;
    }

    public Result getUserInformation(String userName, String password) {
        return signInRepository.getUserInformation(userName, password);
    }
}
