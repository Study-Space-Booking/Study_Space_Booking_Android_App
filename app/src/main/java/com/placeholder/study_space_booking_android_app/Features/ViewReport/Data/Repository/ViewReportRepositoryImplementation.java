package com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Repository;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Sources.ViewReportLocalSource;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Sources.ViewReportRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Repository.ViewReportRepository;

import java.util.List;

public class ViewReportRepositoryImplementation implements ViewReportRepository {
    final ViewReportLocalSource viewReportLocalSource;
    final ViewReportRemoteSource viewReportRemoteSource;
    private static volatile ViewReportRepositoryImplementation instance;

    ViewReportRepositoryImplementation(ViewReportLocalSource viewReportLocalSource, ViewReportRemoteSource viewReportRemoteSource) {
        this.viewReportLocalSource = viewReportLocalSource;
        this.viewReportRemoteSource = viewReportRemoteSource;
    }

    public static ViewReportRepositoryImplementation getInstance() {
        if(instance == null) {
            instance = new ViewReportRepositoryImplementation(
                    ViewReportLocalSource.getInstance(),
                    ViewReportRemoteSource.getInstance()
            );
        }
        return instance;
    }

    @Override
    public Result<List<Submission>> getSubmission(List<Submission> submissions, ViewReportListener viewReportListener) {
        return viewReportRemoteSource.getSubmission(submissions, viewReportListener);
    }

    @Override
    public Result<Submission> changeState(Submission submission, ViewReportListener viewReportListener) {
        return viewReportRemoteSource.changeState(submission, viewReportListener);
    }

    @Override
    public Result<Submission> addComment(Submission submission, ViewReportDetailListener viewReportDetailListener) {
        return viewReportRemoteSource.addComment(submission, viewReportDetailListener);
    }

    @Override
    public Result<Submission> deleteSubmission(Submission submission, ViewReportListener viewReportListener) {
        return viewReportRemoteSource.deleteSubmission(submission, viewReportListener);
    }

    @Override
    public Result<Submission> removeListener() {
        return viewReportRemoteSource.removeListener();
    }
}
