package com.adityathakker.egyaan.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.noticeboard.NoticeboardBranch;
import com.adityathakker.egyaan.fragments.noticeboard.NoticeboardCommon;
import com.adityathakker.egyaan.fragments.noticeboard.NoticeboardViewPagerAdapter;
import com.adityathakker.egyaan.utils.AppConst;

public class NoticeboardActivity extends AppCompatActivity {

    private static final String TAG = NoticeboardActivity.class.getSimpleName();
    ViewPager viewPagerNoticeboard;
    TabLayout tabLayoutNoticeboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);
        Toolbar toolbar = findViewById(R.id.toolbarNoticeboard);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.d(TAG, "onCreate: Toolbar Null data");
        }

        viewPagerNoticeboard = findViewById(R.id.activity_view_pager_noticeboard);
        tabLayoutNoticeboard = findViewById(R.id.activity_tab_layout_noticeboard);

        setupViewPager(viewPagerNoticeboard);
        tabLayoutNoticeboard.setupWithViewPager(viewPagerNoticeboard);
    }

    private void setupViewPager(ViewPager viewPagerNoticeboard) {
        NoticeboardViewPagerAdapter noticeboardViewPagerAdapter = new NoticeboardViewPagerAdapter(getSupportFragmentManager());
        noticeboardViewPagerAdapter.addFragment(new NoticeboardBranch(), AppConst.Extras.NOTICEBOARD_BRANCH);
        noticeboardViewPagerAdapter.addFragment(new NoticeboardCommon(), AppConst.Extras.NOTICEBOARD_COMMON);

        viewPagerNoticeboard.setAdapter(noticeboardViewPagerAdapter);
    }

}
