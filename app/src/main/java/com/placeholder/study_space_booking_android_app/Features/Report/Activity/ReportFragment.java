package com.placeholder.study_space_booking_android_app.Features.Report.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Activity.ProblemReportActivity;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportActivity;
import com.placeholder.study_space_booking_android_app.R;

public class ReportFragment extends Fragment {
    Context context;
    Button viewReport;
    Button submitReport;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        viewReport = (Button) view.findViewById(R.id.button_view_report);
        submitReport = (Button) view.findViewById(R.id.button_submit_report);
        viewReport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ViewReportActivity.class);
                        startActivity(intent);
                        /*
                        if(SignInUseCases.user instanceof NormalUser) {
                            Intent intent = new Intent(context, ProblemReportActivity.class);
                            startActivity(intent);
                        } else {

                        }
                        */
                    }
                }
        );

        submitReport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProblemReportActivity.class);
                        startActivity(intent);
                    }
                }
        );

        return view;
    }
}
