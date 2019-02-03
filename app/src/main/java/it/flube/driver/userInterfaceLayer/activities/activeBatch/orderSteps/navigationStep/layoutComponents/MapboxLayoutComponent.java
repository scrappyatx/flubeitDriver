/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.CalculateDistance;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnCameraDidChangeListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnCameraIsChangingListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnCameraWillChangeListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidBecomeIdleListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidFailLoadingMapListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidFinishLoadingMapListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidFinishLoadingStyleListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidFinishRenderingFrameListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnDidFinishRenderingMapListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnSourceChangedListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnWillStartLoadingMapListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnWillStartRenderingFrameListener;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.mapboxOnChangeListeners.MapboxOnWillStartRenderingMapListener;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.LatLonLocation;
import timber.log.Timber;

/**
 * Created on 9/19/2018
 * Project : Driver
 */
public class MapboxLayoutComponent implements
        OnMapReadyCallback, Style.OnStyleLoaded {

    private static final String TAG="MapboxLayoutComponent";

    private String activityGuid;

    private MapView mapView;
    private MapboxMap map;

    private Destination destination;
    private Double closeEnoughInFeet;
    private Boolean hasDestination;

    private LatLonLocation driverLocation;
    private Boolean hasDriverLocation;

    private Marker userLocationMarker;
    private MarkerOptions userLocationMarkerOptions;

    private Marker destLocationMarker;
    private MarkerOptions destLocationMarkerOptions;

    private Response response;

    public MapboxLayoutComponent(AppCompatActivity activity, Bundle savedInstanceState, String activityGuid, Response response){
        this.activityGuid = activityGuid;
        this.response = response;
        Timber.tag(TAG).d("creation START (%s)...", activityGuid);
        //Mapbox.getInstance(activity, activity.getString(R.string.mapbox_access_token));
        createAndInitializeMapView(activity, savedInstanceState);
        EventBus.getDefault().register(this);
        Timber.tag(TAG).d("...creation COMPLETE (%s)", activityGuid);
    }

    private void createAndInitializeMapView(AppCompatActivity activity, Bundle savedInstanceState){
        Timber.tag(TAG).d("createAndInitializeMapView (%s) ", activityGuid);

        this.mapView = (MapView) activity.findViewById(R.id.mapView);
        mapView.onCreate(null);

        logSavedInstanceState(savedInstanceState);
        addMapboxListeners();
        initializeLocationData();

        Timber.tag(TAG).d("   ...createUserLocationMarkerOptions (%s)", activityGuid);
        userLocationMarkerOptions = createUserLocationMarkerOptions(activity);
    }

    private void logSavedInstanceState(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Timber.tag(TAG).d("logSavedInstanceState -> savedInstanceState is null (%s)", activityGuid);
        } else {
            Timber.tag(TAG).d("logSavedInstanceState -> savedInstanceState is NOT null (%s)", activityGuid);
        }
    }

    private void initializeLocationData(){
        Timber.tag(TAG).d("initializeLocationData");
        hasDestination = false;

        if (AndroidDevice.getInstance().getLocationTelemetry().hasLastGoodPosition()){
            Timber.tag(TAG).d("   ...hasDriverLocation TRUE (%s)", activityGuid);
            hasDriverLocation = true;
            driverLocation = AndroidDevice.getInstance().getLocationTelemetry().getLastGoodPosition();
        } else {
            Timber.tag(TAG).d("   ...hasDriverLocation FALSE (%s)", activityGuid);
            hasDriverLocation = false;
        }
    }

    private void addMapboxListeners(){
        Timber.tag(TAG).d("addMapboxListeners (%s)", activityGuid);
        //camera
        Timber.tag(TAG).d("   ...adding camera listeners (%s)", activityGuid);
        mapView.addOnCameraWillChangeListener(new MapboxOnCameraWillChangeListener());
        mapView.addOnCameraDidChangeListener(new MapboxOnCameraDidChangeListener());
        //mapView.addOnCameraIsChangingListener(new MapboxOnCameraIsChangingListener());

        //loading map
        Timber.tag(TAG).d("   ...adding loading map listeners (%s)", activityGuid);
        mapView.addOnWillStartLoadingMapListener(new MapboxOnWillStartLoadingMapListener());
        mapView.addOnDidFailLoadingMapListener(new MapboxOnDidFailLoadingMapListener());
        mapView.addOnDidFinishLoadingMapListener(new MapboxOnDidFinishLoadingMapListener());

        //loading style
        Timber.tag(TAG).d("   ...adding loading style listeners (%s)", activityGuid);
        mapView.addOnDidFinishLoadingStyleListener(new MapboxOnDidFinishLoadingStyleListener());

        //rendering frame
        //mapView.addOnWillStartRenderingFrameListener(new MapboxOnWillStartRenderingFrameListener());
        //mapView.addOnDidFinishRenderingFrameListener(new MapboxOnDidFinishRenderingFrameListener());

        //rendering map
        Timber.tag(TAG).d("   ...adding rendering map listeners (%s)", activityGuid);
        mapView.addOnWillStartRenderingMapListener(new MapboxOnWillStartRenderingMapListener());
        mapView.addOnDidFinishRenderingMapListener(new MapboxOnDidFinishRenderingMapListener());

        //source changed
        Timber.tag(TAG).d("   ...adding source changed listeners (%s)", activityGuid);
        mapView.addOnSourceChangedListener(new MapboxOnSourceChangedListener());

        //idle
        Timber.tag(TAG).d("   ...adding idle listeners (%s)", activityGuid);
        mapView.addOnDidBecomeIdleListener(new MapboxOnDidBecomeIdleListener());
    }


    public void onStart() {
        mapView.onStart();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    public void onResume() {
        mapView.onResume();
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }




    public void onPause() {
        mapView.onPause();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
    }



    public void onStop(){
        mapView.onStop();
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
    }

    public void onLowMemory() {
        mapView.onLowMemory();
        Timber.tag(TAG).d("onLowMemory (%s)", activityGuid);
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState (%s)", activityGuid);
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }

    public void setDestination(AppCompatActivity activity, Destination destination, Double closeEnoughInFeet){
        this.destination = destination;
        this.closeEnoughInFeet = closeEnoughInFeet;
        hasDestination = true;

        destLocationMarkerOptions = createDestinationMarkerOptions(activity, destination);

        mapView.getMapAsync(this);
        Timber.tag(TAG).d("setDestination (%s)", activityGuid);
    }

    public void setVisible(){
        mapView.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("setVisible (%s)", activityGuid);
    }

    public void setInvisible(){
        mapView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("setInvisible (%s)", activityGuid);
    }

    public void setGone(){
        mapView.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone (%s)", activityGuid);
    }



    @SuppressWarnings( {"MissingPermission"})
        public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Timber.tag(TAG).d("     ...onMapReady (%s)", activityGuid);
        this.map = mapboxMap;
        //mapbox 7.0.0 or later
        this.map.setStyle(Style.MAPBOX_STREETS, this);
        //this.map.setStyle(Style.MAPBOX_STREETS);

        //stuffToDoWhenMapIsReady();
        Timber.tag(TAG).d("     ...onMapReady COMPLETE (%s)", activityGuid);

    }

    // needed in mapbox sdk 7.0.0 or later
    public void onStyleLoaded(@NonNull Style style){
        Timber.tag(TAG).d("     ...onStyleLoaded START (%s)", activityGuid);
        stuffToDoWhenMapIsReady();
    }

    private void stuffToDoWhenMapIsReady(){
        Timber.tag(TAG).d("   ...adding destination marker (%s)", activityGuid);
        updateOrCreateDestinationLocationMarker();

        Timber.tag(TAG).d("   ...adding driver marker (%s)", activityGuid);
        updateOrCreateUserLocationMarker();

        //now see if we are close enough
        if (isCloseEnough()){
            Timber.tag(TAG).d("   driver is close enough (%s)", activityGuid);
            response.driverIsCloseEnough();
        } else {
            Timber.tag(TAG).d("   driver is NOT close enough (%s)", activityGuid);
            response.driverIsNotCloseEnough();
        }

        Timber.tag(TAG).d("  ...zooming camera to destination (%s)", activityGuid);

        //now zoom to destination
        if (hasDriverLocation){
            //show both driver location and destination location on map
            Timber.tag(TAG).d("   ...building bounding box for driver location and destination (%s)", activityGuid);
            LatLngBounds latLngBounds = new LatLngBounds.Builder()
                    .include(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()))
                    .include(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()))
                    .build();

            Timber.tag(TAG).d("   ...easing camera (%s)", activityGuid);
            this.map.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 2000);

        } else {
            /// just show map centered on destination
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()))
                    .zoom(10)
                    .bearing(0)
                    .tilt(0)
                    .build();
            Timber.tag(TAG).d("  ...zooming camera to destination (%s)", activityGuid);
            this.map.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
        }

        Timber.tag(TAG).d("...onStyleLoaded COMPLETE (%s)", activityGuid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("LocationTrackingPositionChangedEvent (%s)", activityGuid);
        Timber.tag(TAG).d("   ... latitude  : " + event.getPosition().getLatitude());
        Timber.tag(TAG).d("   ... longitude : " + event.getPosition().getLongitude());

        //update driver location
        driverLocation = event.getPosition();
        hasDriverLocation = true;

        //update driver position on map
        updateOrCreateUserLocationMarker();

        //now see if we are close enough
        if (isCloseEnough()){
            Timber.tag(TAG).d("   driver is close enough (%s)", activityGuid);
            response.driverIsCloseEnough();
        } else {
            Timber.tag(TAG).d("   driver is not close enough (%s)", activityGuid);
            response.driverIsNotCloseEnough();
        }
    }

    private MarkerOptions createDestinationMarkerOptions(AppCompatActivity activity, Destination destination){
        Bitmap b;
        String title;

        switch (destination.getTargetType()){
            case SERVICE_PROVIDER:
                b =  MapIconUtilities.getServiceProviderActiveIcon(activity);
                title = "Service Provider";
                break;
            case CUSTOMER_HOME:
                b =  MapIconUtilities.getCustomerHomeActiveIcon(activity);
                title = "Customer Home";
                break;
            case CUSTOMER_WORK:
                b =  MapIconUtilities.getCustomerWorkActiveIcon(activity);
                title = "Customer Work";
                break;
            case OTHER:
            default:
                title = "Other";
                b =  MapIconUtilities.getOtherIcon(activity);
                break;
        }

        return new MarkerOptions()
                .title(title)
                .icon(IconFactory.getInstance(activity).fromBitmap(b));

    }

    private MarkerOptions createUserLocationMarkerOptions(AppCompatActivity activity){
        return new MarkerOptions()
                .title("Me")
                .icon(IconFactory.getInstance(activity).fromBitmap(MapIconUtilities.getDriverLocationIcon(activity)));

    }

    private void updateOrCreateDestinationLocationMarker(){
        Timber.tag(TAG).d("updateOrCreateDestinationLocationMarker... (%s)", activityGuid);
        if (hasDestination) {
            Timber.tag(TAG).d("   ...have destination (%s)", activityGuid);
            if (map!=null) {
                Timber.tag(TAG).d("   ...map is not null (%s)", activityGuid);
                if (destLocationMarker == null) {
                    Timber.tag(TAG).d("  need to create destLocationMarker (%s)", activityGuid);
                    destLocationMarkerOptions.setPosition(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()));
                    destLocationMarker = map.addMarker(destLocationMarkerOptions);
                } else {
                    Timber.tag(TAG).d("  need to update destLocationMarker (%s)", activityGuid);
                    destLocationMarker.setPosition(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()));
                    map.updateMarker(destLocationMarker);
                }
            }
        }
    }

    private void updateOrCreateUserLocationMarker(){
        Timber.tag(TAG).d("updateOrCreateUserLocationMarker... (%s)", activityGuid);
        if (hasDriverLocation) {
            Timber.tag(TAG).d("   ...have driver location (%s)", activityGuid);
            if (map!=null) {
                Timber.tag(TAG).d("   ...map is not null (%s)", activityGuid);
                if (userLocationMarker == null) {
                    Timber.tag(TAG).d("  need to create userLocationMarker (%s)", activityGuid);
                    userLocationMarkerOptions.setPosition(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));
                    userLocationMarker = map.addMarker(userLocationMarkerOptions);
                } else {
                    Timber.tag(TAG).d("  need to update userLocationMarker (%s)", activityGuid);
                    userLocationMarker.setPosition(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));
                    map.updateMarker(userLocationMarker);
                }
            }
        }
    }

    private boolean isCloseEnough(){
        if (hasDriverLocation) {
            return CalculateDistance.isCloseEnough(destination.getTargetLatLon(), driverLocation, closeEnoughInFeet);
        } else {
            return false;
        }
    }

    public interface Response {
        void driverIsCloseEnough();

        void driverIsNotCloseEnough();
    }

}
