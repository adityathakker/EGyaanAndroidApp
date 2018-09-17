package com.adityathakker.egyaan.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fireion on 23/11/17.
 */

public class FileDownloadTask {

    private static final String TAG = FileDownloadTask.class.getSimpleName();
    public Context context;
    private Button downloadButton;
    private String downloadUrl = null, downloadFileName = null;

    public FileDownloadTask(Context context, Button downloadButton, String downloadUrl, String downloadFileName) {
        this.context = context;
        this.downloadButton = downloadButton;
        this.downloadUrl = downloadUrl;
        this.downloadFileName = downloadFileName;
        Log.e(TAG, "FileDownloadTask: " + downloadFileName);

        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File fileStorage = null;
        File outputFile = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d(TAG, "doInBackground: Download URL " + downloadUrl);
                URL url = new URL(downloadUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + httpURLConnection.getResponseCode()
                            + " " + httpURLConnection.getResponseMessage());
                }

                if (new CommonTasks.CheckForSDCard().isSDCardPresent()) {
                    fileStorage = new File(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                                    + AppConst.Extras.DOWNLOAD_DIRECTORY + "/" + AppConst.Extras.DOWNLOAD_NOTES_FILES_DIRECTORY);
                } else
                    Toast.makeText(context, AppConst.Messages.ERROR_NO_SD_CARD, Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!fileStorage.exists()) {
                    if (fileStorage.mkdirs()) {
                        Log.e(TAG, "Directory Created.");
                    } else {
                        Log.e(TAG, "Directory Not Created.");
                    }
                } else {
                    Log.e(TAG, "doInBackground: Directory already");
                }

                outputFile = new File(fileStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    if (outputFile.createNewFile()) {
                        Log.e(TAG, "File Created.");
                    } else {
                        Log.e(TAG, "File Not Created.");
                    }
                } else {
//                    Toast.makeText(context, "File " + AppConst.Messages.FILE_EXISTS, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "doInBackground: File Already Exists");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = httpURLConnection.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloadButton.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    downloadButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "Download " + AppConst.Statuses.FAILED, Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downloadButton.setEnabled(true);
                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(context, "Download " + AppConst.Statuses.FAILED, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        downloadButton.setEnabled(true);
                    }
                }, 3000);

                Log.e(TAG, "Download Failed with exception - " + e.getLocalizedMessage());
            }
            super.onPostExecute(result);
        }
    }
}