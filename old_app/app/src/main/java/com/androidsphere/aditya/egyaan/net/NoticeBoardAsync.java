package com.androidsphere.aditya.egyaan.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidsphere.aditya.egyaan.adapters.NoticeRecyclerAdapter;
import com.androidsphere.aditya.egyaan.model.Notice;

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

/**
 * Created by aditya9172 on 19/1/16.
 */
public class NoticeBoardAsync  extends AsyncTask<String,Void,String>{
    private static final String TAG = NoticeBoardAsync.class.getSimpleName();
    ProgressDialog progressDialog;
    Context context;
    NoticeRecyclerAdapter noticeRecyclerAdapter;

    NoticeBoardAsync(Context context, NoticeRecyclerAdapter noticeRecyclerAdapter){
        this.context = context;
        this.noticeRecyclerAdapter = noticeRecyclerAdapter;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
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
        Log.d(TAG, "NoticeBoardAsync doInBackground: Result: " + result);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ArrayList<Notice> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length();i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String noticeString = jsonObject.getString("notice");
                Notice notice = new Notice.Builder(title,noticeString).build();
                arrayList.add(notice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        noticeRecyclerAdapter.setData(arrayList);
        noticeRecyclerAdapter.refresh();


        progressDialog.dismiss();

    }
}
