package com.adityathakker.egyaan.fragments.noticeboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.NoticeboardModel;
import com.adityathakker.egyaan.ui.activities.FullNoticeboardViewActivity;

import java.util.List;

/**
 * Created by fireion on 1/12/17.
 */

public class NoticeboardAdapter extends RecyclerView.Adapter<NoticeboardAdapter.NoticeboardViewHolder> {

    private static final String TAG = NoticeboardAdapter.class.getSimpleName();
    Context context;
    private List<NoticeboardModel> noticeboardModelList;

    public NoticeboardAdapter(Context noticeboardActivity, List<NoticeboardModel> noticeboardModels) {
        this.context = noticeboardActivity;
        this.noticeboardModelList = noticeboardModels;
    }

    @Override
    public NoticeboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_noticeboard_activity_common, parent, false);
        return new NoticeboardAdapter.NoticeboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeboardViewHolder holder, int position) {
        final NoticeboardModel noticeboardModel = noticeboardModelList.get(position);

        holder.textViewNoticeTitle.setText(noticeboardModel.getTitle());
        holder.textViewNoticeDate.setText(noticeboardModel.getDate());
        holder.textViewNoticeTime.setText(noticeboardModel.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetailedNoticeboard = new Intent(context, FullNoticeboardViewActivity.class);
                intentDetailedNoticeboard.putExtra("noticeId", noticeboardModel.getId());
                context.startActivity(intentDetailedNoticeboard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeboardModelList.size();
    }

    class NoticeboardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNoticeTitle, textViewNoticeDate, textViewNoticeTime;

        NoticeboardViewHolder(View itemView) {
            super(itemView);
            textViewNoticeTitle = itemView.findViewById(R.id.textViewNoticeTitle);
            textViewNoticeDate = itemView.findViewById(R.id.textViewNoticeDate);
            textViewNoticeTime = itemView.findViewById(R.id.textViewNoticeTime);
        }
    }
}
