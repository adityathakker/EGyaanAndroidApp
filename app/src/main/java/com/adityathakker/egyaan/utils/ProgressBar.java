package com.adityathakker.egyaan.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by fireion on 18/2/18.
 */

public class ProgressBar {
    MaterialDialog materialDialog;

    public void showProgressBar(Context context) {
        materialDialog = new MaterialDialog.Builder(context)
                .title(AppConst.Messages.LOADING)
                .content(AppConst.Messages.PLEASE_WAIT)
                .progress(true, 0)
                .show();
    }

    public void dismissProgressBar() {
        materialDialog.dismiss();
    }
}
