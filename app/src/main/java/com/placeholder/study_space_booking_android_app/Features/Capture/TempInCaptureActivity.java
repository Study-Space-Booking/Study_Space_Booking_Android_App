package com.placeholder.study_space_booking_android_app.Features.Capture;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.State;
import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Data.Sources.ScanOptionRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases.ScanListener;
import com.placeholder.study_space_booking_android_app.R;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Activity.ScanOptionActivity;
import com.placeholder.study_space_booking_android_app.Features.ScanOption.Logic.UseCases.ScanOptionUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.Features.Welcome.Activity.WelcomeActivity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class TempInCaptureActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, ScanListener {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    // public DBTimeSlotManager dbTimeSlotManager;
    public ScanOptionRemoteSource scanOptionRemoteSource = ScanOptionRemoteSource.getInstance();
    public ScanOptionUseCases scanOptionUseCases;
    private List<TimeSlot> list;
    private String result;
    private TimeSlot realTimeSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dbTimeSlotManager = DBTimeSlotManager.getInstance();
        scanOptionUseCases = ScanOptionUseCases.getInstance();
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(TempInCaptureActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        //scanSignIn(myResult);
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(TempInCaptureActivity.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scanTmpBack(myResult);
            }
        });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public void scanTmpBack(String result) {
//        Log.d("debug", "scan sign in ");
//        Log.d("debug", SignInUseCases.user.getId().toString());

        this.result = result;
        scanOptionRemoteSource.getAllBookings((NormalUser) SignInUseCases.user, TempInCaptureActivity.this);

//        if (t == null) {
//            Toast.makeText(this, "No booking is found for the user", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, ScanOptionActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
//        } else { // check if the place and seat id is correct to match the user
//            if (t.getPlaceId() == (result.charAt(0) - '0')) {
//                Toast.makeText(this, "place is correct", Toast.LENGTH_SHORT).show();
//
//                if (t.getSeatId() == (result.charAt(1) - '0')) {
//                    Toast.makeText(this, "seat is correct", Toast.LENGTH_SHORT).show();
//                    Log.d("debug","seat is correct");
//                    t.setTempBackTime((int) (System.currentTimeMillis() / 1000)); // update the sign in time;
//                    t.setState(State.SIGNIN); // update the state
//                    dbTimeSlotManager.updateTimeSlot(t);
//                    Toast.makeText(this,"Temporary Back is successfully!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(this, WelcomeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
//
//                } else { // if seat is incorrect, toast
//                    Toast.makeText(this, "No booking for this seat is found for the user", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(this, ScanOptionActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
//                }
//
//            } else { // if place is incorrect, toast
//                Toast.makeText(this, "No booking in this place is found for the user", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, ScanOptionActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(intent);
//            }
//        }
    }


    @SuppressLint("NewApi")
    @Override
    public void onGetTimeSlotSuccess(List<TimeSlot> t) {
        this.list = t;

        if (list.isEmpty()) {
            Log.d("debug", "local source is null");
            Toast.makeText(this, "No Bookings found", Toast.LENGTH_SHORT);
        }

        list.sort(new Comparator<TimeSlot>() {
            @Override
            public int compare(TimeSlot a, TimeSlot b) {
                return a.getBookStartTime() - b.getBookStartTime();
            }
        });

        for (TimeSlot ts : list) {
            if (ts.getState() == State.TMPLEAVE) {
                realTimeSlot = ts;
                break;
            }
        }


        if (realTimeSlot == null) {
            Toast.makeText(this, "No booking is found for the user", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ScanOptionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else { // check if the place and seat id is correct to match the user
            if (realTimeSlot.getPlaceId() == (result.charAt(0) - '0')) {
                Toast.makeText(this, "place is correct", Toast.LENGTH_SHORT).show();

                boolean flag = false;
                if (result.length() == 2) {
                    if (realTimeSlot.getSeatId() == (result.charAt(1) - '0'))
                        flag = true;
                } else {
                    if (realTimeSlot.getSeatId() == (result.charAt(1) - '0') * 10 + (result.charAt(2) - '0'))
                        flag = true;
                }
                if (flag) {
                    Toast.makeText(this, "seat is correct", Toast.LENGTH_SHORT).show();
                    Log.d("debug","seat is correct");
                    realTimeSlot.setTempBackTime((int) (System.currentTimeMillis() / 1000)); // update the sign in time;
                    realTimeSlot.setState(State.SIGNIN); // update the state
                    // dbTimeSlotManager.updateTimeSlot(t);

                    Map<String, Object> update = new HashMap<>();
                    update.put(realTimeSlot.getKey() + "/state", realTimeSlot.getState());
                    update.put(realTimeSlot.getKey() + "/tempBackTime", realTimeSlot.getTempBackTime());
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("timeslot");
                    databaseReference.updateChildren(update).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT);
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Updated Failed", Toast.LENGTH_SHORT);
                                        }
                                    }
                            );


                    Toast.makeText(this,"Temp Sign in successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                } else { // if seat is incorrect, toast
                    Toast.makeText(this, "No booking for this seat is found for the user", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ScanOptionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }

            } else { // if place is incorrect, toast
                Toast.makeText(this, "No booking in this place is found for the user", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScanOptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onGetTimeSlotNotFound() {

    }

    @Override
    public void onGetTimeSlotFail(DatabaseError databaseError) {

    }
}
