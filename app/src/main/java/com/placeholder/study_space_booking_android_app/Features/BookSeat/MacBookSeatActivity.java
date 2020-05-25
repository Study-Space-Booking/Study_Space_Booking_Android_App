package com.placeholder.study_space_booking_android_app.Features.BookSeat;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.placeholder.study_space_booking_android_app.R;

import java.util.Calendar;

public class MacBookSeatActivity extends AppCompatActivity implements
        View.OnClickListener {


    private static String TAG = "BookSeatActivity";
    Button btnDatePicker, btnTimePicker, btnDatePickerTo, btnTimePickerTo;
    EditText txtDate, txtTime, txtDateTo, txtTimeTo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mYearTo, mMonthTo, mDayTo, mHourTo, mMinuteTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookseat);

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
    }
}

