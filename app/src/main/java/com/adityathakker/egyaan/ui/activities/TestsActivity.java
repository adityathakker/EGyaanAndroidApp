package com.adityathakker.egyaan.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.fragments.tests.TestsAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.TestsModel;
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

public class TestsActivity extends AppCompatActivity {

    private static final String TAG = TestsActivity.class.getSimpleName();
    RecyclerView recyclerViewTests;
    DatabaseHandler testsDatabaseHandler;
    SharedPreferences testsSharedPreferences;
    Details details;
    String userId;
    List<TestsModel> testsModelList;
    TestsAdapter testsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "onCreate: Toolbar Null data");
        }

        recyclerViewTests = findViewById(R.id.recycler_view_tests);
        recyclerViewTests.setLayoutManager(new LinearLayoutManager(this));

        testsDatabaseHandler = new DatabaseHandler(this);
        testsSharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = testsDatabaseHandler.getStudent(testsSharedPreferences.getString(AppConst.Extras.USERNAME, null));

        userId = details.getUserId();

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            if (testsDatabaseHandler.deleteTestsDataTable("0")) {
                getAndSetTests();
            } else {
                Log.e(TAG, "onCreate: Error while deleting tests data");
            }
        } else {
//            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
//            getAndSetTestsFromDatabase();
            if (!getAndSetTestsFromDatabase()) {
                setContentView(R.layout.custom_no_data_found);
            } else {
                Log.e(TAG, "onCreateView: Something from Tests Activity");
            }
        }
    }

    private boolean getAndSetTestsFromDatabase() {
        if (!testsDatabaseHandler.getTests(userId).toString().equals("[]")) {
            testsModelList = testsDatabaseHandler.getTests(userId);
            testsAdapter = new TestsAdapter(this, testsModelList);
            recyclerViewTests.setAdapter(testsAdapter);
            return true;
        } else {
            return false;
        }
    }

    private void getAndSetTests() {
        testsModelList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);
        Call<List<GeneralModel>> testDataCall = apIs.testsData(userId);
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
//                            Log.d(TAG, "onResponse: " + courseId + " " + courseName);

                            testDataModel.setTestId(testId);
                            testDataModel.setTitle(testTitle);
                            testDataModel.setDateOfTest(testDate);
                            testDataModel.setMarks(testMarks);
                            testDataModel.setTotalMarks(testTotalMarks);
                            testDataModel.setType(testType);

                            testsModelList.add(testDataModel);
                            addTestsToDatabase(testId, testTitle, testDate, testMarks, testTotalMarks, testType, userId);
                        }
                        testsAdapter = new TestsAdapter(TestsActivity.this, testsModelList);
                        recyclerViewTests.setAdapter(testsAdapter);

                    } else {
//                        Toast.makeText(TestsActivity.this, AppConst.Statuses.FAILED, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.custom_no_data_found);
                    }
                } else {
                    Toast.makeText(TestsActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(TestsActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTestsToDatabase(String testId, String testTitle, String testDate, String testMarks, String testTotalMarks, String testType, String userId) {

        boolean result = testsDatabaseHandler.insertTests(testId, testTitle, testDate, testMarks, testTotalMarks, testType, userId);

        if (!result) {
            Toast.makeText(this, "Error while inserting", Toast.LENGTH_SHORT).show();
        }
//        Log.d(TAG, "addTestsToDatabase: Inserted");

    }
}
