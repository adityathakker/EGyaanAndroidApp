package com.adityathakker.egyaan.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.timetable.MainTimetableAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.NoticeboardModel;
import com.adityathakker.egyaan.models.TestsModel;
import com.adityathakker.egyaan.models.TimetableData;
import com.adityathakker.egyaan.ui.activities.NotesActivity;
import com.adityathakker.egyaan.ui.activities.NoticeboardActivity;
import com.adityathakker.egyaan.ui.activities.TestsActivity;
import com.adityathakker.egyaan.ui.activities.TimetableActivity;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;
import com.adityathakker.egyaan.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class HomeActivity extends Fragment {

    private static final String TAG = HomeActivity.class.getSimpleName();
    DatabaseHandler databaseHandlerHome;
    Details details;
    List<TimetableData> timetableDataList;
    List<TestsModel> testsModelList;
    List<NoticeboardModel> noticeboardModelList;
    SharedPreferences sharedPreferencesHome;
    String batch_id, day_id, userId;
    MainTimetableAdapter mainTimetableAdapter;

    @BindView(R.id.recycler_view_home_timetable)
    RecyclerView recyclerViewTimetable;
    @BindView(R.id.textView_timetable_day_home)
    TextView textViewTimetableDayHome;
    @BindView(R.id.testTitleHome)
    TextView textViewTestTitleHome;
    @BindView(R.id.testDateHome)
    TextView textViewTestDateHome;
    @BindView(R.id.testMarksHome)
    TextView textViewTestMarksHome;
    @BindView(R.id.testTotalMarksHome)
    TextView textViewTestTotalMarksHome;
    @BindView(R.id.testAttendanceHome)
    TextView textViewTestAttendanceHome;
    @BindView(R.id.textViewNoticeTitleMain)
    TextView textViewNoticeTitle;
    @BindView(R.id.textViewNoticeDateMain)
    TextView textViewNoticeDate;
    @BindView(R.id.textViewNoticeTimeMain)
    TextView textViewNoticeTime;
    @BindView(R.id.textViewNoticeNoData)
    TextView textViewNoticeNoData;
    @BindView(R.id.linearLayoutHomeNoticeboardDT)
    LinearLayout linearLayoutHideNoticeboard;

    public HomeActivity() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewHome = inflater.inflate(R.layout.activity_home, container, false);
        ButterKnife.bind(this, viewHome);

        recyclerViewTimetable.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        databaseHandlerHome = new DatabaseHandler(getActivity());
        sharedPreferencesHome = getActivity().getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = databaseHandlerHome.getStudent(sharedPreferencesHome.getString(AppConst.Extras.USERNAME, null));

        userId = details.getUserId();
        batch_id = details.getBatchId();
        day_id = String.valueOf(getCurrentDate() + 1);

        if (CommonTasks.isDataOn(getActivity()).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(getActivity()).equals(AppConst.Extras.MOBILE)) {
            //Get Online
            Timetable timetableOnline = new Timetable();
            if (databaseHandlerHome.deleteTimetableDataTable(day_id, "-2")) {
//                Log.d(TAG, "onCreateView: Deleted " + day_id);
                timetableOnline.getAndSetTimetableData();
            } else {
                Log.e(TAG, "onCreateView: Error while deleting timetable home data of " + day_id);
            }

            Tests testsOnline = new Tests();
            if (databaseHandlerHome.deleteTestsDataTable("-2")) {
                testsOnline.getTestsData();
            } else {
                Log.e(TAG, "onCreateView: Error while deleting tests home data");
            }

            Noticeboard noticeboardOnline = new Noticeboard();
            noticeboardOnline.getNoticeboardData();

        } else {
            //Get Offline
//            Toast.makeText(getActivity(), "You are Offline", Toast.LENGTH_SHORT).show();
            Timetable timetableOffline = new Timetable();
            timetableOffline.getAndSetTimetableDataFromHomeDatabase(day_id);

            Tests testsOffline = new Tests();
            testsOffline.getAndSetTestsFromHomeDatabase(userId);

            Noticeboard noticeboardOffline = new Noticeboard();
            noticeboardOffline.getAndSetNoticeboardDataFromHomeDatabase();
        }

        return viewHome;
    }

    @OnClick(R.id.activity_timeTable)
    public void linearLayoutTimetable(final View view) {
        Intent intentTimetable = new Intent(getActivity(), TimetableActivity.class);
        startActivity(intentTimetable);
    }

    @OnClick(R.id.activity_noticeboard)
    public void linearLayoutNoticeboard(final View view) {
        if (CommonTasks.isDataOn(getActivity()).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(getActivity()).equals(AppConst.Extras.MOBILE)) {
            Intent intentNotice = new Intent(getActivity(), NoticeboardActivity.class);
            startActivity(intentNotice);
        } else {
            CommonTasks.showMessage(view, AppConst.Messages.NO_INTERNET);
        }
    }

    @OnClick(R.id.activity_tests)
    public void linearLayoutTests(final View view) {
        Intent intentTests = new Intent(getActivity(), TestsActivity.class);
        startActivity(intentTests);
    }

    @OnClick(R.id.activity_notes)
    public void linearLayoutNotes(final View view) {
        Intent intentNotes = new Intent(getActivity(), NotesActivity.class);
        startActivity(intentNotes);
    }

    private int getCurrentDate() {
        String day;
        Calendar c = Calendar.getInstance();

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_1 + ")";
            textViewTimetableDayHome.setText(day);
            return 0;
        } else if (Calendar.TUESDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_2 + ")";
            textViewTimetableDayHome.setText(day);
            return 1;
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_3 + ")";
            textViewTimetableDayHome.setText(day);
            return 2;
        } else if (Calendar.THURSDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_4 + ")";
            textViewTimetableDayHome.setText(day);
            return 3;
        } else if (Calendar.FRIDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_5 + ")";
            textViewTimetableDayHome.setText(day);
            return 4;
        } else if (Calendar.SATURDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_6 + ")";
            textViewTimetableDayHome.setText(day);
            return 5;
        } else if (Calendar.SUNDAY == dayOfWeek) {
            day = "(" + AppConst.Extras.DAY_7 + ")";
            textViewTimetableDayHome.setText(day);
            return 6;
        } else {
            Log.e(TAG, "getCurrentDate: Something went wrong");
        }
        return -1;
    }

    private class Timetable {
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
                                String courseName = models.get(0).getTimetableDataList().get(i).getCourse();
                                String time = models.get(0).getTimetableDataList().get(i).getTime();

                                timetableData.setCourse(courseName);
                                timetableData.setTime(time);

                                timetableDataList.add(timetableData);
                                addTimetableToHomeDatabase(courseName, time, day_id);
                            }
                            mainTimetableAdapter = new MainTimetableAdapter(getActivity(), timetableDataList);
                            recyclerViewTimetable.setAdapter(mainTimetableAdapter);
                        } else {
                            Toast.makeText(getActivity(), AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    call.cancel();
//                    getAndSetTimetableDataFromHomeDatabase(day_id);
                    //Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void addTimetableToHomeDatabase(String courseName, String time, String day_id) {
            boolean result = databaseHandlerHome.insertTimetableHome(day_id, time, courseName);

            if (!result) {
                Toast.makeText(getActivity(), "Error while inserting timetable", Toast.LENGTH_SHORT).show();
            }
            //Log.d(TAG, "addTimetableToHomeDatabase: Inserted");
        }

        private void getAndSetTimetableDataFromHomeDatabase(String dayId) {
            timetableDataList = databaseHandlerHome.getTimetableHome(dayId);
            mainTimetableAdapter = new MainTimetableAdapter(getActivity(), timetableDataList);
            recyclerViewTimetable.setAdapter(mainTimetableAdapter);
        }
    }

    private class Noticeboard {
        private void getNoticeboardData() {
            noticeboardModelList = new ArrayList<>();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);
            Call<List<GeneralModel>> testDataCall = apIs.dashboardNoticeboardData(batch_id);
            testDataCall.enqueue(new Callback<List<GeneralModel>>() {
                @Override
                public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                    if (response.isSuccessful()) {
                        List<GeneralModel> models = response.body();
                        String status = models.get(0).getStatus();
                        if (status.equals(AppConst.Statuses.SUCCESS)) {
                            for (int i = 0; i < models.get(0).getNoticeboard().size(); i++) {
                                NoticeboardModel noticeboardModel = new NoticeboardModel();
                                String noticeId = models.get(0).getNoticeboard().get(i).getId();
                                String noticeTitle = models.get(0).getNoticeboard().get(i).getTitle();
                                String noticeDate = models.get(0).getNoticeboard().get(i).getDate();
                                String noticeTime = models.get(0).getNoticeboard().get(i).getTime();
                                String noticeType = models.get(0).getNoticeboard().get(i).getType();

                                noticeboardModel.setId(noticeId);
                                noticeboardModel.setTitle(noticeTitle);
                                noticeboardModel.setDate(noticeDate);
                                noticeboardModel.setTime(noticeTime);
                                noticeboardModel.setType(noticeType);

                                noticeboardModelList.add(noticeboardModel);
                            }
                            setNoticeboardData(noticeboardModelList);
                        } else {
                            Toast.makeText(getActivity(), AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    call.cancel();
//                    getAndSetNoticeboardDataFromHomeDatabase();
                    //Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setNoticeboardData(List<NoticeboardModel> noticeboardModelList) {
            NoticeboardModel noticeboardModel = noticeboardModelList.get(noticeboardModelList.size() - 1);

            textViewNoticeTitle.setText(noticeboardModel.getTitle());
            textViewNoticeDate.setText(noticeboardModel.getDate());
            textViewNoticeTime.setText(noticeboardModel.getTime());
        }

        private void getAndSetNoticeboardDataFromHomeDatabase() {
            textViewNoticeTitle.setVisibility(View.GONE);
            linearLayoutHideNoticeboard.setVisibility(View.GONE);
            textViewNoticeNoData.setVisibility(View.VISIBLE);
        }
    }

    private class Tests {

        private void getTestsData() {
            testsModelList = new ArrayList<>();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apIs = retrofit.create(APIs.class);
            Call<List<GeneralModel>> testDataCall = apIs.dashboardTestData(userId);
            testDataCall.enqueue(new Callback<List<GeneralModel>>() {
                @Override
                public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                    if (response.isSuccessful()) {
                        List<GeneralModel> models = response.body();
                        String status = models.get(0).getStatus();
                        if (status.equals(AppConst.Statuses.SUCCESS)) {
                            for (int i = 0; i < models.get(0).getTests().size(); i++) {
                                TestsModel testDataModel = new TestsModel();
                                String testId = models.get(0).getTests().get(i).getTestId();
                                String testTitle = models.get(0).getTests().get(i).getTitle();
                                String testDate = models.get(0).getTests().get(i).getDateOfTest();
                                String testMarks = models.get(0).getTests().get(i).getMarks();
                                String testTotalMarks = models.get(0).getTests().get(i).getTotalMarks();
                                String testType = models.get(0).getTests().get(i).getType();

                                testDataModel.setTestId(testId);
                                testDataModel.setTitle(testTitle);
                                testDataModel.setDateOfTest(testDate);
                                testDataModel.setMarks(testMarks);
                                testDataModel.setTotalMarks(testTotalMarks);
                                testDataModel.setType(testType);

                                testsModelList.add(testDataModel);
                                addTestsToHomeDatabase(testId, testTitle, testDate, testMarks, testTotalMarks, testType, userId);
                            }
                            setTestData(testsModelList);

                        } else {
                            Toast.makeText(getActivity(), AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    call.cancel();
//                    getAndSetTestsFromHomeDatabase(userId);
                    //Toast.makeText(getActivity(), AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void addTestsToHomeDatabase(String testId, String testTitle, String testDate, String testMarks, String testTotalMarks, String testType, String userId) {
            boolean result = databaseHandlerHome.insertTestsHome(testId, testTitle, testDate, testMarks, testTotalMarks, testType, userId);

            if (!result) {
                Toast.makeText(getActivity(), "Error while inserting tests", Toast.LENGTH_SHORT).show();
            }
            //Log.d(TAG, "addTestsToHomeDatabase: Inserted");
        }

        private void getAndSetTestsFromHomeDatabase(String userId) {
            testsModelList = databaseHandlerHome.getTestsHome(userId);
            setTestData(testsModelList);
        }

        private void setTestData(List<TestsModel> testsModelList) {
            TestsModel testsModel = testsModelList.get(testsModelList.size() - 1);

            textViewTestTitleHome.setText(testsModel.getTitle());
            textViewTestDateHome.setText(testsModel.getDateOfTest());

            if (testsModel.getMarks().equals("-")) {
                textViewTestMarksHome.setVisibility(View.GONE);
                textViewTestTotalMarksHome.setVisibility(View.GONE);
                textViewTestAttendanceHome.setVisibility(View.VISIBLE);
                textViewTestAttendanceHome.setText("Absent");
                textViewTestAttendanceHome.setTextColor(Color.RED);
            } else {
                textViewTestMarksHome.setText(testsModel.getMarks());
                textViewTestTotalMarksHome.setText("/" + testsModel.getTotalMarks());
            }
        }

    }
}
