package com.androidsphere.aditya.egyaan.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.androidsphere.aditya.egyaan.AppConstants;
import com.androidsphere.aditya.egyaan.MyApplication;
import com.androidsphere.aditya.egyaan.R;
import com.androidsphere.aditya.egyaan.net.TimeTableFetcher;

public class StudentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(AppConstants.SharedPrefs.FIRST_TIME_LOGIN_STUDENT, true)) {
            new TimeTableFetcher(this, sharedPreferences.getString(AppConstants.SharedPrefs.BRANCH, null)).execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*TextView name,email;
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_student);
        name = (TextView) headerView.findViewById(R.id.name_header_student);
        email = (TextView) headerView.findViewById(R.id.email_header_student);
        name.setText(sharedPreferences.getString(AppConstants.SharedPrefs.FNAME, "John") + " " + sharedPreferences.getString(AppConstants.SharedPrefs.LNAME, "Doe"));
        email.setText(sharedPreferences.getString(AppConstants.SharedPrefs.EMAIL, "johndoe@gmail.com"));*/

        /*Menu navMenu = navigationView.getMenu();
        navMenu.getItem(R.id.nav_dashboard_student).setChecked(true);*/


        Button timetable = (Button) findViewById(R.id.timetable_button_student);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().trackEvent("Action", "Click", "Timetable button");
                Intent intent = new Intent(StudentActivity.this, TimeTableActivity.class);
                startActivity(intent);
            }
        });

        Button noticeBoard = (Button) findViewById(R.id.notice_board_button_student);
        noticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().trackEvent("Action", "Click", "Notice Board button");
                Intent intent = new Intent(StudentActivity.this, NoticeBoardActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard_student) {
            // Handle the camera action

        } else if (id == R.id.nav_settings_student) {
            Toast.makeText(this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout_student) {
            SharedPreferences.Editor editor = getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, MODE_PRIVATE).edit();
            editor.putBoolean(AppConstants.SharedPrefs.ALREADY_LOGGED_IN, false);
            editor.putString(AppConstants.SharedPrefs.ACCOUNT_TYPE, "-1");
            editor.putString(AppConstants.SharedPrefs.UID, "-1");
            editor.putString(AppConstants.SharedPrefs.FNAME, "-1");
            editor.putString(AppConstants.SharedPrefs.LNAME, "-1");
            editor.putString(AppConstants.SharedPrefs.EMAIL, "-1");
            editor.putString(AppConstants.SharedPrefs.PASSWORD, "-1");
            editor.putString(AppConstants.SharedPrefs.BRANCH, "-1");
            editor.commit();

            Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
