package com.placeholder.study_space_booking_android_app.Features.BookSeat.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Usecases.BookSeatUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MacBookSeatActivity extends AppCompatActivity implements
        View.OnClickListener {


    private static String TAG = "BookSeatActivity";
    Button btnDatePicker, btnTimePicker, btnDatePickerTo, btnTimePickerTo;
    EditText txtDate, txtTime, txtDateTo, txtTimeTo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mYearTo, mMonthTo, mDayTo, mHourTo, mMinuteTo;

    private Map<FloatingActionButton, Integer> seatMap;
    private Map<Integer, FloatingActionButton> buttonMap;
    private BookSeatUseCases bookSeatUseCases;
    private FloatingActionButton[] seatButtons;
    private Button buttonConfirmTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macbookseat);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        btnDatePickerTo = (Button) findViewById(R.id.btn_dateto);
        btnTimePickerTo = (Button) findViewById(R.id.btn_timeto);
        txtDateTo = (EditText) findViewById(R.id.in_dateto);
        txtTimeTo = (EditText) findViewById(R.id.in_timeto);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnDatePickerTo.setOnClickListener(this);
        btnTimePickerTo.setOnClickListener(this);

        Log.d(TAG, "on Create method called"); // log

        bookSeatUseCases = BookSeatUseCases.getInstance();

        seatButtons = new FloatingActionButton[]{
                findViewById(R.id.floating_action_button1),
                findViewById(R.id.floating_action_button2),
                findViewById(R.id.floating_action_button3),
                findViewById(R.id.floating_action_button4)
        };

        for(int i = 0; i < seatButtons.length; i = i + 1) {
            seatButtons[i].setOnClickListener(this);
        }

        buttonConfirmTime = (Button) findViewById(R.id.button_confirm_time);
        buttonConfirmTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // From
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        // To

        if (v == btnDatePickerTo) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYearTo = c.get(Calendar.YEAR);
            mMonthTo = c.get(Calendar.MONTH);
            mDayTo = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYearTo, mMonthTo, mDayTo);
            datePickerDialog.show();
        }
        if (v == btnTimePickerTo) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHourTo = c.get(Calendar.HOUR_OF_DAY);
            mMinuteTo = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeTo.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourTo, mMinuteTo, false);
            timePickerDialog.show();
        }

        if(v == buttonConfirmTime) {
            final LoadDialogue loadDialogue = new LoadDialogue(this, R.layout.dialogue_loading);
            loadDialogue.startDialogue();

            Result<List<Integer>> result = bookSeatUseCases.getOccupiedSeat(startTime, endTime, placeId);

            if(result instanceof Result.Accepted) {
                List<Integer> seatsOccupied = ((Result.Accepted<List<Integer>>) result).getModel();
                for(int i = 0; i < seatsOccupied.size(); i = i + 1) {
                    FloatingActionButton floatingActionButton = buttonMap.get(seatsOccupied.get(i));
                    floatingActionButton.setBackgroundColor(Color.RED);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDialogue.stopDialogue();
                    }
                }, 1000);
            }
        }
        if(v instanceof FloatingActionButton) {
            Integer seatId = seatMap.get(v);
            if(bookSeatUseCases.isOccupied(seatId)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Seat" + seatId.toString());
                builder.setMessage("The seat is occupied");
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.dialogue_confirm_booking, null));

                builder.show();
            }
        }
    }

    public void getSeats() {
        Result<List<Integer>> result = bookSeatUseCases.getAllSeatId(bookSeatUseCases.getPlaceId());
        if(result instanceof Result.Accepted) {
            List<Integer> seats = ((Result.Accepted<List<Integer>>) result).getModel();
            seatMap = new HashMap<FloatingActionButton, Integer>();
            buttonMap = new HashMap<Integer, FloatingActionButton>();
            for(int i = 0; i < seatButtons.length; i = i + 1) {
                seatMap.put(seatButtons[i], seats.get(i));
                buttonMap.put(seats.get(i), seatButtons[i]);
            }

        }
    }


}

