package com.androidsphere.aditya.egyaan.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidsphere.aditya.egyaan.AppConstants;
import com.androidsphere.aditya.egyaan.R;
import com.androidsphere.aditya.egyaan.net.LoginChecker;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(AppConstants.SharedPrefs.ALREADY_LOGGED_IN, false)) {
            Log.d(TAG, "onCreate: SharedPrefs Returned Already Loggen In => True");
            String accountType = sharedPreferences.getString(AppConstants.SharedPrefs.ACCOUNT_TYPE, "-1");
            if (accountType.equalsIgnoreCase("student")) {
                Intent intent = new Intent(this, StudentActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ParentActivity.class);
                startActivity(intent);
            }
        } else {
            Log.d(TAG, "onCreate: SharedPrefs Returned Already Loggen In => False");
            setInitialPreferences();
        }

        final EditText email, password;
        email = (EditText) findViewById(R.id.email_activity_login);
        password = (EditText) findViewById(R.id.password_activity_login);


        Button loginButton = (Button) findViewById(R.id.button_activity_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                if (emailString.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (passwordString.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    LoginChecker loginChecker = new LoginChecker(LoginActivity.this, emailString, passwordString);
                    loginChecker.execute();
                }
            }
        });

    }

    private void setInitialPreferences() {
        SharedPreferences.Editor editor = getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(AppConstants.SharedPrefs.ALREADY_LOGGED_IN, false);
        editor.putString(AppConstants.SharedPrefs.ACCOUNT_TYPE, "-1");
        editor.putString(AppConstants.SharedPrefs.UID, "-1");
        editor.putString(AppConstants.SharedPrefs.FNAME, "-1");
        editor.putString(AppConstants.SharedPrefs.LNAME, "-1");
        editor.putString(AppConstants.SharedPrefs.EMAIL, "-1");
        editor.putString(AppConstants.SharedPrefs.PASSWORD, "-1");
        editor.putString(AppConstants.SharedPrefs.BRANCH, "-1");
        editor.commit();
    }

    public void setPrefs(Context context, String[] result, String email, String password) {
        if (result[0] != null && result[1] != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, Context.MODE_PRIVATE).edit();
            try {
                JSONObject jsonObject = new JSONObject(result[0]);
                String message = jsonObject.getString("message");
                if (message.equalsIgnoreCase("account_exists")) {
                    String accountType = jsonObject.getString("account_type");
                    if (accountType.equalsIgnoreCase("student")) {
                        String uid = jsonObject.getString("uid");
                        String firstName = jsonObject.getString("fname");
                        String lastName = jsonObject.getString("lname");
                        String branch = jsonObject.getString("branch");

                        editor.putBoolean(AppConstants.SharedPrefs.ALREADY_LOGGED_IN, true);
                        editor.putString(AppConstants.SharedPrefs.ACCOUNT_TYPE, "student");
                        editor.putString(AppConstants.SharedPrefs.UID, uid);
                        editor.putString(AppConstants.SharedPrefs.FNAME, firstName);
                        editor.putString(AppConstants.SharedPrefs.LNAME, lastName);
                        editor.putString(AppConstants.SharedPrefs.EMAIL, email);
                        editor.putString(AppConstants.SharedPrefs.PASSWORD, password);
                        editor.putString(AppConstants.SharedPrefs.BRANCH, branch);
                        editor.putString(AppConstants.SharedPrefs.GCM_REG_ID, result[1]);
                        editor.commit();

                        Intent intent = new Intent(context, StudentActivity.class);
                        context.startActivity(intent);
                    } else {
                        String uid = jsonObject.getString("uid");
                        String firstName = jsonObject.getString("fname");
                        String lastName = jsonObject.getString("lname");

                        editor.putBoolean(AppConstants.SharedPrefs.ALREADY_LOGGED_IN, true);
                        editor.putString(AppConstants.SharedPrefs.ACCOUNT_TYPE, "parent");
                        editor.putString(AppConstants.SharedPrefs.UID, uid);
                        editor.putString(AppConstants.SharedPrefs.FNAME, firstName);
                        editor.putString(AppConstants.SharedPrefs.LNAME, lastName);
                        editor.putString(AppConstants.SharedPrefs.EMAIL, email);
                        editor.putString(AppConstants.SharedPrefs.PASSWORD, password);
                        editor.putString(AppConstants.SharedPrefs.BRANCH, "-1");
                        editor.putString(AppConstants.SharedPrefs.GCM_REG_ID, result[1]);
                        editor.commit();

                        Intent intent = new Intent(context, ParentActivity.class);
                        context.startActivity(intent);
                    }
                } else if (message.equalsIgnoreCase("post_parameters_error")) {
                    Toast.makeText(context, "Post Param Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No Such Account", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}


