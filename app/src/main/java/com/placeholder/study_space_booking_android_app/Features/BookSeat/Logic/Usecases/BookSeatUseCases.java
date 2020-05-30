package com.placeholder.study_space_booking_android_app.Features.Register.Logic.Usecases;


import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Repository.BookSeatRepository;
import com.placeholder.study_space_booking_android_app.Features.Register.Data.Repository.RegisterRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.Register.Logic.Repository.RegisterRepository;


public class BookSeatUseCases {
    private final BookSeatRepository bookSeatRepository;
    private static volatile RegisterUseCases instance;

    private BookSeatUseCases(BookSeatRepository bookSeatRepository) {
        this.bookSeatRepository = bookSeatRepository;
    }

    public static RegisterUseCases getInstance() {
        if(instance == null) {
            instance = new BookSeatUseCases(BookSeatRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Integer CurrStateOfSeat(TimeSlot t) {
        return bookSeatRepository.CurrStateOfSeat(t);
    }
}
