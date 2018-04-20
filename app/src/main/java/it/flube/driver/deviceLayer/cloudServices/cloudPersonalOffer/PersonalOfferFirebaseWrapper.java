/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim.FirebaseScheduledBatchesAdd;
import it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.FirebaseOfferClaimRequest;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet.FirebasePersonalOfferDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet.FirebasePersonalOfferOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet.FirebasePersonalOfferRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet.FirebasePersonalOfferServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet.FirebasePersonalOfferSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchStart.FirebaseScheduledBatchesRemove;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.demoBatch.FirebaseBatchDataDelete;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerAdd.FirebaseBatchDataSaveBlob;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerAdd.FirebaseDemoOffersAddToOfferList;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim.FirebaseDemoOffersRemove;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offersMonitor.FirebasePersonalOffersMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import timber.log.Timber;

/**
 * Created on 3/28/2018
 * Project : Driver
 */
public class PersonalOfferFirebaseWrapper implements
        CloudPersonalOfferInterface {

    private static final String TAG = "PersonalOfferFirebaseWrapper";

    private static final String BASE_NODE_PERSONAL_OFFER_DATA = "userReadable/batchData";
    private static final String OFFER_CLAIM_REQUEST_NODE = "userWriteable/claimOfferRequest";
    private static final String OFFER_CLAIM_RESPONSE_NODE = "userReadable/claimOfferResponse";

    private final String baseNodePersonalOffers;

    private String personalOffersNode;
    private String batchDataNode;
    private String offerClaimRequestNode;
    private String offerClaimResponseNode;


    private FirebasePersonalOffersMonitor firebasePersonalOffersMonitor;

    public PersonalOfferFirebaseWrapper(CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");

        baseNodePersonalOffers = cloudConfig.getCloudDatabaseBaseNodePersonalOffers();
        Timber.tag(TAG).d("   baseNodePersonalOffers = " + baseNodePersonalOffers);

    }

    private void getNodes(Driver driver) {
        personalOffersNode = baseNodePersonalOffers+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getPersonalOffersNode();
        Timber.tag(TAG).d("personalOffersNode = " + personalOffersNode);

        batchDataNode = BASE_NODE_PERSONAL_OFFER_DATA;
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        offerClaimRequestNode = OFFER_CLAIM_REQUEST_NODE;
        Timber.tag(TAG).d("offerClaimRequestNode = " + offerClaimRequestNode);

        offerClaimResponseNode = OFFER_CLAIM_RESPONSE_NODE;
        Timber.tag(TAG).d("offerClaimResponseNode = " + offerClaimResponseNode);
    }

    ////
    ////    MONITOR FOR PERSONAL OFFERS
    ////

    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudPersonalOfferInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebasePersonalOffersMonitor = new FirebasePersonalOffersMonitor(FirebaseDatabase.getInstance().getReference(personalOffersNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode), offersLists);

        Timber.tag(TAG).d("   ....startListening");
        firebasePersonalOffersMonitor.startListening();

        response.cloudPersonalOffersStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }


    public void stopMonitoringRequest(CloudPersonalOfferInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudPersonalOffersStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebasePersonalOffersMonitor != null) {
            Timber.tag(TAG).d("      ....firebasePersonalOffersMonitor exists, stopListening & set to null");
            firebasePersonalOffersMonitor.stopListening();
            firebasePersonalOffersMonitor = null;
        }
    }

    ////
    //// CLAIM OFFER
    ////
    public void claimOfferRequest(Driver driver, String batchGuid, BatchDetail.BatchType batchType, CloudOfferClaimInterface.ClaimOfferResponse response){
        Timber.tag(TAG).d("claimOfferRequest START...");

        new FirebaseOfferClaimRequest().claimOfferRequest(FirebaseDatabase.getInstance().getReference(offerClaimRequestNode),
                FirebaseDatabase.getInstance().getReference(offerClaimResponseNode),
                driver.getClientId(), batchGuid, batchType,
                response);

    }


    ////
    ////  GETTERS FOR BATCH DETAIL, SERVICE ORDER LIST, ROUTE STOP LIST, AND ORDER STEP LIST
    ////

    public void getBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getPersonalOfferBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePersonalOfferSummaryGet().getBatchSummary(FirebaseDatabase.getInstance(batchDataNode).getReference(),batchGuid, response);
    }

    public void getBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response){
        Timber.tag(TAG).d("getPersonalOfferBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePersonalOfferDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }


    public void getServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getPersonalOfferServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePersonalOfferServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }

    public void getRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response){
        Timber.tag(TAG).d("getPersonalOfferRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePersonalOfferRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode),batchGuid, response);

    }

    public void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response){
        Timber.tag(TAG).d("getPersonalOfferOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePersonalOfferOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }

}
