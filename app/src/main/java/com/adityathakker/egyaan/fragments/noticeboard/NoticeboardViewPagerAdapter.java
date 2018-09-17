package com.adityathakker.egyaan.fragments.noticeboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fireion on 1/12/17.
 */

public class NoticeboardViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentArrayList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    public NoticeboardViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        stringList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
