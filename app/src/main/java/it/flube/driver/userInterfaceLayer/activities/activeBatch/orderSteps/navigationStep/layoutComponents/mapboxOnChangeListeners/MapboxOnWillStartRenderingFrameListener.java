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
public class MapboxOnWillStartRenderingFrameListener implements
        MapView.OnWillStartRenderingFrameListener {
    private static final String TAG = "MapboxOnWillStartRenderingFrameListener";

    public void onWillStartRenderingFrame() {
        Timber.tag(TAG).d("onWillStartRenderingFrame");
    }
}