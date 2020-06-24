package com.placeholder.study_space_booking_android_app.Features.Home.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.placeholder.study_space_booking_android_app.Core.Beans.TimeSlot;
import com.placeholder.study_space_booking_android_app.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter {
    private List<TimeSlot> history;
    private Context context;
    //private LinearLayout linearLayout;
    private int resource;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.history = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date startDate = new Date(history.get(position).getBookStartTime() * 1000);
        Date endDate = new Date(history.get(position).getBookEndTime() * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String startTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());
        calendar.setTime(endDate);
        String endTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());

        String placeString;
        if (history.get(position).getPlaceId() == 1) {
            placeString = "Place:\nMac Commons";
        } else {
            placeString = "Place:\nPC Commons";
        }
        String seatString = " Seat: " +  history.get(position).getSeatId();
        String timeString = " From " + startTimeString + "\n To " + endTimeString;
        // String stateString = "state: " + history.get(position).getState();
        String arrivalString = " Arrival at " + history.get(position).getInTime();
        String signOutString = " Sign out at " + history.get(position).getOutTime();
        //Result<String> result = HomeFragment.HOME_USE_CASES.getPlaceName(history.get(position).getPlaceId());

        //LinearLayout view;
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(resource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //if(result instanceof Result.Handle) {
        //    holder.place.setText("place not found");
        //} else {
        //    holder.place.setText(((Result.Accepted<String>) result).getModel());
        //}
        holder.place.setText(placeString);
        holder.seat.setText(seatString);
        holder.time.setText(timeString);
        //holder.state.setText(stateString);
        holder.arrivalTime.setText(arrivalString);
        holder.signOutTime.setText(signOutString);

        return convertView;
    }

    public static final class ViewHolder {
        View view;
        TextView place;
        TextView seat;
        TextView time;
        TextView state;
        TextView arrivalTime;
        TextView signOutTime;
        ViewHolder(View view) {
            this.view = view;
            place = (TextView) view.findViewById(R.id.history_place);
            seat = (TextView) view.findViewById(R.id.history_seat);
            time = (TextView) view.findViewById(R.id.history_time);
            //state = (TextView) view.findViewById(R.id.histroy_state);
            arrivalTime = (TextView) view.findViewById(R.id.history_arrival);
            signOutTime = (TextView) view.findViewById(R.id.history_sign_out);
        }
    }
}
