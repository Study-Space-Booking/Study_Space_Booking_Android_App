package com.placeholder.study_space_booking_android_app.Features.Home.Data.Sources;

import android.database.Cursor;
import android.util.Log;

import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.db.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.db.DBPlaceManager;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;
import com.placeholder.study_space_booking_android_app.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeLocalSource implements HomeSource {
    private static volatile HomeLocalSource instance;
    private final DBLogHistoryManager dbLogHistoryManager;
    private final DBTimeSlotManager dbTimeSlotManager;
    private final DBPlaceManager dbPlaceManager;
    private final DBUserInformationManager dbUserInformationManager;


    private HomeLocalSource(DBLogHistoryManager dbLogHistoryManager,
                            DBTimeSlotManager dbTimeSlotManager, DBPlaceManager dbPlaceManager,
                            DBUserInformationManager dbUserInformationManager) {
        this.dbLogHistoryManager = dbLogHistoryManager;
        this.dbTimeSlotManager = dbTimeSlotManager;
        this.dbPlaceManager = dbPlaceManager;
        this.dbUserInformationManager = dbUserInformationManager;
    }

    public static HomeLocalSource getInstance() {
        if (instance == null) {
            instance = new HomeLocalSource(
                    DBLogHistoryManager.getInstance(),
                    DBTimeSlotManager.getInstance(),
                    DBPlaceManager.getInstance(),
                    DBUserInformationManager.getInstance()
            );
        }
        return instance;
    }

    @Override
    public Result<List<User>> getAllUsers() {
        Cursor cursor = dbUserInformationManager.getAllUser();
        List<User> list = new ArrayList<>();
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user"));
            } else {

                while (cursor.moveToNext()) {
                    list.add(
                            new NormalUser(
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED))
                            )
                    );
                }
                return new Result.Accepted<>(list);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<List<TimeSlot>> getHistory(NormalUser user) {
        Cursor cursor = dbLogHistoryManager.getUserHistory(user);
        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user history"));
            } else {
                List<TimeSlot> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    list.add(
                            new TimeSlot(
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_PLACE_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_SEAT_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_USER_ID)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOK_START_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOK_END_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_ARRIVAL_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_SIGNOUTIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_LEAVE_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_TEMPORARY_BACK_TIME)),
                                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_COLUMN_BOOKING_STATE))
                            )
                    );
                }
                return new Result.Accepted<>(list);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<List<TimeSlot>> getAllBookings(NormalUser user) {
        Log.d("in home local source", " create a new cursor");
        Cursor cursor = dbTimeSlotManager.getUserTimeSlot(user.getId());
        boolean check = cursor == null;
        Log.d("in home local source", " check if cursor is null: " + check);

        try {
            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No booking"));
            } else {
                List<TimeSlot> list = new ArrayList<>();
                while (cursor.moveToNext()) {
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
                }
                return new Result.Accepted<>(list);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<TimeSlot> callOffBooking(TimeSlot timeSlot) {
        try {
            int result = dbTimeSlotManager.deleteTimeSlot(timeSlot.getId());
            if (result == 0) {
                return new Result.Handle(new IllegalArgumentException("The booking is not found"));
            } else {
                return new Result.Accepted<>(timeSlot);
            }
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<String> getPlaceName(Integer placeId) {
        try {
            String result = dbPlaceManager.getPlaceName(placeId);
            return new Result.Accepted<String>(result);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }


    public Result<List<TimeSlot>> getUserTimeSlot(String username) {
        NormalUser user = new NormalUser();
        NormalUser resultuser;
        Result<List<TimeSlot>> resultlist;
        try {
            Cursor cursor = dbUserInformationManager.getUserInformation(username, null, null);

            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user"));
            } else {

                while (cursor.moveToNext()) {
                    user = new NormalUser(
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ID)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED))
                    );
                }
                resultuser = (user);
            }

            resultlist = getHistory((NormalUser) resultuser);

            return resultlist;

        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Handle(e);
        }
    }

    public Result<NormalUser> getUserInfo(String username) {
        NormalUser user = new NormalUser();
        try {
            Cursor cursor = dbUserInformationManager.getUserInformation(username, null, null);

            if (cursor.getCount() == 0) {
                return new Result.Handle(new IllegalArgumentException("No user"));
            } else {

                while (cursor.moveToNext()) {
                    user = new NormalUser(
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ID)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_CREDIT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_PASSWORD)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_COLUMN_ISBLOCKED))
                    );
                }
                return new Result.Accepted<>(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Handle(e);
        }

    }

    public void updateUser(NormalUser user) {
        dbUserInformationManager.updateUserInformation(user);
    }
}
