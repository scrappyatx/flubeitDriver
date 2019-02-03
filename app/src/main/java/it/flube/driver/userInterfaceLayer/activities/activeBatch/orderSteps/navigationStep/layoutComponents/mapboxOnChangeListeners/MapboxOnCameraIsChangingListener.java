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
public class MapboxOnCameraIsChangingListener implements
        MapView.OnCameraIsChangingListener {
    public final static String TAG="MapboxOnCameraIsChangingListener";

    public void onCameraIsChanging(){
        Timber.tag(TAG).d("onCameraIsChanging");
    }
}
