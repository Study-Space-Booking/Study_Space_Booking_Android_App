package com.placeholder.study_space_booking_android_app.Features.BookSeat.Activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadDialogue {
    private Activity activity;
    private AlertDialog dialog;
    private int resource;

    LoadDialogue(Activity activity, int resource) {
        this.activity = activity;
        this.resource = resource;
    }

    public void startDialogue() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(resource, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void stopDialogue() {
        dialog.dismiss();
    }

}
