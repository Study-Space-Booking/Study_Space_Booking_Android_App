package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Usecases;


import android.content.Intent;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository.RegisterRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Model.RegisterListener;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;


public class RegisterUseCases {
    private final RegisterRepository registerRepository;
    private static volatile RegisterUseCases instance;
    private static final int PASSWORD_LENGTH = 6;
    private static final int NUMBER_OF_CHARACTERS_NEEDED = 1;
    private static final int NUMBER_OF_DIGITS_NEEDED = 1;

    private RegisterUseCases(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public static RegisterUseCases getInstance() {
        if(instance == null) {
            instance = new RegisterUseCases(RegisterRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<NormalUser> validate(String userName, String password, String confirmPassword, RegisterListener registerListener) {
        if(userName.equals("") || password.equals("") || confirmPassword.equals("")) {
            return new Result.Handle(new IllegalArgumentException("All the fields need to be completed"));
        } else if(!passwordMatch(password, confirmPassword)) {
            return new Result.Handle(new IllegalArgumentException("Confirm password needs to match the passowrd entered"));
        } else if(!isValidUserName(userName)) {
            return new Result.Handle(new IllegalArgumentException("Register with the school email address"));
        } else if(!isPasswordLengthValid(password)) {
            return new Result.Handle(new IllegalArgumentException("Password needs to have a length of at least " + PASSWORD_LENGTH));
        } else if(!isPasswordValid(password)) {
            return new Result.Handle(new IllegalArgumentException(
                    "Password needs to have at least " + NUMBER_OF_CHARACTERS_NEEDED + " character and " + NUMBER_OF_DIGITS_NEEDED + " digit"));
        } else {
            registerRepository.hasExistingUser(userName, registerListener);
            return new Result.Accepted<>(null);
        }
    }

    public Result<NormalUser> register(String userName, String password, RegisterListener registerListener) {
        return registerRepository.register(userName, password, registerListener);
    }

    public boolean passwordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean isValidUserName(String userName) {
        if(userName.length() != 18) {
            return false;
        } else {
            boolean check = true;
            for(int i = 0; i < 7; i = i + 1) {
                if(userName.charAt(i + 1) < '0' || userName.charAt(i + 1) > '9') {
                    check = false;
                }
            }
            return check && userName.charAt(0) == 'e' && userName.substring(8, 18).equals("@u.nus.edu");
        }
    }

    public boolean isPasswordLengthValid(String password) {
        return password.length() >= 6;
    }

    public boolean isPasswordValid(String password) {
        boolean hasCharacter = false;
        boolean hasDigit = false;
        for(int i = 0; i < password.length(); i = i + 1) {
            if(Character.isLetter(password.charAt(i))) {
                hasCharacter = true;
            } else if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else {

            }
        }
        return hasCharacter && hasDigit;
    }
}
