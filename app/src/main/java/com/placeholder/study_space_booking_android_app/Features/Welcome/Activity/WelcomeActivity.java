package com.placeholder.study_space_booking_android_app.Features.Welcome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.placeholder.study_space_booking_android_app.Features.Report.Activity.ReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HomeFragment;
import com.placeholder.study_space_booking_android_app.Features.Place.Activity.PlaceFragment;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Activity.ScanOptionActivity;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

public class WelcomeActivity extends AppCompatActivity {
    public FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private Toolbar toolbar;
    private static final String TAG = "WelcomeActivity";

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment selectedFragment = null;
                    switch(item.getItemId()) {
                        case R.id.navigation_main_page:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_booking:
                            selectedFragment = new PlaceFragment();
                            break;
                        case R.id.navigation_report:
                            selectedFragment = new ReportFragment();
                            break;
                    }
                    fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar = findViewById(R.id.toolbar_welcome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome! " + SignInUseCases.user.getUserName());

        fragmentManager = getSupportFragmentManager();
        //fragmentTransaction = fragmentManager.beginTransaction();


        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState == null) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
                bottomNavigationView.setOnNavigationItemReselectedListener(navigationItemReselectedListener);
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_toolbar_camera:
                startActivity(new Intent(this, ScanOptionActivity.class));
                break;
            case R.id.item_toolbar_home:
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.item_toolbar_booking:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}