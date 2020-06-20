package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Model;

public interface RegisterListener {
    void onNewUser();
    void onExistingUser();
    void onRegistered();
    void onCheckRegistration();
}
