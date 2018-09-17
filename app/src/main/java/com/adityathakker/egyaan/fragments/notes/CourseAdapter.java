package com.adityathakker.egyaan.fragments.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.CourseDataModel;
import com.adityathakker.egyaan.ui.activities.FullNotesViewActivity;

import java.util.List;

/**
 * Created by fireion on 16/11/17.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private static final String TAG = CourseAdapter.class.getSimpleName();
    private List<CourseDataModel> courseDataModels;
    Context context;

    public CourseAdapter(Context notesActivity, List<CourseDataModel> courseDataModels) {
        this.context = notesActivity;
        this.courseDataModels = courseDataModels;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notes_activity, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        final CourseDataModel courseDataModel = courseDataModels.get(position);
//        Log.d(TAG, "onBindViewHolder: " + courseDataModel.getName());

        holder.textViewNotesTitle.setText(courseDataModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFullNotesView = new Intent(context, FullNotesViewActivity.class);
                intentFullNotesView.putExtra("courseId", courseDataModel.getId());
                context.startActivity(intentFullNotesView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseDataModels.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNotesTitle;

        CourseViewHolder(View itemView) {
            super(itemView);

            textViewNotesTitle = itemView.findViewById(R.id.notesTitle);
        }
    }
}
