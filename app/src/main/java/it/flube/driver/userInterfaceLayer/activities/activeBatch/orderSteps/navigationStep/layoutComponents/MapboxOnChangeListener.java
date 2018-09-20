/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import com.mapbox.mapboxsdk.maps.MapView;

import timber.log.Timber;

import static com.mapbox.mapboxsdk.maps.MapView.DID_FAIL_LOADING_MAP;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_LOADING_MAP;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_LOADING_STYLE;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_RENDERING_FRAME;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_RENDERING_FRAME_FULLY_RENDERED;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_RENDERING_MAP;
import static com.mapbox.mapboxsdk.maps.MapView.DID_FINISH_RENDERING_MAP_FULLY_RENDERED;
import static com.mapbox.mapboxsdk.maps.MapView.REGION_DID_CHANGE;
import static com.mapbox.mapboxsdk.maps.MapView.REGION_DID_CHANGE_ANIMATED;
import static com.mapbox.mapboxsdk.maps.MapView.REGION_IS_CHANGING;
import static com.mapbox.mapboxsdk.maps.MapView.REGION_WILL_CHANGE;
import static com.mapbox.mapboxsdk.maps.MapView.REGION_WILL_CHANGE_ANIMATED;
import static com.mapbox.mapboxsdk.maps.MapView.SOURCE_DID_CHANGE;
import static com.mapbox.mapboxsdk.maps.MapView.WILL_START_LOADING_MAP;
import static com.mapbox.mapboxsdk.maps.MapView.WILL_START_RENDERING_FRAME;
import static com.mapbox.mapboxsdk.maps.MapView.WILL_START_RENDERING_MAP;

/**
 * Created on 9/20/2018
 * Project : Driver
 */
public class MapboxOnChangeListener implements
        MapView.OnMapChangedListener{
    private static final String TAG = "MapboxOnChangeListener";

    ///map listener
    public void onMapChanged(int change) {
        Timber.tag(TAG).d("onMapChanged -> " + change);
        switch (change) {
            case REGION_WILL_CHANGE:
                Timber.tag(TAG).d("...REGION_WILL_CHANGE");
                break;
            case REGION_WILL_CHANGE_ANIMATED:
                Timber.tag(TAG).d("...REGION_WILL_CHANGE_ANIMATED");
                break;
            case REGION_IS_CHANGING:
                Timber.tag(TAG).d("...REGION_IS_CHANGING");
                break;
            case REGION_DID_CHANGE:
                Timber.tag(TAG).d("...REGION_DID_CHANGE");
                break;
            case REGION_DID_CHANGE_ANIMATED:
                Timber.tag(TAG).d("...REGION_DID_CHANGE_ANIMATED");
                break;
            case WILL_START_LOADING_MAP:
                Timber.tag(TAG).d("...WILL_START_LOADING_MAP");
                break;
            case DID_FINISH_LOADING_MAP:
                Timber.tag(TAG).d("...DID_FINISH_LOADING_MAP");
                break;
            case DID_FAIL_LOADING_MAP:
                Timber.tag(TAG).d("...DID_FAIL_LOADING_MAP");
                break;
            case WILL_START_RENDERING_FRAME:
                Timber.tag(TAG).d("...WILL_START_RENDERING_FRAME");
                break;
            case DID_FINISH_RENDERING_FRAME:
                Timber.tag(TAG).d("...DID_FINISH_RENDERING_FRAME");
                break;
            case DID_FINISH_RENDERING_FRAME_FULLY_RENDERED:
                Timber.tag(TAG).d("...DID_FINISH_RENDERING_FRAME_FULLY_RENDERED");
                break;
            case WILL_START_RENDERING_MAP:
                Timber.tag(TAG).d("...WILL_START_RENDERING_MAP");
                break;
            case DID_FINISH_RENDERING_MAP:
                Timber.tag(TAG).d("...DID_FINISH_RENDERING_MAP");
                break;
            case DID_FINISH_RENDERING_MAP_FULLY_RENDERED:
                Timber.tag(TAG).d("...DID_FINISH_RENDERING_MAP_FULLY_RENDERED");
                break;
            case DID_FINISH_LOADING_STYLE:
                Timber.tag(TAG).d("...DID_FINISH_LOADING_STYLE");
                break;
            case SOURCE_DID_CHANGE:
                Timber.tag(TAG).d("...SOURCE_DID_CHANGE");
                break;
            default:
                Timber.tag(TAG).d("...Unknown event");
                break;
        }
    }

}
