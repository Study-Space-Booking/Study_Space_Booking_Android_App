package com.placeholder.study_space_booking_android_app.Features.ScanOption.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.placeholder.study_space_booking_android_app.Features.Capture.SignInCaptureActivity;
import com.placeholder.study_space_booking_android_app.Features.Capture.SignOutCaptureActivity;
import com.placeholder.study_space_booking_android_app.Features.Capture.TempInCaptureActivity;
import com.placeholder.study_space_booking_android_app.Features.Capture.TempOutCaptureActivity;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases.ScanOptionUseCases;
import com.placeholder.study_space_booking_android_app.db.Injection;
import com.placeholder.study_space_booking_android_app.R;

public class ScanOptionActivity extends AppCompatActivity {
    public final String TAG = "ScanOption Activity";
    Button signin;
    Button signout;
    Button tmpleave;
    Button tmpback;
    Toolbar toolbar;
    ScanOptionUseCases scanOptionUseCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanoption);

        Injection injection = new Injection();
        injection.inject(this);

        scanOptionUseCases = ScanOptionUseCases.getInstance();

        signin = (Button) findViewById(R.id.btn_scan_signin);
        signout = (Button) findViewById(R.id.btn_scan_signout);
        tmpleave = (Button) findViewById(R.id.btn_scan_tmpleave);
        tmpback = (Button) findViewById(R.id.btn_scan_tempback);

        signin.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ScanOptionActivity.this, SignInCaptureActivity.class));
                    }
                }

        );

        signout.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ScanOptionActivity.this, SignOutCaptureActivity.class));
                    }
                }
        );

        tmpleave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ScanOptionActivity.this, TempOutCaptureActivity.class));
                    }
                }
        );

        tmpback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ScanOptionActivity.this, TempInCaptureActivity.class));
                    }
                }
        );


    }


}
