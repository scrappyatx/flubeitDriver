/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailSave;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSave;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepSave;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchSaveCurrentData implements
        FirebaseBatchDetailSave.SaveBatchDetailResponse,
        FirebaseServiceOrderSave.SaveServiceOrderResponse,
        FirebaseOrderStepSave.SaveOrderStepResponse {

    private static final String TAG = "FirebaseActiveBatchSaveCurrentData";

    CloudDatabaseInterface.SaveCurrentActiveBatchDataResponse response;
    ResponseCounter responseCounter;

    public void saveCurrentActiveBatchData(DatabaseReference batchDataRef,
                                    BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep,
                                    CloudDatabaseInterface.SaveCurrentActiveBatchDataResponse response){

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.response = response;

        responseCounter = new ResponseCounter(3);
        new FirebaseBatchDetailSave().saveBatchDetailRequest(batchDataRef, batchDetail, this);
        new FirebaseServiceOrderSave().saveServiceOrderRequest(batchDataRef, serviceOrder, this);
        new FirebaseOrderStepSave().saveOrderStepRequest(batchDataRef, orderStep, this);
        Timber.tag(TAG).d("saving current active batch data...");
    }

    public void saveOrderStepComplete(){
        responseCounter.onResponse();
        Timber.tag(TAG).d("   ...orderStep complete -> response " + responseCounter.getCount().toString());
        checkIfFinished();
    }

    public void saveBatchDetailComplete(){
        responseCounter.onResponse();
        Timber.tag(TAG).d("   ...batchDetail complete -> response " + responseCounter.getCount().toString());
        checkIfFinished();
    }

    public void saveServiceOrderComplete(){
        responseCounter.onResponse();
        Timber.tag(TAG).d("   ...service order complete -> response " + responseCounter.getCount().toString());
        checkIfFinished();
    }

    private void checkIfFinished(){
        if (responseCounter.isFinished()){
            response.cloudDatabaseSaveCurrentActiveBatchDataComplete();
            Timber.tag(TAG).d("...COMPLETE");
        }
    }
}
