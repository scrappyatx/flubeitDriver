/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import it.flube.libbatchdata.builders.LatLonLocationBuilder;
import it.flube.libbatchdata.entities.LatLonLocation;
import timber.log.Timber;

/**
 * Created on 11/11/2017
 * Project : Driver
 */

public class MapUtilities {
    private static final String TAG = "MapUtilities";

    private static final String MAP_PACKAGE = "com.google.android.apps.maps";

    public static void startNavigation(@NonNull Context activityContext, @NonNull LatLonLocation destination) {
        Timber.tag(TAG).d("getNavigationIntent START...");

        Intent mapIntent = getIntent(destination);

        if (mapIntent.resolveActivity(activityContext.getPackageManager()) != null) {
            try {
                activityContext.startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                Timber.tag(TAG).w("error trying to launch navigation activity");
                Timber.tag(TAG).e(e);
            }

        } else {
            Timber.tag(TAG).d("could not resolve activity");
        }
    }

    public static Boolean isNavigationPackageAvailable(@NonNull Context activityContext){
        Timber.tag(TAG).d("isNavigationPackageAvailable...");

        //build intent to random coordinates
        Intent mapIntent = getIntent(new LatLonLocationBuilder.Builder()
                .location(30.545762,-97.757865 )
                .build());

        //see if we can resolve the intent
        if (mapIntent.resolveActivity(activityContext.getPackageManager()) != null) {
            Timber.tag(TAG).d("...is available");
            return true;
        } else {
            Timber.tag(TAG).d("...is NOT available");
            return false;
        }
    }

    private static Intent getIntent(@NonNull LatLonLocation destination){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination.getLatitude() + "," + destination.getLongitude());
        Timber.tag(TAG).d("gmmItentUri : " + gmmIntentUri.toString());

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(MAP_PACKAGE);

        return mapIntent;
    }

}
