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

    private Boolean lastGoodPositionSaved;
    private LatLonLocation lastGoodPosition;

    public LocationEngineWrapper(Context appContext){
        //locationEngine = LostLocationEngine.getLocationEngine(appContext);
        locationEngine = AndroidLocationEngine.getLocationEngine(appContext);
        lastGoodPositionSaved = false;
    }

    public LocationEngine getLocationEngine(){
        return locationEngine;
    }

    public void locationTrackingStartRequest(LocationTrackingStartResponse response, LocationTrackingPositionChanged update) {
        this.response = response;
        this.update = update;

        locationEngine.addLocationEngineListener(this);

        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);

        locationEngine.setInterval(20*1000); //want updates at least every 20 seconds
        locationEngine.setFastestInterval(5*1000); //but will accept them as fast as every 5 seconds
        locationEngine.setSmallestDisplacement(3);  //but only if displacement is 3 meters or more

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

            lastGoodPositionSaved = true;
            lastGoodPosition = position;

            update.positionChanged(position);
        }
    }

    public Boolean hasLastGoodPosition(){
        return lastGoodPositionSaved;
    }

    public LatLonLocation getLastGoodPosition(){
        return lastGoodPosition;
    }

}
