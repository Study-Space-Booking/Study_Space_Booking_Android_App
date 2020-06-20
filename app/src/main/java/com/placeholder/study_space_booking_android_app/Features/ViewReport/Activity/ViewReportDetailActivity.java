package com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.storage.StorageReference;
import com.placeholder.study_space_booking_android_app.Core.Beans.NormalUser;
import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.UseCases.ViewReportUseCases;
import com.placeholder.study_space_booking_android_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewReportDetailActivity extends AppCompatActivity implements ViewReportDetailListener {
    TextView time;
    TextView user;
    TextView place;
    TextView seat;
    TextView description;
    TextView existingComment;
    EditText commentArea;
    ImageView imageView;
    ListView comments;
    Button comment;
    Toolbar toolbar;
    StorageReference storageReference;

    ViewReportUseCases viewReportUseCases;

    Submission submission;

    ArrayList<String> commentList;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_detail);

        toolbar = findViewById(R.id.toolbar_view_report_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Report");

        time = (TextView) findViewById(R.id.view_report_detail_time);
        user = (TextView) findViewById(R.id.view_report_detail_user);
        place = (TextView) findViewById(R.id.view_report_detail_place);
        seat = (TextView) findViewById(R.id.view_report_detail_seat);
        description = (TextView) findViewById(R.id.view_report_detail_description);
        existingComment = (TextView) findViewById(R.id.view_report_existing_comment);
        imageView = (ImageView) findViewById(R.id.image_view_report_photo);
        comment = (Button) findViewById(R.id.button_add_comment);
        comments = (ListView) findViewById(R.id.list_view_comment);
        commentArea = (EditText) findViewById(R.id.view_report_add_comment);

        viewReportUseCases = ViewReportUseCases.getInstance();

        if(getIntent().hasExtra("submission")) {
            submission = getIntent().getParcelableExtra("submission");
            description.setText(submission.getDescription());
            user.setText(submission.getUserName());
            time.setText(submission.getSelectedDate() + " " + submission.getSelectedTime());
            place.setText(submission.getSelectedPlace());
            seat.setText(submission.getSelectedSeat());

            commentList = submission.getCommentList();
            if(commentList.size() == 0) {
                existingComment.setText("No existing comment");
            } else {
                existingComment.setText("Existing comment");
            }
            arrayAdapter = new ArrayAdapter<>(ViewReportDetailActivity.this, android.R.layout.simple_list_item_1, commentList);

            comments.setAdapter(arrayAdapter);
            setCommentListHeight(comments);

            Picasso.get()
                    .load(submission.getImageUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_cloud_download_black_24dp)
                    .into(imageView);


            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(comment.getText().toString().trim().equals("")) {
                        Toast.makeText(ViewReportDetailActivity.this, "No comment", Toast.LENGTH_SHORT).show();
                    } else {
                        String user;
                        if(SignInUseCases.user instanceof NormalUser) {
                            user = SignInUseCases.user.getUserName() + " noraml user ";
                        } else {
                            user = SignInUseCases.user.getUserName() + " administrator ";
                        }
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        submission.addComment(user, commentArea.getText().toString(), date);
                        Result<Submission> result = viewReportUseCases.addComment(submission, ViewReportDetailActivity.this);

                        if(result instanceof Result.Handle) {
                            Toast.makeText(ViewReportDetailActivity.this, ((Result.Handle) result).getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onAddCommentFailure() {
        Toast.makeText(ViewReportDetailActivity.this, "Check", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCommentSuccess() {
        commentArea.setText("");
        commentList.clear();
        commentList.addAll(submission.getCommentList());
        arrayAdapter.notifyDataSetChanged();
        setCommentListHeight(comments);
        existingComment.setText("Existing Comment:");
        Toast.makeText(ViewReportDetailActivity.this, "Add comment", Toast.LENGTH_SHORT).show();
    }

    public void setCommentListHeight(ListView listView) {
        if(listView.getAdapter() != null) {
            ListAdapter listAdapter = listView.getAdapter();
            int height = 0;
            for(int i = 0; i < listAdapter.getCount(); i = i + 1) {
                View itemView = listAdapter.getView(i, null, listView);
                itemView.measure(0, 0);
                height = height + itemView.getMeasuredHeight();

            }

            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = height + listView.getDividerHeight() * (listAdapter.getCount() - 1);
            listView.setLayoutParams(layoutParams);
            listView.requestLayout();
        }
    }
}
