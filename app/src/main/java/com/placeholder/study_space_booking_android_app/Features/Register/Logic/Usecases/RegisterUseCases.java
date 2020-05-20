package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Usecases;

import android.os.ResultReceiver;

import com.placeholder.bookingapplication.R;
import com.placeholder.study_space_booking_android_app.Core.Beans.UserBean;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Bean.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.Repository.SignInRepository;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;

public class RegisterUseCases {
    private final RegisterRepository registerRepository;
    private static volatile RegisterUseCases instance;

    private RegisterUseCases(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public static RegisterUseCases getInstance(RegisterRepository registerRepository) {
        if(instance == null) {
            instance = new RegisterUseCases(registerRepository);
        }
        return instance;
    }

    public Result register(String userName, String password, String confirmPassword) {
        if(userName == null || password == null || confirmPassword == null) {
            return new Result.Handle(new IllegalArgumentException("Check user information"));
        } else if(!passwordMatch(password, confirmPassword)) {
            return new Result.Handle(new IllegalArgumentException("Confirm password"));
        } else if(registerRepository.hasExistingUser(userName, password)) {
            return new Result.Handle(new IllegalArgumentException("Existing user"));
        } else {
            return registerRepository.register(userName, password);
        }
    }

    public boolean passwordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
