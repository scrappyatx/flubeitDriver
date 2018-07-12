/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import timber.log.Timber;

/**
 * Created on 7/10/2018
 * Project : Driver
 */
public class CheckCallPermission implements
        PermissionListener,
        PermissionRequestErrorListener,
        DialogInterface.OnClickListener {
    private static final String TAG = "CheckCallPermission";

    private AppCompatActivity activity;
    private Response response;


    public CheckCallPermission(){
    }

    public void checkCallPermissionRequest(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("checkCallPermissionRequest START...");
        this.response = response;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.tag(TAG).d("   ...dynamic persmissions, need to check");

            if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Timber.tag(TAG).d("   ...don't have permission, need to ask for it");
                this.activity = activity;
                Dexter.withActivity(activity)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(this)
                        .withErrorListener(this)
                        .onSameThread()
                        .check();
            } else {
                Timber.tag(TAG).d("   ...we have permission");
                response.callPermissionYes();
            }
        } else {
            Timber.tag(TAG).d("   ...static permissions,no need to check");
            response.callPermissionYes();
        }

    }

    ////
    //// PermissionListener interface
    ////
    public void onPermissionGranted(PermissionGrantedResponse response){
        Timber.tag(TAG).d("onPermissionGranted");
        this.response.callPermissionYes();
    }

    public void onPermissionDenied(PermissionDeniedResponse response){
        Timber.tag(TAG).d("onPermissionDenied");

        if (response.isPermanentlyDenied()) {
            Timber.tag(TAG).d("isPermanentlyDenied = TRUE");
            showSettingsDialog(activity);
        } else {
            Timber.tag(TAG).d("isPermanentlyDenied = FALSE");
            this.response.callPermissionNo();
        }
    }

    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token){
        Timber.tag(TAG).d("onPermissionRationaleShouldBeShown");
        //token.continuePermissionRequest();
        token.cancelPermissionRequest();
        response.callPermissionNo();
    }

    ////
    ////    Permission Error Interface
    ////
    public void onError(DexterError e){
        Timber.tag(TAG).d("onError -> " + e.toString());
        response.callPermissionNo();
    }

    ////
    /// Show settings dialog
    ///
    private void showSettingsDialog(AppCompatActivity activity){
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Need permissions")
                .setMessage("This app needs permissions to use this feature.  You can grant them in app settings")
                .setPositiveButton("GOTO SETTINGS",this)
                .setNegativeButton("Cancel", this)
                .create();

        dialog.show();
    }

    public void onClick(DialogInterface dialog, int which){
        Timber.tag(TAG).d("clicked button -> " + which);
        dialog.cancel();
    }

    public void close(){
        activity = null;
    }


    public interface Response {
        void callPermissionYes();

        void callPermissionNo();
    }
}
