/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;

/**
 * Created on 12/19/2017
 * Project : Driver
 */

public class QuitAppAlertDialog {
    public final static String TAG = "QuitAppAlertDialog";

    public AlertDialog getAlertDialog(AppCompatActivity activity){
        return new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.quit_app_alert_title))
                .setMessage(activity.getResources().getString(R.string.quit_app_alert_message))
                .setIcon(activity.getResources().getDrawable(R.drawable.app_logo_round))
                .setPositiveButton(activity.getResources().getString(R.string.quit_app_alert_positive_caption), new OnYesClick(activity))
                .setNegativeButton(activity.getResources().getString(R.string.quit_app_alert_negative_caption), new OnNoClick())
                .create();
    }

    private class OnYesClick implements DialogInterface.OnClickListener {
        private AppCompatActivity activity;

        public OnYesClick(AppCompatActivity activity){
            this.activity = activity;
        }
        public void onClick(DialogInterface dialog, int whichButton){
            activity.finish();
        }
    }

    private class OnNoClick implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int whichButton){
            dialog.dismiss();
        }
    }
}
