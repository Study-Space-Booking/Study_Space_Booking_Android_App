package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Data.Sources;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.UserInfoListener;

import java.util.ArrayList;
import java.util.List;

public class SeatRemoteSource {
    private static volatile SeatRemoteSource instance;
    private  DatabaseReference seatdatabaseReference;
    private DatabaseReference userdatabaseReference;
    private DatabaseReference historydatabaseReference;
    private List<Seat> allSeats = new ArrayList<>();
    private List<TimeSlot> seatTimeSlot = new ArrayList<>();


    SeatRemoteSource(DatabaseReference seatdatabaseReference, DatabaseReference userdatabaseReference, DatabaseReference historydatabaseReference) {
        this.seatdatabaseReference = seatdatabaseReference;
        this.userdatabaseReference = userdatabaseReference;
        this.historydatabaseReference = historydatabaseReference;
    }

    public static SeatRemoteSource getInstance() {
        if(instance == null) {
            instance = new SeatRemoteSource(FirebaseDatabase.getInstance().getReference().child("seat"),
                    FirebaseDatabase.getInstance().getReference().child("user").child("normal"),
                    FirebaseDatabase.getInstance().getReference().child("history"));
        }
        return instance;
    }

    public Result<List<Seat>> getAllSeats(final SeatListener seatListener) {
        try {
            seatdatabaseReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            allSeats.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                Seat seat = snapshot.getValue(Seat.class);

                                seat.setKey(snapshot.getKey());
                                allSeats.add(seat);
                            }

                            seatListener.onGetSeatSuccess(allSeats);
                            //check.set(0, true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //check.set(0, false);
                            seatListener.onGetSeatFail(databaseError);
                        }
                    }
            );

            return new Result.Accepted<>(allSeats);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    public Result<List<TimeSlot>> getSeatTimeSlot(Integer id, final SeatListener seatListener) {
        try {
            Query query = historydatabaseReference.orderByChild("seatId").equalTo(id);
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            seatTimeSlot.clear();
                            if(!dataSnapshot.exists()) {
                                seatListener.onSeatTimeSlotNotFound();
                            } else {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                    timeSlot.setKey(snapshot.getKey());
                                    seatTimeSlot.add(timeSlot);
                                }
                                seatListener.onSeatTimeSlotSuccess(seatTimeSlot);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            seatListener.onSeatTimeSlotFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(seatTimeSlot);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    public Result<NormalUser> getUserInfo(Integer id, final UserInfoListener userInfoListener) {
        try {

            Query query = userdatabaseReference.orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()) {

                                userInfoListener.onFoundNoUserInfo();
                            } else {
                                NormalUser getuser = new NormalUser();
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    getuser = snapshot.getValue(NormalUser.class);
                                    getuser.setKey(snapshot.getKey());
                                    break;
                                }
                                Log.d("call", "call back");
                                userInfoListener.onGetUserInfoSuccess(getuser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            userInfoListener.onGetUserInfoFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

}
