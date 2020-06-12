package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Repository;

import android.net.Uri;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Activity.ProblemReportActivity;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Sources.ProblemReportLocalSource;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Sources.ProblemReportRemoteSource;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Repository.ProblemReportRepository;

public class ProblemReportRepositoryImplementation implements ProblemReportRepository {
    final ProblemReportLocalSource problemReportLocalSource;
    final ProblemReportRemoteSource problemReportRemoteSource;
    private static volatile ProblemReportRepositoryImplementation instance;

    ProblemReportRepositoryImplementation(ProblemReportLocalSource problemReportLocalSource,
                                          ProblemReportRemoteSource problemReportRemoteSource) {
        this.problemReportLocalSource = problemReportLocalSource;
        this.problemReportRemoteSource = problemReportRemoteSource;
    }

    public static ProblemReportRepositoryImplementation getInstance() {
        if(instance == null) {
            instance = new ProblemReportRepositoryImplementation(
                    ProblemReportLocalSource.getInstance(),
                    ProblemReportRemoteSource.getInstance()
            );
        }
        return instance;
    }

    @Override
    public Result<Submission> submitReport(ProblemReportListener problemReportListener, Submission submission, Uri imageUri, String name) {
        return problemReportRemoteSource.submitReport(problemReportListener, submission, imageUri, name);
    }
}
