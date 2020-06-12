package com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Submission implements Parcelable {
    String selectedDate;
    String selectedTime;
    String selectedPlace;
    String selectedSeat;
    String description;
    String imageUrl;
    String userName;
    String key;
    String comment;

    public Submission() {

    }

    public Submission(String selectedDate, String selectedTime, String selectedPlace,
                      String selectedSeat, String imageUrl, String userName, String description) {
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedPlace = selectedPlace;
        this.selectedSeat = selectedSeat;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.description = description;
        this.comment = "";
    }

    protected Submission(Parcel in) {
        selectedDate = in.readString();
        selectedTime = in.readString();
        selectedPlace = in.readString();
        selectedSeat = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        userName = in.readString();
        key = in.readString();
        comment = in.readString();
    }

    public static final Creator<Submission> CREATOR = new Creator<Submission>() {
        @Override
        public Submission createFromParcel(Parcel in) {
            return new Submission(in);
        }

        @Override
        public Submission[] newArray(int size) {
            return new Submission[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public String getSelectedPlace() {
        return selectedPlace;
    }

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void addComment(String userName, String comment, Date date) {
        this.comment = this.comment + userName + ": " + comment + " on " + DateFormat.getDateTimeInstance().format(date) + ", divider, ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedDate);
        dest.writeString(selectedTime);
        dest.writeString(selectedPlace);
        dest.writeString(selectedSeat);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(userName);
        dest.writeString(key);
        dest.writeString(comment);
    }

    public ArrayList<String> getCommentList() {
        Boolean check = comment == null;
        Log.d("in submission:", "comment is null" + check);
        if(comment.equals("")) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(this.comment.split(", divider, ")));
        }
    }
}
