package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<TimeSlot> bookings;
    private HomeListener homeListener;

    HomeRecyclerViewAdapter(List<TimeSlot> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_booking, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(linearLayout);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Result<String> result = HomeFragment.HOME_USE_CASES.getPlaceName(bookings.get(position).getPlaceId());
        long startTime = bookings.get(position).getBookStartTime();
        startTime = startTime * 1000;
        long endTime = bookings.get(position).getBookEndTime();
        endTime = endTime * 1000;
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String startTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());
        calendar.setTime(endDate);
        String endTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());
        String place;
        if (bookings.get(position).getPlaceId() == 1) {
            place = "Mac Commons";
        } else {
            place = "PC Commons";
        }
        //Result<String> result = HomeFragment.HOME_USE_CASES.getPlaceName(bookings.get(position).getPlaceId());
        final String bookingSeat = "Place: "+ place + "           " + " Seat: " + bookings.get(position).getSeatId().toString();
        final String bookingTime = "From: " + startTimeString +
                "\nTo:     " + endTimeString;
        String bookingState = "Status: future booking";

        //if(result instanceof Result.Handle) {
        //    ((HomeViewHolder) holder).title.setText("place not found");
        //} else {
        //    ((HomeViewHolder) holder).title.setText(((Result.Accepted<String>)result).getModel());
        //}


        final TimeSlot timeSlot = bookings.get(position);
        if (timeSlot.getPlaceId() == 1) {
            ((HomeViewHolder) holder).title.setBackgroundResource(R.drawable.macbackground);
        } else {
            ((HomeViewHolder) holder).title.setBackgroundResource(R.drawable.pcbackground);
        }
//        ImageView img= (ImageView) findViewById(R.id.image);
//        img.setImageResource(R.drawable.my_image);
        ((HomeViewHolder) holder).seat.setText(bookingSeat);
        ((HomeViewHolder) holder).time.setText(bookingTime);
        ((HomeViewHolder) holder).state.setText(bookingState);
        /*
        ((HomeViewHolder) holder).callOffBooking.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callOffBookings(timeSlot, homeListener);
                    }
                }
        );
        */
    }

    /*
    void callOffBookings(TimeSlot timeSlot, HomeListener homeListener) {
        int position = bookings.indexOf(timeSlot);
        HomeFragment.HOME_USE_CASES.callOffBooking(bookings.get(position), homeListener);
        bookings.remove(position);
        notifyItemRemoved(position);
    }
    */

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public final class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //View view;
        ImageView title;
        TextView seat;
        TextView time;
        TextView state;
        Button callOffBooking;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.view = itemView;
            title = (ImageView) itemView.findViewById(R.id.item_booking_title);
            seat = (TextView) itemView.findViewById(R.id.item_booking_seat);
            time = (TextView) itemView.findViewById(R.id.item_booking_time);
            state = (TextView) itemView.findViewById(R.id.item_booking_state);
            callOffBooking = (Button) itemView.findViewById(R.id.call_off_booking_button);
            callOffBooking.setOnClickListener(this);
        }

        /*
        public HomeViewHolder() {
            super(linearLayout);
            this.linearLayout = linearLayout;
            title = (ImageView) linearLayout.findViewById(R.id.item_booking_title);
            seat = (TextView) linearLayout.findViewById(R.id.item_booking_seat);
            time = (TextView) linearLayout.findViewById(R.id.item_booking_time);
            state = (TextView) linearLayout.findViewById(R.id.item_booking_state);
            callOffBooking = (Button) linearLayout.findViewById(R.id.call_off_booking_button);
            callOffBooking.setOnClickListener(this);
        }
        */
        @Override
        public void onClick(View v) {
            int postion = getAdapterPosition();
            if(homeListener != null && postion != RecyclerView.NO_POSITION) {
                homeListener.onCallOffBookingsClick(postion);
                /*
                HomeRecyclerViewAdapter.this.homeListener.onCallOffBookingsClick(postion);
                TimeSlot booking = bookings.get(postion);
                bookings.remove(postion);
                notifyDataSetChanged();
                Result<TimeSlot> result = HomeFragment.HOME_USE_CASES.callOffBooking(booking, homeListener);
                homeListener.onCallOffBookingSuccess();
                */
            }
        }
    }

    public void setHomeListener(HomeListener homeListener) {
        this.homeListener = homeListener;
    }
}
