/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners;

import com.mapbox.mapboxsdk.maps.MapView;

import timber.log.Timber;

/**
 * Created on 1/16/2019
 * Project : Driver
 */
public class MapboxOnDidFinishLoadingStyleListener implements
        MapView.OnDidFinishLoadingStyleListener {
    private static final String TAG = "MapboxOnDidFinishLoadingStyleListener";

    public void onDidFinishLoadingStyle() {
        Timber.tag(TAG).d("onDidFinishLoadingStyle");
    }
}
