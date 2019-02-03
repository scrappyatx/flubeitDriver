/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
//import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

import it.flube.driver.R;
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
import it.flube.libbatchdata.entities.RouteStop;
import timber.log.Timber;

/**
 * Created on 1/8/2018
 * Project : Driver
 */

public class TabLocationsLayoutComponents
        implements OnMapReadyCallback, Style.OnStyleLoaded {

    public final static String TAG = "TabLocationsLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_tab_locations_viewgroup.xml
    ///
    private ConstraintLayout layout;

    private MapView mapView;
    private MapboxMap map;
    private ArrayList<RouteStop> routeList;

    public TabLocationsLayoutComponents(AppCompatActivity activity, Bundle savedInstanceState){
        Timber.tag(TAG).d("creating...");

        //Mapbox.getInstance(activity, activity.getString(R.string.mapbox_access_token));

        layout = (ConstraintLayout) activity.findViewById(R.id.batch_tab_locations_viewgroup);

        mapView = (MapView) activity.findViewById(R.id.mapView);
        // add listeners - there's a shit ton of them

        // camera listeners
        mapView.addOnCameraWillChangeListener(new MapboxOnCameraWillChangeListener());
        mapView.addOnCameraDidChangeListener(new MapboxOnCameraDidChangeListener());
        //mapView.addOnCameraIsChangingListener(new MapboxOnCameraIsChangingListener());

        // map loading listener
        mapView.addOnWillStartLoadingMapListener(new MapboxOnWillStartLoadingMapListener());
        mapView.addOnDidFailLoadingMapListener(new MapboxOnDidFailLoadingMapListener());
        mapView.addOnDidFinishLoadingMapListener(new MapboxOnDidFinishLoadingMapListener());


        // style loading
        mapView.addOnDidFinishLoadingStyleListener(new MapboxOnDidFinishLoadingStyleListener());

        // frame rendering
        //mapView.addOnWillStartRenderingFrameListener(new MapboxOnWillStartRenderingFrameListener());
        //mapView.addOnDidFinishRenderingFrameListener(new MapboxOnDidFinishRenderingFrameListener());

        //map rendering
        mapView.addOnWillStartRenderingMapListener(new MapboxOnWillStartRenderingMapListener());
        mapView.addOnDidFinishRenderingMapListener(new MapboxOnDidFinishRenderingMapListener());

        // map source changes
        mapView.addOnSourceChangedListener(new MapboxOnSourceChangedListener());



        // map is idle
        mapView.addOnDidBecomeIdleListener(new MapboxOnDidBecomeIdleListener());

        mapView.onCreate(savedInstanceState);

        setInvisible();
        Timber.tag(TAG).d("...component created");
    }

    public void setValues(AppCompatActivity activity, ArrayList<RouteStop> routeList){

        this.routeList = new ArrayList<RouteStop>();
        this.routeList.clear();
        this.routeList.addAll(routeList);

        mapView.getMapAsync(this);
        Timber.tag(TAG).d("...setValues");
    }

    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Timber.tag(TAG).d("     ...onMapReady");
        this.map = mapboxMap;
        //this.map.setStyle(Style.MAPBOX_STREETS);
        // needed for mapbox sdk 7.0.0
        this.map.setStyle(Style.MAPBOX_STREETS, this);
        //addMarkersForRouteStops();
        Timber.tag(TAG).d("     ...onMapReady COMPLETE");
    }

    //
    // just needed for mapbox sdk 7.0.0
    public void onStyleLoaded(@NonNull Style style){
        Timber.tag(TAG).d("     ...onStyleLoaded");
        /// map the locations to visit on the map
        addMarkersForRouteStops();
        Timber.tag(TAG).d("     ...onStyleLoaded COMPLETE");
    }

    private void addMarkersForRouteStops(){
        Timber.tag(TAG).d("      ...addMarkersForRouteStops START");
        Timber.tag(TAG).d("         ...routeList.size() = " + routeList.size());
        if (routeList.size() <= 0){
            Timber.tag(TAG).d("      ...no locations, do nothing");
        } else {
            Timber.tag(TAG).d("      ...adding locations to map");
            doMarkers();
        }
        Timber.tag(TAG).d("      ...addMarkersForRouteStops START");
    }

    private void doMarkers(){
        Timber.tag(TAG).d("   ...adding markers START");

        for (RouteStop routeStop : routeList){
            Timber.tag(TAG).d("      ...adding sequence " + routeStop.getSequence().toString() + " : " + routeStop.getDescription());

            //add a marker for this route stop to the map
            Marker marker = map.addMarker(new MarkerOptions()
                    .title(routeStop.getDescription())
                    .snippet("Sequence : " + routeStop.getSequence().toString())
                    .position(new LatLng(routeStop.getLatLonLocation().getLatitude(), routeStop.getLatLonLocation().getLongitude())));

        }

        Timber.tag(TAG).d("      ...building lat/long bounding box");
        LatLngBounds bounds = MapboxUtilities.getLatLngBoundsForRouteStops(routeList);

        Timber.tag(TAG).d("      ...animating camera");
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        Timber.tag(TAG).d("      ...moving camera");
        map.moveCamera(cu);

        Timber.tag(TAG).d("   ...adding markers COMPLETE");
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
        mapView.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        mapView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.GONE);
        mapView.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }


    public void onStart(){
        mapView.onStart();
        Timber.tag(TAG).d("onStart");
    }
    public void onPause() {
        mapView.onPause();
        Timber.tag(TAG).d( "onPause");
    }

    public void onResume(){
        mapView.onResume();
        Timber.tag(TAG).d("onResume");
    }

    public void onStop(){
        mapView.onStop();
        Timber.tag(TAG).d("onStop");
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onLowMemory() {
        mapView.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    public void onDestroy() {
        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

}
