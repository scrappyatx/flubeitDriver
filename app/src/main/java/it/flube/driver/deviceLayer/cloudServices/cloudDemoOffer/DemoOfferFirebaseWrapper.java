/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.demoBatch.FirebaseDemoBatchForfeit;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerAdd.FirebaseDemoOfferAdd;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim.FirebaseDemoOfferClaim;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offersMonitor.FirebaseDemoOffersMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import timber.log.Timber;

/**
 * Created on 3/24/2018
 * Project : Driver
 */

public class DemoOfferFirebaseWrapper implements
        CloudDemoOfferInterface {

    private static final String TAG = "DemoOfferFirebaseWrapper";

    private final String baseNodeDemoOffers;
    private final String baseNodeBatchData;
    private final String baseNodeScheduledBatches;

    private String demoOffersNode;
    private String batchDataNode;
    private String scheduledBatchesNode;

    private FirebaseDemoOffersMonitor firebaseDemoOffersMonitor;

    public DemoOfferFirebaseWrapper(CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");

        baseNodeDemoOffers = cloudConfig.getCloudDatabaseBaseNodeDemoOffers();
        Timber.tag(TAG).d("   baseNodeDemoOffers = " + baseNodeDemoOffers);

        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeScheduledBatches = cloudConfig.getCloudDatabaseBaseNodeScheduledBatches();
        Timber.tag(TAG).d("   baseNodeScheduledBatches = " + baseNodeScheduledBatches);
    }

    private void getNodes(Driver driver) {
        demoOffersNode = baseNodeDemoOffers + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getDemoOffersNode();
        Timber.tag(TAG).d("   ...demoOffersNode = " + demoOffersNode);

        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        scheduledBatchesNode = baseNodeScheduledBatches+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getScheduledBatchesNode();
        Timber.tag(TAG).d("   ...scheduledBatchesNode = " + scheduledBatchesNode);
    }

    ////
    ////    MONITOR FOR DEMO OFFERS
    ////

    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudDemoOfferInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebaseDemoOffersMonitor = new FirebaseDemoOffersMonitor(FirebaseDatabase.getInstance().getReference(demoOffersNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode), offersLists);

        Timber.tag(TAG).d("   ....startListening");
        firebaseDemoOffersMonitor.startListening();

        response.cloudDemoOffersStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }


    public void stopMonitoringRequest(CloudDemoOfferInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudDemoOffersStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebaseDemoOffersMonitor != null) {
            Timber.tag(TAG).d("      ....firebaseDemoOffersMonitor exists, stopListening & set to null");
            firebaseDemoOffersMonitor.stopListening();
            firebaseDemoOffersMonitor = null;
        }
    }

    ///
    ///     ADD DEMO OFFER
    ///

    public void addDemoOfferRequest(Driver driver, BatchHolder batchHolder, AddDemoOfferResponse response) {
        Timber.tag(TAG).d("addDemoOfferRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoOfferAdd().addOffer(FirebaseDatabase.getInstance().getReference(demoOffersNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchHolder, response);
    }

    ///
    ///  CLAIM DEMO OFFER
    ///
    public void claimOfferRequest(Driver driver, String batchGuid, ClaimOfferResponse response){
        Timber.tag(TAG).d("claimOfferRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoOfferClaim().claimOfferRequest(FirebaseDatabase.getInstance().getReference(demoOffersNode),
                FirebaseDatabase.getInstance().getReference(scheduledBatchesNode), batchGuid, response);
    }


    ////
    ////  GETTERS FOR BATCH DETAIL, SERVICE ORDER LIST, ROUTE STOP LIST, AND ORDER STEP LIST
    ////
    public void getBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseDemoBatchSummaryGet().getBatchSummary(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }

    public void getBatchDetailRequest(Driver driver, String batchGuid, CloudDemoOfferInterface.GetBatchDetailResponse response){
        Timber.tag(TAG).d("getBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoBatchDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }


    public void getServiceOrderListRequest(Driver driver, String batchGuid, CloudDemoOfferInterface.GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoBatchServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }

    public void getRouteStopListRequest(Driver driver, String batchGuid, CloudDemoOfferInterface.GetRouteStopListResponse response){
        Timber.tag(TAG).d("getRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode),batchGuid, response);

    }

    public void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, CloudDemoOfferInterface.GetOrderStepListResponse response){
        Timber.tag(TAG).d("getOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseDemoOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }

}
