/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.FirebaseBatchForfeit;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchMonitor.FirebaseScheduledBatchesMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchStart.FirebaseScheduledBatchStart;

import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.ScheduledBatchFirebaseConstants.BATCH_FORFEIT_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.ScheduledBatchFirebaseConstants.BATCH_FORFEIT_RESPONSE_NODE;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public class ScheduledBatchFirebaseWrapper implements
        CloudScheduledBatchInterface {

    private static final String TAG = "ScheduledBatchFirebaseWrapper";

    private final String driverDb;
    private final String baseNodeBatchData;
    private final String baseNodeScheduledBatches;

    private String batchDataNode;
    private String scheduledBatchesNode;
    private String batchForfeitRequestNode;
    private String batchForfeitResponseNode;


    private FirebaseScheduledBatchesMonitor firebaseScheduledBatchesMonitor;

    public ScheduledBatchFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment, CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");
        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);
        
        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeScheduledBatches = cloudConfig.getCloudDatabaseBaseNodeScheduledBatches();
        Timber.tag(TAG).d("   baseNodeScheduledBatches = " + baseNodeScheduledBatches);
    }

    private void getNodes(Driver driver) {

        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        scheduledBatchesNode = baseNodeScheduledBatches+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getScheduledBatchesNode();
        Timber.tag(TAG).d("   ...scheduledBatchesNode = " + scheduledBatchesNode);

        batchForfeitRequestNode = BATCH_FORFEIT_REQUEST_NODE;
        Timber.tag(TAG).d("batchForfeitRequestNode = " + batchForfeitRequestNode);

        batchForfeitResponseNode = BATCH_FORFEIT_RESPONSE_NODE + "/" + driver.getClientId();
        Timber.tag(TAG).d("batchForfeitResponseNode = " + batchForfeitResponseNode);
    }

    ///
    /// MONITOR FOR SCHEDULED BATCHES
    ///
    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudScheduledBatchInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebaseScheduledBatchesMonitor = new FirebaseScheduledBatchesMonitor(FirebaseDatabase.getInstance(driverDb).getReference(scheduledBatchesNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), offersLists);

        Timber.tag(TAG).d("   ....startListening");
        firebaseScheduledBatchesMonitor.startListening();

        response.cloudScheduledBatchStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }


    public void stopMonitoringRequest(CloudScheduledBatchInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudScheduledBatchStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebaseScheduledBatchesMonitor != null) {
            Timber.tag(TAG).d("      ....firebaseScheduledBatchesMonitor exists, stopListening & set to null");
            firebaseScheduledBatchesMonitor.stopListening();
            firebaseScheduledBatchesMonitor = null;
        }
    }

    ///
    /// BATCH FORFEIT REQUEST
    ///
    public void batchForfeitRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.BatchForfeitResponse response){
        Timber.tag(TAG).d("batchForfeitRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseBatchForfeit().forfeitBatchRequest(FirebaseDatabase.getInstance(driverDb).getReference(scheduledBatchesNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), FirebaseDatabase.getInstance(driverDb).getReference(batchForfeitRequestNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchForfeitResponseNode), driver.getClientId(), batchGuid, response);
    }



    ///
    /// BATCH START REQUEST
    ///

    public void startScheduledBatchRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.StartScheduledBatchResponse response){
        Timber.tag(TAG).d("startScheduledBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseScheduledBatchStart().startScheduledBatchRequest(FirebaseDatabase.getInstance(driverDb).getReference(scheduledBatchesNode), batchGuid, response);
    }


    ////
    //// GETTERS for ScheduledBatchData
    ////        BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList

    public void getScheduledBatchSummaryRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseScheduledBatchSummaryGet().getBatchSummary(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }


    public void getScheduledBatchDetailRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.GetBatchDetailResponse response){
        Timber.tag(TAG).d("getBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseScheduledBatchDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }



    public void getScheduledBatchServiceOrderListRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseScheduledBatchServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }



    public void getScheduledBatchRouteStopListRequest(Driver driver, String batchGuid, CloudScheduledBatchInterface.GetRouteStopListResponse response){
        Timber.tag(TAG).d("getRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseScheduledBatchRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),batchGuid, response);
    }


    public void getScheduledBatchOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, CloudScheduledBatchInterface.GetOrderStepListResponse response){
        Timber.tag(TAG).d("getOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseScheduledBatchOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }

}
