package com.placeholder.study_space_booking_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.placeholder.study_space_booking_android_app.R;
import com.placeholder.study_space_booking_android_app.Features.SignIn.Activity.SignInActivity;

public class MainActivity extends AppCompatActivity {

    // private appApi mApi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button_go_to_sign_in);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }


}
