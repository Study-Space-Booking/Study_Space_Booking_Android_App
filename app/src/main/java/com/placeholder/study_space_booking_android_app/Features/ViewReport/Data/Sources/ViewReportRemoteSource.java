package com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Sources;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewReportRemoteSource implements ViewReportSource {
    final FirebaseStorage firebaseStorage;
    final DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    private static volatile ViewReportRemoteSource instance;

    ViewReportRemoteSource(FirebaseStorage firebaseStorage, DatabaseReference databaseReference) {
        this.firebaseStorage = firebaseStorage;
        this.databaseReference = databaseReference;
    }

    public static ViewReportRemoteSource getInstance() {
        if(instance == null) {
            instance = new ViewReportRemoteSource(
                    FirebaseStorage.getInstance(),
                    FirebaseDatabase.getInstance().getReference("submission")
            );
        }
        return instance;
    }

    @Override
    public Result<List<Submission>> getSubmission(final List<Submission> submissions, final ViewReportListener viewReportListener) {
        try {
            //final List<Submission> submissions = new ArrayList<>();
            //final List<Boolean> check = new ArrayList<>();
            //check.add(true);
            valueEventListener = databaseReference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            submissions.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d("in remote source: ", "get information");
                                Submission submission = snapshot.getValue(Submission.class);
                                Log.d("in remote source: ", "Submission:" + submission.getSelectedPlace() + " " + submission.getDescription());
                                submission.setKey(snapshot.getKey());
                                submissions.add(submission);
                            }
                            viewReportListener.onGetSubmissionSuccess();
                            //check.set(0, true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //check.set(0, false);
                            viewReportListener.onGetSubmissionFailure(databaseError);
                        }
                    }
            );
            /*
            Log.d("in remote source:", "Size: " + submissions.size());
            for(int i = 0; i < submissions.size(); i = i + 1) {
                Log.d("in remote source: ", "Submission:" + submissions.get(i).getSelectedPlace() + " " + submissions.get(i).getDescription());
            }
            */
            return new Result.Accepted<>(submissions);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<Submission> deleteSubmission(final Submission submission, final ViewReportListener viewReportListener) {
        try {
            final StorageReference storageReference = firebaseStorage.getReferenceFromUrl(submission.getImageUrl());
            storageReference.delete().addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String key = submission.getKey();
                            databaseReference.child(key).removeValue();
                            viewReportListener.onDeleteSubmissionSuccess();

                        }
                    }
            );
            return new Result.Accepted<>(submission);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<Submission> removeListener() {
        try {
            databaseReference.removeEventListener(valueEventListener);
            return new Result.Accepted<>(null);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<Submission> changeState(Submission submission, final ViewReportListener viewReportListener) {
        try {
            Map<String, Object> update = new HashMap<>();
            update.put(submission.getKey() + "/state", submission.getState());
            databaseReference.updateChildren(update).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            viewReportListener.onChangeStateFailure();
                        }
                    }
            )
            .addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            viewReportListener.onChangeStateSuccess();
                        }
                    }
            )
            ;
            return new Result.Accepted<>(submission);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }

    @Override
    public Result<Submission> addComment(Submission submission, final ViewReportDetailListener viewReportDetailListener) {
        try {
            Map<String, Object> update = new HashMap<>();
            update.put(submission.getKey() + "/comment", submission.getComment());
            databaseReference.updateChildren(update).addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            viewReportDetailListener.onAddCommentSuccess();
                        }
                    })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    viewReportDetailListener.onAddCommentFailure();
                                }
                            }
                    );


            return new Result.Accepted<>(submission);
        } catch (Exception exception) {
            return new Result.Handle(exception);
        }
    }
}
