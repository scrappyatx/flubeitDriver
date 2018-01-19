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

public class AuthNoProfileAlertDialog {
    public final static String TAG = "AuthNoProfileAlertDialog";

    public AlertDialog getAlertDialog(AppCompatActivity activity){
        return new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.no_profile_app_alert_title))
                .setMessage(activity.getResources().getString(R.string.no_profile_app_alert_message))
                .setIcon(activity.getResources().getDrawable(R.drawable.app_logo_round))
                .setNeutralButton(activity.getResources().getString(R.string.no_profile_app_alert_neutral_caption), new OnOkClick())
                .create();
    }

    private class OnOkClick implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int whichButton){
            dialog.dismiss();
        }
    }
}
