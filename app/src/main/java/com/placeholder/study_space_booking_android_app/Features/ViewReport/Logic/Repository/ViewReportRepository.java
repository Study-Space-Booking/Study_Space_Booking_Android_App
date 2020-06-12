package com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;

import java.util.List;

public interface ViewReportRepository {
    Result<List<Submission>> getSubmission(List<Submission> submissions, ViewReportListener viewReportListener);
    Result<Submission> deleteSubmission(Submission submission, ViewReportListener viewReportListener);
    Result<Submission> removeListener();
    Result<Submission> addComment(Submission submission, ViewReportDetailListener viewReportDetailListener);
}
