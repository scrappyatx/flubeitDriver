/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;

/**
 * Created on 1/8/2019
 * Project : Driver
 */
public class AuthUiResponseErrorAlertDialog {
    public final static String TAG = "AuthUiResponseErrorAlertDialog";

    public AlertDialog getAlertDialog(AppCompatActivity activity, String alertMessage){
        return new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.signin_authui_response_error_dialog_title))
                .setMessage(alertMessage)
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
