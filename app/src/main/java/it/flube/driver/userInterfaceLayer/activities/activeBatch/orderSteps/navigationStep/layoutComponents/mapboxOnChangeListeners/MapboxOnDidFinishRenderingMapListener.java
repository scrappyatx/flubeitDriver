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
public class MapboxOnDidFinishRenderingMapListener implements
        MapView.OnDidFinishRenderingMapListener {
    private static final String TAG = "MapboxOnDidFinishRenderingMapListener";

    public void onDidFinishRenderingMap(boolean didFinish) {
        Timber.tag(TAG).d("onDidFinishRenderingMap -> " + Boolean.valueOf(didFinish).toString());
    }
}

