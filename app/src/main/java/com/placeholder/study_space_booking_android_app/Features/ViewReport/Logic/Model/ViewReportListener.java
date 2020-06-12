package com.placeholder.study_space_booking_android_app.Features.ViewReport.Logic.Model;

import com.google.firebase.database.DatabaseError;

public interface ViewReportListener {
    void onGetSubmissionFailure(DatabaseError databaseError);
    void onGetSubmissionSuccess();
    void onDeleteSubmissionSuccess();
}
