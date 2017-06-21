package com.adityathakker.egyaan.ui.activities;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.interfaces.APIs;
import com.adityathakker.egyaan.models.GeneralModel;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.activity_login_imageview_strips)
    ImageView stripsBackground;
    @BindView(R.id.activity_login_imageview_egyaan_logo)
    ImageView egyaanLogo;

    @BindView(R.id.activity_login_textview_support_login)
    TextView supportLogin;
    @BindView(R.id.activity_login_textview_support_welcome)
    TextView supportWelcome;
    @BindView(R.id.activity_login_edittext_email_address)
    EditText emailEditText;
    @BindView(R.id.activity_login_edittext_password)
    EditText passwordEditText;
    @BindView(R.id.activity_login_button_login)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialAnimateViews();
    }

    private void initialAnimateViews() {
        Animation slideUpBackground  = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_slide_up);
        Animation slideUpLogin  = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_slide_down);

        slideUpBackground.setInterpolator(new DecelerateInterpolator());
        slideUpBackground.setDuration(300);
        slideUpBackground.setStartOffset(150);
        stripsBackground.startAnimation(slideUpBackground);

        slideDown.setInterpolator(new DecelerateInterpolator());
        slideDown.setDuration(300);
        slideDown.setStartOffset(250);
        egyaanLogo.startAnimation(slideDown);

        slideUpLogin.setDuration(200);
        slideUpLogin.setInterpolator(new DecelerateInterpolator());
        slideUpLogin.setStartOffset(200);
        supportLogin.startAnimation(slideUpLogin);
        slideUpLogin.setStartOffset(250);
        emailEditText.startAnimation(slideUpLogin);
        slideUpLogin.setStartOffset(300);
        passwordEditText.startAnimation(slideUpLogin);
        slideUpLogin.setStartOffset(350);
        loginButton.startAnimation(slideUpLogin);
    }

    @OnClick(R.id.activity_login_button_login)
    public void validateAndLogin(View view){
        String emailString = emailEditText.getText().toString();
        if(emailString == null || emailString.equals("")){
            Toast.makeText(this, "Email Address Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(500);
            return;
        }

        String passwordString = passwordEditText.getText().toString();
        if(passwordString == null || passwordString.equals("")){
            Toast.makeText(this, "Password Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(500);
            return;
        }
        if(CommonTasks.isDataOn(this)){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apis = retrofit.create(APIs.class);
            Call<GeneralModel> loginCheckerCall = apis.loginChecker(emailString, passwordString);
            loginCheckerCall.enqueue(new Callback<GeneralModel>() {
                @Override
                public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                    GeneralModel generalModel = response.body();
                    if(generalModel.getStatus().equals(AppConst.Statuses.SUCCESS)){
                        //redirect
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, generalModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralModel> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }



    }
}
