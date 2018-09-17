package com.androidsphere.aditya.egyaan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidsphere.aditya.egyaan.R;
import com.androidsphere.aditya.egyaan.model.Notice;

import java.util.ArrayList;

/**
 * Created by aditya9172 on 19/1/16.
 */
public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Notice> arrayList;
    NoticeRecyclerAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public void setData(ArrayList<Notice> arrayList){
        this.arrayList = arrayList;
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(inflater.inflate(R.layout.notice_row,parent,false));
    }


    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        Notice eachNotice = arrayList.get(position);
        holder.title.setText(eachNotice.getTitle());
        holder.notice.setText(eachNotice.getNotice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class NoticeViewHolder extends RecyclerView.ViewHolder{
    TextView title, notice;
    public NoticeViewHolder(View itemView) {
        super(itemView);
        title  = (TextView) itemView.findViewById(R.id.notice_title);
        notice  = (TextView) itemView.findViewById(R.id.notice_description);
    }
}
