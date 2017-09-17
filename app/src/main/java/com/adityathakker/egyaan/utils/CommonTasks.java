package com.adityathakker.egyaan.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 26/12/16.
 */

public class CommonTasks {

    public static boolean isDataOn(Context context) {
//        boolean haveConnectedWifi = false;
//        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
//        for (NetworkInfo ni : netInfo) {
//            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
//                if (ni.isConnected())
//                    haveConnectedWifi = true;
//            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
//                if (ni.isConnected())
//                    haveConnectedMobile = true;
//        }
//        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void showMessage(View view) {
        Snackbar snackbar = Snackbar.make(view, AppConst.Messages.NO_INTERNET, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        TextView snackTextView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setTextColor(Color.RED);
        snackbar.show();
    }
}
