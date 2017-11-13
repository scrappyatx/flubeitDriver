/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchRemove;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchServerNode;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchSetData;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.FirebaseActiveBatchNodeMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchAcknowledgeFinishedBatch;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchAcknowledgeRemovedBatch;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchStepFinishPrep;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataDelete;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataSaveBlob;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.routeStops.FirebaseRouteStopListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepSetWorkStage;
import it.flube.driver.deviceLayer.cloudDatabase.completedBatches.FirebaseCompletedBatchesServerNode;
import it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers.FirebaseDemoOffersAdd;
import it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers.FirebaseDemoOffersMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.deviceAndUser.FirebaseDevice;
import it.flube.driver.deviceLayer.cloudDatabase.deviceAndUser.FirebaseUser;
import it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers.FirebaseDemoOffersRemove;
import it.flube.driver.deviceLayer.cloudDatabase.offers.personalOffers.FirebasePersonalOffersMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.offers.publicOffers.FirebasePublicOffersMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches.FirebaseScheduledBatchStart;
import it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches.FirebaseScheduledBatchesAdd;
import it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches.FirebaseScheduledBatchesMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches.FirebaseScheduledBatchesRemove;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebaseWrapper implements
        CloudDatabaseInterface {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudDatabaseFirebaseWrapper instance = new CloudDatabaseFirebaseWrapper();
    }

    private CloudDatabaseFirebaseWrapper() {}

    public static CloudDatabaseFirebaseWrapper getInstance() {
        return CloudDatabaseFirebaseWrapper.Loader.instance;
    }

    private static final String TAG = "CloudDatabaseFirebaseWrapper";

    private FirebaseDatabase database;
    private Driver driver;

    private String demoOffersNode;
    private String userNode;
    private String deviceNode;
    private String batchDataNode;
    private String scheduledBatchesNode;
    private String personalOffersNode;
    private String publicOffersNode;
    private String activeBatchNode;

    private String syncNodeForUserOwnedData;
    private String syncNodeForPublicOffers;
    private String syncNodeForPersonalOffers;

    private FirebaseDemoOffersMonitor firebaseDemoOffersMonitor;
    private FirebasePublicOffersMonitor firebasePublicOffersMonitor;
    private FirebasePersonalOffersMonitor firebasePersonalOffersMonitor;
    private FirebaseScheduledBatchesMonitor firebaseScheduledBatchesMonitor;
    private FirebaseActiveBatchNodeMonitor firebaseActiveBatchMonitor;

    public void connectRequest(AppRemoteConfigInterface remoteConfig, Driver driver, ConnectResponse response){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.goOnline();
        Timber.tag(TAG).d("setPersistenceEnabled TRUE for OFFLINE persistence");

        this.driver = driver;

        setupNodeStrings(remoteConfig, driver);

        //setup database to sync mobile with backend on the sync nodes
        database.getReference(syncNodeForUserOwnedData).keepSynced(true);
        database.getReference(syncNodeForPublicOffers).keepSynced(true);
        database.getReference(syncNodeForPersonalOffers).keepSynced(true);

        //setup persistent objects for monitors
        firebaseDemoOffersMonitor = new FirebaseDemoOffersMonitor(database.getReference(demoOffersNode), database.getReference(batchDataNode));
        firebasePublicOffersMonitor = new FirebasePublicOffersMonitor(database.getReference(publicOffersNode), database.getReference(batchDataNode));
        firebasePersonalOffersMonitor = new FirebasePersonalOffersMonitor(database.getReference(personalOffersNode), database.getReference(batchDataNode));
        firebaseScheduledBatchesMonitor = new FirebaseScheduledBatchesMonitor(database.getReference(scheduledBatchesNode), database.getReference(batchDataNode));
        firebaseActiveBatchMonitor = new FirebaseActiveBatchNodeMonitor(database.getReference(activeBatchNode), database.getReference(batchDataNode));

        response.cloudDatabaseConnectComplete();
    }

    private void setupNodeStrings(AppRemoteConfigInterface remoteConfig, Driver driver){
        userNode = remoteConfig.getCloudDatabaseBaseNodeUserData();
        Timber.tag(TAG).d("userNode = " + userNode);

        deviceNode = remoteConfig.getCloudDatabaseBaseNodeDeviceData();
        Timber.tag(TAG).d("deviceNode = " + deviceNode);

        demoOffersNode = remoteConfig.getCloudDatabaseBaseNodeDemoOffers() + "/" + driver.getClientId() + "/" + driver.getDemoOffersNode();
        Timber.tag(TAG).d("demoOffersNode = " + demoOffersNode);

        publicOffersNode = remoteConfig.getCloudDatabaseBaseNodePublicOffers()+ "/" + driver.getClientId() + "/" + driver.getPublicOffersNode();
        Timber.tag(TAG).d("publicOffersNode = " + publicOffersNode);

        personalOffersNode = remoteConfig.getCloudDatabaseBaseNodePersonalOffers()+ "/" + driver.getClientId() + "/" + driver.getPersonalOffersNode();
        Timber.tag(TAG).d("personalOffersNode = " + personalOffersNode);

        batchDataNode = remoteConfig.getCloudDatabaseBaseNodeBatchData() + "/" + driver.getClientId() + "/" + driver.getBatchDataNode();
        Timber.tag(TAG).d("batchDataNode = " + batchDataNode);

        scheduledBatchesNode = remoteConfig.getCloudDatabaseBaseNodeScheduledBatches()+ "/" + driver.getClientId() + "/" + driver.getScheduledBatchesNode();
        Timber.tag(TAG).d("scheduledBatchesNode = " + scheduledBatchesNode);

        activeBatchNode = remoteConfig.getCloudDatabaseBaseNodeActiveBatch()+ "/" + driver.getClientId() + "/" + driver.getActiveBatchNode();
        Timber.tag(TAG).d("activeBatchNode = " + activeBatchNode);

        syncNodeForUserOwnedData = "userOwned/users" + "/" + driver.getClientId();
        syncNodeForPublicOffers = "userReadable/publicOffers";
        syncNodeForPersonalOffers = "userReadable/users" + "/" + driver.getClientId();
    }

    public void disconnect(){

        firebaseDemoOffersMonitor = null;
        firebaseDemoOffersMonitor = null;
        firebasePersonalOffersMonitor = null;
        firebaseScheduledBatchesMonitor = null;
        firebaseActiveBatchMonitor = null;

        database.goOffline();
        database = null;
    }

    ///
    ///     LISTENING FOR OFFERS
    ///

    public void startMonitoring(){
        firebaseDemoOffersMonitor.startListening();
        firebasePublicOffersMonitor.startListening();
        firebasePersonalOffersMonitor.startListening();

        firebaseScheduledBatchesMonitor.startListening();
        firebaseActiveBatchMonitor.startListening();
    }

    public void stopMonitoring() {
        firebaseDemoOffersMonitor.stopListening();
        firebasePublicOffersMonitor.stopListening();
        firebasePersonalOffersMonitor.stopListening();

        firebaseScheduledBatchesMonitor.stopListening();
        firebaseActiveBatchMonitor.stopListening();
    }


    ///
    ///     USER METHODS
    ///
    public void saveUserRequest(CloudDatabaseInterface.SaveResponse response) {
        FirebaseUser firebaseUser = new FirebaseUser(database, userNode);
        firebaseUser.saveUserRequest(driver, response);
        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());

        firebaseUser.saveUserLastLogin(driver);
        Timber.tag(TAG).d("saving DRIVER lastLogin --> clientId " + driver.getClientId() + " name : " + driver.getDisplayName());
    }

    ///
    ///    DEVICE INFO METHODS
    ///

    public void saveDeviceInfoRequest(DeviceInfo deviceInfo, SaveDeviceInfoResponse response) {
        FirebaseDevice firebaseDevice = new FirebaseDevice(database, deviceNode);
        firebaseDevice.saveDeviceInfoRequest(driver, deviceInfo, response);
        Timber.tag(TAG).d("saving DEVICE INFO object --> device GUID : " + deviceInfo.getDeviceGUID());
    }

    ///
    ///     DEMO OFFER METHODS
    ///

    public void addDemoOfferToOfferListRequest(String batchGuid, CloudDatabaseInterface.AddDemoOfferToOfferListResponse response) {
        new FirebaseDemoOffersAdd().addDemoOfferToOfferListRequest(database.getReference(demoOffersNode),batchGuid, response);
        Timber.tag(TAG).d("add DEMO OFFER to offer list : batchGuid --> " + batchGuid);
    }

    public void removeDemoOfferFromOfferListRequest(String batchGuid, RemoveDemoOfferFromOfferListResponse response) {
        new FirebaseDemoOffersRemove().removeDemoOfferFromOfferListRequest(database.getReference(demoOffersNode), batchGuid, response);
        Timber.tag(TAG).d("removing DEMO OFFER from offer list : batchGuid --> " + batchGuid);
    }

    ///
    ///    DEMO BATCH DATA METHODS
    ///

    public void addDemoBatchToScheduledBatchListRequest(String batchGuid, AddDemoBatchToScheduledBatchListResponse response) {
        new FirebaseScheduledBatchesAdd().addDemoBatchToScheduledBatchListRequest(database.getReference(scheduledBatchesNode), batchGuid, response);
        Timber.tag(TAG).d("adding batch to scheduled batch list : batch guid --> " + batchGuid);
    }

    public void removeDemoBatchFromScheduledBatchListRequest(String batchGuid, RemoveDemoBatchFromScheduledBatchListResponse response) {
        new FirebaseScheduledBatchesRemove().removeDemoBatchFromScheduledBatchListRequest(database.getReference(scheduledBatchesNode), batchGuid, response);
        Timber.tag(TAG).d("removing batch from scheduled batch list : batch guid --> " + batchGuid);
    }

    ///
    ///  BATCH DATA METHODS
    ///

    public void saveBatchDataRequest(BatchHolder batchHolder, SaveBatchDataResponse response) {
        new FirebaseBatchDataSaveBlob().saveDemoBatchDataRequest(database.getReference(batchDataNode), batchHolder, response);
        Timber.tag(TAG).d("saving DEMO BATCH DATA ---> batch Guid -> " +  batchHolder.getBatch().getGuid());
    }

    public void deleteBatchDataRequest(String batchGuid, DeleteBatchDataResponse response) {
        new FirebaseBatchDataDelete().deleteDemoBatchDataRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("deleting DEMO BATCH DATA ---> batch Guid -> " + batchGuid);
    }


    ///
    ///   BATCH INFORMATION METHODS
    ///

    public void getBatchDetailRequest(String batchGuid, GetBatchDetailResponse response) {
        new FirebaseBatchDetailGet().getBatchDetailRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("getting batch detail for batch guid : " + batchGuid);
    }

    public void getServiceOrderListRequest(String batchGuid, CloudDatabaseInterface.GetServiceOrderListResponse response){
        new FirebaseServiceOrderListGet().getServiceOrderListRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("getting service order list for batch guid : " + batchGuid);
    }

    public void getRouteStopListRequest(String batchGuid, CloudDatabaseInterface.GetRouteStopListResponse response){
        new FirebaseRouteStopListGet().getRouteStopListRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("getting route stop list for batch guid : " + batchGuid);
    }

    public void getOrderStepListRequest(String batchGuid, String serviceOrderGuid, CloudDatabaseInterface.GetOrderStepListResponse response){
        new FirebaseOrderStepListGet().getOrderStepList(database.getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
        Timber.tag(TAG).d("getting step list for batch guid : " + batchGuid + " service order guid " + serviceOrderGuid);
    }


    ///
    ///  ACTIVE BATCH MANAGEMENT METHODS
    ///

    public void startScheduledBatchRequest(String batchGuid, ActorType actorType, CloudDatabaseInterface.StartScheduledBatchResponse response){
        new FirebaseScheduledBatchStart().startBatchRequest(database.getReference(activeBatchNode), batchGuid, actorType, response);
        Timber.tag(TAG).d("starting demo batch : batchGuid " + batchGuid);
    }


    public void removeActiveBatchRequest(ActorType actorType, CloudDatabaseInterface.RemoveActiveBatchResponse response){
        new FirebaseActiveBatchRemove().removeBatchRequest(database.getReference(activeBatchNode), actorType, response);
        Timber.tag(TAG).d("removing batch");

    }

    ///public void startActiveBatchStepRequest(ActorType actorType,
    ///                                        BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep,
     ///                                       CloudDatabaseInterface.StartActiveBatchStepResponse response){
////
///
///        Timber.tag(TAG).d("start active batch step");
///    }

    public void finishActiveBatchStepRequest(ActorType actorType, FinishActiveBatchStepResponse response) {

        new FirebaseActiveBatchStepFinishPrep().finishStepRequest(database.getReference(activeBatchNode),
                database.getReference(batchDataNode), actorType, response);
        Timber.tag(TAG).d("finish active batch step request");
    }

    public void acknowledgeFinishedBatchRequest(AcknowledgeFinishedBatchResponse response) {
        Timber.tag(TAG).d("acknowledge finished batch request");

        new FirebaseActiveBatchAcknowledgeFinishedBatch().acknowledgeFinishedBatch(database.getReference(activeBatchNode), response);

    }

    public void acknowledgeRemovedBatchRequest(AcknowledgeRemovedBatchResponse response) {
        Timber.tag(TAG).d("acknowledge removed batch step request");

        new FirebaseActiveBatchAcknowledgeRemovedBatch().acknowledgeRemovedBatch(database.getReference(activeBatchNode), response);
    }

    ///public void setActiveBatchNodesRequest(String batchGuid, Integer serviceOrderSequence, Integer stepSequence,
    ///                                       CloudDatabaseInterface.ActionType actionType,
    ///                                       CloudDatabaseInterface.ActiveBatchNodesUpdated response){
    ///    Timber.tag(TAG).d("set Active Batch node Request");
    ///    new FirebaseActiveBatchSetData().setDataRequest(database.getReference(activeBatchNode),
    ///            batchGuid, serviceOrderSequence, stepSequence,
    ///            actionType, ActorType.MOBILE_USER,
    ///            response);
    /// }

    /// public void setActiveBatchNodesNullRequest(CloudDatabaseInterface.ActiveBatchNodesUpdated response){
    ///    Timber.tag(TAG).d("set active batch nodes null request");
    ///    new FirebaseActiveBatchSetData().setDataNullRequest(database.getReference(activeBatchNode), response);
    /// }

    /// public void setBatchDetailStatusRequest(BatchDetail batchDetail, BatchDetail.WorkStatus status, BatchDetailStatusUpdated response) {
    ///    Timber.tag(TAG).d("set batch detail status request");
    ///    new FirebaseBatchDetailSetStatus().setBatchDetailStatusRequest(database.getReference(batchDataNode), batchDetail, status, response);
    /// }

    /// public void setServiceOrderStatusRequest(ServiceOrder serviceOrder, ServiceOrder.ServiceOrderStatus status, CloudDatabaseInterface.ServiceOrderStatusUpdated response){
    ///    Timber.tag(TAG).d("set service order status request");
    ///    new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(database.getReference(batchDataNode), serviceOrder, status, response);
    /// }

    /// public void setOrderStepWorkStageRequest(@NonNull OrderStepInterface step, @NonNull OrderStepInterface.WorkStage workStage, @NonNull OrderStepWorkStageUpdated response){
    ///    Timber.tag(TAG).d("set order step work stage request");
    ///    Timber.tag(TAG).d("    stepGuid --> " + step.getGuid());
    ///    new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(database.getReference(batchDataNode), step, workStage, response);
    /// }


    ////
    ////    Server Node Status -> Active Batch
    ////

    public void updateActiveBatchServerNodeStatus(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){
        Timber.tag(TAG).d("started active batch, putting notification on server node");
        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(database.getReference(), driver, batchDetail, serviceOrder, step);
    }

    public void updateActiveBatchServerNodeStatus(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation){
        Timber.tag(TAG).d("started active batch, putting notification on server node");
        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(database.getReference(), driver, batchDetail, serviceOrder, step, driverLocation);
    }

    public void updateActiveBatchServerNodeStatus(String batchGuid){
        Timber.tag(TAG).d("stopping active batch, putting notification on server node");
        new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(database.getReference(), batchGuid);
    }

    ///
    ///   Server Node Status -> Completed Batch
    ///

    public void updateBatchCompletedServerNode(BatchDetail batchDetail){
        Timber.tag(TAG).d("batch complete, putting notification on server node");
        new FirebaseCompletedBatchesServerNode().setCompletedBatchRequest(database.getReference(), driver, batchDetail);
    }


}
