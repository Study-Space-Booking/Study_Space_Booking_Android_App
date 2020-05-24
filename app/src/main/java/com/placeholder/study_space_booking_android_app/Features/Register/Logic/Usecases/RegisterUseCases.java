package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Usecases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository.RegisterRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;


public class RegisterUseCases {
    private final RegisterRepository registerRepository;
    private static volatile RegisterUseCases instance;

    private RegisterUseCases(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public static RegisterUseCases getInstance() {
        if(instance == null) {
            instance = new RegisterUseCases(RegisterRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<NormalUser> register(String userName, String password, String confirmPassword) {
        if(userName.equals("") || password.equals("") || confirmPassword.equals("")) {
            return new Result.Handle(new IllegalArgumentException("Check user information"));
        } else if(!passwordMatch(password, confirmPassword)) {
            return new Result.Handle(new IllegalArgumentException("Confirm password"));
        } else if(registerRepository.hasExistingUser(userName)) {
            return new Result.Handle(new IllegalArgumentException("Existing user"));
        } else {
            return registerRepository.register(userName, password);
        }
    }

    public boolean passwordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
