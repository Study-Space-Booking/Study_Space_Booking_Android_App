package com.placeholder.study_space_booking_android_app.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TSService extends Service {
    private static final String TAG = "MyService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "TimeSlot Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startid)
    {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Intent intents = new Intent(this,RefreshTS.class);
//        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intents);
        Log.d(TAG, "onStart");
        return START_STICKY;
    }
}