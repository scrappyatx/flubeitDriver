/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDatabase;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.FirebaseActiveBatchNodeMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offersMonitor.FirebaseDemoOffersMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offersMonitor.FirebasePersonalOffersMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offersMonitor.FirebasePublicOffersMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchMonitor.FirebaseScheduledBatchesMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebaseWrapper {

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

    public CloudDatabaseFirebaseWrapper() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.goOnline();
        isMonitoring = false;
        isConnected = false;

        //TODO when a user logs out, need to set database to OFFLINE, then go backONLINE as part of a user connection attempt
    }



    private void setupNodeStrings(CloudConfigInterface remoteConfig, Driver driver){
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



}
