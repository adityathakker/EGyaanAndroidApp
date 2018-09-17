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
 * Created by fireion on 8/12/17.
 */

public class MainTimetableAdapter extends RecyclerView.Adapter<MainTimetableAdapter.MainTimetableViewHolder> {
    private static final String TAG = MainTimetableAdapter.class.getSimpleName();
    List<TimetableData> timetableDataList;
    Context context;

    public MainTimetableAdapter(Context fragmentActivity, List<TimetableData> timetableDataList) {
        this.context = fragmentActivity;
        this.timetableDataList = timetableDataList;
    }

    @Override
    public MainTimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_timetable_main, parent, false);
        return new MainTimetableAdapter.MainTimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainTimetableViewHolder holder, int position) {
        TimetableData timetableData = timetableDataList.get(position);

        holder.textViewCourseName.setText(timetableData.getCourse());
        holder.textViewTime.setText(timetableData.getTime());
    }

    @Override
    public int getItemCount() {
        return timetableDataList.size();
    }

    class MainTimetableViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCourseName, textViewTime;

        MainTimetableViewHolder(View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.card_row_course_name_main);
            textViewTime = itemView.findViewById(R.id.card_row_time_main);
        }
    }
}
