package com.placeholder.study_space_booking_android_app.Features;

import android.content.Context;

import com.placeholder.study_space_booking_android_app.db.DBAdminManager;
import com.placeholder.study_space_booking_android_app.db.DBLogHistoryManager;
import com.placeholder.study_space_booking_android_app.db.DBPlaceManager;
import com.placeholder.study_space_booking_android_app.db.DBProbRepoManager;
import com.placeholder.study_space_booking_android_app.db.DBSeatManager;
import com.placeholder.study_space_booking_android_app.db.DBTimeSlotManager;
import com.placeholder.study_space_booking_android_app.db.DBUserInformationManager;

public class Injection {
    public static void inject(Context context) {
        DBPlaceManager dbPlaceManager = DBPlaceManager.getInstance();
        dbPlaceManager.initialize(context);

        DBTimeSlotManager dbTimeSlotManager = DBTimeSlotManager.getInstance();
        dbTimeSlotManager.initialize(context);

        DBLogHistoryManager dbLogHistoryManager = DBLogHistoryManager.getInstance();
        dbLogHistoryManager.initialize(context);

        DBUserInformationManager dbUserInformationManager = DBUserInformationManager.getInstance();
        dbUserInformationManager.initialize(context);

        DBAdminManager dbAdminManager = DBAdminManager.getInstance();
        dbAdminManager.initialize(context);

        DBProbRepoManager dbProbRepoManager = DBProbRepoManager.getInstance();
        dbProbRepoManager.initialize(context);

        DBSeatManager dbSeatManager = DBSeatManager.getInstance();
        dbSeatManager.initialize(context);
    }
}
