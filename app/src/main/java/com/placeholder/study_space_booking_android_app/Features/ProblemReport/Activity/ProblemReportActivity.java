package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.UseCases.ProblemReportUseCases;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ProblemReportActivity extends AppCompatActivity implements ProblemReportListener {
    private static final int CHOOSE_PHOTO_REQUEST = 1;

    private String selectedDate;
    private String selectedTime;
    private String selectedPlace;


    private TextView chooseDate;
    private TextView chooseTime;
    private TextView choosePlace;
    private Button choosePhoto;
    //private Button showPhoto;
    private Button submit;
    public ProgressBar progressBar;
    private ImageView imageView;
    private EditText description;
    private EditText chooseSeat;
    private Uri imageUri;

    private Toolbar toolbar;
    //private StorageReference storageReference;
    //private DatabaseReference databaseReference;
    //private StorageTask storageTask;

    private ProblemReportUseCases problemReportUseCases;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);

        toolbar = findViewById(R.id.toolbar_problem_report);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Problem report");

        chooseDate = (TextView) findViewById(R.id.problem_report_chosen_date);
        chooseTime = (TextView) findViewById(R.id.problem_report_chosen_time);
        choosePlace = (TextView) findViewById(R.id.problem_report_chosen_place);
        chooseSeat = (EditText) findViewById(R.id.problem_report_chosen_seat);
        choosePhoto = (Button) findViewById(R.id.button_submit_photo);
        //showPhoto = (Button) findViewById(R.id.button_show_photo);
        submit = (Button) findViewById(R.id.button_submit);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_photo);
        imageView = (ImageView) findViewById(R.id.image_view_photo);
        description = (EditText) findViewById(R.id.problem_report_add_description);

        //storageReference = FirebaseStorage.getInstance().getReference("submission");
        //databaseReference = FirebaseDatabase.getInstance().getReference("submission");

        problemReportUseCases = ProblemReportUseCases.getInstance();

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChooseDate();
            }
        });

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChooseTime();
            }
        });

        choosePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChoosePlace(v);
            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        /*
        showPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReport();
            }
        });


     }


     private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_PHOTO_REQUEST);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_PHOTO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);

        }
    }

    private void submitReport() {
        if(imageUri == null) {
            Toast.makeText(ProblemReportActivity.this, "No file selected", Toast.LENGTH_SHORT).show();
        } else if(checkInformation() != true) {
            Toast.makeText(ProblemReportActivity.this, "No information", Toast.LENGTH_SHORT).show();
        } else {
            String reportSeat = chooseSeat.getText().toString();
            String reportDescription = description.getText().toString();
            Result<Submission> result = problemReportUseCases.submitReport(
                    ProblemReportActivity.this,
                    new Submission(
                            selectedDate,
                            selectedTime,
                            selectedPlace,
                            reportSeat,
                            null,
                            SignInUseCases.user.getUserName(),
                            reportDescription
                    ),
                    imageUri
            );

            if(result instanceof Result.Handle) {
                Toast.makeText(ProblemReportActivity.this, ((Result.Handle) result).getException().getMessage(), Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }

    /*
    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    */
    /*
    private void submitReport() {
        if (imageUri == null) {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        } else if (checkInformation() == false){
            Toast.makeText(this,"No information", Toast.LENGTH_SHORT).show();
        } else if (storageTask != null && storageTask.isInProgress()) {
            Toast.makeText(this, "submission in progress", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference fileReference = storageReference.child(
                    System.currentTimeMillis() + "." + getExtension(imageUri)
            );

            storageTask = fileReference.putFile(imageUri)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100;
                            progressBar.setProgress((int) progress);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProblemReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 1000);
                            Toast.makeText(ProblemReportActivity.this, "report submitted", Toast.LENGTH_SHORT).show();
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            final String reportSeat = chooseSeat.getText().toString();
                            final String reportDescription = description.getText().toString();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Submission submit = new Submission(
                                            selectedDate,
                                            selectedTime,
                                            selectedPlace,
                                            reportSeat,
                                            uri.toString(),
                                            reportDescription);
                                    String submitId = databaseReference.push().getKey();
                                    databaseReference.child(submitId).setValue(submit);
                                }
                            });
                        }
                    });
        }
    }
    */

    public void setChooseDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int date = calendar.get(calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ProblemReportActivity.this,
                R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int currentMonth = month + 1;
                selectedDate = dayOfMonth + "/" + currentMonth + "/" + year;
                chooseDate.setText(selectedDate);
            }
        }, year, month, date);
        datePickerDialog.show();

    }
    public void setChooseTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        int minute = calendar.get(calendar.MINUTE);
        boolean hourFormat = DateFormat.is24HourFormat(ProblemReportActivity.this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ProblemReportActivity.this,
                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = hourOfDay + " " + minute;
                chooseTime.setText(selectedTime);
            }
        }, hour, minute, hourFormat);
        timePickerDialog.show();
    }
    public void setChoosePlace(View view) {
        PopupMenu popupMenu = new PopupMenu(ProblemReportActivity.this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selectedPlace = item.getTitle().toString();
                choosePlace.setText(selectedPlace);
                return true;
            }
        });
        popupMenu.inflate(R.menu.place_menu);
        popupMenu.show();
    }
    public boolean checkInformation() {
        return selectedDate != null && selectedTime != null && selectedPlace != null &&
                !(chooseSeat.getText().toString().trim().equals("")) &&
                !(description.getText().toString().trim().equals(""));

    }

    @Override
    public void inSubmitReportProgress(double progress) {
        progressBar.setProgress((int) progress);
    }

    @Override
    public void onSubmitReportFailure(Exception exception) {
        Toast.makeText(ProblemReportActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitReportSuccess() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(0);
            }
        }, 1000);
        Toast.makeText(ProblemReportActivity.this, "report submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inProgress() {
        Toast.makeText(ProblemReportActivity.this, "submission in progress", Toast.LENGTH_SHORT).show();
    }
}