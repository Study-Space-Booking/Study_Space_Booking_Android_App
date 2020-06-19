package com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Usecases;


import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Data.Repository.BookSeatRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Model.BookSeatListener;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository.BookSeatRepository;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository.RegisterRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;

import java.util.ArrayList;
import java.util.List;


public class BookSeatUseCases {
    private final BookSeatRepository bookSeatRepository;
    private static volatile BookSeatUseCases instance;
    private List<Integer> seats;

    private BookSeatUseCases(BookSeatRepository bookSeatRepository) {
        this.bookSeatRepository = bookSeatRepository;
    }

    public static BookSeatUseCases getInstance() {
        if(instance == null) {
            instance = new BookSeatUseCases(BookSeatRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Integer CurrStateOfSeat(TimeSlot t) {
        return bookSeatRepository.CurrStateOfSeat(t);
    }

    public Result<List<Integer>> getAllSeatId(Integer placeId, BookSeatListener bookSeatListener) {
        return bookSeatRepository.getAllSeatId(placeId, bookSeatListener);
    }

//    public Result<List<Integer>> getOccupiedSeat(Integer startTime, Integer endTime, Integer placeId, BookSeatListener bookSeatListener) {
//        Result<List<TimeSlot>> result = bookSeatRepository.getAllBooking(startTime, endTime, placeId, bookSeatListener);
//        //Log.d("debug", "debgug can see?");
//        if(result instanceof Result.Handle) {
//            //Log.d("debug", "debgug can see?");
//            return new Result.Handle(new Exception("cannot find booking information"));
//        } else {
//            List<TimeSlot> bookings = ((Result.Accepted<List<TimeSlot>>) result).getModel();
//            List<Integer> seats = new ArrayList<>();
//            for(int i = 0; i < bookings.size(); i = i + 1) {
//                if (bookings.get(i).getBookEndTime() > startTime && bookings.get(i).getBookStartTime() < endTime)
//                    seats.add(bookings.get(i).getSeatId());
//            }
//            this.seats = seats;
//            return new Result.Accepted<>(seats);
//        }
//    }

    public boolean isOccupied(Integer seatId) {
        if(seats == null) {
            return true;
        } else {
            boolean result = false;
            for(int i = 0; i < seats.size(); i = i + 1) {
                if(seats.get(i).equals(seatId)) {
                    result = true;
                }
            }
            return result;
        }
    }

    public Result<List<TimeSlot>> getAllBooking(Integer startTime, Integer endTime, Integer placeId, BookSeatListener bookSeatListener) {
        return bookSeatRepository.getAllBooking(startTime, endTime, placeId, bookSeatListener);
    }
    public void bookSeat(TimeSlot t, BookSeatListener bookSeatListener) {
        bookSeatRepository.insertBooking(t, bookSeatListener);
    }

    public Result<Submission> removeListener() {
        return bookSeatRepository.removeListener();
    }
}
