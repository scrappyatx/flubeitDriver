/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.updateActiveBatchServerNode;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchContactPersonsByServiceOrderGet;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/29/2017
 * Project : Driver
 */

public class FirebaseActiveBatchServerNode implements
        OnCompleteListener<Void>,
        CloudActiveBatchInterface.GetContactPersonsByServiceOrderResponse {

    private static final String TAG = "FirebaseActiveBatchServerNode";

    /// driverData node
    private static final String DRIVER_DATA_NODE = "driverData";
    private static final String CLIENT_ID_PROPERTY = "clientId";
    private static final String FIRST_NAME_PROPERTY = "firstName";
    private static final String PROXY_DIAL_NUMBER = "driverProxyDialNumber";
    private static final String PROXY_DISPLAY_NUMBER = "driverProxyDisplayNumber";

    /// currentBatch node
    private static final String CURRENT_BATCH_NODE = "currentBatch";
    private static final String BATCH_DETAIL_NODE = "batchDetail";
    private static final String CONTACT_PERSONS_BY_SERVICE_ORDER_NODE = "contactPersonsByServiceOrder";

    /// currentServiceOrder node
    private static final String CURRENT_SERVICE_ORDER_NODE = "currentServiceOrder";
    private static final String SERVICE_ORDER_NODE = "serviceOrder";

    /// currentStep node
    private static final String CURRENT_STEP_NODE = "currentStep";
    private static final String ORDER_STEP_NODE = "step";

    // driverLocation node
    private static final String DRIVER_LOCATION_NODE = "driverLocation";
    private static final String LAST_UPDATE_LOCATION = "lastUpdateLocation";
    private static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";

    private HashMap<String, Object> updateData;
    private DatabaseReference activeBatchRef;
    private String batchGuid;

    ////
    //// TODO this is inefficient, rewrites a lot of data on every update.  needs to be refactored with the "only start a step once" logic
    ////

    public void activeBatchServerNodeUpdateRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, Driver driver,
                                                   BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        this.activeBatchRef = activeBatchRef;
        this.batchGuid = batchDetail.getBatchGuid();

        updateData = getBaselineData(driver, batchDetail, serviceOrder, step);
        getContactPersonData(batchDataRef, batchDetail);
    }

    public void activeBatchServerNodeUpdateRequestWithLocation(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, Driver driver,
                                                   BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        this.activeBatchRef = activeBatchRef;
        this.batchGuid = batchDetail.getBatchGuid();

        updateData = getBaselineData(driver, batchDetail, serviceOrder, step);
        updateData.put(DRIVER_LOCATION_NODE, getDriverLocationNode(driverLocation));
        getContactPersonData(batchDataRef, batchDetail);
    }

    public void activeBatchServerNodeUpdateRequestRemove(DatabaseReference activeBatchRef, String batchGuid){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        activeBatchRef.child(batchGuid).setValue(null).addOnCompleteListener(this);
    }


    public void activeBatchServerNodeUpdateRequestLocationOnly(DatabaseReference activeBatchRef, String batchGuid, LatLonLocation driverLocation){
        updateData = new HashMap<String, Object>();
        updateData.put(DRIVER_LOCATION_NODE, getDriverLocationNode(driverLocation));
        activeBatchRef.child(batchGuid).updateChildren(updateData).addOnCompleteListener(this);
    }

    public void activeBatchServerNodeUpdateDriverProxyInfo(Driver driver, String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber){
        updateData = new HashMap<String, Object>();
        updateData.put(DRIVER_DATA_NODE, getDriverProxyInfo(driverProxyDialNumber, driverProxyDisplayNumber));
        activeBatchRef.child(batchGuid).updateChildren(updateData).addOnCompleteListener(this);
    }


    ////
    //// private methods for building data hashmap
    ////

    private HashMap<String, Object> getBaselineData(Driver driver,
                                                    BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step) {

        HashMap<String, Object> data = new HashMap<String, Object>();

        ///put in driverData
        data.put(DRIVER_DATA_NODE, getDriverDataNode(driver));

        ///put in currentBatch node
        data.put(CURRENT_BATCH_NODE, getCurrentBatchNode(batchDetail));

        //put in currentServiceOrder node
        data.put(CURRENT_SERVICE_ORDER_NODE, getCurrentServiceOrderNode(serviceOrder));

        //put in currentStep node
        data.put(CURRENT_STEP_NODE, getCurrentStepNode(step));

        Timber.tag(TAG).d("   baseline data...");
        return data;
    }

    private HashMap<String, Object> getDriverDataNode(Driver driver){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(CLIENT_ID_PROPERTY, driver.getClientId());
        data.put(FIRST_NAME_PROPERTY, driver.getNameSettings().getFirstName());
        return data;
    }

    private HashMap<String, Object> getCurrentBatchNode(BatchDetail batchDetail){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_DETAIL_NODE, batchDetail);
        return data;
    }

    private HashMap<String, Object> getCurrentServiceOrderNode(ServiceOrder serviceOrder){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(SERVICE_ORDER_NODE, serviceOrder);
        return data;
    }

    private HashMap<String, Object> getCurrentStepNode(OrderStepInterface step){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ORDER_STEP_NODE, step);
        return data;
    }

    private HashMap<String, Object> getDriverLocationNode(LatLonLocation driverLocation){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(LAST_UPDATE_LOCATION, driverLocation);
        data.put(LAST_UPDATE_TIMESTAMP, ServerValue.TIMESTAMP);
        return data;
    }

    private HashMap<String, Object> getDriverProxyInfo(String driverProxyDialNumber, String driverProxyDisplayNumber){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(PROXY_DIAL_NUMBER, driverProxyDialNumber);
        data.put(PROXY_DISPLAY_NUMBER, driverProxyDisplayNumber);
        return data;
    }

    private void getContactPersonData(DatabaseReference batchDataRef, BatchDetail batchDetail){
        /// add in contact person data
        new FirebaseActiveBatchContactPersonsByServiceOrderGet().getContactPersonsByServiceOrder(batchDataRef, batchDetail.getBatchGuid(), this);
    }

    ///
    /// CloudActiveBatchInterface.GetContactPersonsByServiceOrderResponse
    ///
    public void cloudGetActiveBatchContactPersonsByServiceOrderSuccess(ContactPersonsByServiceOrder contactList){
        // got data
        Timber.tag(TAG).d("cloudGetActiveBatchContactPersonsByServiceOrderSuccess");

        HashMap<String, Object> currentBatch = (HashMap<String, Object>) updateData.get(CURRENT_BATCH_NODE);
        currentBatch.put(CONTACT_PERSONS_BY_SERVICE_ORDER_NODE, contactList.getContactPersonsByServiceOrder());

        activeBatchRef.child(batchGuid).updateChildren(updateData).addOnCompleteListener(this);
    }

    public void cloudGetActiveBatchContactPersonsByServiceOrderFailure(){
        ///didn't get any data
        Timber.tag(TAG).d("cloudGetActiveBatchContactPersonsByServiceOrderFailure");
        activeBatchRef.child(batchGuid).updateChildren(updateData).addOnCompleteListener(this);
    }

    ///
    /// OnCompleteListener
    ///
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
    }


}
