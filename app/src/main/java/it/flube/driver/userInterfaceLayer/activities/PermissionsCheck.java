/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import timber.log.Timber;

/**
 * Created on 7/31/2017
 * Project : Driver
 */

public class PermissionsCheck {
    private static final String TAG = "PermissionsCheck";
    private static final int PERMISSION_CALLBACK_CONSTANT = 327;
    private static final int REQUEST_PERMISSION_SETTING = 863;

    private String[] permissionsRequired;
    private Boolean sentToSettings;
    private PermissionResponse response;
    private Boolean checkInProgress;

    public PermissionsCheck(){
        permissionsRequired = retrieveManifestPermissions() ;
        sentToSettings = false;
        checkInProgress = false;
    }

    public void checkPermissionRequest(AppCompatActivity activity, PermissionResponse response){
        this.response = response;

        Timber.tag(TAG).d("Checking Permission Status START");

        if (!checkInProgress) {
            if (haveAllPermissions(activity)) {
                Timber.tag(TAG).d("...we have all required permissions");
                proceedAfterPermissionSuccess();
            } else {
                Timber.tag(TAG).d("...we don't have all required permissions");
                Timber.tag(TAG).d("...we don't have all required permissions");

                if (shouldShowRationale(activity)) {
                    Timber.tag(TAG).d("...showing Rationale dialog");
                    showRationaleDialog(activity, new OnGrant(activity), new OnCancel(activity));
                } else {
                    //show fail dialog, give them a chance to go to settings
                    Timber.tag(TAG).d("...showing SettingsAsk dialog");
                    showSettingsAskDialog(activity);
                }

            }
        }
    }

    public Boolean haveRequiredPermissions(AppCompatActivity activity) {
        return haveAllPermissions(activity);
    }

    public void checkRequestPermissionResult(AppCompatActivity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        Timber.tag(TAG).d("starting checkRequestPermissionResult...");
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){

            if (haveAllPermissions(activity)){
                Timber.tag(TAG).d(" ....all required permissions GRANTED");
                proceedAfterPermissionSuccess();
            } else {
                Timber.tag(TAG).d(" ....all required permissions DENIED");
                proceedAfterPermissionFailure();
            }
        }
    }


    public void checkActivityResult(AppCompatActivity activity, int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (haveAllPermissions(activity)) {
                proceedAfterPermissionSuccess();
            } else {
                proceedAfterPermissionFailure();
            }
        }
    }


    public void close(){

    }

    private String[] retrieveManifestPermissions() {
        Timber.tag(TAG).d("retreiving permissions from manifest");
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
    }


    private Boolean haveAllPermissions(AppCompatActivity activity) {
        Boolean result = true;
        for (String permission : permissionsRequired) {
            Boolean thisPermissionGranted = (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED);
            result = result && thisPermissionGranted;
            Timber.tag(TAG).d("permission --> " + permission + " user granted --> " + thisPermissionGranted);
        }
        return result;
    }

    private Boolean shouldShowRationale(AppCompatActivity activity) {
        Boolean result = false;
        for (String permission : permissionsRequired) {
            Boolean shouldShowRationaleForThisPermission = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            result = result || shouldShowRationaleForThisPermission;
            Timber.tag(TAG).d("permission --> " + permission + " should show rationale --> " + shouldShowRationaleForThisPermission);
        }
        return result;
    }

    private void showRationaleDialog(AppCompatActivity activity, DialogInterface.OnClickListener onGrantClick, DialogInterface.OnClickListener onCancelClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("flube.it Driver needs multiple permissions");
        builder.setMessage("Camera permission is needed so you can take photos during a service order.\n\nLocation permission is needed to track your location while you are using the app");
        builder.setPositiveButton("Grant", onGrantClick);
        builder.setNegativeButton("Cancel", onCancelClick);
        builder.show();
        Timber.tag(TAG).d("Showing RationaleDialog...");
    }



    private class OnGrant implements DialogInterface.OnClickListener {
        private AppCompatActivity activity;

        public OnGrant(AppCompatActivity activity) {
            this.activity = activity;
        }
        public void onClick(DialogInterface dialog, int which) {
            Timber.tag(TAG).d("...clicked Grant");
            dialog.cancel();
            ActivityCompat.requestPermissions(activity ,permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
        }
    }

    private class OnCancel implements DialogInterface.OnClickListener {
        private AppCompatActivity activity;

        public OnCancel(AppCompatActivity activity) {
            this.activity = activity;
        }

        public void onClick(DialogInterface dialog, int which) {
            Timber.tag(TAG).d("...clicked Cancel");
            dialog.cancel();
            showSettingsAskDialog(activity);
        }
    }

    private void showSettingsAskDialog(AppCompatActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("flube.it Driver will shutdown");
        builder.setMessage("You must grant all required permissions to use the flube.it Driver app");
        builder.setPositiveButton("Settings", new OnSettings(activity));
        builder.setNegativeButton("Exit App", new OnExitApp());
        builder.show();
        Timber.tag(TAG).d("Showing FinishAppDialog...");
    }

    private class OnSettings implements DialogInterface.OnClickListener {
        private AppCompatActivity activity;

        public OnSettings(AppCompatActivity activity) {
            this.activity = activity;
        }
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        }
    }

    private class OnExitApp implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            proceedAfterPermissionFailure();
        }
    }



    private void proceedAfterPermissionSuccess() {
        Timber.tag(TAG).d("Permission Check COMPLETE -> SUCCESS");
        checkInProgress = false;
        response.checkPermissionsSuccess();
    }

    private void proceedAfterPermissionFailure(){
        Timber.tag(TAG).d("Permission Check COMPLETE -> FAILURE");
        checkInProgress = false;
        response.checkPermissionsFailure();
    }


    public interface PermissionResponse {
        void checkPermissionsSuccess();

        void checkPermissionsFailure();
    }


}
