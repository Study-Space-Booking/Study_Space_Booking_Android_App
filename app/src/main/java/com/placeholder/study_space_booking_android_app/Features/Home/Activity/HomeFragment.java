package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HomeListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private Context context;
    private List<TimeSlot> bookings;
    public static final HomeUseCases HOME_USE_CASES = HomeUseCases.getInstance();
    TextView currentCredit;
    TextView history;
    TextView showBookings;
    ProgressBar progressBar;
    HomeUseCases homeUseCases;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.show_booking_recycler_view);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        currentCredit = (TextView) view.findViewById(R.id.current_credit);
        history = (TextView) view.findViewById(R.id.go_to_history);
        showBookings = (TextView)view.findViewById(R.id.show_bookings);

        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar_home);

        currentCredit.setText(SignInUseCases.user.getUserName()+"'s Credit : " + ((NormalUser) SignInUseCases.user).getCredit().toString());

        homeUseCases = HomeUseCases.getInstance();

        //DBTimeSlotManager dbTimeSlotManager = DBTimeSlotManager.getInstance();
        //dbTimeSlotManager.initialize(getActivity().getApplicationContext());

        history.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HistoryActivity.class);
                        startActivity(intent);
                    }
                }
        );
        /*
        List<TimeSlot> bookings = new ArrayList<>();
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        );

        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(bookings);
        */

        bookings = new ArrayList<>();
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(bookings);
        homeRecyclerViewAdapter.setHomeListener(this);
        recyclerView.setAdapter(homeRecyclerViewAdapter);

        Result<List<TimeSlot>> result = HOME_USE_CASES.getAllBookings(bookings, (NormalUser) SignInUseCases.user, this); // get all bookings of a normal user

        if(result instanceof Result.Handle) {
            showBookings.setText(((Result.Handle) result).getException().getMessage()); // show the exception in the textbox
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(new ArrayList<TimeSlot>());
            //recyclerView.setAdapter(homeRecyclerViewAdapter);
        } else {
            //homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(((Result.Accepted<List<TimeSlot>>) result).getModel());
            //homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(bookings);
        }
        //recyclerView.setAdapter(homeRecyclerViewAdapter);
        return view;
    }

    public void refresh() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(currentFragment instanceof HomeFragment) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(this);
            fragmentTransaction.attach(this);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onGetBookingsSuccess(List<TimeSlot> bookings) {
        //Toast.makeText(context, "get booking", Toast.LENGTH_SHORT).show();
        homeRecyclerViewAdapter.notifyDataSetChanged();
        showBookings.setText("Booking List:");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetBookingsFailure(Exception exception) {
        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoBookingFound() {
        Toast.makeText(context, "No booking found", Toast.LENGTH_SHORT).show();
        showBookings.setText("No booking:");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCallOffBookingSuccess() {
        //homeRecyclerViewAdapter.notifyDataSetChanged();
        Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCallOffBookingFailure(Exception exception) {
        Toast.makeText(context, "Check", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCallOffBookingsClick(int position) {
        TimeSlot booking = bookings.get(position);
        bookings.remove(position);
        homeRecyclerViewAdapter.notifyDataSetChanged();
        Result<TimeSlot> result = homeUseCases.callOffBooking(booking, HomeFragment.this);
        if(result instanceof Result.Handle) {
            Toast.makeText(context, ((Result.Handle) result).getException().getMessage(), Toast.LENGTH_SHORT).show();
        } else {

        }
    }

}
