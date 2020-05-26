package com.placeholder.study_space_booking_android_app.Features.Place.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.placeholder.study_space_booking_android_app.Features.BookSeat.MacBookSeatActivity;
import com.placeholder.study_space_booking_android_app.Features.BookSeat.PCBookSeatActivity;
import com.placeholder.study_space_booking_android_app.R;


public class PlaceFragment extends Fragment implements OnClickListener {
    private static final String TAG = "PlaceFragment";
    private ImageButton macCommons;
    private ImageButton pcCommons;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_place, container, false);
        macCommons = (ImageButton) view.findViewById(R.id.imageButton_Mac);
        pcCommons = (ImageButton) view.findViewById(R.id.imageButton_PC);

        macCommons.setOnClickListener(this);
        pcCommons.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_Mac:
                startActivity(new Intent(getActivity(), MacBookSeatActivity.class));
                break;
            case R.id.imageButton_PC:
                startActivity(new Intent(getActivity(), PCBookSeatActivity.class));
                break;
        }

    }
}
