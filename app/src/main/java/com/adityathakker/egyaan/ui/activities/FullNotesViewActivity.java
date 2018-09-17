package com.adityathakker.egyaan.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.notes.NotesAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.NotesModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FullNotesViewActivity extends AppCompatActivity {

    private static final String TAG = FullNotesViewActivity.class.getSimpleName();
    RecyclerView recyclerViewFullViewNotes;
    List<NotesModel> notesModelsList;
    String courseId;
    NotesAdapter notesAdapter;
    SharedPreferences fullNotesSharedPreferences;
    DatabaseHandler fullNotesDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_notes_view);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "onCreate: Toolbar Null data");
        }

        recyclerViewFullViewNotes = findViewById(R.id.recycler_view_notes_full_view);
        recyclerViewFullViewNotes.setLayoutManager(new LinearLayoutManager(this));

        fullNotesDatabaseHandler = new DatabaseHandler(this);
        fullNotesSharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);

        Intent intentData = getIntent();
        courseId = intentData.getStringExtra("courseId");
//        Log.d(TAG, "onCreate: courseId " + courseId);

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            getAndSetNotes();
        } else {
//            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
            if (!getAndSetNotesFromDatabase()) {
                setContentView(R.layout.custom_no_data_found);
            } else {
                Log.e(TAG, "onCreate: Something in FullNotesView");
            }
        }
    }

    private boolean getAndSetNotesFromDatabase() {
        if (!fullNotesDatabaseHandler.getNotes(courseId).equals("[]")) {
            notesModelsList = fullNotesDatabaseHandler.getNotes(courseId);
            notesAdapter = new NotesAdapter(this, notesModelsList);
            recyclerViewFullViewNotes.setAdapter(notesAdapter);
            return true;
        } else {
            return false;
        }
    }

    private void getAndSetNotes() {
        notesModelsList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);
        final Call<List<GeneralModel>> notesDataCall = apIs.notesData(courseId);
        notesDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getNotes().size(); i++) {
                            NotesModel notesModel = new NotesModel();
                            String teacherName = models.get(0).getNotes().get(i).getName();
                            String notesTitle = models.get(0).getNotes().get(i).getTitle();
                            String notesFilePath = models.get(0).getNotes().get(i).getFile();
                            String notesFileSize = models.get(0).getNotes().get(i).getSize();
                            String notesFileExt = models.get(0).getNotes().get(i).getType();
                            String notesFilePages = models.get(0).getNotes().get(i).getPages();

//                            Log.d(TAG, "onResponse: path " + teacherName);

                            notesModel.setTitle(notesTitle);
                            notesModel.setFile(notesFilePath);
                            notesModel.setType(notesFileExt);
                            notesModel.setSize(notesFileSize);
                            notesModel.setPages(notesFilePages);
                            notesModel.setName(teacherName);

                            notesModelsList.add(notesModel);
                            addNotesToDatabase(notesTitle, notesFilePath, notesFileExt, notesFileSize,
                                    notesFilePages, teacherName, courseId);
                        }
                        notesAdapter = new NotesAdapter(FullNotesViewActivity.this, notesModelsList);
                        recyclerViewFullViewNotes.setAdapter(notesAdapter);

                    } else {
//                        Toast.makeText(FullNotesViewActivity.this, AppConst.Statuses.FAILED, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.custom_no_data_found);
                    }
                } else {
                    Toast.makeText(FullNotesViewActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(FullNotesViewActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNotesToDatabase(String notesTitle, String notesFilePath, String notesFileExt, String notesFileSize, String notesFilePages, String teacherName, String courseId) {

        boolean result = fullNotesDatabaseHandler.insertNotes(teacherName, notesTitle, notesFilePath, notesFileSize, notesFileExt, notesFilePages, courseId);

        if (!result) {
            Toast.makeText(this, "Error while inserting", Toast.LENGTH_SHORT).show();
        }
//        Log.d(TAG, "addNotesToDatabase: Inserted");
    }
}