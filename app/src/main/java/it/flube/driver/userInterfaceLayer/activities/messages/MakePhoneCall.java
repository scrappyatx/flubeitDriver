/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;



import timber.log.Timber;

/**
 * Created on 7/10/2018
 * Project : Driver
 */
public class MakePhoneCall  {
    private static final String TAG = "MakePhoneCall";

    private static final String CALL_INTENT_PREFIX = "tel:";

    public void dialNumberRequest(AppCompatActivity activity, String dialNumber) {
        Timber.tag(TAG).d("dialNumberRequest START, dialNumber -> " + dialNumber);

        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(CALL_INTENT_PREFIX + dialNumber));

        if (callIntent.resolveActivity(activity.getPackageManager()) == null) {
            Timber.tag(TAG).d("   ...can't resolve intent, do nothing");

        } else {
            Timber.tag(TAG).d("   ...can resolve intent, try to make call");

            try {
                Timber.tag(TAG).d("   ...about to start intent");
                activity.startActivity(callIntent);
                Timber.tag(TAG).d("   ...started intent");

            } catch (SecurityException e) {
                Timber.tag(TAG).w("Security Exception when trying to launch call intent");
                Timber.tag(TAG).e(e);
            } catch (Exception e){
                Timber.tag(TAG).w("exception when trying to launch call intent");
                Timber.tag(TAG).e(e);
            }
        }
    }
}
