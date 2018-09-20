/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.CalculateDistance;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.LatLonLocation;
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
 * Created on 9/19/2018
 * Project : Driver
 */
public class MapboxLayoutComponent implements
        OnMapReadyCallback {

    private static final String TAG="MapboxLayoutComponent";

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

    public MapboxLayoutComponent(AppCompatActivity activity, Bundle savedInstanceState, Response response){

        this.response = response;

        Mapbox.getInstance(activity, activity.getString(R.string.mapbox_access_token));

        mapView = (MapView) activity.findViewById(R.id.mapView);
        mapView.addOnMapChangedListener(new MapboxOnChangeListener());

        mapView.onCreate(savedInstanceState);

        hasDestination = false;

        if (AndroidDevice.getInstance().getLocationTelemetry().hasLastGoodPosition()){
            Timber.tag(TAG).d("hasDriverLocation TRUE");
            hasDriverLocation = true;
            driverLocation = AndroidDevice.getInstance().getLocationTelemetry().getLastGoodPosition();
        } else {
            Timber.tag(TAG).d("hasDriverLocation FALSE");
            hasDriverLocation = false;
        }

        userLocationMarkerOptions = createUserLocationMarkerOptions(activity);

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("created");
    }

    public void onStart() {
        mapView.onStart();
        Timber.tag(TAG).d("onStart");
    }

    public void onPause() {
        mapView.onPause();
        Timber.tag(TAG).d("onPause");
    }

    public void onResume() {
        mapView.onResume();
        Timber.tag(TAG).d("onResume");
    }

    public void onStop(){
        mapView.onStop();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

    public void setDestination(AppCompatActivity activity, Destination destination, Double closeEnoughInFeet){
        this.destination = destination;
        this.closeEnoughInFeet = closeEnoughInFeet;
        hasDestination = true;

        destLocationMarkerOptions = createDestinationMarkerOptions(activity, destination);

        mapView.getMapAsync(this);
        Timber.tag(TAG).d("setDestination");
    }

    public void setVisible(){
        mapView.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("setVisible");
    }

    public void setInvisible(){
        mapView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        mapView.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone");
    }



    @SuppressWarnings( {"MissingPermission"})
    public void onMapReady(MapboxMap mapboxMap) {
        Timber.tag(TAG).d("onMapReady START...");
        this.map = mapboxMap;


        Timber.tag(TAG).d("   ...adding destination marker");
        updateOrCreateDestinationLocationMarker();

        Timber.tag(TAG).d("   ...adding driver marker");
        updateOrCreateUserLocationMarker();

        //now see if we are close enough
        if (isCloseEnough()){
            Timber.tag(TAG).d("   driver is close enough");
            response.driverIsCloseEnough();
        } else {
            Timber.tag(TAG).d("   driver is NOT close enough");
            response.driverIsNotCloseEnough();
        }

        Timber.tag(TAG).d("  ...zooming camera to destination");
        //now zoom to destination
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()))
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

        Timber.tag(TAG).d("...onMapReady COMPLETE");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("LocationTrackingPositionChangedEvent");
        Timber.tag(TAG).d("   ... latitude  : " + event.getPosition().getLatitude());
        Timber.tag(TAG).d("   ... longitude : " + event.getPosition().getLongitude());

        //update driver location
        driverLocation = event.getPosition();
        hasDriverLocation = true;

        //update driver position on map
        updateOrCreateUserLocationMarker();

        //now see if we are close enough
        if (isCloseEnough()){
            Timber.tag(TAG).d("   driver is close enough");
            response.driverIsCloseEnough();
        } else {
            Timber.tag(TAG).d("   driver is not close enough");
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
        Timber.tag(TAG).d("updateOrCreateDestinationLocationMarker...");
        if (hasDestination) {
            Timber.tag(TAG).d("   ...have destination");
            if (map!=null) {
                Timber.tag(TAG).d("   ...map is not null");
                if (destLocationMarker == null) {
                    Timber.tag(TAG).d("  need to create destLocationMarker");
                    destLocationMarkerOptions.setPosition(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()));
                    destLocationMarker = map.addMarker(destLocationMarkerOptions);
                } else {
                    Timber.tag(TAG).d("  need to update destLocationMarker");
                    destLocationMarker.setPosition(new LatLng(destination.getTargetLatLon().getLatitude(), destination.getTargetLatLon().getLongitude()));
                    map.updateMarker(destLocationMarker);
                }
            }
        }
    }

    private void updateOrCreateUserLocationMarker(){
        Timber.tag(TAG).d("updateOrCreateUserLocationMarker...");
        if (hasDriverLocation) {
            Timber.tag(TAG).d("   ...have driver location");
            if (map!=null) {
                Timber.tag(TAG).d("   ...map is not null");
                if (userLocationMarker == null) {
                    Timber.tag(TAG).d("  need to create userLocationMarker");
                    userLocationMarkerOptions.setPosition(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));
                    userLocationMarker = map.addMarker(userLocationMarkerOptions);
                } else {
                    Timber.tag(TAG).d("  need to update userLocationMarker");
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
