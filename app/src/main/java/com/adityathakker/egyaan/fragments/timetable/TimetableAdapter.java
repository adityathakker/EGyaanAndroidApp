package com.adityathakker.egyaan.fragments.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.TimetableData;

import java.util.List;

/**
 * Created by fireion on 28/6/17.
 */

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private static final String TAG = TimetableAdapter.class.getSimpleName();
    List<TimetableData> timetableDataList;
    Context context;

    TimetableAdapter(Context fragmentActivity, List<TimetableData> timetableDataList) {
        this.context = fragmentActivity;
        this.timetableDataList = timetableDataList;
    }

    @Override
    public TimetableAdapter.TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_timetable_activity, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimetableAdapter.TimetableViewHolder holder, int position) {
        TimetableData timetableData = timetableDataList.get(position);

        holder.courseName.setText(timetableData.getCourse());
        holder.timeTextView.setText(timetableData.getTime());
        holder.teacherName.setText(timetableData.getTeacher());
    }

    @Override
    public int getItemCount() {
        return timetableDataList.size();
    }

    public class TimetableViewHolder extends RecyclerView.ViewHolder {

        TextView courseName, teacherName, timeTextView;

        public TimetableViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.card_row_course_name);
            teacherName = itemView.findViewById(R.id.card_row_teacher_name);
            timeTextView = itemView.findViewById(R.id.card_row_time);
        }
    }
}
