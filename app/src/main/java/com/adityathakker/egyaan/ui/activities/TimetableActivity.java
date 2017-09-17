package com.adityathakker.egyaan.ui.activities;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.TimetableDayFive;
import com.adityathakker.egyaan.fragments.TimetableDayFour;
import com.adityathakker.egyaan.fragments.TimetableDayOne;
import com.adityathakker.egyaan.fragments.TimetableDaySeven;
import com.adityathakker.egyaan.fragments.TimetableDaySix;
import com.adityathakker.egyaan.fragments.TimetableDayThree;
import com.adityathakker.egyaan.fragments.TimetableDayTwo;
import com.adityathakker.egyaan.fragments.ViewPagerAdapter;
import com.adityathakker.egyaan.utils.AppConst;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fireion on 28/6/17.
 */

public class TimetableActivity extends AppCompatActivity {

    private static final String TAG = TimetableActivity.class.getSimpleName();
    @BindView(R.id.toolbar_timetable_activity)
    Toolbar toolbarTimetable;
    @BindView(R.id.activity_view_pager)
    ViewPager viewPagerTimetable;
    @BindView(R.id.activity_tab_layout)
    TabLayout tabLayoutTimetable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarTimetable);
        setupViewPager(viewPagerTimetable);
        tabLayoutTimetable.setupWithViewPager(viewPagerTimetable);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setupViewPager(ViewPager viewPagerTimetable) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TimetableDayOne(), AppConst.Extras.DAY_1);
        viewPagerAdapter.addFragment(new TimetableDayTwo(), AppConst.Extras.DAY_2);
        viewPagerAdapter.addFragment(new TimetableDayThree(), AppConst.Extras.DAY_3);
        viewPagerAdapter.addFragment(new TimetableDayFour(), AppConst.Extras.DAY_4);
        viewPagerAdapter.addFragment(new TimetableDayFive(), AppConst.Extras.DAY_5);
        viewPagerAdapter.addFragment(new TimetableDaySix(), AppConst.Extras.DAY_6);
        viewPagerAdapter.addFragment(new TimetableDaySeven(), AppConst.Extras.DAY_7);

        viewPagerTimetable.setAdapter(viewPagerAdapter);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(0, true);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(1, true);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(2, true);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(3, true);
        } else if (Calendar.FRIDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(4, true);
        } else if (Calendar.SATURDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(5, true);
        } else if (Calendar.SUNDAY == dayOfWeek) {
            viewPagerTimetable.setCurrentItem(6, true);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}