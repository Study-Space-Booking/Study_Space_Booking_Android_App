package com.placeholder.study_space_booking_android_app.Features.Register.Data.Sources;

public class RegisterRemoteSource {
    private static volatile RegisterRemoteSource instance;

    public static RegisterRemoteSource getInstance() {
        if(instance == null) {
            instance = new RegisterRemoteSource();
        }
        return instance;
    }
}
