package com.placeholder.study_space_booking_android_app.Features.Welcome.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.placeholder.study_space_booking_android_app.Features.Home.Activity.HomeFragment;
import com.placeholder.study_space_booking_android_app.Features.Place.Activity.PlaceFragment;
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
        getSupportActionBar().setTitle("Welcome");

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
}