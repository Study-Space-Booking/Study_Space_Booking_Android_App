package com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Repository;

import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources.BookSeatLocalSource;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources.BookSeatRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Model.BookSeatListener;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository.BookSeatRepository;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

import java.util.ArrayList;
import java.util.List;

public class BookSeatRepositoryImplementation implements BookSeatRepository {
    private final BookSeatLocalSource bookSeatLocalSource;
    private final BookSeatRemoteSource bookSeatRemoteSource;
    private static volatile BookSeatRepositoryImplementation instance;
    List<Integer> seatIDs = new ArrayList<>();

    BookSeatRepositoryImplementation(BookSeatLocalSource bookSeatLocalSource, BookSeatRemoteSource bookSeatRemoteSource) {
        this.bookSeatLocalSource = bookSeatLocalSource;
        this.bookSeatRemoteSource = bookSeatRemoteSource;
    }

    public static BookSeatRepositoryImplementation getInstance() {
        if(instance == null) {
            instance = new BookSeatRepositoryImplementation(BookSeatLocalSource.getInstance(), BookSeatRemoteSource.getInstance());
        }
        return instance;
    }

    @Override
    public Integer CurrStateOfSeat(TimeSlot t) {
        return null;
    }

    @Override
    public Result<TimeSlot> chooseTimeSlot(Integer bookTime, Integer UserId, Integer SeatId, Integer PlaceId) {
        return null;
    }

    @Override
    public Result<List<Integer>> getAllSeatId(Integer placeId, BookSeatListener bookSeatListener) {
        //Log.d("debug", "debug at repo implementation");
        return bookSeatRemoteSource.getAllSeatId(placeId,bookSeatListener);
    }

    @Override
    public Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId, BookSeatListener bookSeatListener) {
        //Log.d("debug", "debgug can see?");
        return bookSeatRemoteSource.getAllBooking(startTime, endTime, placeId, bookSeatListener);
    }

    @Override
    public void insertBooking(TimeSlot timeSlot, BookSeatListener bookSeatListener) {
        bookSeatRemoteSource.insertBooking(timeSlot, bookSeatListener);
    }

    public Result<Submission> removeListener() {
        return bookSeatRemoteSource.removeListener();
    }

    public Result<List<TimeSlot>> getMyBookings(NormalUser user, final BookSeatListener bookSeatListener) {
        return bookSeatRemoteSource.getMyBookings(user, bookSeatListener);
    }
}
