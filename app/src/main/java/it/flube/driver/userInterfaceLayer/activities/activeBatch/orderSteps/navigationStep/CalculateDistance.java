/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.location.Location;

import it.flube.libbatchdata.entities.LatLonLocation;
import timber.log.Timber;

/**
 * Created on 11/11/2017
 * Project : Driver
 */

public class CalculateDistance {
    private static final String TAG = "CalculateDistance";

    private static final Double EARTH_RADIUS = 20902230.9711;    //feet
    private static final Double METERS_TO_FEET = 3.28084;       // 1 meter = 3.28084 feet
    private static final Double DEFAULT_ZOOM_LEVEL = 10.0;

    public static Double getZoomLevel(LatLonLocation loc1, LatLonLocation loc2, Double minZoomLevel, Double maxZoomLevel){
        Timber.tag(TAG).d("getZoomLevel START...");

        Timber.tag(TAG).d("location 1:");
        Timber.tag(TAG).d("   latitude  : " + loc1.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc1.getLongitude());
        Timber.tag(TAG).d("location 2:");
        Timber.tag(TAG).d("   latitude  : " + loc2.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc2.getLongitude());

        Timber.tag(TAG).d("minZoomLevel : " + minZoomLevel);
        Timber.tag(TAG).d("maxZoomLevel : " + maxZoomLevel);

        float[] dist = new float[2];
        try {
            Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude(), dist);

            Double distanceInFeet = dist[0] * METERS_TO_FEET;
            Timber.tag(TAG).d("distance (ft)     : " + distanceInFeet.toString());

            Double initZoom = convertDistanceToZoom(distanceInFeet);
            Timber.tag(TAG).d("init Zoom level   : " + initZoom);

            if (initZoom < minZoomLevel) {
                Timber.tag(TAG).d("setting initZoom to minZoomLevel");
                initZoom = minZoomLevel;
            } else if (initZoom > maxZoomLevel) {
                Timber.tag(TAG).d("setting initZoom to maxZoomLevel");
                initZoom = maxZoomLevel;
            }
            Timber.tag(TAG).d("returning zoom level : " + initZoom);

            return initZoom;
        } catch (Exception e) {
            Timber.tag(TAG).w("exception calculating zoom level, returning default zoom level");
            return DEFAULT_ZOOM_LEVEL;
        }
    }

    private static Double convertDistanceToZoom(Double distance){
        if (distance > 290000) {
            return 6.5;
        } else if (distance > 190000) {
            return 7.5;
        } else if (distance > 90000) {
            return 8.5;
        } else if (distance > 65000) {
            return 9.0;
        } else if (distance > 49000) {
            return 9.5;
        } else if (distance > 29000) {
            return 10.0;
        } else if (distance > 20000) {
            return 10.5;
        } else if (distance > 13000) {
            return 11.0;
        } else if (distance > 11000) {
            return 11.5;
        } else if (distance > 8500) {
            return 12.0;
        } else if (distance > 6000) {
            return 12.5;
        } else if (distance > 3500) {
            return 13.0;
        } else if (distance > 2100) {
            return 13.5;
        } else if (distance > 800) {
            return 14.0;
        } else if (distance > 260) {
            return 14.5;
        } else {
            return 16.0;
        }
    }

    public static Boolean isCloseEnough(LatLonLocation loc1, LatLonLocation loc2, Double closeEnoughInFeet){
        Timber.tag(TAG).d("isCloseEnough START...");

        Timber.tag(TAG).d("location 1:");
        Timber.tag(TAG).d("   latitude  : " + loc1.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc1.getLongitude());
        Timber.tag(TAG).d("location 2:");
        Timber.tag(TAG).d("   latitude  : " + loc2.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc2.getLongitude());

        float[] dist = new float[2];
        try {
            Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude(), dist);

            Double distanceInFeet = dist[0] * METERS_TO_FEET;
            Timber.tag(TAG).d("distance (ft)     : " + distanceInFeet.toString());
            Timber.tag(TAG).d("close enough (ft) : " + closeEnoughInFeet);

            if (distanceInFeet > closeEnoughInFeet) {
                Timber.tag(TAG).d("not close enough");
                return false;
            } else {
                Timber.tag(TAG).d("close enough");
                return true;
            }
        } catch (Exception e){
            Timber.tag(TAG).w("exception calculating distance between two locations");
            return false;
        }
    }

    public static Boolean isCloseEnoughHaversine(LatLonLocation loc1, LatLonLocation loc2, Double closeEnoughInFeet){
        Timber.tag(TAG).d("isCloseEnoughHaversine START...");

        Timber.tag(TAG).d("location 1:");
        Timber.tag(TAG).d("   latitude  : " + loc1.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc1.getLongitude());
        Timber.tag(TAG).d("location 2:");
        Timber.tag(TAG).d("   latitude  : " + loc2.getLatitude());
        Timber.tag(TAG).d("   longitude : " + loc2.getLongitude());

        // calculated using haversine formula
        //http://www.movable-type.co.uk/scripts/latlong.html
        try {
            Double lat1 = Math.toRadians(loc1.getLatitude());
            Double lat2 = Math.toRadians(loc2.getLatitude());
            Timber.tag(TAG).d("lat1 (radians) : " + lat1);
            Timber.tag(TAG).d("lat2 (radians) : " + lat2);

            Double deltaLat = Math.toRadians((loc2.getLatitude() - loc1.getLatitude()));
            Double deltaLon = Math.toRadians((loc2.getLongitude() - loc1.getLongitude()));
            Timber.tag(TAG).d("deltaLat (radians) : " + deltaLat);
            Timber.tag(TAG).d("deltaLon (radians) : " + deltaLon);

            Double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                    (Math.pow(Math.sin(deltaLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2));
            Timber.tag(TAG).d("a : " + a);

            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Timber.tag(TAG).d("c : " + c);

            Double distanceInFeet = EARTH_RADIUS * c;

            Timber.tag(TAG).d("distance (ft)     : " + distanceInFeet.toString());
            Timber.tag(TAG).d("close enough (ft) : " + closeEnoughInFeet);

            if (distanceInFeet > closeEnoughInFeet) {
                Timber.tag(TAG).d("not close enough");
                return false;
            } else {
                Timber.tag(TAG).d("close enough");
                return true;
            }

        } catch (Exception e) {
            Timber.tag(TAG).w("exception calculating distance between two locations");
            Timber.tag(TAG).e(e);
            return false;
        }
    }

}
