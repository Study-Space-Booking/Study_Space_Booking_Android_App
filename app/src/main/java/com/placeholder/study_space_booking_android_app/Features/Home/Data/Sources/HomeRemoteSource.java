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
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.AdminHistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeRemoteSource implements HomeSource{
    private static volatile HomeRemoteSource instance;
    private final DatabaseReference databaseReference;
    private DatabaseReference userdatabaseReference;
    private DatabaseReference historydatabaseReference;
    ValueEventListener valueEventListener;
    List<NormalUser> allUserList = new ArrayList<>();
    List<TimeSlot> history = new ArrayList<>();

    HomeRemoteSource(DatabaseReference databaseReference, DatabaseReference userdatabaseReference, DatabaseReference historydatabaseReference) {
        this.databaseReference = databaseReference;
        this.userdatabaseReference = userdatabaseReference;
        this.historydatabaseReference = historydatabaseReference;
    }

    public static HomeRemoteSource getInstance() {
        if(instance == null) {
            instance = new HomeRemoteSource(FirebaseDatabase.getInstance().getReference().child("timeslot"),
                    FirebaseDatabase.getInstance().getReference().child("user").child("normal"),
                    FirebaseDatabase.getInstance().getReference().child("history"));
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
    public Result<List<TimeSlot>> getHistory(NormalUser user, final HistoryListener historyListener) {
        try {
            Query query = historydatabaseReference.orderByChild("userId").equalTo(user.getId());
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            history.clear();
                            if(!dataSnapshot.exists()) {
                                historyListener.onNoHistoryFound();
                            } else {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                    timeSlot.setKey(snapshot.getKey());
                                    history.add(timeSlot);
                                }
                                historyListener.onGetHistorySuccess(history);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            historyListener.onGetHistoryFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(history);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
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
    public Result<List<NormalUser>> getAllUsers(final AdminHistoryListener adminHistoryListener) {
        try {
            valueEventListener = userdatabaseReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            allUserList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("in remote source: ", "get information");
                                NormalUser normalUser = snapshot.getValue(NormalUser.class);
                                normalUser.setKey(snapshot.getKey());
                                allUserList.add(normalUser);
                            }
                            adminHistoryListener.onGetAllUserSuccess(allUserList);
                            //check.set(0, true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //check.set(0, false);
                            adminHistoryListener.onGetAllUserFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(allUserList);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<NormalUser> getUserInfo(String name, final HistoryListener historyListener) {
        try {
            Query query = userdatabaseReference.orderByChild("userName").equalTo(name);
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("in home remote source", "onDataChange: snapshot exists" + dataSnapshot.exists());
                            if(!dataSnapshot.exists()) {
                                historyListener.onFoundNoUserInfo();
                            } else {
                                NormalUser getuser = new NormalUser();
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    getuser = snapshot.getValue(NormalUser.class);
                                    getuser.setKey(snapshot.getKey());
                                    break;
                                }
                                historyListener.onGetUserInfoSuccess(getuser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            historyListener.onGetUserInfoFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public void updateUser(NormalUser user, final HistoryListener historyListener) {
        try {
            Map<String, Object> update = new HashMap<>();
            update.put(user.getKey() + "/credit", user.getCredit());
            userdatabaseReference.updateChildren(update).addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            historyListener.onUpdateUserSuccess();
                        }
                    })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    historyListener.onUpdateUserFail(e);
                                }
                            }
                    );
        } catch (Exception exception) {
            Log.d("exception", exception.getMessage());
        }
    }



}
