package com.adityathakker.egyaan.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.notes.CourseAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.CourseDataModel;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.DatabaseHandler;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fireion on 16/11/17.
 */

public class NotesActivity extends AppCompatActivity {

    private static final String TAG = NotesActivity.class.getSimpleName();
    DatabaseHandler notesDatabaseHandler;
    Details details;
    RecyclerView recyclerViewNotes;
    List<CourseDataModel> courseDataModels;
    String userId;
    CourseAdapter courseAdapter;
    File fileStorage = null;
    SharedPreferences notesSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "onCreate: Toolbar Null data");
        }

        recyclerViewNotes = findViewById(R.id.recycler_view_notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));

        notesDatabaseHandler = new DatabaseHandler(this);
        notesSharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = notesDatabaseHandler.getStudent(notesSharedPreferences.getString(AppConst.Extras.USERNAME, null));

        userId = details.getUserId();
//        Log.d(TAG, "onCreate: " + userId);

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            if (notesDatabaseHandler.deleteCoursesDataTable() && notesDatabaseHandler.deleteNotesDataTable()) {
                getAndSetCourse();
            } else {
                Log.e(TAG, "onCreate: Error while deleting notes and courses table");
            }
        } else {
//            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
//            getAndSetCourseFromDatabase();
            if (!getAndSetCourseFromDatabase()) {
                setContentView(R.layout.custom_no_data_found);
            } else {
                Log.e(TAG, "onCreateView: Something in Notes Activity");
            }
        }
        createDirectoryForNotes();
    }

    private boolean getAndSetCourseFromDatabase() {
        if (!notesDatabaseHandler.getCourses(userId).equals("[]")) {
            courseDataModels = notesDatabaseHandler.getCourses(userId);
            courseAdapter = new CourseAdapter(this, courseDataModels);
            recyclerViewNotes.setAdapter(courseAdapter);
            return true;
        } else {
            return false;
        }
    }

    private void createDirectoryForNotes() {
        if (new CommonTasks.CheckForSDCard().isSDCardPresent()) {
            fileStorage = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                            + AppConst.Extras.DOWNLOAD_DIRECTORY + "/" + AppConst.Extras.DOWNLOAD_NOTES_FILES_DIRECTORY);
        } else
            Toast.makeText(NotesActivity.this, AppConst.Messages.ERROR_NO_SD_CARD, Toast.LENGTH_SHORT).show();

        //If File is not present create directory
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (Build.VERSION.SDK_INT >= 23) {
                Dexter.withActivity(this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
//                                Log.d(TAG, "onPermissionGranted: " + response);
                                if (!fileStorage.exists()) {
                                    if (fileStorage.mkdirs()) {
                                        Log.e(TAG, "Directory Created.");
                                    } else {
                                        Log.e(TAG, "Directory Not Created.");
                                    }
                                } else {
                                    Log.e(TAG, "NotesActivity: Directory already");
                                }
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
//                                Log.d(TAG, "onPermissionGranted: " + response);
                                if (response.isPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            } else { //permission is automatically granted on sdk<23 upon installation
//                Log.d(TAG, "Permission is granted 2");
                if (fileStorage.mkdirs()) {
                    Log.e(TAG, "Directory Created.");
                } else {
                    Log.e(TAG, "Directory Not Created.");
                }
            }
        } else {
            Log.d(TAG, "Permission is granted 1");
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void getAndSetCourse() {
        courseDataModels = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);
        Call<List<GeneralModel>> courseDataCall = apIs.courseData(userId);
        courseDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getCourseDataModels().size(); i++) {
                            CourseDataModel courseDataModel = new CourseDataModel();
                            String courseId = models.get(0).getCourseDataModels().get(i).getId();
                            String courseName = models.get(0).getCourseDataModels().get(i).getName();

                            courseDataModel.setId(courseId);
                            courseDataModel.setName(courseName);

                            courseDataModels.add(courseDataModel);
                            addCoursesToDatabase(courseId, courseName, userId);
                        }
                        courseAdapter = new CourseAdapter(NotesActivity.this, courseDataModels);
                        recyclerViewNotes.setAdapter(courseAdapter);

                    } else {
//                        Toast.makeText(NotesActivity.this, AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.custom_no_data_found);
                    }
                } else {
                    Toast.makeText(NotesActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(NotesActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCoursesToDatabase(String courseId, String courseName, String userId) {

        boolean result = notesDatabaseHandler.insertCourses(courseId, courseName, userId);

        if (!result) {
            Toast.makeText(this, "Error while inserting", Toast.LENGTH_SHORT).show();
        }
//        Log.d(TAG, "addCoursesToDatabase: Inserted");
    }
}