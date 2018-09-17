package com.androidsphere.aditya.egyaan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidsphere.aditya.egyaan.R;

import java.util.ArrayList;
import java.util.HashMap;


public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<HashMap<String, String>> arrayList;

    public TimeTableAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        arrayList = new ArrayList<>();
    }

    public void getTimeTableArrayList(ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.timetable_row, parent, false);
        return new TimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeTableViewHolder holder, int position) {
        HashMap<String, String> temp = arrayList.get(position);
        holder.subjectName.setText(temp.get("subject"));
        holder.subjectTime.setText(temp.get("time"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class TimeTableViewHolder extends RecyclerView.ViewHolder {
    TextView subjectName, subjectTime;

    public TimeTableViewHolder(View itemView) {
        super(itemView);
        subjectName = (TextView) itemView.findViewById(R.id.subject_name);
        subjectTime = (TextView) itemView.findViewById(R.id.subject_time);
    }
}
