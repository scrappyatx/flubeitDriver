/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;



import timber.log.Timber;

/**
 * Created on 7/10/2018
 * Project : Driver
 */
public class CheckCallPermission {
    private static final String TAG = "CheckCallPermission";

    private static final int REQUEST_CALL_PHONE = 1572;

    private Response response;


    public CheckCallPermission(){
    }

    public void checkCallPermissionRequest(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("checkCallPermissionRequest START...");
        this.response = response;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.tag(TAG).d("   ...dynamic permissions, need to check");

            if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Timber.tag(TAG).d("   ...don't have permission, need to ask for it");

                if (activity.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Timber.tag(TAG).d("      ...user needs explanation, return no permission");
                    response.callPermissionNo();
                } else {
                    Timber.tag(TAG).d("      ...don't need to show the user an explanation, just go to permissions");
                    activity.requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                }
            } else {
                Timber.tag(TAG).d("   ...we have permission");
                response.callPermissionYes();
            }
        } else {
            Timber.tag(TAG).d("   ...static permissions,no need to check");
            response.callPermissionYes();
        }

    }

    public void gotoSettings(AppCompatActivity activity){
        Timber.tag(TAG).d("gotoSettings...");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.tag(TAG).d("...going to settings");
            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(i, REQUEST_CALL_PHONE);
        } else {
            Timber.tag(TAG).d("...should never get here");
        }

    }

    public void onRequestPermissionResult(int requestCode, String permissions[], int [] grantResults){
        Timber.tag(TAG).d("onRequestPermissionResult, requestCode -> " + requestCode);
        switch (requestCode) {
            case (REQUEST_CALL_PHONE):
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //permission granted
                    Timber.tag(TAG).d("   ...permission GRANTED");
                    response.callPermissionYes();
                } else {
                    //permission denied
                    Timber.tag(TAG).d("   ...permission DENIED");
                    response.callPermissionNo();
                }
                break;
            default:
                Timber.tag(TAG).d("   ...unrecognized requestCode, ignore");
                break;
        }
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }


    public interface Response {
        void callPermissionYes();

        void callPermissionNo();
    }
}
