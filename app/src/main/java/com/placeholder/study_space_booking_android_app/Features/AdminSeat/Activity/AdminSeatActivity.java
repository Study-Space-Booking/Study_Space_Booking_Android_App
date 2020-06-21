package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.Seat;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.Model.SeatListener;
import com.placeholder.study_space_booking_android_app.Features.AdminSeat.logic.UseCases.SeatUseCases;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.AdminHistoryActivity;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HistoryActivity;
import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Activity.SignInActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportActivity;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;




        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.Toast;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
        import com.placeholder.study_space_booking_android_app.Core.Beans.User;
        import com.placeholder.study_space_booking_android_app.Features.Home.logic.UseCases.HomeUseCases;
        import com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportActivity;
        import com.placeholder.study_space_booking_android_app.R;

        import java.util.ArrayList;
        import java.util.List;

public class AdminSeatActivity extends AppCompatActivity implements SeatListener {
    public static final SeatUseCases seatUseCases = SeatUseCases.getInstance();

    //get all user names

    private String[] mStrs;
    private SearchView mSearchView;
    private ListView mListView;
    private Toolbar toolbar;
    List<Seat> realist = new ArrayList<>();
    List<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView);
        getSeatID();
//        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
        mListView.setBackgroundColor(Color. rgb(255,250,250));
        mListView.setTextFilterEnabled(true);
        mSearchView.setQueryHint("Please enter seat ID");

        toolbar = findViewById(R.id.toolbar_searching);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seat History");



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigationadmin);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_pr_page:
                        Intent intent = new Intent(AdminSeatActivity.this, ViewReportActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_user:
                        Intent a = new Intent(AdminSeatActivity.this, AdminHistoryActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_seat:
                        Intent b = new Intent(AdminSeatActivity.this, AdminSeatActivity.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mListView.setFilterText(newText);
                } else {
                    mListView.clearTextFilter();
                }
                return false;
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String seatID = mStrs[position];
                Intent intent = new Intent(getBaseContext(), SeatHistoryActivity.class);
                intent.putExtra("EXTRA_SEATID", seatID);
                startActivity(intent);
            }
        });

    }

    public void getSeatID() {
        Result<List<Seat>> list = seatUseCases.getAllSeats(this);



        if (list instanceof Result.Handle) {
            //Log.d("signin", "s == null");
            Exception exception = ((Result.Handle) list).getException();
            Toast.makeText(AdminSeatActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {

        }

    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem logout = menu.findItem(R.id.item_log_out_admin);
        if(SignInUseCases.user instanceof NormalUser)
        {
            logout.setVisible(false);
        }
        else
        {
            logout.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_log_out_admin:
                SignInActivity.setEditUserName("");
                SignInActivity.setEditPassword("");
                Intent logout = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(logout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onGetSeatSuccess(List<Seat> list) {
        realist.clear();
        arr.clear();
        mStrs = null;
        Toast.makeText(AdminSeatActivity.this, "get seat ids", Toast.LENGTH_SHORT).show();
        realist = list;
        for (Seat u : realist) {
            arr.add(u.getId().toString());
        }
        String[] result = new String[realist.size()];
        mStrs = arr.toArray(result);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));

    }

    @Override
    public void onGetSeatFail(DatabaseError databaseError) {
        Toast.makeText(this, databaseError.toString(),Toast.LENGTH_SHORT);
    }

    @Override
    public void onSeatTimeSlotNotFound() {

    }

    @Override
    public void onSeatTimeSlotSuccess(List<TimeSlot> timeSlots) {

    }

    @Override
    public void onSeatTimeSlotFail(DatabaseError databaseError) {

    }
}
