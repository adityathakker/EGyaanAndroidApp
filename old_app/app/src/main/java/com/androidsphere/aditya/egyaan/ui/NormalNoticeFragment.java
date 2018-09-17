package com.androidsphere.aditya.egyaan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidsphere.aditya.egyaan.AppConstants;
import com.androidsphere.aditya.egyaan.adapters.NoticeRecyclerAdapter;
import com.androidsphere.aditya.egyaan.R;
import com.androidsphere.aditya.egyaan.net.NoticeBoardAsync;

/**
 * Created by aditya9172 on 19/1/16.
 */
public class NormalNoticeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_normal_notice, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_normal_notice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        NoticeRecyclerAdapter noticeRecyclerAdapter = new NoticeRecyclerAdapter(getActivity());
        recyclerView.setAdapter(noticeRecyclerAdapter);
        NoticeBoardAsync noticeBoardAsync = new NoticeBoardAsync(getActivity(),noticeRecyclerAdapter);
        noticeBoardAsync.execute(AppConstants.URLs.NORMAL_NOTICE);
    }
}
