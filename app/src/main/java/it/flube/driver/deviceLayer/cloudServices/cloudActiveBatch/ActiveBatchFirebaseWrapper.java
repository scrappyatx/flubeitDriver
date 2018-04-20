/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.acknowledgeRemovedBatch.FirebaseActiveBatchAcknowledgeRemovedBatch;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.FirebaseActiveBatchNodeMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchStart;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.saveMapLocation.FirebaseBatchDataSaveMapLocation;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish.FirebaseActiveBatchStepFinishPrep;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.acknowledgeFinishedBatch.FirebaseActiveBatchAcknowledgeFinishedBatch;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.updateActiveBatchServerNode.FirebaseActiveBatchServerNode;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.updateCompletedBatchesServerNode.FirebaseCompletedBatchesServerNode;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class ActiveBatchFirebaseWrapper implements
        CloudActiveBatchInterface {

    private static final String TAG = "ActiveBatchFirebaseWrapper";

    private static final String ACTIVE_BATCH_SERVER_NOTIFICATION_NODE = "userWriteable/activeBatches";
    private static final String COMPLETED_BATCH_SERVER_NOTIFICATION_NODE = "userWriteable/completedBatches";


    private final String baseNodeBatchData;
    private final String baseNodeActiveBatch;

    private String activeBatchNode;
    private String batchDataNode;
    private String activeBatchNotificationNode;
    private String completedBatchNotificationNode;


    private FirebaseActiveBatchNodeMonitor firebaseActiveBatchMonitor;

    public ActiveBatchFirebaseWrapper(CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");

        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeActiveBatch = cloudConfig.getCloudDatabaseBaseNodeActiveBatch();
        Timber.tag(TAG).d("   baseNodeActiveBatch = " + baseNodeActiveBatch);
    }

    private void getNodes(Driver driver) {

        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        activeBatchNode = baseNodeActiveBatch+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getActiveBatchNode();
        Timber.tag(TAG).d("activeBatchNode = " + activeBatchNode);

        activeBatchNotificationNode = ACTIVE_BATCH_SERVER_NOTIFICATION_NODE;
        Timber.tag(TAG).d("activeBatchNotificationNode = " + activeBatchNotificationNode);

        completedBatchNotificationNode = COMPLETED_BATCH_SERVER_NOTIFICATION_NODE;
        Timber.tag(TAG).d("completedBatchNotificationNode = " + completedBatchNotificationNode);
    }
    ///
    /// MONITOR FOR ACTIVE BATCH
    ///
    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudActiveBatchInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebaseActiveBatchMonitor = new FirebaseActiveBatchNodeMonitor(FirebaseDatabase.getInstance().getReference(activeBatchNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode));

        Timber.tag(TAG).d("   ....startListening");
        firebaseActiveBatchMonitor.startListening();

        response.cloudActiveBatchStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }



    public void stopMonitoringRequest(CloudActiveBatchInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudActiveBatchStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebaseActiveBatchMonitor != null) {
            Timber.tag(TAG).d("      ....firebaseActiveBatchMonitor exists, stopListening & set to null");
            firebaseActiveBatchMonitor.stopListening();
            firebaseActiveBatchMonitor = null;
        }
    }


    ////
    ////  STARTING AN ACTIVE BATCH
    ////
    public void startActiveBatchRequest(Driver driver, String batchGuid, ActiveBatchManageInterface.ActorType actorType, StartActiveBatchResponse response){
        Timber.tag(TAG).d("startActiveBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...keeping data data for the active batch synced");
        FirebaseDatabase.getInstance().getReference(batchDataNode).child(batchGuid).keepSynced(true);

        new FirebaseActiveBatchStart().startBatchRequest(FirebaseDatabase.getInstance().getReference(activeBatchNode),batchGuid, actorType, response);
    }


    ////
    //// DOING THE ACTIVE BATCH
    ////
    public void finishActiveBatchStepRequest(Driver driver, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.FinishActiveBatchStepResponse response){
        Timber.tag(TAG).d("finishActiveBatchStepRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchStepFinishPrep().finishStepRequest(FirebaseDatabase.getInstance().getReference(activeBatchNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode), actorType, response);
    }



    public void acknowledgeFinishedBatchRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.AcknowledgeFinishedBatchResponse response){
        Timber.tag(TAG).d("acknowledgeFinishedBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...removing sync node for this batch data");
        FirebaseDatabase.getInstance().getReference(batchDataNode).child(batchGuid).keepSynced(false);

        new FirebaseActiveBatchAcknowledgeFinishedBatch().acknowledgeFinishedBatch(FirebaseDatabase.getInstance().getReference(activeBatchNode), response);
    }



    public void acknowledgeRemovedBatchRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse response){
        Timber.tag(TAG).d("acknowledgeRemovedBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...removing sync node for this batch data");
        FirebaseDatabase.getInstance().getReference(batchDataNode).child(batchGuid).keepSynced(false);

        new FirebaseActiveBatchAcknowledgeRemovedBatch().acknowledgeRemovedBatch(FirebaseDatabase.getInstance().getReference(activeBatchNode), response);
    }



    public void saveMapLocationRequest(Driver driver, String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                LatLonLocation location, CloudActiveBatchInterface.SaveMapLocationResponse response){

        Timber.tag(TAG).d("saveMapLocationRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseBatchDataSaveMapLocation().saveMapLocation(FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, location, response);

    }



    ////
    //// STATUS MONITORING & TRACKING OF THE ACTIVE BATCH
    ////

    public void updateActiveBatchServerNodeStatus(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){
        Timber.tag(TAG).d("updateActiveBatchServerNodeStatus START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(FirebaseDatabase.getInstance().getReference(activeBatchNotificationNode),
                driver, batchDetail, serviceOrder, step);
    }

    public void updateActiveBatchServerNodeStatus(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation){
        Timber.tag(TAG).d("updateActiveBatchServerNodeStatus START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(FirebaseDatabase.getInstance().getReference(activeBatchNotificationNode),
                driver, batchDetail, serviceOrder, step, driverLocation);
    }

    public void updateActiveBatchServerNodeStatus(Driver driver, String batchGuid){
        Timber.tag(TAG).d("updateActiveBatchServerNodeStatus START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(FirebaseDatabase.getInstance().getReference(activeBatchNotificationNode),
                batchGuid);
    }

    ///    sets the server node for a completed batch

    public void updateBatchCompletedServerNode(Driver driver, String batchGuid){
        Timber.tag(TAG).d("updateBatchCompletedServerNode START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseCompletedBatchesServerNode().setCompletedBatchRequest(FirebaseDatabase.getInstance().getReference(completedBatchNotificationNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode),driver, batchGuid);
    }

    ///
    ///  GETTERS FOR ACTIVE BATCH DATA
    ///        BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList
    ///

    public void getActiveBatchSummaryRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getActiveBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseActiveBatchSummaryGet().getBatchSummary(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchDetailRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetBatchDetailResponse response){
        Timber.tag(TAG).d("getBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchServiceOrderListRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchRouteStopListRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetRouteStopListResponse response){
        Timber.tag(TAG).d("getRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode),batchGuid, response);
    }



    public void getActiveBatchOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, CloudActiveBatchInterface.GetOrderStepListResponse response){
        Timber.tag(TAG).d("getOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }


}
