/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchSaveCurrentData;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor.FirebaseActiveBatchMonitor;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataDelete;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.FirebaseBatchDataSave;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.routeStops.FirebaseRouteStopListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepListGet;
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
import it.flube.driver.modelLayer.entities.RouteStop;
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
    private FirebaseActiveBatchMonitor firebaseActiveBatchMonitor;

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
        firebaseActiveBatchMonitor = new FirebaseActiveBatchMonitor(database.getReference(activeBatchNode), database.getReference(batchDataNode));

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
    public void saveDemoBatchDataRequest(BatchHolder batchHolder, SaveDemoBatchDataResponse response) {
        new FirebaseBatchDataSave().saveDemoBatchDataRequest(database.getReference(batchDataNode), batchHolder, response);
        Timber.tag(TAG).d("saving DEMO BATCH DATA ---> batch Guid -> " +  batchHolder.getBatch().getGuid());
    }

    public void deleteDemoBatchDataRequest(String batchGuid, DeleteDemoBatchDataResponse response) {
        new FirebaseBatchDataDelete().deleteDemoBatchDataRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("deleting DEMO BATCH DATA ---> batch Guid -> " + batchGuid);
    }


    public void addDemoBatchToScheduledBatchListRequest(String batchGuid, AddDemoBatchToScheduledBatchListResponse response) {
        new FirebaseScheduledBatchesAdd().addDemoBatchToScheduledBatchListRequest(database.getReference(scheduledBatchesNode), batchGuid, response);
        Timber.tag(TAG).d("adding batch to scheduled batch list : batch guid --> " + batchGuid);
    }

    public void removeDemoBatchFromScheduledBatchListRequest(String batchGuid, RemoveDemoBatchFromScheduledBatchListResponse response) {
        new FirebaseScheduledBatchesRemove().removeDemoBatchFromScheduledBatchListRequest(database.getReference(scheduledBatchesNode), batchGuid, response);
        Timber.tag(TAG).d("removing batch from scheduled batch list : batch guid --> " + batchGuid);
    }

    public void startDemoBatchRequest(String batchGuid, StartDemoBatchComplete response) {
        new FirebaseScheduledBatchStart().startBatchRequest(database.getReference(activeBatchNode), batchGuid, response);
        Timber.tag(TAG).d("starting demo batch : batchGuid " + batchGuid);
    }


    ///
    ///  BATCH DETAIL METHODS
    ///
    public void getBatchDetailRequest(String batchGuid, GetBatchDetailResponse response) {
        new FirebaseBatchDetailGet().getBatchDetailRequest(database.getReference(batchDataNode), batchGuid, response);
        Timber.tag(TAG).d("getting batch detail for batch guid : " + batchGuid);
    }

    /// ACTIVE BATCH METHODS
    public void saveCurrentActiveBatchData(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep, SaveCurrentActiveBatchDataResponse response) {
        new FirebaseActiveBatchSaveCurrentData().saveCurrentActiveBatchData(database.getReference(batchDataNode),
                batchDetail, serviceOrder, orderStep, response);
        Timber.tag(TAG).d("saving current active batch data");
    }

    public  void gotoNextStepRequest(GotoNextStepResponse response){

    }

    /// BATCH INFORMATION METHODS

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



}
