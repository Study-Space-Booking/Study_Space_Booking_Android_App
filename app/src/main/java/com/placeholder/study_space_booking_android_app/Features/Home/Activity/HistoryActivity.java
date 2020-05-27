package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private TextView historyInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");


        historyInformation = (TextView) findViewById(R.id.history);


        Result<List<TimeSlot>> history = HomeFragment.HOME_USE_CASES.getHistory((NormalUser) SignInUseCases.user);
        if(history instanceof Result.Handle) {
            historyInformation.setText("No history");
        } else {
            listView = (ListView) findViewById(R.id.show_history_list_view);
            historyAdapter = new HistoryAdapter(
                    this,
                    R.layout.item_history,
                    ((Result.Accepted<List<TimeSlot>>) history).getModel()
            );
            listView.setAdapter(historyAdapter);
        }

        /*
        List<TimeSlot> bookings = new ArrayList<>();
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 7, 8, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 9, 10, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 11, 12, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 13, 14, 7, 8, 9, 10, 11)
        );
        bookings.add(new TimeSlot(
                1, 2, 3, 4, 15, 16, 7, 8, 9, 10, 11)
        );


        listView = (ListView) findViewById(R.id.show_history_list_view);
        historyAdapter = new HistoryAdapter(
                this,
                R.layout.item_history,
                bookings
        );
        listView.setAdapter(historyAdapter);
        */
    }
}
