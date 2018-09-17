package com.adityathakker.egyaan.interfaces;

import com.adityathakker.egyaan.models.GeneralModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 24/12/16.
 */

public interface APIs {
    @GET("login.php")
    Call<List<GeneralModel>> loginChecker(@Query("username") String email, @Query("password") String password);

    @GET("timetable.php")
    Call<List<GeneralModel>> timetableData(@Query("batch_id") String batchId, @Query("day_id") String day_id);

    @GET("getCourse.php")
    Call<List<GeneralModel>> courseData(@Query("user_id") String userId);

    @GET("notes.php")
    Call<List<GeneralModel>> notesData(@Query("course_id") String courseId);

    @GET("tests.php")
    Call<List<GeneralModel>> testsData(@Query("user_id") String userId);

    @GET("testDetails.php")
    Call<List<GeneralModel>> testDetailsData(@Query("test_id") String testId, @Query("user_id") String userId);

    @GET("noticeboard.php")
    Call<List<GeneralModel>> noticeData(@Query("email") String emailId);

    @GET("detailedNoticeboard.php")
    Call<List<GeneralModel>> detailedNoticeData(@Query("id") String noticeId);

    @GET("dashboardTest.php")
    Call<List<GeneralModel>> dashboardTestData(@Query("user_id") String userId);

    @GET("dashboardNotice.php")
    Call<List<GeneralModel>> dashboardNoticeboardData(@Query("branch_id") String branchId);
}
