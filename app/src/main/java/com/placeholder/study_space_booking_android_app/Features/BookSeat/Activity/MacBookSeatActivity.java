package com.placeholder.study_space_booking_android_app.Features.BookSeat.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.DeniedByServerException;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.State;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBSeatManager;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.Logic.Usecases.BookSeatUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Activity.SignInActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.Inflater;

public class MacBookSeatActivity extends AppCompatActivity implements
        View.OnClickListener {


    private static String TAG = "BookSeatActivity";
    Button btnDatePicker, btnTimePicker, btnDatePickerTo, btnTimePickerTo;
    EditText txtDate, txtTime, txtDateTo, txtTimeTo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mYearTo, mMonthTo, mDayTo, mHourTo, mMinuteTo;

    public DBTimeSlotManager dbTimeSlotManager = DBTimeSlotManager.getInstance();
    public DBSeatManager dbSeatManager = DBSeatManager.getInstance();

    private int mYearc, mMonthc, mDayc, mHourc, mMinutec;
    private int mYearToc, mMonthToc, mDayToc, mHourToc, mMinuteToc;
    private Integer startTime, endTime;

    private int placeId = 1;


    private Map<Button, Integer> seatMap = new HashMap<>();
    private Map<Integer, Button> buttonMap = new HashMap<>();
    private BookSeatUseCases bookSeatUseCases;
    private Button[] seatButtons = new Button[4];
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

        dbTimeSlotManager.initialize(this);
        dbSeatManager.initialize(this);
        Log.d(TAG, "on Create method called"); // log

        bookSeatUseCases = BookSeatUseCases.getInstance();

        seatButtons = new Button[] {
                findViewById(R.id.floating_action_button1),
                findViewById(R.id.floating_action_button2),
                findViewById(R.id.floating_action_button3),
                findViewById(R.id.floating_action_button4)
        };

        for(int i = 0; i < seatButtons.length; i = i + 1) {
            seatButtons[i].setOnClickListener(this);
            seatButtons[i].setBackgroundColor(Color.BLUE);
        }

        buttonConfirmTime = (Button) findViewById(R.id.button_confirm_time);
        buttonConfirmTime.setOnClickListener(this);

        this.getSeats();
    }

    @Override
    public void onClick(View v) {

        // From
        if (v == btnDatePicker) {

            // Get Current Date
            Calendar a = Calendar.getInstance();
            mYear = a.get(Calendar.YEAR);
            mMonth = a.get(Calendar.MONTH);
            mDay = a.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            mYearc = year;
                            mMonthc = monthOfYear;
                            mDayc = dayOfMonth;
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == btnTimePicker) {

            // Get Current Time
            Calendar a = Calendar.getInstance();
            mHour = a.get(Calendar.HOUR_OF_DAY);
            mMinute = a.get(Calendar.MINUTE);


            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHourc = hourOfDay;
                            mMinutec = minute;
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        // To

        else if (v == btnDatePickerTo) {

            // Get Current Date
            Calendar c = Calendar.getInstance();
            mYearTo = c.get(Calendar.YEAR);
            mMonthTo = c.get(Calendar.MONTH);
            mDayTo = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialogTo = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            mYearToc = year;
                            mMonthToc = monthOfYear;
                            mDayToc = dayOfMonth;
                            txtDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYearTo, mMonthTo, mDayTo);
            datePickerDialogTo.show();
        }
        else if (v == btnTimePickerTo) {

            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHourTo = c.get(Calendar.HOUR_OF_DAY);
            mMinuteTo = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialogTo = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mMinuteToc = minute;
                            mHourToc = hourOfDay;
                            txtTimeTo.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourTo, mMinuteTo, true);
            timePickerDialogTo.show();
        }

        else if(v == buttonConfirmTime) {
            final LoadDialogue loadDialogue = new LoadDialogue(this, R.layout.dialogue_loading);
            loadDialogue.startDialogue();

            Log.d("Time", String.valueOf(mHourc));
            Log.d("Time2", String.valueOf(mHourToc));
            // calculating startTime
//            String myDate = mYearc+"/"+mMonthc+"/"+mDayc+" "+mHourc+":"+mMinutec+":00";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Date date = null;
//
//            try {
//                date = sdf.parse(myDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            long millis = date.getTime();
//            Integer startTime = (int) (millis/1000);
//            //
//            Log.d("StartTime", String.valueOf(startTime));
            Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));
            calendar.set(Calendar.YEAR, mYearc);
            calendar.set(Calendar.MONTH, mMonthc);
            calendar.set(Calendar.DAY_OF_MONTH, mDayc);
            calendar.set(Calendar.HOUR_OF_DAY, mHourc);
            calendar.set(Calendar.MINUTE, mMinutec);
            Date date = calendar.getTime();
            startTime = (int) (date.getTime()/ 1000);
            Log.d("StartTime", String.valueOf(startTime));

            // calculating endTime
            Calendar calendarTo = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));

            calendarTo.set(Calendar.YEAR, mYearToc);
            calendarTo.set(Calendar.MONTH, mMonthToc);
            calendarTo.set(Calendar.DAY_OF_MONTH, mDayToc);
            calendarTo.set(Calendar.HOUR_OF_DAY, mHourToc);
            calendarTo.set(Calendar.MINUTE, mMinuteToc);
            Date dateTo = calendarTo.getTime();
            endTime = (int) (dateTo.getTime()/ 1000);
            Log.d("EndTime", String.valueOf(endTime));

            //
            //Log.d("debug", "debgug can see?");
            Result<List<Integer>> result = bookSeatUseCases.getOccupiedSeat(startTime, endTime, placeId);

            // Log.d("debug", "debgug can see?");
            if(result instanceof Result.Accepted) {
                List<Integer> seatsOccupied = ((Result.Accepted<List<Integer>>) result).getModel();
                //Log.d("debug", "debgug can see?");
                for(int i = 0; i < seatsOccupied.size(); i = i + 1) {
                    Button button = buttonMap.get(seatsOccupied.get(i));
                    assert button != null; // throw exception in case that null that a null pointer exception maybe thrown
                    Log.d("Coloring seats", String.valueOf(button.getId()));
                    button.setBackgroundColor(Color.RED);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDialogue.stopDialogue();
                    }
                }, 1000);
            }
            //Log.d("debug", "debgug can see?");
        }
        else if(v instanceof Button) {
            final Integer seatId = seatMap.get(v);
            if(bookSeatUseCases.isOccupied(seatId)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Seat" + seatId.toString());
                builder.setMessage("The seat is occupied");
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                LayoutInflater inflater = this.getLayoutInflater();
//                builder.setView(inflater.inflate(R.layout.dialogue_confirm_booking, null));

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MacBookSeatActivity.this.bookSeat(seatId);
                        buttonConfirmTime.performClick();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
                builder.setMessage("Please confirm your booking");

                builder.show();
            }
        }
    }

    public void getSeats() {
        //Log.d("debug", "get all seats debugging");
        Result<List<Integer>> result = bookSeatUseCases.getAllSeatId(this.placeId);
        //Log.d("debug", "get all seats debugging");
        if(result instanceof Result.Accepted) {

            List<Integer> seats = ((Result.Accepted<List<Integer>>) result).getModel();

            for(int i = 0; i < seatButtons.length; i = i + 1) {

                seatMap.put(seatButtons[i], seats.get(i));
                buttonMap.put(seats.get(i), seatButtons[i]);
            }

        }
    }

    public void bookSeat(Integer seatId) {
        TimeSlot book = new TimeSlot(0, this.placeId, seatId, SignInUseCases.user.getId(), startTime, endTime,
                0,0,0,0, State.BOOKED); // state is turned to 1;
        bookSeatUseCases.bookSeat(book);
    }

}

