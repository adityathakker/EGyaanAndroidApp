package com.adityathakker.egyaan.fragments.noticeboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.NoticeboardModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fireion on 1/12/17.
 */

public class NoticeboardBranch extends Fragment {

    private static final String TAG = NoticeboardBranch.class.getSimpleName();
    List<NoticeboardModel> noticeboardModelList;
    RecyclerView recyclerViewNoticeboard;
    SharedPreferences sharedPreferencesNoticeboard;
    NoticeboardAdapter noticeboardAdapter;
    String emailId;
    View viewNotice;

    public NoticeboardBranch() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewNotice = inflater.inflate(R.layout.content_noticeboard, container, false);
        recyclerViewNoticeboard = viewNotice.findViewById(R.id.recycler_view_noticeboard);
        recyclerViewNoticeboard.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedPreferencesNoticeboard = getContext().getSharedPreferences(AppConst.Extras.PROJ_NAME, Context.MODE_PRIVATE);
        emailId = sharedPreferencesNoticeboard.getString(AppConst.Extras.USERNAME, null);

        //Check if we are here for first time or not
        if (CommonTasks.isDataOn(getContext()).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(getContext()).equals(AppConst.Extras.MOBILE)) {
            //Get Online
            getAndSetNoticeData();
        } else {
            //Get Offline
//            viewNotice = inflater.inflate(R.layout.custom_no_internet_connection, container, false);
            Toast.makeText(getContext(), AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }

        return viewNotice;
    }

    private void getAndSetNoticeData() {
        noticeboardModelList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apis = retrofit.create(APIs.class);
        Call<List<GeneralModel>> noticeboardDataCall = apis.noticeData(emailId);
        noticeboardDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getNoticeboard().size(); i++) {
                            NoticeboardModel noticeboardModel = new NoticeboardModel();
                            String noticeId = models.get(0).getNoticeboard().get(i).getId();
                            String noticeTitle = models.get(0).getNoticeboard().get(i).getTitle();
                            String noticeDate = models.get(0).getNoticeboard().get(i).getDate();
                            String noticeTime = models.get(0).getNoticeboard().get(i).getTime();
                            String noticeType = models.get(0).getNoticeboard().get(i).getType();

                            noticeboardModel.setId(noticeId);
                            noticeboardModel.setTitle(noticeTitle);
                            noticeboardModel.setDate(noticeDate);
                            noticeboardModel.setTime(noticeTime);
                            noticeboardModel.setType(noticeType);

                            if (noticeType.equals("10")) {
                                noticeboardModelList.add(noticeboardModel);
                            } else {
                                Log.e(TAG, "onResponse: Common Notice.");
                            }
                        }
                        noticeboardAdapter = new NoticeboardAdapter(getContext(), noticeboardModelList);
                        recyclerViewNoticeboard.setAdapter(noticeboardAdapter);
                    } else {
                        Toast.makeText(getContext(), AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(getContext(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }
}