/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.content.Context;
import android.location.Location;
import android.os.HandlerThread;

import com.mapbox.services.android.telemetry.location.AndroidLocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;

import it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation.LocationTrackingPositionChangedHandler;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import timber.log.Timber;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public class LocationEngineWrapper implements
        LocationTelemetryInterface,
        LocationEngineListener {

    private static final String TAG = "LocationEngineWrapper";
    private LocationEngine locationEngine;
    private LocationTrackingStartResponse response;
    private LocationTrackingPositionChanged update;

    private Boolean isTracking;
    private Boolean lastGoodPositionSaved;
    private LatLonLocation lastGoodPosition;



    public LocationEngineWrapper(Context appContext){
        //locationEngine = LostLocationEngine.getLocationEngine(appContext);
        locationEngine = AndroidLocationEngine.getLocationEngine(appContext);
        lastGoodPositionSaved = false;
        isTracking = false;
        update = new LocationTrackingPositionChangedHandler();
        Timber.tag(TAG).d("instance CREATED");
    }

    public LocationEngine getLocationEngine(){
        return locationEngine;
    }

    public void locationTrackingStartRequest(LocationTrackingStartResponse response) {
        Timber.tag(TAG).d("locationTrackingStartRequest START...");
        this.response = response;

        if (!isTracking) {
            Timber.tag(TAG).d("...adding listener");
            //locationEngine.addLocationEngineListener(this);

            Timber.tag(TAG).d("...setting priority");
            locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);

            Timber.tag(TAG).d("...setting interval & displacement");
            locationEngine.setInterval(20 * 1000); //want updates at least every 20 seconds
            locationEngine.setFastestInterval(5 * 1000); //but will accept them as fast as every 5 seconds
            locationEngine.setSmallestDisplacement(3);  //but only if displacement is 3 meters or more

            Timber.tag(TAG).d("...activating location engine");
            locationEngine.activate();
        } else {
            Timber.tag(TAG).d("...already TRACKING, do nothing");
        }
        Timber.tag(TAG).d("...locationTrackingStartRequest COMPLETE");
    }

    ///
    ///   response from adding LocationEngineListener
    ///
    public void onConnected() {
        Timber.tag(TAG).d("   ...onConnected START");
        try {
                locationEngine.requestLocationUpdates();
                response.locationTrackingStartSuccess();
                isTracking = true;
                Timber.tag(TAG).d("      ...SUCCESS");
            } catch (SecurityException e) {
                Timber.tag(TAG).e(e);
                isTracking = false;
                Timber.tag(TAG).d("      ...FAILURE");
                response.locationTrackingStartFailure();
            }
            Timber.tag(TAG).d("   ...onConnected COMPLETE");
    }

    public void locationTrackingStopRequest(LocationTrackingStopResponse response) {
        Timber.tag(TAG).d("locationTrackingStopRequest START...");

        if (isTracking) {
            Timber.tag(TAG).d("   ...removing location updates & listener");
            locationEngine.removeLocationUpdates();
            locationEngine.removeLocationEngineListener(this);
            Timber.tag(TAG).d("   ...deactivating location engine");
            locationEngine.deactivate();
            isTracking = false;

        } else {
            Timber.tag(TAG).d("   ...not tracking, do nothing");
        }
        Timber.tag(TAG).d("....locationTrackingStopRequest COMPLETE");
        response.locationTrackingStopComplete();
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            Timber.tag(TAG).d("New location received: " + location.toString());

            LatLonLocation position = new LatLonLocation();
            position.setLatitude(location.getLatitude());
            position.setLongitude(location.getLongitude());

            lastGoodPositionSaved = true;
            lastGoodPosition = position;

            if (isTracking) {
                update.positionChanged(position);

            } else {

            }

        }
    }

    public Boolean hasLastGoodPosition(){
        return lastGoodPositionSaved;
    }

    public LatLonLocation getLastGoodPosition(){
        return lastGoodPosition;
    }

}
