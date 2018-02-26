/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.telemetry.location.LocationEngine;

import com.mapbox.services.api.directions.v5.models.DirectionsRoute;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
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

    public void finishStep(String milestoneEvent){
        Timber.tag(TAG).d("finishing STEP");
        useCaseExecutor.execute(new UseCaseFinishCurrentStepRequest(device, milestoneEvent, new UseCaseFinishCurrentStepResponseHandler()));
    }

    public void manuallyConfirmArrival(Context activityContext, ServiceOrderNavigationStep step) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);

        builder.setMessage("I have arrived at the destination, but GPS is not working");
        builder.setTitle("Confirm Arrival");
        builder.setPositiveButton("OK", new YesClick(step));
        builder.setNegativeButton("Cancel", new NoClick());

        AlertDialog dialog = builder.create();
        dialog.show();

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

    private class YesClick implements DialogInterface.OnClickListener {
        private ServiceOrderNavigationStep step;

        public YesClick(ServiceOrderNavigationStep step){
            this.step = step;
        }

        public void onClick(DialogInterface dialog, int id) {
            // User clicked OK button
            Timber.tag(TAG).d("user clicked yes");
            finishStep(step.getMilestoneWhenFinished());
        }
    }

    private class NoClick implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            // User clicked No button
            Timber.tag(TAG).d("user clicked no");
        }
    }

}
