/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.saveMapLocation;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 1/19/2018
 * Project : Driver
 */

public class FirebaseBatchDataSaveMapLocation
        implements OnCompleteListener<Void> {

    private static final String TAG = "FirebaseBatchDataSaveMapLocation";

    private static final String MAP_LOCATION_NODE = "mapLocations";

    private static final String BATCH_GUID_NODE = "batchGuid";
    private static final String SERVICE_ORDER_NODE = "serviceOrderGuid";
    private static final String ORDER_STEP_NODE = "stepGuid";

    private static final String LAT_LON_NODE = "location";
    private static final String LATITUDE_NODE = "latitude";
    private static final String LONGITUDE_NODE = "longitude";
    private static final String TIMESTAMP_NODE = "timestamp";


    private CloudActiveBatchInterface.SaveMapLocationResponse response;

    public void saveMapLocation(DatabaseReference batchDataRef,
                               String batchGuid, String orderGuid, String stepGuid,
                               LatLonLocation location,
                                CloudActiveBatchInterface.SaveMapLocationResponse response){

        Timber.tag(TAG).d("saveMapLocation START...");

        this.response = response;

        HashMap<String, Object> data = new HashMap<String, Object>();
        HashMap<String, Object> locationData = new HashMap<String, Object>();

        Timber.tag(TAG).d("   ...create locationData HashMap");
        // create location hashmap
        locationData.put(LATITUDE_NODE, location.getLatitude());
        locationData.put(LONGITUDE_NODE, location.getLongitude());

        Timber.tag(TAG).d("   ...create data HashMap");
        //create data hashmap
        data.put(TIMESTAMP_NODE, ServerValue.TIMESTAMP);
        data.put(BATCH_GUID_NODE, batchGuid);
        data.put(SERVICE_ORDER_NODE, orderGuid);
        data.put(ORDER_STEP_NODE, stepGuid);
        data.put(LAT_LON_NODE, locationData);

        Timber.tag(TAG).d("      ...batchGuid    = " + batchGuid);
        Timber.tag(TAG).d("      ...orderGuid    = " + orderGuid);
        Timber.tag(TAG).d("      ...stepGuid     = " + stepGuid);
        Timber.tag(TAG).d("      ...latitude     = " + location.getLatitude());
        Timber.tag(TAG).d("      ...longitude    = " + location.getLongitude());

        Timber.tag(TAG).d("   ...saving data to mapLocationNode");
        batchDataRef.child(batchGuid).child(MAP_LOCATION_NODE).push().setValue(data).addOnCompleteListener(this);
        Timber.tag(TAG).d("...saveMapLocation COMPLETE");
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudActiveBatchSaveMapLocationComplete();
    }

}
