/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.content.Context;

import com.mapbox.mapboxsdk.Mapbox;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public class MapBoxWrapper {
    private static final String TAG = "MapBoxWrapper";
    private static final String API_KEY = "pk.eyJ1Ijoic2NyYXBweWF0eCIsImEiOiJjajViNjNjNHUwdTNxMzNwMWg4ZWMwN2thIn0.olrIh8570OamyDRxCaHBuA";


    public MapBoxWrapper(Context context){
        Mapbox.getInstance(context, API_KEY);
    }



}
