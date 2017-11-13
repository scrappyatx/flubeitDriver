/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;


import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.geojson.Point;
import com.mapbox.services.commons.models.Position;


import it.flube.driver.modelLayer.entities.LatLonLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created on 10/21/2017
 * Project : Driver
 */

public class CalculateRoute implements Callback<DirectionsResponse> {
    private static final String TAG = "CalculateRoute";

    private CalculateRouteResponse calcResponse;

    public void getRouteRequest(String mapboxAccessToken, LatLonLocation origin, LatLonLocation destination, CalculateRouteResponse calcResponse) {
        Timber.tag(TAG).d("calculating route...");
        this.calcResponse = calcResponse;

        Position originPosition = Position.fromCoordinates(origin.getLongitude(), origin.getLatitude());
        Position destinationPosition = Position.fromCoordinates(destination.getLongitude(), destination.getLatitude());

        Point originPoint = Point.fromCoordinates(originPosition);
        //fromLngLat(origin.getLongitude(), origin.getLatitude());
        Point destinationPoint = Point.fromCoordinates(destinationPosition);
                //fromLngLat(destination.getLongitude(), destination.getLatitude());

        NavigationRoute.builder()
                .accessToken(mapboxAccessToken)
                .origin(originPosition)
                .destination(destinationPosition)
                .build()
                .getRoute(this);
    }

    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

        Timber.tag(TAG).d("onResponse...");
        Timber.tag(TAG).d("   ...response code: " + response.code());

        if (response.body() == null) {

            Timber.tag(TAG).w("   ...FAILURE -> response.body() is null");
            calcResponse.getRouteFailure();

        } else if (response.body().getRoutes().size() < 1) {
            Timber.tag(TAG).w("   ...FAILURE -> getRoutes().size() = " + response.body().getRoutes().size());
           calcResponse.getRouteFailure();

        } else {

            Timber.tag(TAG).d("   ...SUCCESS -> getRoutes().size() = " + response.body().getRoutes().size());
            calcResponse.getRouteSuccess(response.body().getRoutes().get(0));
        }
        Timber.tag(TAG).d("COMPLETE onResponse");
    }

    public void onFailure(Call<DirectionsResponse> call, Throwable throwable){
        Timber.tag(TAG).w("onFailure --> error : " + throwable.getMessage());
        calcResponse.getRouteFailure();
    }

    public interface CalculateRouteResponse {
        void getRouteSuccess(DirectionsRoute directionsRoute);

        void getRouteFailure();
    }
}
