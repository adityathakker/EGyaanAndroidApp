package com.adityathakker.egyaan.interfaces;

import com.adityathakker.egyaan.models.GeneralModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 24/12/16.
 */

public interface APIs {
    @GET("login/login_checker.php")
    Call<GeneralModel> loginChecker(@Query("email") String email, @Query("password") String password);
}
