package com.adityathakker.egyaan.fragments.noticeboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.FullNoticeboardViewModel;
import com.adityathakker.egyaan.ui.activities.FullNoticeboardViewActivity;

import java.util.List;

/**
 * Created by fireion on 19/12/17.
 */

public class FullNoticeboardViewAdapter extends RecyclerView.Adapter<FullNoticeboardViewAdapter.FullNoticeboardViewHolder> {

    List<FullNoticeboardViewModel> fullNoticeboardViewModelList;
    Context context;

    public FullNoticeboardViewAdapter(FullNoticeboardViewActivity fullNoticeboardViewActivity, List<FullNoticeboardViewModel> fullNoticeboardViewModelList) {
        this.context = fullNoticeboardViewActivity;
        this.fullNoticeboardViewModelList = fullNoticeboardViewModelList;
    }

    @Override
    public FullNoticeboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_full_noticeboard_view, parent, false);
        return new FullNoticeboardViewAdapter.FullNoticeboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FullNoticeboardViewHolder holder, int position) {
        FullNoticeboardViewModel fullNoticeboardViewModel = fullNoticeboardViewModelList.get(position);

        holder.textViewNoticeTitle.setText(fullNoticeboardViewModel.getTitle());
        holder.textViewNoticeDate.setText(fullNoticeboardViewModel.getDate());
        holder.textViewNoticeTime.setText(fullNoticeboardViewModel.getTime());
        holder.textViewNoticeDescription.setText(fullNoticeboardViewModel.getNotice());

        if (fullNoticeboardViewModel.getFile().equals("-")) {
            holder.linearLayoutNoticeMaterial.setVisibility(View.GONE);
        } else {
            //This is incomplete
            holder.textViewNoticeFileName.setText(fullNoticeboardViewModel.getFile());
        }

    }

    @Override
    public int getItemCount() {
        return fullNoticeboardViewModelList.size();
    }

    class FullNoticeboardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNoticeTitle, textViewNoticeDate, textViewNoticeTime, textViewNoticeDescription,
                textViewNoticeFileName;
        ImageView imageViewNoticeFileTypeImg;
        LinearLayout linearLayoutNoticeMaterial;

        FullNoticeboardViewHolder(View itemView) {
            super(itemView);

            textViewNoticeTitle = itemView.findViewById(R.id.full_noticeboard_notice_title);
            textViewNoticeDate = itemView.findViewById(R.id.full_noticeboard_notice_date);
            textViewNoticeTime = itemView.findViewById(R.id.full_noticeboard_notice_time);
            textViewNoticeDescription = itemView.findViewById(R.id.full_noticeboard_notice_notice_description);
            textViewNoticeFileName = itemView.findViewById(R.id.full_noticeboard_notice_file_name);

            imageViewNoticeFileTypeImg = itemView.findViewById(R.id.full_noticeboard_notice_file_image);
            linearLayoutNoticeMaterial = itemView.findViewById(R.id.linearLayoutNoticeboardMaterial);
        }
    }
}
