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

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.UseCases.SeatUseCases;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HistoryAdapter;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HomeFragment;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class SeatHistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private SeatHistoryAdapter historyAdapter;
    private TextView historyInformation;
    private SearchView searchView;
    private ImageView v;
//    private EditText credit;
//    private Button change;
    private static final String TAG = "SeatHistoryActivity";
    SeatUseCases seatUseCases = SeatUseCases.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // set the visibility of search view

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seathistory);

        toolbar = findViewById(R.id.toolbar_seathistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        List<TimeSlot> bookings = new ArrayList<>();

//        if (SignInUseCases.user instanceof NormalUser) {
//            searchView.setVisibility(View.GONE);
//            v.setVisibility(View.GONE);
//        }
        Result<List<TimeSlot>> history;
        //DBLogHistoryManager dbLogHistoryManager = DBLogHistoryManager.getInstance();
        //DBTimeSlotManager dbTimeSlotManager = DBTimeSlotManager.getInstance();
        //DBPlaceManager dbPlaceManager = DBPlaceManager.getInstance();
        //dbLogHistoryManager.initialize(this);
        //dbTimeSlotManager.initialize(this);
        //dbPlaceManager.initialize(this);
        historyInformation = (TextView) findViewById(R.id.seathistory);
//        credit = (EditText) findViewById(R.id.creditEditText);
//        change = (Button) findViewById(R.id.changeCreditButtion);
        //seatUseCases.getInstance();

            String name = getIntent().getStringExtra("EXTRA_SEATID");

            // set edit text and credit
            history = seatUseCases.getSeatTimeSlot(Integer.valueOf(name));
            if (history instanceof Result.Handle) {
                historyInformation.setText("No history");
            } else {
                bookings = (((Result.Accepted<List<TimeSlot>>) history).getModel());
            }


        listView = (ListView) findViewById(R.id.show_seathistory_list_view);
        historyAdapter = new SeatHistoryAdapter(
                this,
                R.layout.item_seathistory,
                bookings
        );
        listView.setAdapter(historyAdapter);
    }


//    public NormalUser getUser(String username) {
//        Result<NormalUser> user = HomeFragment.HOME_USE_CASES.getUserInfo(username);
//        NormalUser realUser = new NormalUser();
//        if (user instanceof Result.Handle) {
//            Exception exception = ((Result.Handle) user).getException();
//            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
//        } else {
//            Log.d("cool", "found the bug");
//            realUser = ((Result.Accepted<NormalUser>) user).getModel();
//        }
//        return realUser;
//
//    }

//    public void creditChange(String username) {
//        user = getUser(username);
//        credit.setText(user.getCredit().toString());
//    }
}