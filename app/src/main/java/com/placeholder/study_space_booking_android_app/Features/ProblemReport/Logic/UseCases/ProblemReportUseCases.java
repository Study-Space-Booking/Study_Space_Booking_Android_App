package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.UseCases;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.placeholder.study_space_booking_android_app.Core.Beans.Result;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Activity.ProblemReportActivity;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.ProblemReportListener;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Data.Repository.ProblemReportRepositoryImplementation;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Repository.ProblemReportRepository;
import com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity.ViewReportActivity;

public class ProblemReportUseCases {
    final ProblemReportRepository problemReportRepository;
    private static volatile ProblemReportUseCases instance;

    ProblemReportUseCases(ProblemReportRepository problemReportRepository) {
        this.problemReportRepository = problemReportRepository;
    }

    public static ProblemReportUseCases getInstance() {
        if(instance == null) {
            instance = new ProblemReportUseCases(ProblemReportRepositoryImplementation.getInstance());
        }
        return instance;
    }

    public Result<Submission> submitReport(ProblemReportActivity problemReportActivity, Submission submission, Uri imageUri) {
        return problemReportRepository.submitReport(
                problemReportActivity,
                submission,
                imageUri,
                System.currentTimeMillis() + "." + getExtension(problemReportActivity, imageUri)
        );
    }

    private String getExtension(Activity activity, Uri uri) {
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
