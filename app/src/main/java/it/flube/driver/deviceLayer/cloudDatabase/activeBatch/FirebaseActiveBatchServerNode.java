/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/29/2017
 * Project : Driver
 */

public class FirebaseActiveBatchServerNode implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseActiveBatchServerNode";

    private static final String CLIENT_ID_PROPERTY = "clientId";
    private static final String FIRST_NAME_PROPERTY = "firstName";
    private static final String BATCH_TYPE_PROPERTY = "batchType";
    private static final String BATCH_TITLE = "batchTitle";
    private static final String CURRENT_SERVICE_ORDER_TITLE = "currentServiceOrderTitle";
    private static final String CURRENT_STEP_TITLE = "currentStepTitle";
    private static final String CURRENT_STEP_TASK_TYPE_PROPERTY = "currentStepTaskType";
    private static final String CURRENT_STEP_START_TIME_PROPERTY = "currentStepStartedTimestamp";
    private static final String CURRENT_SERVICE_ORDER_SEQUENCE_PROPERTY = "currentServiceOrderSequence";
    private static final String CURRENT_STEP_SEQUENCE_PROPERTY = "currentStepSequence";
    private static final String SERVICE_ORDER_COUNT_PROPERTY = "serviceOrderCount";
    private static final String STEP_COUNT_PROPERTY = "stepCount";
    private static final String DRIVER_LOCATION_PROPERTY = "driverLocationWhenCurrentStepStarted";


    private static final String ACTIVE_BATCH_SERVER_NOTIFICATION_NODE = "userWriteable/activeBatches";

    public void activeBatchServerNodeUpdateRequest(DatabaseReference activeBatchRef, Driver driver,
                                                   BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());

        HashMap<String, Object> data = getBaselineData(driver, batchDetail, serviceOrder, step);

        activeBatchRef.child(ACTIVE_BATCH_SERVER_NOTIFICATION_NODE).child(batchDetail.getBatchGuid()).setValue(data).addOnCompleteListener(this);
    }

    public void activeBatchServerNodeUpdateRequest(DatabaseReference activeBatchRef, Driver driver,
                                                   BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());

        HashMap<String, Object> data = getBaselineData(driver, batchDetail, serviceOrder, step);
        data.put(DRIVER_LOCATION_PROPERTY, driverLocation);

        activeBatchRef.child(ACTIVE_BATCH_SERVER_NOTIFICATION_NODE).child(batchDetail.getBatchGuid()).setValue(data).addOnCompleteListener(this);

    }

    public void activeBatchServerNodeUpdateRequest(DatabaseReference activeBatchRef, String batchGuid){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        activeBatchRef.child(ACTIVE_BATCH_SERVER_NOTIFICATION_NODE).child(batchGuid).setValue(null).addOnCompleteListener(this);
    }

    private HashMap<String, Object> getBaselineData(Driver driver,
                                                    BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step) {

        HashMap<String, Object> data = new HashMap<String, Object>();

        data.put(CLIENT_ID_PROPERTY, driver.getClientId());
        data.put(FIRST_NAME_PROPERTY, driver.getNameSettings().getFirstName());

        data.put(BATCH_TYPE_PROPERTY, batchDetail.getBatchType().toString());

        data.put(BATCH_TITLE, batchDetail.getTitle());
        data.put(CURRENT_SERVICE_ORDER_TITLE, serviceOrder.getTitle());
        data.put(CURRENT_STEP_TITLE, step.getTitle());

        data.put(CURRENT_STEP_TASK_TYPE_PROPERTY, step.getTaskType().toString());
        data.put(CURRENT_STEP_START_TIME_PROPERTY, ServerValue.TIMESTAMP);

        data.put(CURRENT_SERVICE_ORDER_SEQUENCE_PROPERTY, serviceOrder.getSequence());
        data.put(SERVICE_ORDER_COUNT_PROPERTY, batchDetail.getServiceOrderCount());

        data.put(CURRENT_STEP_SEQUENCE_PROPERTY, step.getSequence());
        data.put(STEP_COUNT_PROPERTY, serviceOrder.getTotalSteps());

        Timber.tag(TAG).d("   baseline data...");
        Timber.tag(TAG).d("         clientId             --> " + driver.getClientId());
        Timber.tag(TAG).d("         driver firstName     --> " + driver.getNameSettings().getFirstName());

        Timber.tag(TAG).d("         batchType            --> " + batchDetail.getBatchType().toString());

        Timber.tag(TAG).d("         batchTitle           --> " + batchDetail.getTitle());
        Timber.tag(TAG).d("         serviceOrderTitle    --> " + serviceOrder.getTitle());
        Timber.tag(TAG).d("         stepTitle            --> " + step.getTitle());

        Timber.tag(TAG).d("         stepType             --> " + step.getTaskType().toString());

        Timber.tag(TAG).d("         serviceOrderSequence --> " + serviceOrder.getSequence());
        Timber.tag(TAG).d("         serviceOrderCount    --> " + batchDetail.getServiceOrderCount());

        Timber.tag(TAG).d("         stepSequence         --> " + step.getSequence());
        Timber.tag(TAG).d("         stepCount            --> " + serviceOrder.getTotalSteps());
        return data;
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
    }
}
