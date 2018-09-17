package com.adityathakker.egyaan.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.noticeboard.FullNoticeboardViewAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.FullNoticeboardViewModel;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FullNoticeboardViewActivity extends AppCompatActivity {

    private static final String TAG = FullNoticeboardViewActivity.class.getSimpleName();
    List<FullNoticeboardViewModel> fullNoticeboardViewModelList;
    RecyclerView recyclerViewFullNoticeboardView;
    FullNoticeboardViewAdapter fullNoticeboardViewAdapter;
    String noticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_noticeboard_view);
        Toolbar toolbar = findViewById(R.id.toolbarFullNoticeboardView);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "onCreate: Toolbar Null data");
        }

        recyclerViewFullNoticeboardView = findViewById(R.id.recycler_view_noticeboard_full_view);
        recyclerViewFullNoticeboardView.setLayoutManager(new LinearLayoutManager(this));

        noticeId = getIntent().getStringExtra("noticeId");

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            getAndSetFullNotice();
        } else {
//            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
            setContentView(R.layout.custom_no_internet_connection);
        }
    }

    private void getAndSetFullNotice() {
        fullNoticeboardViewModelList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);
        Call<List<GeneralModel>> courseDataCall = apIs.detailedNoticeData(noticeId);
        courseDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getFullNotice().size(); i++) {
                            FullNoticeboardViewModel fullNoticeboardViewModel = new FullNoticeboardViewModel();
                            String noticeTitle = models.get(0).getFullNotice().get(i).getTitle();
                            String noticeDate = models.get(0).getFullNotice().get(i).getDate();
                            String noticeTime = models.get(0).getFullNotice().get(i).getTime();
                            String noticeDescription = models.get(0).getFullNotice().get(i).getNotice();
                            String noticeFile = models.get(0).getFullNotice().get(i).getFile();

                            fullNoticeboardViewModel.setTitle(noticeTitle);
                            fullNoticeboardViewModel.setDate(noticeDate);
                            fullNoticeboardViewModel.setTime(noticeTime);
                            fullNoticeboardViewModel.setNotice(noticeDescription);
                            fullNoticeboardViewModel.setFile(noticeFile);

                            fullNoticeboardViewModelList.add(fullNoticeboardViewModel);
                        }
                        fullNoticeboardViewAdapter = new FullNoticeboardViewAdapter(FullNoticeboardViewActivity.this, fullNoticeboardViewModelList);
                        recyclerViewFullNoticeboardView.setAdapter(fullNoticeboardViewAdapter);

                    } else {
//                        Toast.makeText(FullNoticeboardViewActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.custom_no_data_found);
                    }
                } else {
                    Toast.makeText(FullNoticeboardViewActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(FullNoticeboardViewActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
