package com.placeholder.study_space_booking_android_app;

import android.content.Context;

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
