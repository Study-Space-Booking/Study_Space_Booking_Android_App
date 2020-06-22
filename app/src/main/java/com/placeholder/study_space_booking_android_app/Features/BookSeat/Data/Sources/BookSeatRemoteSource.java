package com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Model.BookSeatListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HistoryListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookSeatRemoteSource {
    final DatabaseReference databaseReference;
    final DatabaseReference seatdatabaseReference;
    private static volatile BookSeatRemoteSource instance;
    ValueEventListener valueEventListener;
    ValueEventListener valueEventListener2;

    final List<TimeSlot> currentTs = new ArrayList<>();
    final List<TimeSlot> timeSlots = new ArrayList<>();

    final List<Seat> seats = new ArrayList<>();
    final List<Integer> seatIDs = new ArrayList<>();

    final List<TimeSlot> myBookings = new ArrayList<>();

    BookSeatRemoteSource(DatabaseReference databaseReference, DatabaseReference seatdatabaseReference) {
        this.databaseReference = databaseReference;
        this.seatdatabaseReference = seatdatabaseReference;
    }

    public static BookSeatRemoteSource getInstance() {
        if(instance == null) {
            instance = new BookSeatRemoteSource(
                    FirebaseDatabase.getInstance().getReference("timeslot"),
                    FirebaseDatabase.getInstance().getReference("seat")
            );
        }
        return instance;
    }
    public Result<List<TimeSlot>> getAllBooking(final Integer startTime, final Integer endTime, final Integer placeId, final BookSeatListener bookSeatListener) {
        try {

            valueEventListener = databaseReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            timeSlots.clear();
                            currentTs.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("in remote source: ", "get information");
                                TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                Log.d("in remote source: ", "timeSlots:" + timeSlot.getPlaceId() + " " + timeSlot.getUserId());
                                timeSlot.setKey(snapshot.getKey());
                                BookSeatRemoteSource.this.timeSlots.add(timeSlot);
                            }

                            for (TimeSlot ts : timeSlots) {
                                if ((ts.getPlaceId() == placeId) && (ts.getBookEndTime() >= startTime && ts.getBookStartTime() <= endTime)) {
                                    currentTs.add(ts);
                                }
                            }
                            bookSeatListener.onGetBookingsSuccess(currentTs);
                            //check.set(0, true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //check.set(0, false);
                            bookSeatListener.onGetBookingsFailure(databaseError);
                        }
                    }
            );

            return new Result.Accepted<>(currentTs);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }

    }

    public Result<List<Integer>> getAllSeatId (final Integer placeId, final BookSeatListener bookSeatListener){
        try {
            Log.d("remote", "get information");
            Query query = seatdatabaseReference.orderByChild("placeId").equalTo(placeId);
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("remote", "get information222");
                            BookSeatRemoteSource.this.seats.clear();
                            Log.d("remote", "get information +++++++  seat place id: " );
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Seat seat = snapshot.getValue(Seat.class);
                                Log.d("remote", "circling time ");
                                seat.setKey(snapshot.getKey());
                                Log.d("remote", "get information +++++++  seat place id: " + seat.getId());
                                BookSeatRemoteSource.this.seats.add(seat);
                            }

                            for (Seat ts : seats) {
                                if ((ts.getPlaceId() == placeId)) {
                                    seatIDs.add(ts.getId());
                                }
                            }
                            bookSeatListener.onGetSeatSuccess(seatIDs);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("remote", "canceled");
                            bookSeatListener.onGetSeatFailure(databaseError);
                        }
                    }
            );

            Log.d("remote", "get information666");
            return new Result.Accepted<>(seatIDs);
        } catch (Exception exception) {
            Log.d("remote", "bugsbgusbugs: " + exception.getMessage());
            return new Result.Handle(exception);
        }
    }

    public void insertBooking(TimeSlot ts, final BookSeatListener bookSeatListener) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(ts).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        bookSeatListener.onAddBookingsSuccess();
                    }
                }
        );
    }

    public Result<Submission> removeListener() {
        try {
            databaseReference.removeEventListener(valueEventListener);
            seatdatabaseReference.removeEventListener(valueEventListener2);
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    public Result<List<TimeSlot>> getMyBookings(NormalUser user, final BookSeatListener bookSeatListener) {
        try {
            Query query = databaseReference.orderByChild("userId").equalTo(user.getId());
            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myBookings.clear();
                            if(!dataSnapshot.exists()) {
                                bookSeatListener.onGetMyBookingsNotFound();
                            } else {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                                    timeSlot.setKey(snapshot.getKey());
                                    myBookings.add(timeSlot);
                                }
                                bookSeatListener.onGetMyBookingsSuccess(myBookings);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            bookSeatListener.onGetMyBookingsFail(databaseError);
                        }
                    }
            );
            return new Result.Accepted<>(myBookings);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }


}
