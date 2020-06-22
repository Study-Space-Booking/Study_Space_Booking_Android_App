package com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.State;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases.ScanListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScanOptionRemoteSource {
    private DatabaseReference databaseReference;
    private static volatile ScanOptionRemoteSource instance;
    TimeSlot timeSlot;
    List<TimeSlot> bookings = new ArrayList<>();

    ScanOptionRemoteSource(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public static ScanOptionRemoteSource getInstance() {
        if (instance == null) {
            instance = new ScanOptionRemoteSource(
                    FirebaseDatabase.getInstance().getReference().child("timeslot")
            );
        }
        return instance;
    }


    public List<TimeSlot> getAllBookings(NormalUser user, final ScanListener scanListener) {
        try {
            Query query = databaseReference.orderByChild("userId").equalTo(user.getId());
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            bookings.clear();
                            Log.d("in home remote source", "onDataChange: snapshot exists" + dataSnapshot.exists());
                            if(!dataSnapshot.exists()) {
                                scanListener.onGetTimeSlotNotFound();
                            } else {
                                Log.d("in home remote source", "onDataChange: before loading booking size" + bookings.size());
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                    timeSlot.setKey(snapshot.getKey());
                                    bookings.add(timeSlot);
                                }
                                Log.d("in home remote source", "onDataChange: after loading booking size" + bookings.size());
                                scanListener.onGetTimeSlotSuccess(bookings);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            scanListener.onGetTimeSlotFail(databaseError);
                        }
                    }
            );
            return (bookings);
        } catch (Exception exception) {
            Log.d("debug", exception.getMessage());
        }
        return null;
    }


}
