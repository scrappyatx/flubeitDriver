/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import timber.log.Timber;

/**
 * Created on 7/16/2017
 * Project : Driver
 */

public class DeviceCheck {
    private final String TAG = "AppInitialization";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 2473; // can be any integer, just identifies this specific request

    private AppCompatActivity activity;
    private Context applicationContext;
    private GoogleApiAvailability googleApi;
    private DeviceCheck.Response response;
    private Dialog errorDialog;

    public DeviceCheck(Context applicationContext, AppCompatActivity activity) {
        this.applicationContext = applicationContext;
        this.activity = activity;
    }

    public void doDeviceCheck(DeviceCheck.Response response) {
        ///  Google Play Services are required for firebase
        ///  This checks to see if they are installed or updated on the device, and if not, shows
        ///  the user a dialog where they can install or update them.
        ///
        ///  reference --> https://stackoverflow.com/questions/22493465/check-if-correct-google-play-service-available-unfortunately-application-has-s

        this.response = response;
        Timber.tag(TAG).d("about to get instance for google availability api");
        googleApi = GoogleApiAvailability.getInstance();
        Timber.tag(TAG).d(TAG,"got instance for google availability api");

        int resultCode = googleApi.isGooglePlayServicesAvailable(applicationContext);

        if (resultCode != ConnectionResult.SUCCESS) {
            // we DO NOT have Google Play Services available
            Timber.tag(TAG).d(TAG,"Connection result = " + googleApi.getErrorString(resultCode));

            if (errorDialog == null) {
                Timber.tag(TAG).d(TAG,"creating error dialog");
                errorDialog = googleApi.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                errorDialog.setCancelable(false);
                errorDialog.setCanceledOnTouchOutside(false);
                errorDialog.setOnDismissListener(new DeviceCheck.ErrorDialogListener());
            }

            if (!errorDialog.isShowing()) {
                Timber.tag(TAG).d(TAG,"showing error dialog");
                errorDialog.show();
            }
        } else {
            // we DO have googe play services available
            Timber.tag(TAG).d(TAG,"google api AVAILABLE");
            response.deviceCheckSuccess();
        }

    }

    private class ErrorDialogListener implements DialogInterface.OnDismissListener {
        public void onDismiss(DialogInterface dialogInterface) {
            Timber.tag(TAG).d(TAG,"error dialog was dismissed");
            if (googleApi.isGooglePlayServicesAvailable(applicationContext) == ConnectionResult.SUCCESS) {
                response.deviceCheckSuccess();
            } else {
                response.deviceCheckFailure();
            }
        }
    }

    public void close(){
        applicationContext=null;
        activity = null;
    }

    public interface Response {
        void deviceCheckSuccess();

        void deviceCheckFailure();
    }

}
