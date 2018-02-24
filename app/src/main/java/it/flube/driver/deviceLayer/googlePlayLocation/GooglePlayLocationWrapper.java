/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.googlePlayLocation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import timber.log.Timber;

/**
 * Created on 1/18/2018
 * Project : Driver
 */

public class GooglePlayLocationWrapper implements
        LocationTelemetryInterface,
        OnCompleteListener<android.location.Location>,
        LocationCallbackWrapper.UpdateLastGoodPositionInterface {

    private static final String TAG = "GooglePlayLocationWrapper";

    private static final long UPDATE_INTERVAL = 20 * 1000;              // want updates at least every 20 seconds
    private static final long FASTEST_UPDATE_INTERVAL = 5 * 1000;       // but will accept them as fast as every 5 seconds
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;      // but only if displacement is 3 meters or more


    private final static boolean forceNetwork = false;


    private FusedLocationProviderClient client;
    private HandlerThread handlerThread;
    private LocationCallbackWrapper callback;

    private Boolean lastGoodPositionSaved;
    private LatLonLocation lastGoodPosition;

    private Boolean clientOk;
    private Boolean isTracking;


    public GooglePlayLocationWrapper(Context appContext){
        Timber.tag(TAG).d("creating GooglePlayLocationWrapper START...");

        clientOk = false;
        lastGoodPositionSaved = false;
        lastGoodPosition = new LatLonLocation();
        isTracking = false;

        client = LocationServices.getFusedLocationProviderClient(appContext);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission( appContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( appContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Timber.tag(TAG).d("   ...SDK >= 23 AND we have permission, ok to continue");
                clientOk = true;
            } else {
                Timber.tag(TAG).w("   ...SDK >= 23 AND don't have permission, should never get to this point");
                clientOk = false;
            }
        } else {
            Timber.tag(TAG).d("   ...SDK < 23, ok to continue");
            clientOk = true;
        }

        if (clientOk) {
            client.getLastLocation().addOnCompleteListener(this);
        }

        Timber.tag(TAG).d("...GooglePlayLocationWrapper CREATED");
    }

    public void locationTrackingStartRequest(LocationTelemetryInterface.LocationTrackingStartResponse response){
        Timber.tag(TAG).d("locationTrackingStartRequest START...");

        if (!isTracking) {
            Timber.tag(TAG).d("   ...not already tracking, checking client");

            if (clientOk) {
                Timber.tag(TAG).d("   ...client ok, making location request");

                LocationRequest locationRequest = LocationRequest.create();
                try {
                    handlerThread = new HandlerThread("locationHandlerThread");
                    handlerThread.start();
                    callback = new LocationCallbackWrapper(this);

                    client.requestLocationUpdates(getLocationRequest(), callback, handlerThread.getLooper());

                    isTracking = true;

                    Timber.tag(TAG).d("   ...tracking STARTED");
                    response.locationTrackingStartSuccess();

                } catch (SecurityException e) {
                    Timber.tag(TAG).w("   ...tracking NOT STARTED");
                    response.locationTrackingStartFailure();
                }
            } else {
                Timber.tag(TAG).d("   ...client NOT ok, can't start tracking");
                response.locationTrackingStartFailure();
            }
        } else {
            Timber.tag(TAG).d("   ...already tracking, do nothing");
            response.locationTrackingStartSuccess();
        }
        Timber.tag(TAG).d("...locationTrackingStartRequest COMPLETE");
    }

    private LocationRequest getLocationRequest(){

        LocationRequest locationRequest = LocationRequest.create();             // create a LocationRequest with default parameters
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);    // most accurate locations available
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        locationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);

        return locationRequest;
    }


    public void locationTrackingStopRequest(LocationTelemetryInterface.LocationTrackingStopResponse response){
        Timber.tag(TAG).d("locationTrackingStartRequest START...");

        if (isTracking){
            Timber.tag(TAG).d("   ...we are tracking, check the client");
            if (clientOk) {
                Timber.tag(TAG).d("   ...client is ok, so let's turn it off");
                try {
                    client.removeLocationUpdates(callback);
                    handlerThread.quitSafely();
                    Timber.tag(TAG).d("   ...removed location updates & quit thread handler");
                } catch (Exception e){
                    Timber.tag(TAG).w("      ...error while stopping location tracking");
                    Timber.tag(TAG).e(e);
                }
            } else {
                Timber.tag(TAG).d("   ...client is NOT ok, so do nothing");
            }
        } else {
            Timber.tag(TAG).d("   ...we aren't tracking, so do nothing");
        }
        isTracking = false;
        response.locationTrackingStopComplete();
        Timber.tag(TAG).d("...locationTrackingStartRequest COMPLETE");
    }



    public Boolean hasLastGoodPosition(){
        return lastGoodPositionSaved;
    }

    public LatLonLocation getLastGoodPosition(){
        return lastGoodPosition;
    }

    private void updateLastGoodPosition(Location location){
        Timber.tag(TAG).d("updatingLastGoodPosition START...");
        lastGoodPositionSaved = true;
        lastGoodPosition.setLatitude(location.getLatitude());
        lastGoodPosition.setLongitude(location.getLongitude());
        Timber.tag(TAG).d("   ...latitude -> " + location.getLatitude());
        Timber.tag(TAG).d("   ...longitude -> " + location.getLongitude());
        Timber.tag(TAG).d("...updatingLastGoodPosition COMPLETE");
    }

    /// onComplete listener interface
    public void onComplete(@NonNull Task<android.location.Location> task){
        Timber.tag(TAG).d("   ...onComplete!");
        if (task.isSuccessful()){
            Timber.tag(TAG).d("      ...was successful!");
            if (task.getResult() != null){
                Timber.tag(TAG).d("         ...got last location!");
                updateLastGoodPosition(task.getResult());
            } else {
                Timber.tag(TAG).d("         ...result was null!");
            }
        } else {
            Timber.tag(TAG).d("      ...was NOT successful!");
        }
    }

    ///
    ///  UpdateLastGoodPositionInterface
    ///
    public void locationUpdated(@NonNull Location location){
        Timber.tag(TAG).d("   ...location update received!");
        updateLastGoodPosition(location);
    }

}
