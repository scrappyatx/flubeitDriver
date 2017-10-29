/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.support.v7.app.AppCompatActivity;

import com.mapbox.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.location.LostLocationEngine;

import com.mapbox.services.commons.models.Position;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import timber.log.Timber;

/**
 * Created on 10/15/2017
 * Project : Driver
 */

public class NavigationController implements
        CalculateRoute.CalculateRouteResponse {

    private final String TAG = "NavigationController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    private MapboxNavigation navigation;
    private LocationEngine locationEngine;

    private CalculateRoute calculateRoute;
    private String accessToken;


    @SuppressWarnings( {"MissingPermission"})
    public NavigationController(AppCompatActivity activity, String accessToken) {
        Timber.tag(TAG).d("controller CREATED");

        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();

        //this.accessToken = accessToken;

        //locationEngine = LostLocationEngine.getLocationEngine(activity);
        //locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        //locationEngine.activate();

        //navigation = new MapboxNavigation(activity,accessToken);
        //navigation.setLocationEngine(locationEngine);
    }

    public void startNavigation(AppCompatActivity activity, LatLonLocation origin, LatLonLocation destination){

        // Pass in your Amazon Polly pool id for speech synthesis using Amazon Polly
        // Set to null to use the default Android speech synthesizer
        //String awsPool = null;

        //Boolean simulateRoute = true;


        //calculateRoute = new CalculateRoute();

        //Position originPosition = Position.fromCoordinates(origin.getLongitude(), origin.getLatitude());
        //Position destinationPosition = Position.fromCoordinates(destination.getLongitude(), destination.getLatitude());

        //Point originPoint = Point.fromLngLat(origin.getLongitude(), origin.getLatitude());
        //Point destinationPoint = Point.fromLngLat(destination.getLongitude(), destination.getLatitude());


        //Timber.tag(TAG).d("requesting route...");
        //calculateRoute.getRouteRequest(accessToken, origin, destination, this);
        //NavigationLauncher.startNavigation(activity, originPoint, destinationPoint, awsPool, simulateRoute);
    }

    public void finishStep(){
        Timber.tag(TAG).d("finishing STEP");
        useCaseExecutor.execute(new UseCaseFinishCurrentStepRequest(device, new UseCaseFinishCurrentStepResponseHandler()));
    }


    public void close(){
        useCaseExecutor = null;
        device = null;
        //navigation.endNavigation();
        //navigation.onDestroy();

        Timber.tag(TAG).d("controller CLOSED");
    }

    public void getRouteSuccess(DirectionsRoute directionsRoute) {
        Timber.tag(TAG).d("...got a route!");

        // Pass in your Amazon Polly pool id for speech synthesis using Amazon Polly
        // Set to null to use the default Android speech synthesizer
       // String awsPoolId = null;

        //boolean simulateRoute = true;


    }

    public void getRouteFailure() {
        Timber.tag(TAG).d("... couldn't get a route");
    }

}
