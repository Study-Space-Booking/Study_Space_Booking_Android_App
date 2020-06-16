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

import com.google.zxing.common.StringUtils;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private TextView historyInformation;
    private SearchView searchView;
    private ImageView v;
    private EditText credit;
    private Button change;
    private NormalUser user;
    private static final String TAG = "HistoryActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // set the visibility of search view

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar_history);
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
        historyInformation = (TextView) findViewById(R.id.history);
        credit = (EditText) findViewById(R.id.creditEditText);
        change = (Button) findViewById(R.id.changeCreditButtion);



        if (SignInUseCases.user instanceof NormalUser) {
            credit.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            history = HomeFragment.HOME_USE_CASES.getHistory((NormalUser) SignInUseCases.user);
            if (history instanceof Result.Handle) {
                historyInformation.setText("No history");
            } else {
                bookings = (((Result.Accepted<List<TimeSlot>>) history).getModel());
            }
        } else {
            String name = getIntent().getStringExtra("EXTRA_USERNAME");
            // set edit text and credit
            user = getUser(name);
            creditChange(name);
            history = HomeFragment.HOME_USE_CASES.getUserTimeSlot(name);
            if (history instanceof Result.Handle) {
                historyInformation.setText("No history");
            } else {
                bookings = (((Result.Accepted<List<TimeSlot>>) history).getModel());
            }
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
                        HomeFragment.HOME_USE_CASES.updateUser(newUser);
                        //startService(new Intent(SignInActivity.this, TSService.class));
                    }
                }
        );

        listView = (ListView) findViewById(R.id.show_history_list_view);
        historyAdapter = new HistoryAdapter(
                this,
                R.layout.item_history,
                bookings
        );
        listView.setAdapter(historyAdapter);
    }


    public NormalUser getUser(String username) {
        Result<NormalUser> user = HomeFragment.HOME_USE_CASES.getUserInfo(username);
        NormalUser realUser = new NormalUser();
        if (user instanceof Result.Handle) {
            Exception exception = ((Result.Handle) user).getException();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
        } else {
            Log.d("cool", "found the bug");
            realUser = ((Result.Accepted<NormalUser>)user).getModel();
        }
        return realUser;

    }
    public void creditChange(String username) {
        user = getUser(username);
        credit.setText(user.getCredit().toString());
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
