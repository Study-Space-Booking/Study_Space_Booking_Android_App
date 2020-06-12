package com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Sources;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;

import java.util.List;

public class ViewReportLocalSource implements ViewReportSource {
    private static volatile ViewReportLocalSource instance;

    public static ViewReportLocalSource getInstance() {
        if(instance == null) {
            instance = new ViewReportLocalSource();
        }
        return instance;
    }

    @Override
    public Result<List<Submission>> getSubmission(List<Submission> submissions, ViewReportListener viewReportListener) {
        return null;
    }

    @Override
    public Result<Submission> addComment(Submission submission, ViewReportDetailListener viewReportDetailListener) {
        return null;
    }

    @Override
    public Result<Submission> deleteSubmission(Submission submission, ViewReportListener viewReportListener) {
        return null;
    }

    @Override
    public Result<Submission> removeListener() {
        return null;
    }
}
