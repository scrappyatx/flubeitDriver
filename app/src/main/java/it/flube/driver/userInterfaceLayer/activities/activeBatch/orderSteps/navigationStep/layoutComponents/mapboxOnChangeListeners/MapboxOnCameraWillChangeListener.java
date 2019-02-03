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
public class MapboxOnCameraWillChangeListener implements
        MapView.OnCameraWillChangeListener {
    public final static String TAG="MapboxOnCameraWillChangeListener";

    public void onCameraWillChange(boolean didChange){
        Timber.tag(TAG).d("onCameraWillChange -> " + Boolean.valueOf(didChange).toString());
    }
}
