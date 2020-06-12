package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Sources;

import android.net.Uri;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Activity.ProblemReportActivity;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

public class ProblemReportLocalSource implements ProblemReportSource {
    private static volatile ProblemReportLocalSource instance;

    public static ProblemReportLocalSource getInstance() {
        if(instance == null) {
            instance = new ProblemReportLocalSource();
        }
        return instance;
    }

    @Override
    public Result<Submission> submitReport(ProblemReportListener problemReportListener, Submission submission, Uri imageUri, String name) {
        return null;
    }
}
