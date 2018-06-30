/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer;

import com.google.firebase.database.FirebaseDatabase;


import it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.FirebaseOfferClaimRequest;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offerDataGet.FirebasePublicOfferDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offerDataGet.FirebasePublicOfferOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offerDataGet.FirebasePublicOfferRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offerDataGet.FirebasePublicOfferServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offerDataGet.FirebasePublicOfferSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offersMonitor.FirebasePublicOffersMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.PublicOfferFirebaseConstants.BASE_NODE_PUBLIC_OFFER_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.PublicOfferFirebaseConstants.OFFER_CLAIM_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.PublicOfferFirebaseConstants.OFFER_CLAIM_RESPONSE_NODE;

/**
 * Created on 3/28/2018
 * Project : Driver
 */
public class PublicOfferFirebaseWrapper implements
        CloudPublicOfferInterface {

    private static final String TAG = "PublicOfferFirebaseWrapper";

    private final String baseNodePublicOffers;

    private String publicOffersNode;
    private String batchDataNode;
    private String offerClaimRequestNode;
    private String offerClaimResponseNode;


    private FirebasePublicOffersMonitor firebasePublicOffersMonitor;

    public PublicOfferFirebaseWrapper(CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");

        baseNodePublicOffers = cloudConfig.getCloudDatabaseBaseNodePublicOffers();
        Timber.tag(TAG).d("   baseNodePublicOffers = " + baseNodePublicOffers);

    }

    private void getNodes(Driver driver) {
        publicOffersNode = baseNodePublicOffers + "/"  + driver.getCloudDatabaseSettings().getPublicOffersNode();
        Timber.tag(TAG).d("publicOffersNode = " + publicOffersNode);

        batchDataNode = BASE_NODE_PUBLIC_OFFER_DATA;
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        offerClaimRequestNode = OFFER_CLAIM_REQUEST_NODE;
        Timber.tag(TAG).d("offerClaimRequestNode = " + offerClaimRequestNode);

        offerClaimResponseNode = OFFER_CLAIM_RESPONSE_NODE;
        Timber.tag(TAG).d("offerClaimResponseNode = " + offerClaimResponseNode);
    }

////
    ////    MONITOR FOR PUBLIC OFFERS
    ////

    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudPublicOfferInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebasePublicOffersMonitor = new FirebasePublicOffersMonitor(FirebaseDatabase.getInstance().getReference(publicOffersNode),
                FirebaseDatabase.getInstance().getReference(batchDataNode), offersLists);

        Timber.tag(TAG).d("   ....startListening");
        firebasePublicOffersMonitor.startListening();

        response.cloudPublicOffersStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }


    public void stopMonitoringRequest(CloudPublicOfferInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudPublicOffersStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebasePublicOffersMonitor != null) {
            Timber.tag(TAG).d("      ....firebasePersonalOffersMonitor exists, stopListening & set to null");
            firebasePublicOffersMonitor.stopListening();
            firebasePublicOffersMonitor = null;
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

    public void getBatchSummaryRequest(Driver driver, String batchGuid, CloudPublicOfferInterface.GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getPersonalOfferBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePublicOfferSummaryGet().getBatchSummary(FirebaseDatabase.getInstance(batchDataNode).getReference(),batchGuid, response);
    }

    public void getBatchDetailRequest(Driver driver, String batchGuid, CloudPublicOfferInterface.GetBatchDetailResponse response){
        Timber.tag(TAG).d("getPersonalOfferBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePublicOfferDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }


    public void getServiceOrderListRequest(Driver driver, String batchGuid, CloudPublicOfferInterface.GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getPersonalOfferServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePublicOfferServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, response);
    }

    public void getRouteStopListRequest(Driver driver, String batchGuid, CloudPublicOfferInterface.GetRouteStopListResponse response){
        Timber.tag(TAG).d("getPersonalOfferRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePublicOfferRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance().getReference(batchDataNode),batchGuid, response);

    }

    public void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, CloudPublicOfferInterface.GetOrderStepListResponse response){
        Timber.tag(TAG).d("getPersonalOfferOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebasePublicOfferOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance().getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }



}
