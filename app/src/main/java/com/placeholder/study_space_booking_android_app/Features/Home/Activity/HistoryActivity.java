package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

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
import com.google.zxing.common.StringUtils;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.Model.HistoryListener;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryListener {
    private Toolbar toolbar;
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private TextView historyInformation;
    private SearchView searchView;
    private ImageView v;
    private EditText credit;
    private Button change;
    private NormalUser user;
    private TextView username;
    private static final String TAG = "HistoryActivity";
    private List<TimeSlot> bookings = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // set the visibility of search view

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");


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
        historyInformation = (TextView) findViewById(R.id.history);
        credit = (EditText) findViewById(R.id.creditEditText);
        change = (Button) findViewById(R.id.changeCreditButtion);
        username = (TextView) findViewById(R.id.usercredit_text);


        if (SignInUseCases.user instanceof NormalUser) {
            credit.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            history = HomeFragment.HOME_USE_CASES.getHistory((NormalUser) SignInUseCases.user, this);

            if (history instanceof Result.Handle) {
                historyInformation.setText("No history");
            } else {

            }
        } else {
            String name = getIntent().getStringExtra("EXTRA_USERNAME");
            // set edit text and credit
            if (Character.isLetter(name.charAt(0))) {
                getUser(name);
            } else {
                getUserID(Integer.valueOf(name));
            }

//            history = HomeFragment.HOME_USE_CASES.getHistory(user, this);
//            if (history instanceof Result.Handle) {
//                historyInformation.setText("No history");
//            } else {
//
//            }
        }

        change.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("cool", "great!!!!!!!!!!!!!!!!!!!!");
                        String stringCredit =credit.getText().toString();
//                        stringCredit.replace("credit: ", "");
                        Log.d("cool", "String credit = " + stringCredit);
                        int newnewCredit = Integer.valueOf(stringCredit);
//
                        NormalUser newUser = user;
                        newUser.setCredit(newnewCredit);
                        HomeFragment.HOME_USE_CASES.updateUser(newUser, HistoryActivity.this);
                        //startService(new Intent(SignInActivity.this, TSService.class));
                    }
                }
        );

        listView = (ListView) findViewById(R.id.show_history_list_view);

    }


    public void getUser(String username) {
        Result<NormalUser> user = HomeFragment.HOME_USE_CASES.getUserInfo(username, HistoryActivity.this);

        if (user instanceof Result.Handle) {
            Exception exception = ((Result.Handle) user).getException();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
        } else {
            Log.d("cool", "found the bug");

        }

    }

    public void getUserID(Integer id) {
        Result<NormalUser> user = HomeFragment.HOME_USE_CASES.getUserInfoID(id, HistoryActivity.this);

        if (user instanceof Result.Handle) {
            Exception exception = ((Result.Handle) user).getException();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
        } else {
            Log.d("cool", "found the bug");

        }

    }
    public void creditChange() {
        credit.setText(user.getCredit().toString());
    }

    @Override
    public void onNoHistoryFound() {
        Toast.makeText(this,"No User History Found", Toast.LENGTH_SHORT);
    }

    @Override
    public void onGetHistorySuccess(List<TimeSlot> list) {
        bookings = list;
        historyAdapter = new HistoryAdapter(
                this,
                R.layout.item_history,
                bookings
        );
        listView.setAdapter(historyAdapter);
    }

    @Override
    public void onGetHistoryFail(DatabaseError databaseError) {
        Toast.makeText(this,databaseError.toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onGetUserInfoSuccess(NormalUser user) {
        this.user = user;
        creditChange();
        Result<List<TimeSlot>> history = HomeFragment.HOME_USE_CASES.getHistory(user, this);
        if (history instanceof Result.Handle) {
            historyInformation.setText("No history");
        } else {

        }
    }

    @Override
    public void onGetUserInfoFail(DatabaseError databaseError) {
        Toast.makeText(this,databaseError.toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onFoundNoUserInfo() {
        Toast.makeText(this,"No User Information Found", Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpdateUserSuccess() {
        Toast.makeText(this, "User Credit Updated", Toast.LENGTH_LONG);
    }

    @Override
    public void onUpdateUserFail(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
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


//        searchView = (SearchView) findViewById(R.id.search);
//        v = (ImageView) searchView.findViewById(R.id.search_button);
//        v.setImageResource(R.drawable.ic_search_24px); //Changing the image
//
//        searchView.setQueryHint(getResources().getString(R.string.search_hint));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do your search
//
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!TextUtils.isEmpty(newText)){
//                    listView.setFilterText(newText);
//                }else{
//                    listView.clearTextFilter();
//                }
//                return false;
//
//            }
//        });
//    }



}

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
////        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
////        searchView = (SearchView) searchViewMenuItem.getActionView();
////        v = (ImageView) searchView.findViewById(R.id.search_button);
////        v.setImageResource(R.drawable.ic_search_24px); //Changing the image
////
////        searchView.setQueryHint(getResources().getString(R.string.search_hint));
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                //Do your search
////
////                return false;
////
////            }
////
////            @Override
////            public boolean onQueryTextChange(String newText) {
////                if (!TextUtils.isEmpty(newText)){
////                    listView.setFilterText(newText);
////                }else{
////                    listView.clearTextFilter();
////                }
////                return false;
////
////            }
////        });
//        return true;
//    }
//}
