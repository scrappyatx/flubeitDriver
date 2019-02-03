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
public class MapboxOnWillStartLoadingMapListener implements
        MapView.OnWillStartLoadingMapListener {
    private static final String TAG = "MapboxOnWillStartLoadingMapListener";

    public void onWillStartLoadingMap() {
        Timber.tag(TAG).d("onWillStartLoadingMap");
    }
}
