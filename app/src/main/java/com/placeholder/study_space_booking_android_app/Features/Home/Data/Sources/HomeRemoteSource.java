package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;

import java.util.ArrayList;
import java.util.List;

public class HomeRemoteSource implements HomeSource{
    private static volatile HomeRemoteSource instance;
    private final DatabaseReference databaseReference;

    HomeRemoteSource(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public static HomeRemoteSource getInstance() {
        if(instance == null) {
            instance = new HomeRemoteSource(FirebaseDatabase.getInstance().getReference().child("timeslot"));
        }
        return instance;
    }

    @Override
    public Result<List<TimeSlot>> getAllBookings(final List<TimeSlot> bookings, final NormalUser user, final HomeListener homeListener) {
        try {
            Query query = databaseReference.orderByChild("userId").equalTo(user.getId());
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            bookings.clear();
                            Log.d("in home remote source", "onDataChange: snapshot exists" + dataSnapshot.exists());
                            if(!dataSnapshot.exists()) {
                                homeListener.onNoBookingFound();
                            } else {
                                Log.d("in home remote source", "onDataChange: before loading booking size" + bookings.size());
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                    timeSlot.setKey(snapshot.getKey());
                                    bookings.add(timeSlot);
                                }
                                Log.d("in home remote source", "onDataChange: after loading booking size" + bookings.size());
                                homeListener.onGetBookingsSuccess(bookings);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            homeListener.onGetBookingsFailure(new Exception(databaseError.getMessage()));
                        }
                    }
            );
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }

    }

    @Override
    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        return null;
    }

    @Override
    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot, final HomeListener homeListener) {
        try {
            databaseReference.child(timeSlot.getKey()).removeValue();
            homeListener.onCallOffBookingSuccess();
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }


    @Override
    public Result<List<User>> getAllUsers() {
        return null;
    }

    @Override
    public Result<NormalUser> getUserInfo(String name) {
        return null;
    }

    @Override
    public void updateUser(NormalUser user) {

    }


}
