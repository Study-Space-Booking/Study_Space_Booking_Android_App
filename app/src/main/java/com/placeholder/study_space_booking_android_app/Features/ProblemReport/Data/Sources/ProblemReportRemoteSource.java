package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Sources;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

public class ProblemReportRemoteSource implements ProblemReportSource {
    //List<Boolean> check;
    final StorageReference storageReference;
    final DatabaseReference databaseReference;
    StorageTask storageTask;
    private static volatile ProblemReportRemoteSource instance;

    ProblemReportRemoteSource(StorageReference storageReference, DatabaseReference databaseReference) {
        this.storageReference = storageReference;
        this.databaseReference = databaseReference;
        //this.check = new ArrayList<>();
        //this.check.add(true);
    }

    public static ProblemReportRemoteSource getInstance() {
        if(instance == null) {
            instance = new ProblemReportRemoteSource(
                    FirebaseStorage.getInstance().getReference("submission"),
                    FirebaseDatabase.getInstance().getReference("submission")
            );
        }
        return instance;
    }

    @Override
    public Result<Submission> submitReport(final ProblemReportListener problemReportListener, final Submission submission, Uri imageUri, String name) {
        try {
            if(storageTask != null && storageTask.isInProgress()) {
                //Toast.makeText(activity, "submission in progress", Toast.LENGTH_SHORT).show();
                //check.set(0, false);
                problemReportListener.inProgress();
            } else {
                StorageReference fileReference = storageReference.child(name);
                storageTask = fileReference.putFile(imageUri)
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100;
                                        //activity.progressBar.setProgress((int) progress);
                                        problemReportListener.inSubmitReportProgress(progress);
                                    }
                                }
                        )
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        //check.set(0, false);
                                        problemReportListener.onSubmitReportFailure(e);
                                    }
                                }
                        )
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        /*
                                        Handler handler = new Handler(activity.getMainLooper());
                                        handler.postDelayed(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        activity.progressBar.setProgress(0);
                                                    }
                                                }, 1000);
                                        Toast.makeText(activity, "report submitted", Toast.LENGTH_SHORT).show();
                                        */
                                        problemReportListener.onSubmitReportSuccess();
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(
                                                new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl = uri.toString();
                                                        submission.setImageUrl(imageUrl);
                                                        String submitId = databaseReference.push().getKey();
                                                        databaseReference.child(submitId).setValue(submission);
                                                    }
                                                }
                                        );
                                        //check.set(0, true);
                                    }

                                }
                        );
            }

            return new Result.Accepted<>(submission);
        } catch (Exception exception) {
            //Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
            return new Result.Handle(exception);
        }
    }
}
