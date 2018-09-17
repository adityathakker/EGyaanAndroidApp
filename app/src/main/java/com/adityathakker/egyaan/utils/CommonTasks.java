package com.adityathakker.egyaan.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 26/12/16.
 */

public class CommonTasks {
    private static final String TAG = CommonTasks.class.getSimpleName();

    public static String isDataOn(Context context) {
        String networkType;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoWifi = null;
        NetworkInfo netInfoMobileData = null;
        if (cm != null) {
            netInfoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            netInfoMobileData = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        } else {
            Log.e(TAG, "isDataOn: Connectivity Manager Error");
        }

        if (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting()) {
            networkType = AppConst.Extras.WIFI;
        } else if (netInfoMobileData != null && netInfoMobileData.isConnectedOrConnecting()) {
            networkType = AppConst.Extras.MOBILE;
        } else {
//            Toast.makeText(context, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
            networkType = AppConst.Extras.NULL_CONNECTION;
        }
        return networkType;
    }

    public static void showMessage(View view, String messageString) {
        Snackbar snackbar = Snackbar.make(view, messageString, Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        TextView snackTextView = snackView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setTextColor(Color.RED);
        snackbar.show();
    }

    public static class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
    }
}
