package com.placeholder.study_space_booking_android_app.Features.AdminSeat.Activity;

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
import java.util.List;

public class SeatHistoryAdapter extends ArrayAdapter {
    private List<TimeSlot> history;
    private Context context;
    //private LinearLayout linearLayout;
    private int resource;

    public SeatHistoryAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.history = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(history.get(position).getBookStartTime() * 1000);
        String startTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());
        calendar.setTimeInMillis(history.get(position).getBookEndTime() * 1000);
        String endTimeString = DateFormat.getDateTimeInstance().format(calendar.getTime());

        String userString = "UserID: " +  history.get(position).getUserId();
        String timeString = "from " + startTimeString + " to " + endTimeString;
        String stateString = "state: " + history.get(position).getState();
        String arrivalString = "arrival at " + history.get(position).getInTime();
        String signOutString = "sign out at " + history.get(position).getOutTime();
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
        holder.place.setText("place");
        holder.user.setText(userString);
        holder.time.setText(timeString);
        holder.state.setText(stateString);
        holder.arrivalTime.setText(arrivalString);
        holder.signOutTime.setText(signOutString);

        return convertView;
    }

    public static final class ViewHolder {
        View view;
        TextView place;
        TextView user;
        TextView time;
        TextView state;
        TextView arrivalTime;
        TextView signOutTime;
        ViewHolder(View view) {
            this.view = view;
            place = (TextView) view.findViewById(R.id.seathistory_place);
            user = (TextView) view.findViewById(R.id.seathistory_user);
            time = (TextView) view.findViewById(R.id.seathistory_time);
            state = (TextView) view.findViewById(R.id.seathistroy_state);
            arrivalTime = (TextView) view.findViewById(R.id.seathistory_arrival);
            signOutTime = (TextView) view.findViewById(R.id.seathistory_sign_out);
        }
    }
}
