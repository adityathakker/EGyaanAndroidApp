package com.androidsphere.aditya.egyaan.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidsphere.aditya.egyaan.AppConstants;
import com.androidsphere.aditya.egyaan.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aditya9172 on 17/12/15.
 */
public class TimeTableFetcher extends AsyncTask<Void, Void, String> {
    private static final String TAG = TimeTableFetcher.class.getSimpleName();
    ProgressDialog progressDialog;
    String branch;
    Context context;

    public TimeTableFetcher(Context context, String branch) {
        this.branch = branch;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        try {
            URL url = new URL(AppConstants.URLs.TIMETABLE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("branch", branch);
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = result.substring(result.indexOf("["), result.indexOf("]", result.length() - 1)) + "]";
        Log.d(TAG, "doInBackground: Result: " + result);

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            JSONArray jsonArray = new JSONArray(result);
            ArrayList<HashMap<String, String>> timetableList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String day = jsonObject.getString("day");

                JSONArray timetable = jsonObject.getJSONArray("timetable");
                for (int j = 0; j < timetable.length(); j++) {
                    JSONObject eachTT = timetable.getJSONObject(j);
                    HashMap<String, String> tempHashMap = new HashMap<>();
                    tempHashMap.put("day", day);
                    String time = eachTT.getString("time");
                    String subject = eachTT.getString("subject");
                    tempHashMap.put("time", time);
                    tempHashMap.put("subject", subject);
                    timetableList.add(tempHashMap);
                }
            }
            Log.d(TAG, timetableList.toString());
            databaseHelper.insertIntoTimeTable(timetableList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor sharedPrefs = context.getSharedPreferences(AppConstants.SharedPrefs.PREF_NAME, Context.MODE_PRIVATE).edit();
        sharedPrefs.putBoolean(AppConstants.SharedPrefs.FIRST_TIME_LOGIN_STUDENT, false);
        sharedPrefs.commit();
        progressDialog.dismiss();
    }
}
