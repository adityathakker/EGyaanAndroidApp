package com.androidsphere.aditya.egyaan.net;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.androidsphere.aditya.egyaan.AppConstants;
import com.androidsphere.aditya.egyaan.ui.LoginActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginChecker extends AsyncTask<Void, Void, String[]> {
    private static final String TAG = LoginChecker.class.getSimpleName();
    // please enter your sender id
    String SENDER_ID = "43070224576";
    Context context;
    String email = null, password = null;
    GoogleCloudMessaging gcm;
    String registrationId = null;

    public LoginChecker(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
        gcm = GoogleCloudMessaging.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String[] doInBackground(Void... params) {
        String result = null;
        try {
            URL url = new URL(AppConstants.URLs.LOGIN_CHECKER);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", email)
                    .appendQueryParameter("password", password);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            if (in != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                    result += line;
            }
            in.close();
            conn.disconnect();

            registrationId = gcm.register(SENDER_ID);
            Log.d(TAG, registrationId);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result != null) {
            result = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
        }
        Log.d(TAG, "doInBackground: Result: " + result);
        return new String[]{result, registrationId};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.setPrefs(context, result, email, password);
        loginActivity = null;
    }
}
