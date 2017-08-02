/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import timber.log.Timber;

/**
 * Created on 7/30/2017
 * Project : Driver
 */

public class PermissionsCheckActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionsCheck.PermissionResponse {

    private static final String TAG = "PermissionsCheckActivity";

    private PermissionsCheck permissionsCheck;
    private Boolean haveRequiredPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionsCheck = new PermissionsCheck();
        haveRequiredPermissions = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        permissionsCheck.checkPermissionRequest(this, this);

        if (haveRequiredPermissions) {
            onPermissionResume();
        }

        Timber.tag(TAG).d("onResume");
    }

    protected void onPermissionResume(){
        Timber.tag(TAG).d("onPermissionResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.tag(TAG).d("onPause");

        if (haveRequiredPermissions) {
            onPermissionPause();
        }
    }

    protected void onPermissionPause() {
        Timber.tag(TAG).d("onPermissionPause");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        permissionsCheck.checkRequestPermissionResult(this, requestCode, permissions, grantResults);
        Timber.tag(TAG).d("onRequestPermissionsResult COMPLETE");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsCheck.checkActivityResult(this,requestCode, resultCode, data );
        Timber.tag(TAG).d("onActvityResult COMPLETE");
    }


    public void checkPermissionsSuccess() {
            Timber.tag(TAG).d("We DO have all required permissions");
            haveRequiredPermissions = true;
    }

    public void checkPermissionsFailure() {
            Timber.tag(TAG).d("We DO NOT have all required permissions");
            finish();
    }


}
