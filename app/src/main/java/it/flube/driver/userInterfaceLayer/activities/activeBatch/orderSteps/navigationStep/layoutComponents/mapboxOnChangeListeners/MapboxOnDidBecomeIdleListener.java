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
public class MapboxOnDidBecomeIdleListener implements
        MapView.OnDidBecomeIdleListener {
    private static final String TAG = "MapboxOnDidBecomeIdleListener";

    public void onDidBecomeIdle(){
        Timber.tag(TAG).d("onDidBecomeIdle");
    }
}
