/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 3/26/2018
 * Project : Driver
 */
public interface CloudPublicOfferInterface {

    ///
    /// MONITOR FOR PUBLIC OFFERS
    ///
    void startMonitoringRequest(Driver driver, OffersInterface offersLists, StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudPublicOffersStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudPublicOffersStopMonitoringComplete();
    }

    ///
    ///  OFFERS UPDATED
    ///

    interface PublicOffersUpdated {
        void cloudPublicOffersUpdated(ArrayList<Batch> offerList);
    }

    ////
    //// CLAIM OFFER
    ////
    void claimOfferRequest(Driver driver, String batchGuid, BatchDetail.BatchType batchType, CloudOfferClaimInterface.ClaimOfferResponse response);


    ////
    ////  GETTERS FOR BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList
    ////

    void getBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response);

    interface GetBatchSummaryResponse {
        void cloudGetPublicOfferBatchSummarySuccess(Batch batch);

        void cloudGetPublicOfferBatchSummaryFailure();
    }

    void getBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudGetPublicOfferBatchDetailSuccess(BatchDetail batchDetail);

        void cloudGetPublicOfferBatchDetailFailure();
    }

    void getServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudGetPublicOfferServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudGetPublicOfferServiceOrderListFailure();
    }

    void getRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudGetPublicOfferRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudGetPublicOfferRouteStopListFailure();
    }

    void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudGetPublicOfferOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudGetPublicOfferOrderStepListFailure();
    }

}
