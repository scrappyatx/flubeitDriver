/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.libbatchdata.entities.RouteStop;
import timber.log.Timber;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public class FirebasePersonalOfferRouteStopListGet implements ValueEventListener {
    private static final String TAG = "FirebasePersonalOfferRouteStopListGet";

    private static final String BATCH_DATA_ROUTE_STOPS_NODE = "routeStops";

    private CloudPersonalOfferInterface.GetRouteStopListResponse response;

    public void getRouteStopListRequest(DatabaseReference batchDataRef, String batchGuid, CloudPersonalOfferInterface.GetRouteStopListResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        Timber.tag(TAG).d("getting route stop list for batchGuid : " + batchGuid);

        batchDataRef.child(batchGuid).child(BATCH_DATA_ROUTE_STOPS_NODE)
                .addListenerForSingleValueEvent(this);

    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("...onDataChange");


        if (dataSnapshot.exists()) {

            Timber.tag(TAG).d("   ...route stop data FOUND!");
            Map<Integer, RouteStop> stopMap = new TreeMap<Integer, RouteStop>();
            ArrayList<RouteStop> routeStopList = new ArrayList<RouteStop>();

            try {
                RouteStop routeStop;
                for (DataSnapshot stop : dataSnapshot.getChildren()) {
                    routeStop = stop.getValue(RouteStop.class);

                    Timber.tag(TAG).d("      route stop :");
                    Timber.tag(TAG).d("         guid                : " + routeStop.getGuid());
                    Timber.tag(TAG).d("         batchDetailGuid     : " + routeStop.getBatchDetailGuid());
                    Timber.tag(TAG).d("         batchGuid           : " + routeStop.getBatchGuid());
                    Timber.tag(TAG).d("         description         : " + routeStop.getDescription());
                    Timber.tag(TAG).d("         sequence            : " + routeStop.getSequence());

                    //routeStopList.add(routeStop);
                    stopMap.put(routeStop.getSequence(), routeStop);
                }
                //put results into arraylist, should be sorted by sequence ascending in the treemap
                for (Map.Entry<Integer, RouteStop> entry : stopMap.entrySet()){
                    routeStopList.add(entry.getValue());
                    Timber.tag(TAG).d(" sequence : " + entry.getValue().getSequence() + " routeStopGuid : " + entry.getValue().getGuid());
                }

                response.cloudGetPersonalOfferRouteStopListSuccess(routeStopList);

            } catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
                response.cloudGetPersonalOfferRouteStopListFailure();
            }

        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.cloudGetPersonalOfferRouteStopListFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("...onCancelled -->  error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetPersonalOfferRouteStopListFailure();
    }

}
