package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.UseCases.SeatUseCases;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HistoryAdapter;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HomeFragment;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class SeatHistoryActivity extends AppCompatActivity implements SeatListener {
    private Toolbar toolbar;
    private ListView listView;
    private SeatHistoryAdapter historyAdapter;
    private TextView historyInformation;
    private SearchView searchView;
    private ImageView v;

    private static final String TAG = "SeatHistoryActivity";
    SeatUseCases seatUseCases = SeatUseCases.getInstance();
    private List<TimeSlot> bookings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // set the visibility of search view

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seathistory);

        toolbar = findViewById(R.id.toolbar_seathistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");

        Result<List<TimeSlot>> history;

        historyInformation = (TextView) findViewById(R.id.seathistory);


        String name = getIntent().getStringExtra("EXTRA_SEATID");

            // set edit text and credit
        history = seatUseCases.getSeatTimeSlot(Integer.valueOf(name), this);

        if (history instanceof Result.Handle) {
            historyInformation.setText("No history");
        } else {

        }

        listView = (ListView) findViewById(R.id.show_seathistory_list_view);


    }

    @Override
    public void onGetSeatSuccess(List<Seat> list) {

    }

    @Override
    public void onGetSeatFail(DatabaseError databaseError) {

    }

    @Override
    public void onSeatTimeSlotNotFound() {
        Toast.makeText(this, "No Seat History Found", Toast.LENGTH_SHORT);
    }

    @Override
    public void onSeatTimeSlotSuccess(List<TimeSlot> timeSlots) {
        //bookings.clear();
        Log.d("this", String.valueOf(timeSlots.size()));
        bookings = timeSlots;
        historyAdapter = new SeatHistoryAdapter(
                this,
                R.layout.item_seathistory,
                bookings
        );
        listView.setAdapter(historyAdapter);
    }

    @Override
    public void onSeatTimeSlotFail(DatabaseError databaseError) {
        Toast.makeText(this, databaseError.toString(), Toast.LENGTH_SHORT);
    }

}