package com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;

import java.util.Optional;

public class SignInUseCases {
    private static volatile SignInUseCases instance;
    private final SignInRepository signInRepository;
    public User user = null;

    private SignInUseCases(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    public static SignInUseCases getInstance(SignInRepository signInRepository) {
        if(instance == null) {
            instance = new SignInUseCases(signInRepository);
        }
        return instance;
    }

    public Result<User> signIn(String userName, String password) {
        if(userName == null || password == null) {
            return new Result.Handle(new IllegalArgumentException("Check user information"));
        }
        Result<User> result = getUserInformation(userName, password);
        if(result instanceof Result.Accepted) {
            user = ((Result.Accepted<User>)result).getModel();
        }
        return result;
    }

    public void signOut() {
        user = null;
    }

    public boolean isSignedIn() {
        return user != null;
    }

    public Result<User> getUserInformation(String userName, String password) {
        return signInRepository.getUserInformation(userName, password);
    }
}
