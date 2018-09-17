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
import com.adityathakker.egyaan.fragments.tests.TestsDetailsAdapter;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.models.TestsDetailsModel;
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

public class TestsDetailsActivity extends AppCompatActivity {

    private static final String TAG = TestsActivity.class.getSimpleName();
    RecyclerView recyclerViewTestsDetails;
    DatabaseHandler testsDetailsDatabaseHandler;
    SharedPreferences testsDetailsSharedPreferences;
    Details details;
    String userId, testId;
    List<TestsDetailsModel> testsDetailsModelList;
    TestsDetailsAdapter testsDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "onCreate: Toolbar Null data");
        }

        recyclerViewTestsDetails = findViewById(R.id.recycler_view_tests_details);
        recyclerViewTestsDetails.setLayoutManager(new LinearLayoutManager(this));

        testsDetailsDatabaseHandler = new DatabaseHandler(this);
        testsDetailsSharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = testsDetailsDatabaseHandler.getStudent(testsDetailsSharedPreferences.getString(AppConst.Extras.USERNAME, null));

        userId = details.getUserId();
        testId = getIntent().getStringExtra("testId");
//        Log.d(TAG, "onCreate: Id " + userId + "," + testId);

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            getAndSetTestsDetails();
        } else {
            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }


    private void getAndSetTestsDetails() {
        testsDetailsModelList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.URLs.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIs apIs = retrofit.create(APIs.class);
        Call<List<GeneralModel>> testDetailsDataCall = apIs.testDetailsData(testId, userId);
        testDetailsDataCall.enqueue(new Callback<List<GeneralModel>>() {
            @Override
            public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                if (response.isSuccessful()) {
                    List<GeneralModel> models = response.body();
                    String status = models.get(0).getStatus();
                    if (status.equals(AppConst.Statuses.SUCCESS)) {
                        for (int i = 0; i < models.get(0).getTestDetails().size(); i++) {
                            TestsDetailsModel testsDetailsModel = new TestsDetailsModel();
                            String testDetailsQuestion = models.get(0).getTestDetails().get(i).getQuestion();
                            String testDetailsAnswer = models.get(0).getTestDetails().get(i).getAnswer();
                            String testDetailsCorrectAnswer = models.get(0).getTestDetails().get(i).getCorrectAns();
                            String testDetailsAnswerText = models.get(0).getTestDetails().get(i).getAnswerText();
                            String testDetailsCorrectAnswerText = models.get(0).getTestDetails().get(i).getCorrectAnsText();

                            testsDetailsModel.setQuestion(testDetailsQuestion);
                            testsDetailsModel.setAnswer(testDetailsAnswer);
                            testsDetailsModel.setCorrectAns(testDetailsCorrectAnswer);
                            testsDetailsModel.setAnswerText(testDetailsAnswerText);
                            testsDetailsModel.setCorrectAnsText(testDetailsCorrectAnswerText);

                            testsDetailsModelList.add(testsDetailsModel);
                        }
                        testsDetailsAdapter = new TestsDetailsAdapter(TestsDetailsActivity.this, testsDetailsModelList);
                        recyclerViewTestsDetails.setAdapter(testsDetailsAdapter);

                    } else {
//                        Toast.makeText(TestsDetailsActivity.this, AppConst.Statuses.FAILED, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.custom_no_data_found);
                    }
                } else {
                    Toast.makeText(TestsDetailsActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(TestsDetailsActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
