/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;


import com.mapbox.mapboxsdk.geometry.LatLngBounds;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.RouteStop;
import timber.log.Timber;

/**
 * Created on 1/16/2018
 * Project : Driver
 */

public class MapboxUtilities {

    private static final String TAG = "MapboxUtilities";


    private static final double DEFAULT_LATITUDE = 30.545762;
    private static final double DEFAULT_LONGITUDE = -97.757865;

    private static final double HEADING_NORTH_EAST = 45;
    private static final double HEADING_SOUTH_WEST = 215;
    private static final double OFFSET_DISTANCE_METERS = 709; //1000 meters on a diagonal is 709 meters per side


    public static LatLngBounds getLatLngBoundsForRouteStops(ArrayList<RouteStop> routeList){

        Timber.tag(TAG).d("getLatLngBoundsForRouteStops START, routeList.size() = " + routeList.size());
        //create a builder to computer bounding rectangle for all locations
        LatLngBounds bounds;
        LatLng center;
        LatLng northEast;
        LatLng southWest;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (routeList.size() == 0) {
            /// default bounding box
            Timber.tag(TAG).d("   ...creating center from default point, northEast, southWest points");

            center = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
            northEast = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_NORTH_EAST);
            southWest = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_SOUTH_WEST);

            Timber.tag(TAG).d("   ...adding points to builder");
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(center.latitude, center.longitude));
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(northEast.latitude, center.longitude));
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(southWest.latitude, southWest.longitude));

            Timber.tag(TAG).d("   ...building bounds");
            bounds = builder.build();

        } else if (routeList.size() == 1) {

            Timber.tag(TAG).d("   ...creating center from single routeList point, northEast, southWest points");
            center = new LatLng(routeList.get(0).getLatLonLocation().getLatitude(), routeList.get(0).getLatLonLocation().getLongitude());
            northEast = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_NORTH_EAST);
            southWest = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_SOUTH_WEST);

            Timber.tag(TAG).d("   ...adding points to builder");
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(center.latitude, center.longitude));
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(northEast.latitude, center.longitude));
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(southWest.latitude, southWest.longitude));

            Timber.tag(TAG).d("   ...building bounds");
            bounds = builder.build();

        } else {
            // add all route stops to bounding box
            Timber.tag(TAG).d("   ...adding all points in routeList to builder");
            for (RouteStop routeStop : routeList) {
                Timber.tag(TAG).d("      ...adding sequence " + routeStop.getSequence().toString() + " : " + routeStop.getDescription());
                //add this position to the lat lng bounds
                builder.include((new com.mapbox.mapboxsdk.geometry.LatLng(routeStop.getLatLonLocation().getLatitude(), routeStop.getLatLonLocation().getLongitude())));
            }
            bounds = builder.build();

            Timber.tag(TAG).d("   ...creating center, northEast, southWest points");
            center = new LatLng(bounds.getCenter().getLatitude(), bounds.getCenter().getLongitude());
            northEast = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_NORTH_EAST);
            southWest = SphericalUtil.computeOffset(center, OFFSET_DISTANCE_METERS, HEADING_SOUTH_WEST);

            Timber.tag(TAG).d("   ...adding points to builder");
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(northEast.latitude, center.longitude));
            builder.include(new com.mapbox.mapboxsdk.geometry.LatLng(southWest.latitude, southWest.longitude));

            Timber.tag(TAG).d("   ...building bounds");
            bounds = builder.build();

            // add
        }

        Timber.tag(TAG).d("...getLatLngBoundsForRouteStops END");
        return bounds;
    }
}
