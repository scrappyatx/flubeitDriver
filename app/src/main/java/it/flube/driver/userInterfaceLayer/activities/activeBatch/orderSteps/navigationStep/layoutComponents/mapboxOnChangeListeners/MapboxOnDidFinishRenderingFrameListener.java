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
public class MapboxOnDidFinishRenderingFrameListener implements
        MapView.OnDidFinishRenderingFrameListener {
    private static final String TAG = "MapboxOnDidFinishRenderingFrameListener";

    public void onDidFinishRenderingFrame(boolean didFinish) {
        Timber.tag(TAG).d("onDidFinishRenderingFrame -> " + Boolean.valueOf(didFinish).toString());
    }
}

