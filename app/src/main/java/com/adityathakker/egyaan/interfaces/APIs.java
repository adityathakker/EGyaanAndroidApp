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
}
