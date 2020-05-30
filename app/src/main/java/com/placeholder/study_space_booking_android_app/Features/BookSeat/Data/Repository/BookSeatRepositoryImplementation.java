package com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources.BookSeatLocalSource;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Sources.BookSeatRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository.BookSeatRepository;

import java.util.List;

public class BookSeatRepositoryImplementation implements BookSeatRepository {
    private final BookSeatLocalSource bookSeatLocalSource;
    private final BookSeatRemoteSource bookSeatRemoteSource;
    private static volatile BookSeatRepositoryImplementation instance;

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
    public Result<List<Integer>> getAllSeatId(Integer placeId) {
        return null;
    }

    @Override
    public Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId) {
        return null;
    }

    @Override
    public Result<TimeSlot> insertBooking(TimeSlot timeSlot) {
        return null;
    }
}
