package com.adityathakker.egyaan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.TimetableData;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fireion on 28/6/17.
 */

public class TimetableDayOne extends Fragment {

    private static final String TAG = TimetableDayOne.class.getSimpleName();
    DatabaseHandler databaseHandler;
    Details details;
    List<TimetableData> timetableDataList;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String batch_id, day_id;
    TimetableAdapter timetableAdapter;

    public TimetableDayOne() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewDayOne = inflater.inflate(R.layout.content_timetable_day_common, container, false);
        recyclerView = (RecyclerView) viewDayOne.findViewById(R.id.recycler_view_time_table_day_common);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHandler = new DatabaseHandler(getContext());
        sharedPreferences = getContext().getSharedPreferences(AppConst.Extras.PROJ_NAME, Context.MODE_PRIVATE);
        details = databaseHandler.getStudent(sharedPreferences.getString(AppConst.Extras.USERNAME, null));
        batch_id = details.getBatchId();
//        Log.d(TAG, "onCreateView: " + batch_id);
        day_id = String.valueOf(1);

        //Check if we are here for first time or not
        if (CommonTasks.isDataOn(getContext())) {
            //Get Online
            getAndSetTimetableData();
        } else {
//            Toast.makeText(getContext(), "You are Offline", Toast.LENGTH_SHORT).show();
            //Get Offline
            getAndSetTimetableDataFromDatabase();
        }

        return viewDayOne;
    }

    private void getAndSetTimetableDataFromDatabase() {
        timetableDataList = databaseHandler.getTimetable(day_id);
        timetableAdapter = new TimetableAdapter(getContext(), timetableDataList);
        recyclerView.setAdapter(timetableAdapter);
    }

    private void getAndSetTimetableData() {
        timetableDataList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apis = retrofit.create(APIs.class);
        Call<List<GeneralModel>> timetableDataCall = apis.timetableData(batch_id, day_id);
        timetableDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getTimetableDataList().size(); i++) {
                            TimetableData timetableData = new TimetableData();
                            String dayId = models.get(0).getTimetableDataList().get(i).getDayId();
                            String time = models.get(0).getTimetableDataList().get(i).getTime();
                            String teacherName = models.get(0).getTimetableDataList().get(i).getTeacher();
                            String courseName = models.get(0).getTimetableDataList().get(i).getCourse();
                            String comment = models.get(0).getTimetableDataList().get(i).getComment();
                            timetableData.setDayId(dayId);
                            timetableData.setTime(time);
                            timetableData.setTeacher(teacherName);
                            timetableData.setCourse(courseName);
                            timetableData.setComment(comment);
                            timetableDataList.add(timetableData);
                            addTimetableToDatabase(dayId, time, teacherName, courseName, comment);
                        }
                        setSharedPreferences();
                        timetableAdapter = new TimetableAdapter(getContext(), timetableDataList);
                        recyclerView.setAdapter(timetableAdapter);
                    } else {
                        Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    public void setSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConst.Extras.IS_OPENED_TIMETABLE_FIRST_TIME_DAY_1, true);
        editor.apply();
    }

    public void addTimetableToDatabase(String dayId, String time, String teacherName, String courseName,
                                       String comment) {
        if (sharedPreferences.getBoolean(AppConst.Extras.IS_OPENED_TIMETABLE_FIRST_TIME_DAY_1, false)) {
            Log.d(TAG, "addTimetableToDatabase: False Statement");
        } else {
            boolean result = databaseHandler.insertTimetable(dayId, time, teacherName, courseName, comment);

            if (!result) {
                Toast.makeText(getContext(), "Error while inserting", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "addTimetableToDatabase: Inserted");
        }
    }
}
