package com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.UseCases;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Data.Repository.ViewReportRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportDetailListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model.ViewReportListener;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Repository.ViewReportRepository;

import java.util.List;

public class ViewReportUseCases {
    final ViewReportRepository viewReportRepository;
    private static volatile ViewReportUseCases instance;

    ViewReportUseCases(ViewReportRepository viewReportRepository) {
        this.viewReportRepository = viewReportRepository;
    }

    public static ViewReportUseCases getInstance() {
        if(instance == null) {
            instance = new ViewReportUseCases(ViewReportRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<List<Submission>> getSubmission(List<Submission> submissions, ViewReportListener viewReportListener) {
        return viewReportRepository.getSubmission(submissions, viewReportListener);
    }

    public Result<Submission> deleteSubmission(Submission submission, ViewReportListener viewReportListener) {
        return viewReportRepository.deleteSubmission(submission, viewReportListener);
    }

    public Result<Submission> removeListener() {
        return viewReportRepository.removeListener();
    }

    public Result<Submission> addComment(Submission submission, ViewReportDetailListener viewReportDetailListener) {
        return viewReportRepository.addComment(submission, viewReportDetailListener);
    }

    public Result<Submission> changeState(Submission submission, ViewReportListener viewReportListener) {
        return viewReportRepository.changeState(submission, viewReportListener);
    }
}
