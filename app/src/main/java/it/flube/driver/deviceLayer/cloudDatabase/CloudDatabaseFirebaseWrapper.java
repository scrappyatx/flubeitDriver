/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchRemove;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchServerNode;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.FirebaseActiveBatchNodeMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchAcknowledgeFinishedBatch;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchAcknowledgeRemovedBatch;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps.FirebaseActiveBatchStepFinishPrep;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataDelete;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataSaveBlob;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.mapLocations.FirebaseBatchDataSaveMapLocation;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.routeStops.FirebaseRouteStopListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchForfeitRequest.BatchForfeitRequest;
import it.flube.driver.deviceLayer.cloudDatabase.completedBatches.FirebaseCompletedBatchesServerNode;
import it.flube.driver.deviceLayer.cloudDatabase.driverProfiles.FirebaseDriverProfileGet;
import it.flube.driver.deviceLayer.cloudDatabase.offerClaimRequest.OfferClaimRequest;
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
import it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.AddPhotoUploadTaskNotStarted;
import it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.MovePhotoUploadTaskToFailed;
import it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.MovePhotoUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.MovePhotoUploadTaskToInProgress;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebaseWrapper implements
        CloudDatabaseInterface,
        CloudDatabaseInterface.StopMonitoringResponse {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudDatabaseFirebaseWrapper instance = new CloudDatabaseFirebaseWrapper();
    }

    private CloudDatabaseFirebaseWrapper() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.goOnline();
        isMonitoring = false;
        isConnected = false;

        //TODO when a user logs out, need to set database to OFFLINE, then go backONLINE as part of a user connection attempt
    }

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
    private String driverProfileNode;
    private String publicOffersBatchDataNode;
    private String personalOffersBatchDataNode;

    private String offerClaimRequestNode;
    private String offerClaimResponseNode;

    private String batchForfeitRequestNode;


    private String syncNodeForUserOwnedData;
    private String syncNodeForPublicOffers;
    private String syncNodeForPersonalOffers;

    private FirebaseDemoOffersMonitor firebaseDemoOffersMonitor;
    private FirebasePublicOffersMonitor firebasePublicOffersMonitor;
    private FirebasePersonalOffersMonitor firebasePersonalOffersMonitor;
    private FirebaseScheduledBatchesMonitor firebaseScheduledBatchesMonitor;
    private FirebaseActiveBatchNodeMonitor firebaseActiveBatchMonitor;

    private Boolean isMonitoring;
    private Boolean isConnected;

    public void getUserProfileRequest(String clientId, String email, UserProfileResponse response){
        String driverProfileNode = "userReadable/driverProfiles";


        new FirebaseDriverProfileGet().getDriverProfile(database.getReference(driverProfileNode),
                clientId, email, response);

        Timber.tag(TAG).d("getUserProfileRequest : clientId -> " + clientId + " email -> " + email);
    }

    public void connectDriverRequest(AppRemoteConfigInterface remoteConfig, Driver driver, ConnectResponse response){
        Timber.tag(TAG).d("connectDriverRequest START...");

        Timber.tag(TAG).d("  ...setting driver to current user");
        this.driver = driver;

        Timber.tag(TAG).d("  ...start syncing syncNodes");
        setupNodeStrings(remoteConfig, driver);

        //setup database to sync mobile with backend on the sync nodes
        database.getReference(syncNodeForUserOwnedData).keepSynced(true);
        database.getReference(syncNodeForPublicOffers).keepSynced(true);
        database.getReference(syncNodeForPersonalOffers).keepSynced(true);

        Timber.tag(TAG).d("  ...create monitors for offers & active batch");
        //setup persistent objects for monitors
        firebaseDemoOffersMonitor = new FirebaseDemoOffersMonitor(database.getReference(demoOffersNode), database.getReference(batchDataNode));
        firebasePublicOffersMonitor = new FirebasePublicOffersMonitor(database.getReference(publicOffersNode), database.getReference(publicOffersBatchDataNode));
        firebasePersonalOffersMonitor = new FirebasePersonalOffersMonitor(database.getReference(personalOffersNode), database.getReference(personalOffersBatchDataNode));
        firebaseScheduledBatchesMonitor = new FirebaseScheduledBatchesMonitor(database.getReference(scheduledBatchesNode), database.getReference(batchDataNode));
        firebaseActiveBatchMonitor = new FirebaseActiveBatchNodeMonitor(database.getReference(activeBatchNode), database.getReference(batchDataNode));

        isConnected = true;
        response.cloudDatabaseConnectDriverComplete();
        Timber.tag(TAG).d("...connectDriverRequest COMPLETE");
    }

    private void setupNodeStrings(AppRemoteConfigInterface remoteConfig, Driver driver){
        userNode = remoteConfig.getCloudDatabaseBaseNodeUserData();
        Timber.tag(TAG).d("userNode = " + userNode);

        deviceNode = remoteConfig.getCloudDatabaseBaseNodeDeviceData();
        Timber.tag(TAG).d("deviceNode = " + deviceNode);

        demoOffersNode = remoteConfig.getCloudDatabaseBaseNodeDemoOffers() + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getDemoOffersNode();
        Timber.tag(TAG).d("demoOffersNode = " + demoOffersNode);

        publicOffersNode = remoteConfig.getCloudDatabaseBaseNodePublicOffers()+ "/" + driver.getCloudDatabaseSettings().getPublicOffersNode();
        Timber.tag(TAG).d("publicOffersNode = " + publicOffersNode);

        personalOffersNode = remoteConfig.getCloudDatabaseBaseNodePersonalOffers()+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getPersonalOffersNode();
        Timber.tag(TAG).d("personalOffersNode = " + personalOffersNode);

        batchDataNode = remoteConfig.getCloudDatabaseBaseNodeBatchData() + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("batchDataNode = " + batchDataNode);

        scheduledBatchesNode = remoteConfig.getCloudDatabaseBaseNodeScheduledBatches()+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getScheduledBatchesNode();
        Timber.tag(TAG).d("scheduledBatchesNode = " + scheduledBatchesNode);

        activeBatchNode = remoteConfig.getCloudDatabaseBaseNodeActiveBatch()+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getActiveBatchNode();
        Timber.tag(TAG).d("activeBatchNode = " + activeBatchNode);

        driverProfileNode = "userReadable/driverProfiles";
        Timber.tag(TAG).d("driverProfileNode = " + driverProfileNode);

        publicOffersBatchDataNode = "/userReadable/batchData";
        Timber.tag(TAG).d("publicOffersBatchDataNode = " + publicOffersBatchDataNode);

        personalOffersBatchDataNode = "/userReadable/batchData";
        Timber.tag(TAG).d("personalOffersBatchDataNode = " + personalOffersBatchDataNode);

        offerClaimRequestNode = "/userWriteable/claimOfferRequest";
        Timber.tag(TAG).d("offerClaimRequestNode = " + offerClaimRequestNode);

        offerClaimResponseNode = "/userReadable/claimOfferResponse";
        Timber.tag(TAG).d("offerClaimResponseNode = " + offerClaimResponseNode);

        batchForfeitRequestNode = "/userWriteable/batchForfeitRequest";
        Timber.tag(TAG).d("batchForfeitRequestNode = " + batchForfeitRequestNode);

        syncNodeForUserOwnedData = "userOwned/users" + "/" + driver.getClientId();
        syncNodeForPublicOffers = "userReadable/publicOffers";
        syncNodeForPersonalOffers = "userReadable/users" + "/" + driver.getClientId();
    }

    public void disconnectDriverRequest(DisconnectResponse response){
        Timber.tag(TAG).d("disconnectDriver START...");
        if (isConnected) {
            Timber.tag(TAG).d("   ...stop monitoring offers & active batch");
            stopMonitoring();
            Timber.tag(TAG).d("   ...stop syncing syncNodes");
            //setup database to sync mobile with backend on the sync nodes
            database.getReference(syncNodeForUserOwnedData).keepSynced(false);
            database.getReference(syncNodeForPublicOffers).keepSynced(false);
            database.getReference(syncNodeForPersonalOffers).keepSynced(false);


            Timber.tag(TAG).d("   ...setting offer & batch monitors to null");
            firebaseDemoOffersMonitor = null;
            firebaseDemoOffersMonitor = null;
            firebasePersonalOffersMonitor = null;
            firebaseScheduledBatchesMonitor = null;
            firebaseActiveBatchMonitor = null;

            Timber.tag(TAG).d("  ...setting driver to null");
            this.driver = null;
        } else {
            Timber.tag(TAG).d("   ...already disconnected");
        }

        isConnected = false;
        Timber.tag(TAG).d("...disconnectDriver COMPLETE");
        response.cloudDatabaseDisconnectDriverComplete();
    }

    public void cloudDatabaseStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...stop monitoring request COMPLETE during disconnect driver request");
    }

    ///
    ///     LISTENING FOR OFFERS
    ///

    public void startMonitoringRequest(CloudDatabaseInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");
        startMonitoring();
        response.cloudDatabaseStartMonitoringComplete();
        Timber.tag(TAG).d("...startMonitoringRequest COMPLETE");
    }

    private void startMonitoring(){
        Timber.tag(TAG).d("startMonitoring START...");
        if (isConnected) {
            Timber.tag(TAG).d("   ...we are connected");

            if (isMonitoring) {
                Timber.tag(TAG).d("   ...we are ALREADY monitoring, do nothing");
            } else {
                Timber.tag(TAG).d("   ...starting listeners for offers");
                firebaseDemoOffersMonitor.startListening();
                firebasePublicOffersMonitor.startListening();
                firebasePersonalOffersMonitor.startListening();

                Timber.tag(TAG).d("   ...starting listeners for batches");
                firebaseScheduledBatchesMonitor.startListening();
                firebaseActiveBatchMonitor.startListening();

                isMonitoring = true;
            }
        } else {
            Timber.tag(TAG).d("   ...we are NOT connected, do nothing");
        }

        Timber.tag(TAG).d("...startMonitoring COMPLETE");

    }

    public void stopMonitoringRequest(CloudDatabaseInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");
        stopMonitoring();
        response.cloudDatabaseStopMonitoringComplete();
        Timber.tag(TAG).d("...stopMonitoringRequest COMPLETE");
    }

    private void stopMonitoring() {
        Timber.tag(TAG).d("stopMonitoring START...");

        if (isConnected) {
            Timber.tag(TAG).d("   ...we are connected");
            if (isMonitoring) {
                Timber.tag(TAG).d("   ...stopping listeners for offers");
                firebaseDemoOffersMonitor.stopListening();
                firebasePublicOffersMonitor.stopListening();
                firebasePersonalOffersMonitor.stopListening();

                Timber.tag(TAG).d("   ...stopping listeners for batches");
                firebaseScheduledBatchesMonitor.stopListening();
                firebaseActiveBatchMonitor.stopListening();

                isMonitoring = false;
            } else {
                Timber.tag(TAG).d("   ...we are ALREADY not monitoring, do nothing");
            }
        } else {
            Timber.tag(TAG).d("   ...we are NOT connected, do nothing");
        }
        Timber.tag(TAG).d("...stopMonitoring COMPLETE");
    }


    ///
    ///     USER METHODS
    ///
    public void saveUserRequest(CloudDatabaseInterface.SaveResponse response) {
        FirebaseUser firebaseUser = new FirebaseUser(database, userNode);
        firebaseUser.saveUserRequest(driver, response);
        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getNameSettings().getDisplayName());

        firebaseUser.saveUserLastLogin(driver);
        Timber.tag(TAG).d("saving DRIVER lastLogin --> clientId " + driver.getClientId() + " name : " + driver.getNameSettings().getDisplayName());
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

    public void saveMapLocationRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                LatLonLocation location, CloudDatabaseInterface.SaveMapLocationResponse response){

        Timber.tag(TAG).d("save Map Location request");
        new FirebaseBatchDataSaveMapLocation().saveMapLocation(database.getReference(batchDataNode), batchGuid, serviceOrderGuid, orderStepGuid, location, response);

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

    ///
    ///  Photo Upload Tasks
    ///

    public void addPhotoUploadTaskToNotStartedRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                               String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                               AddPhotoUploadTaskResponse response){

        Timber.tag(TAG).d("addPhotoUploadTaskToNotStartedRequest");
        new AddPhotoUploadTaskNotStarted().addPhotoUploadTaskToNotStartedRequest(database.getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);



    }

    public void movePhotoUploadTaskToInProgress(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                         String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                         String sessionUriString, Double progress,
                                         CloudDatabaseInterface.MovePhotoUploadTaskInProgressResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToInProgress");
        new MovePhotoUploadTaskToInProgress().movePhotoUploadTaskToInProgress(database.getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName,
                sessionUriString, progress, response);

    }

    public void movePhotoUploadTaskToFinished(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                       String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                       CloudDatabaseInterface.MovePhotoUploadTaskFinishedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFinished");
        new MovePhotoUploadTaskToFinished().movePhotoUploadTaskToFinished(database.getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }

    public void movePhotoUploadTaskToFailed(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                     String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                     MovePhotoUploadTaskFailedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFailed");
        new MovePhotoUploadTaskToFailed().movePhotoUploadTaskToFailed(database.getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }

    ////
    ////  CLAIM OFFER REQUEST
    ////
    public void claimOfferRequest(String batchGuid, BatchDetail.BatchType batchType, ClaimOfferResponse response){
        Timber.tag(TAG).d("claimOfferRequest");
        new OfferClaimRequest().claimOfferRequest(database.getReference(offerClaimRequestNode),driver.getClientId(), batchGuid, batchType, response);
    }

    ////
    ////    BATCH FORFEIT REQUEST
    ////
    public void batchForfeitRequest(String batchGuid, BatchDetail.BatchType batchType, BatchForfeitResponse response){
        Timber.tag(TAG).d("batchForfeitRequest");
        new BatchForfeitRequest().batchForfeitRequest(database.getReference(batchForfeitRequestNode), driver.getClientId(), batchGuid, batchType, response);
    }

}
