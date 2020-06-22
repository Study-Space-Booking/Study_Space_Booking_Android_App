package com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Data.Repository.RepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Model.SignInListener;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;

import java.util.Optional;

public class SignInUseCases {
    private static volatile SignInUseCases instance;
    private final SignInRepository signInRepository;
    public static User user = null;

    private SignInUseCases(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    public static SignInUseCases getInstance() {
        if(instance == null) {
            instance = new SignInUseCases(RepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<User> signIn(String userName, String password, SignInListener signInListener) {
        if(userName.equals("") || password.equals("")) {
            return new Result.Handle(new IllegalArgumentException("All the fields need to be completed"));
        }

        Result<User> result = getUserInformation(userName, password, signInListener);
        /*
        if(result instanceof Result.Accepted) {
            user = ((Result.Accepted<User>)result).getModel();
        }
        */
        return result;
    }

    public static void signOut() {
        user = null;
    }

    public static boolean isSignedIn() {
        return user != null;
    }

    public Result<User> getUserInformation(String userName, String password, SignInListener signInListener) {
        return signInRepository.getUserInformation(userName, password, signInListener);
    }
}
