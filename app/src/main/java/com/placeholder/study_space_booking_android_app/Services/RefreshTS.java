package com.placeholder.study_space_booking_android_app.Services;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RefreshTS extends Activity {
    DBTimeSlotManager dbTimeSlotManager = DBTimeSlotManager.getInstance();
    private Handler mRepeatHandler;
    private final static int UPDATE_INTERVAL = 5000;


    Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        dbTimeSlotManager.initialize(this);

        mRepeatHandler = new Handler();
        mRepeatHandler.postDelayed(new Runnable() {
            public void run() {
//                  runs a method every 2000ms
//       example    runThisEvery2seconds();
                RefreshTS.this.updateTimeSlot();
            }
        }, 200000);
    }

    public void updateTimeSlot() {
        List<TimeSlot> ts = this.getAllTimeSlotToUpdate();
        int i = 0;
        for (i = 0; i < ts.size(); i++) {
            if (ts.get(i).getInTime() == 0 && ((System.currentTimeMillis()/1000) - ts.get(i).getBookStartTime()) >= 1*60) { // no sign in
                ts.get(i).setBookEndTime(ts.get(i).getBookStartTime());
                dbTimeSlotManager.updateTimeSlot(ts.get(i));
            } else { // sign in
                if (ts.get(i).getTempLeaveTime() == 0) { // no leave
                    if (ts.get(i).getOutTime() == 0 && ((System.currentTimeMillis()/1000) - ts.get(i).getBookEndTime()) >= 0 ) { // no out
                        ts.get(i).setBookEndTime(ts.get(i).getBookStartTime());
                        dbTimeSlotManager.updateTimeSlot(ts.get(i));
                    } else { // out

                    }

                } else { // leave
                    if (ts.get(i).getTempBackTime() == 0) { // no back
                        ts.get(i).setBookEndTime(ts.get(i).getBookStartTime());
                        dbTimeSlotManager.updateTimeSlot(ts.get(i));
                    } else { // back
                        if (ts.get(i).getOutTime() == 0 && ((System.currentTimeMillis()/1000) - ts.get(i).getBookEndTime()) >= 0 ) { // no out
                            ts.get(i).setBookEndTime(ts.get(i).getBookStartTime());
                            dbTimeSlotManager.updateTimeSlot(ts.get(i));
                        } else { // out
                        }

                    }
                }
            }
        }


    }
    public List<TimeSlot> getAllTimeSlotToUpdate () {
        try {
            Cursor cursor = dbTimeSlotManager.getTimeSlotToUpdate((int) (System.currentTimeMillis()/1000), 15);
            List<TimeSlot> list = new ArrayList<>();
            int i = 1;
            while(cursor.moveToNext()) {
                list.add(
                        new TimeSlot(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_PLACE_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_SEAT_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_USER_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_BOOKSTART_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_BOOKEND_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_IN_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_OUT_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_TEMPLEAVE_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_TEMPBACK_TIME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TABLE_TIMESLOT_STATE))
                        )
                );
                Log.d("debug", "show newly created method to update time slot!!" + String.valueOf(i));
                i++;
            }
            return list;
        } catch (Exception exception) {
            Log.d("exception", "exception encountered in RefreshTS");
            return null;
        }
    }
}
