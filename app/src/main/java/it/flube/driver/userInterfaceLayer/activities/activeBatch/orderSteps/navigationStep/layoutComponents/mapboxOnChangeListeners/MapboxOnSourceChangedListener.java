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
public class MapboxOnSourceChangedListener implements
        MapView.OnSourceChangedListener {
    private static final String TAG = "MapboxOnSourceChangedListener";

    public void onSourceChangedListener(String source) {
        Timber.tag(TAG).d("onSourceChangedListener -> " + source);
    }
}