package com.adityathakker.egyaan.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.adityathakker.egyaan.utils.DatabaseHandler;
import com.adityathakker.egyaan.utils.ProgressBar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

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
    DatabaseHandler databaseHandler;
    ProgressBar progressBarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBarHome = new ProgressBar();

        SharedPreferences sharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(AppConst.Extras.IS_OPENED_FIRST_TIME, false)) {
            //First Time
            initialAnimateViews();
        } else {
            //Not First Time
            SharedPreferences preferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
            String userName = preferences.getString(AppConst.Extras.USERNAME, null);
            String password = preferences.getString(AppConst.Extras.PASSWORD, null);

            if (userName != null && password != null && !userName.equals("") && !password.equals("")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, AppConst.Messages.WRONG_CREDENTIALS, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initialAnimateViews() {
        Animation slideUpBackground = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_slide_up);
        Animation slideUpLogin = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_slide_up);
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
    public void validateAndLogin(final View view) {
        progressBarHome.showProgressBar(this);

        final String emailString = emailEditText.getText().toString();
        if (emailString == null || emailString.equals("")) {
            Toast.makeText(this, "Email Address Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
            progressBarHome.dismissProgressBar();
            return;
        }

        final String passwordString = passwordEditText.getText().toString();
        if (passwordString == null || passwordString.equals("")) {
            Toast.makeText(this, "Password Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
            progressBarHome.dismissProgressBar();
            return;
        }

        if (CommonTasks.isDataOn(this).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(this).equals(AppConst.Extras.MOBILE)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.URLs.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIs apis = retrofit.create(APIs.class);
            Call<List<GeneralModel>> loginCheckerCall = apis.loginChecker(emailString, passwordString);
            loginCheckerCall.enqueue(new Callback<List<GeneralModel>>() {
                @Override
                public void onResponse(Call<List<GeneralModel>> call, Response<List<GeneralModel>> response) {
                    if (response.isSuccessful()) {
                        progressBarHome.dismissProgressBar();
                        List<GeneralModel> models = response.body();
//                        Log.d(TAG, "onResponse: " + models);
                        String status = null;
                        Integer roleId = null;
                        String userId = null, firstName = null, lastName = null, email = null, studentPasswd = null,
                                gender = null, mobile = null, studentProfilePhoto = null, parentProfilePhoto = null,
                                batchId = null, branchId = null, parentName = null, parentEmail = null,
                                parentPasswd = null, parentMobile = null;
                        for (int i = 0; i < models.size(); i++) {
                            status = models.get(i).getStatus();
                        }
                        if (status.equals(AppConst.Statuses.SUCCESS)) {
                            databaseHandler = new DatabaseHandler(getApplicationContext());
                            for (int i = 0; i < models.size(); i++) {
                                roleId = models.get(i).getDetails().getRoleId();
                                userId = models.get(i).getDetails().getUserId();
                                firstName = models.get(i).getDetails().getFirstname();
                                lastName = models.get(i).getDetails().getLastname();
                                email = models.get(i).getDetails().getEmail();
                                studentPasswd = models.get(i).getDetails().getStudentPasswd();
                                gender = models.get(i).getDetails().getGender();
                                mobile = models.get(i).getDetails().getMobile();
                                studentProfilePhoto = models.get(i).getDetails().getStudentProfilePhoto();
                                parentProfilePhoto = models.get(i).getDetails().getParentProfilePhoto();
                                batchId = models.get(i).getDetails().getBatchId();
                                branchId = models.get(i).getDetails().getBranchId();
                                parentName = models.get(i).getDetails().getParentName();
                                parentEmail = models.get(i).getDetails().getParentEmail();
                                parentPasswd = models.get(i).getDetails().getParentPasswd();
                                parentMobile = models.get(i).getDetails().getParentMobile();
                            }
                            if (roleId != null && userId != null && firstName != null && lastName != null
                                    && email != null && studentPasswd != null && gender != null && mobile != null
                                    && studentProfilePhoto != null && parentProfilePhoto != null && batchId != null
                                    && branchId != null && parentName != null && parentEmail != null
                                    && parentPasswd != null && parentMobile != null) {
                                Toast.makeText(LoginActivity.this, "Welcome " + firstName, Toast.LENGTH_SHORT).show();
                                setSharedPreferences(emailString, passwordString, firstName);
                                addStudentDetailsToDatabase(roleId, userId, firstName, lastName, email,
                                        studentPasswd, gender, mobile, studentProfilePhoto, parentProfilePhoto,
                                        batchId, branchId, parentName, parentEmail, parentPasswd, parentMobile);
                                initiateFirebaseProcess();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBarHome.dismissProgressBar();
//                                Toast.makeText(LoginActivity.this, AppConst.Messages.EMPTY_NULL_DATA, Toast.LENGTH_SHORT).show();
                                CommonTasks.showMessage(view, AppConst.Messages.EMPTY_NULL_DATA);
                            }
                        } else {
                            progressBarHome.dismissProgressBar();
//                            Toast.makeText(LoginActivity.this, AppConst.Messages.WRONG_CREDENTIALS, Toast.LENGTH_SHORT).show();
                            CommonTasks.showMessage(view, AppConst.Messages.WRONG_CREDENTIALS);
                        }
                    } else {
                        progressBarHome.dismissProgressBar();
//                        Toast.makeText(LoginActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                        CommonTasks.showMessage(view, AppConst.Messages.UNABLE_TO_REACH_SERVER);
                    }
                }

                @Override
                public void onFailure(Call<List<GeneralModel>> call, Throwable t) {
                    progressBarHome.dismissProgressBar();
                    Log.e(TAG, "onFailure: ", t);
//                    Toast.makeText(LoginActivity.this, AppConst.Messages.UNABLE_TO_REACH_SERVER, Toast.LENGTH_SHORT).show();
                    CommonTasks.showMessage(view, AppConst.Messages.UNABLE_TO_REACH_SERVER);
                }
            });
        } else {
//            Toast.makeText(this, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
            progressBarHome.dismissProgressBar();
            CommonTasks.showMessage(view, AppConst.Messages.NO_INTERNET);
        }
    }

    private void initiateFirebaseProcess() {
        FirebaseMessaging.getInstance().subscribeToTopic("egyaan");
        FirebaseInstanceId.getInstance().getToken();
    }

    public void setSharedPreferences(String email, String password, String firstname) {
        SharedPreferences.Editor editor = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(AppConst.Extras.IS_OPENED_FIRST_TIME, true);
        editor.putString(AppConst.Extras.USERNAME, email);
        editor.putString(AppConst.Extras.PASSWORD, password);
        editor.putString(AppConst.Extras.FIRSTNAME, firstname);
        editor.apply();
    }

    public void addStudentDetailsToDatabase(Integer roleId, String userId, String firstname, String lastname, String email,
                                            String studentPasswd, String gender, String mobile, String studentProfilePhoto,
                                            String parentProfilePhoto, String batchId, String branchId, String parentName,
                                            String parentEmail, String parentPasswd, String parentMobile) {

        boolean result = databaseHandler.insertStudent(roleId, userId, firstname, lastname, email, studentPasswd, gender,
                mobile, studentProfilePhoto, parentProfilePhoto, batchId, branchId, parentName, parentEmail,
                parentPasswd, parentMobile);

        if (!result) {
            Toast.makeText(this, AppConst.Messages.UNABLE_TO_INSERT, Toast.LENGTH_SHORT).show();
        }
    }
}