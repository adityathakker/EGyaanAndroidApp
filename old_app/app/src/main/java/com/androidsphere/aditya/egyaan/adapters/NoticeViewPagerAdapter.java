package com.androidsphere.aditya.egyaan.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.androidsphere.aditya.egyaan.ui.UrgentNoticeFragment;
import com.androidsphere.aditya.egyaan.ui.NormalNoticeFragment;

/**
 * Created by aditya9172 on 19/1/16.
 */
public class NoticeViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public NoticeViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NormalNoticeFragment tab1 = new NormalNoticeFragment();
                return tab1;
            case 1:
                UrgentNoticeFragment tab2 = new UrgentNoticeFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
