/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.content.Context;
import android.location.Location;

import com.mapbox.services.android.telemetry.location.AndroidLocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;

import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import timber.log.Timber;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public class LocationEngineWrapper implements LocationTelemetryInterface, LocationEngineListener {
    private static final String TAG = "LocationEngineWrapper";
    private LocationEngine locationEngine;
    private LocationTrackingStartResponse response;
    private LocationTrackingPositionChanged update;

    public LocationEngineWrapper(Context appContext){
        //locationEngine = LostLocationEngine.getLocationEngine(appContext);
        locationEngine = AndroidLocationEngine.getLocationEngine(appContext);
    }

    public void locationTrackingStartRequest(LocationTrackingStartResponse response, LocationTrackingPositionChanged update) {
        this.response = response;
        this.update = update;
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.addLocationEngineListener(this);
        locationEngine.activate();
        Timber.tag(TAG).d("locationTrackingStartRequest");
    }


    public void onConnected() {
            try {
                locationEngine.requestLocationUpdates();
                response.locationTrackingStartSuccess();
                Timber.tag(TAG).d("...connected!");
            } catch (SecurityException e) {
                Timber.tag(TAG).e(e);
                response.locationTrackingStartFailure();
            }
    }

    public void locationTrackingStopRequest(LocationTrackingStopResponse response) {
        locationEngine.removeLocationUpdates();
        locationEngine.removeLocationEngineListener(this);
        locationEngine.deactivate();
        response.locationTrackingStopComplete();
        Timber.tag(TAG).d("locationTrackingStopRequest COMPLETE");
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            Timber.tag(TAG).d("New location received: " + location.toString());

            LatLonLocation position = new LatLonLocation();
            position.setLatitude(location.getLatitude());
            position.setLongitude(location.getLongitude());

            update.positionChanged(position);
        }
    }

}
