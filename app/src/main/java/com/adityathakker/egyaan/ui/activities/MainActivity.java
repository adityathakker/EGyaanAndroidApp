package com.adityathakker.egyaan.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.HomeActivity;
import com.adityathakker.egyaan.fragments.SettingsActivity;
import com.adityathakker.egyaan.fragments.StudentInformationActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView navigation;
    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_user_profile:
                    toolbar.setTitle(R.string.title_activity_profile);
                    fragment = new StudentInformationActivity();
                    loadFragment(fragment);
                    return true;
                case R.id.action_home:
                    toolbar.setTitle(R.string.title_activity_home);
                    fragment = new HomeActivity();
                    loadFragment(fragment);
                    return true;
                case R.id.action_settings:
                    Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intentSettings);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.action_home);

        toolbar.setTitle(R.string.title_activity_home);
        loadFragment(new HomeActivity());

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutContainer, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}
