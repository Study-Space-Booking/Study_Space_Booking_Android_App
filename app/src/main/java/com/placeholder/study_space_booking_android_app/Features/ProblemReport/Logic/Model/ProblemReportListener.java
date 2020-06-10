package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model;

public interface ProblemReportListener {
    void inSubmitReportProgress(double progress);
    void onSubmitReportFailure(Exception exception);
    void onSubmitReportSuccess();
    void inProgress();
}
