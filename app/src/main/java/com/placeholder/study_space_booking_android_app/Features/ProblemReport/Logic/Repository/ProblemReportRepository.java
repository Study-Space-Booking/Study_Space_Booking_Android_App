package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Repository;

import android.net.Uri;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;

public interface ProblemReportRepository {
    Result<Submission> submitReport(ProblemReportListener problemReportListener, Submission submission, Uri imageUri, String name);
}
