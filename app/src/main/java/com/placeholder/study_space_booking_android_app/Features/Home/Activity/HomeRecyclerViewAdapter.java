package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<TimeSlot> bookings;

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
        final String bookingSeat = "seat: " + bookings.get(position).getSeatId().toString();
        final String bookingTime = "time: " + bookings.get(position).getBookStartTime() +
                " to " + bookings.get(position).getBookEndTime();
        String bookingState = "status: future";

        //if(result instanceof Result.Handle) {
        //    ((HomeViewHolder) holder).title.setText("place not found");
        //} else {
        //    ((HomeViewHolder) holder).title.setText(((Result.Accepted<String>)result).getModel());
        //}


        final TimeSlot timeSlot = bookings.get(position);
        ((HomeViewHolder) holder).title.setText("place");
        ((HomeViewHolder) holder).seat.setText(bookingSeat);
        ((HomeViewHolder) holder).time.setText(bookingTime);
        ((HomeViewHolder) holder).state.setText(bookingState);
        ((HomeViewHolder) holder).callOffBooking.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callOffBookings(timeSlot);
                    }
                }
        );
    }

    void callOffBookings(TimeSlot timeSlot) {
        int position = bookings.indexOf(timeSlot);
        HomeFragment.HOME_USE_CASES.callOffBooking(bookings.get(position));
        bookings.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static final class HomeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title;
        TextView seat;
        TextView time;
        TextView state;
        Button callOffBooking;
        public HomeViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.linearLayout = linearLayout;
            title = (TextView) linearLayout.findViewById(R.id.item_booking_title);
            seat = (TextView) linearLayout.findViewById(R.id.item_booking_seat);
            time = (TextView) linearLayout.findViewById(R.id.item_booking_time);
            state = (TextView) linearLayout.findViewById(R.id.item_booking_state);
            callOffBooking = (Button) linearLayout.findViewById(R.id.call_off_booking_button);
        }
    }
}
