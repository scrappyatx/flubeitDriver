/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public class BatchMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "BatchManageActivity";
    private static final String API_KEY = "pk.eyJ1Ijoic2NyYXBweWF0eCIsImEiOiJjajViNjNjNHUwdTNxMzNwMWg4ZWMwN2thIn0.olrIh8570OamyDRxCaHBuA";

    private ActivityNavigator navigator;
    private BatchMapController controller;
    private DrawerMenu drawer;

    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, API_KEY);

        setContentView(R.layout.activity_batch_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        Timber.tag(TAG).d("onCreate");
    }

    public void onMapReady(MapboxMap mapboxMap) {
        // Customize map with markers, polylines, etc.
        MarkerViewOptions markerViewOptions = new MarkerViewOptions()
                .position(new LatLng(30.545767,-97.757811 ))
                .title("Home")
                .snippet("Home Sweet Home");

        mapboxMap.addMarker(markerViewOptions);

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Timber.tag(TAG).d("onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mapView.getMapAsync(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.batch_map_activity_title);
        controller = new BatchMapController();

        //EventBus.getDefault().register(this);
        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onPause() {
        //EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();


        super.onPause();
        mapView.onPause();
        Timber.tag(TAG).d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        Timber.tag(TAG).d("onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }



}




