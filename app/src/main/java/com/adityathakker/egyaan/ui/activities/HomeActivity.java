package com.adityathakker.egyaan.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.DatabaseHandler;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    DatabaseHandler databaseHandler;
    Details details;

    @BindView(R.id.toolbar_timetable_activity)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;


    @BindView(R.id.activity_timeTable)
    Button timetable;
    @BindView(R.id.activity_notes)
    Button notes;
    @BindView(R.id.activity_noticeboard)
    Button noticeboard;
    @BindView(R.id.activity_syllabus)
    Button syllabus;
    @BindView(R.id.activity_test)
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (!CommonTasks.isDataOn(this)) {
            CommonTasks.showMessage(toolbar);
        }

        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = databaseHandler.getStudent(sharedPreferences.getString(AppConst.Extras.USERNAME, null));

        setUpNavigationDrawer();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View headerView = navigationView.getHeaderView(0);

        ImageView navImageView = (ImageView) headerView.findViewById(R.id.nav_header_imageView);
        TextView navTextViewStudentName = (TextView) headerView.findViewById(R.id.nav_header_textView_student_name);
        TextView navTextViewStudentEmail = (TextView) headerView.findViewById(R.id.nav_header_textView_email);

        navTextViewStudentEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMyInformation = new Intent(HomeActivity.this, StudentInformationActivity.class);
                startActivity(intentMyInformation);
            }
        });

        Glide.with(this).load(AppConst.URLs.IMAGE_URL + "" + details.getStudentProfilePhoto()).into(navImageView);
        navTextViewStudentName.setText(details.getFirstname() + " " + details.getLastname());
        navTextViewStudentEmail.setText(sharedPreferences.getString(AppConst.Extras.USERNAME, null));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.list_home:
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.list_settings:
                        Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
    }

    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.activity_timeTable)
    public void timeTable(View view) {
        Intent intentTimetable = new Intent(this, TimetableActivity.class);
        startActivity(intentTimetable);
    }

    @OnClick(R.id.activity_notes)
    public void notes(View view) {

    }

    @OnClick(R.id.activity_noticeboard)
    public void noticeboard(View view) {

    }

    @OnClick(R.id.activity_syllabus)
    public void syllabus(View view) {

    }

    @OnClick(R.id.activity_test)
    public void test(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sign_out) {
            if (databaseHandler.deleteDatabaseTable()) {
                SharedPreferences.Editor editor = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(this, AppConst.Messages.SUCCESSFULL_LOG_OUT, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                Toast.makeText(this, AppConst.Messages.ERROR_SUCCESSFULL_LOG_OUT, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        drawerLayout.closeDrawer(Gravity.LEFT, false);
//    }
}
