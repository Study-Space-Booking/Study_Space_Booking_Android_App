package com.placeholder.study_space_booking_android_app.Features.ViewReport.Activity;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.placeholder.study_space_booking_android_app.Core.Beans.Admin;
import com.placeholder.study_space_booking_android_app.Features.ProblemReport.Logic.Model.Submission;
import com.placeholder.study_space_booking_android_app.Features.SignIn.logic.UseCases.SignInUseCases;
import com.placeholder.study_space_booking_android_app.R;

import java.util.List;

public class ViewReportAdapter extends RecyclerView.Adapter<ViewReportAdapter.ViewReportHolder> {
    Context context;
    List<Submission> submissions;
    ReportOnItemClickListener reportOnItemClickListener;

    ViewReportAdapter(Context context, List<Submission> submissions) {
        this.context = context;
        this.submissions = submissions;
    }

    @NonNull
    @Override
    public ViewReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ViewReportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewReportHolder holder, int position) {
        Submission submission = submissions.get(position);
        holder.description.setText(submission.getDescription());

    }

    @Override
    public int getItemCount() {
        return submissions.size();
    }

    public class ViewReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView description;
        public TextView detail;
        public TextView delete;
        public ViewReportHolder(@NonNull View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.view_report_description);
            detail = (TextView) itemView.findViewById(R.id.view_report_detail);
            delete = (TextView) itemView.findViewById(R.id.view_report_delete);
            detail.setOnClickListener(this);
            if(SignInUseCases.user instanceof Admin) {
                delete.setOnClickListener(this);
            } else {
                delete.setVisibility(View.INVISIBLE);
            }
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (reportOnItemClickListener != null && position != RecyclerView.NO_POSITION) {
                if (v == detail) {
                    reportOnItemClickListener.onDetailClick(position);
                    //Intent intent = new Intent(context, ViewReportDetailActivity.class, submissions.get(position));
                    //startActivity(intent);
                } else if (v == delete) {
                    reportOnItemClickListener.onDeleteClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose");
            MenuItem itemDetail = menu.add(Menu.NONE, 1, 1, "Detail");
            MenuItem itemDelete = menu.add(Menu.NONE, 2, 2, "Delete");
            itemDetail.setOnMenuItemClickListener(this);
            itemDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            if (reportOnItemClickListener != null && position != RecyclerView.NO_POSITION) {
                switch (item.getItemId()) {
                    case 1:
                        reportOnItemClickListener.onDetailClick(position);
                        break;
                    case 2:
                        reportOnItemClickListener.onDeleteClick(position);
                        break;
                }
            }
            return false;
        }

    }

    public interface ReportOnItemClickListener {
        void onDetailClick(int position);
        void onDeleteClick(int position);
    }

    public void setReportOnItemClickListener(ReportOnItemClickListener reportOnItemClickListener) {
        this.reportOnItemClickListener = reportOnItemClickListener;
    }
}