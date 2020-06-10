package com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.UseCases.ViewReportUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.ArrayList;
import java.util.List;

public class ViewReportActivity extends AppCompatActivity implements ViewReportAdapter.ReportOnItemClickListener, ViewReportListener {
    RecyclerView recyclerView;
    ViewReportAdapter viewReportAdapter;
    //FirebaseStorage firebaseStorage;
    //DatabaseReference databaseReference;
    public List<Submission> submissions;
    //ValueEventListener valueEventListener;
    ProgressBar progressBar;
    ViewReportUseCases viewReportUseCases;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        toolbar = findViewById(R.id.toolbar_view_report);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View report");

        recyclerView = (RecyclerView) findViewById(R.id.report_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_view_report);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //firebaseStorage = FirebaseStorage.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference("submission");

        submissions = new ArrayList<>();
        viewReportAdapter = new com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportAdapter(ViewReportActivity.this, submissions);
        viewReportAdapter.setReportOnItemClickListener(this);
        recyclerView.setAdapter(viewReportAdapter);

        viewReportUseCases = ViewReportUseCases.getInstance();

        Result<List<Submission>> result = viewReportUseCases.getSubmission(submissions, ViewReportActivity.this);


        if(result instanceof Result.Handle) {
            Toast.makeText(this, ((Result.Handle) result).getException().getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            /*
            submissions = ((Result.Accepted<List<Submission>>) result).getModel();


            viewReportAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.INVISIBLE);
            */
        }


        /*
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                submissions.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    Submission submission = snapshot.getValue(Submission.class);
                    submission.setKey(snapshot.getKey());
                    submissions.add(submission);

                }

                viewReportAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewReportActivity.this, "Check", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        */




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewReportUseCases.removeListener();
    }

    @Override
    public void onDetailClick(int position) {
        Intent intent = new Intent(ViewReportActivity.this, ViewReportDetailActivity.class);
        intent.putExtra("submission", submissions.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        final Submission submission = submissions.get(position);
        Result<Submission> result = viewReportUseCases.deleteSubmission(submission, ViewReportActivity.this);
        //final String key = submission.getKey();
        if(result instanceof Result.Handle) {
            Toast.makeText(ViewReportActivity.this, "Check", Toast.LENGTH_SHORT).show();
        } else {
            /*
            viewReportAdapter.notifyDataSetChanged();
            Toast.makeText(ViewReportActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
            */
        }
    }





    @Override
    public void onGetSubmissionFailure(DatabaseError databaseError) {
        Toast.makeText(this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetSubmissionSuccess() {
        viewReportAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDeleteSubmissionSuccess() {
        Toast.makeText(ViewReportActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
    }


}
