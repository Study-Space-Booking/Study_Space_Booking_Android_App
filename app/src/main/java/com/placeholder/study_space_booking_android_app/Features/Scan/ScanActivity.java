package com.placeholder.study_space_booking_android_app.Features.Scan;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.placeholder.study_space_booking_android_app.R;

import java.io.IOException;
import java.security.Permission;

public class ScanActivity extends AppCompatActivity {
    private static final String TAG = "ScanActivity";

    public static final int CAMERA_REQUEST = 1;



    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;
    Toolbar toolbar;
    String result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        toolbar = findViewById(R.id.toolbar_scan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scan");

        surfaceView = (SurfaceView) findViewById(R.id.scan_area);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 400).build();

        surfaceView.getHolder().addCallback(
                new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        if(checkPermission() != true) {
                            requestPermission();
                        } else {
                            try {
                                cameraSource.start(holder);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        cameraSource.stop();
                    }
                }
        );

        barcodeDetector.setProcessor(
                new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        SparseArray<Barcode> detectedItems = detections.getDetectedItems();


                        if(detectedItems.size() != 0) {
                            result = detectedItems.valueAt(0).displayValue;
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            showDialogue();
                        }
                    }
                }
        );
    }

    public void showDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
        //builder.setCancelable(false);
        builder.setTitle("Code scanned");
        builder.setMessage("Choose to proceed for " + result);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, Manifest.permission.CAMERA)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
            builder.setTitle("Request for permission");
            builder.setMessage("The permission is needed");
            builder.setNegativeButton("go back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("permission", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_REQUEST) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScanActivity.this, "Check", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
