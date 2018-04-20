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
public interface CloudPersonalOfferInterface {

    ///
    /// MONITOR FOR PERSONAL OFFERS
    ///
    void startMonitoringRequest(Driver driver, OffersInterface offersLists, StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudPersonalOffersStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudPersonalOffersStopMonitoringComplete();
    }

    ///
    ///  OFFERS UPDATED
    ///

    interface PersonalOffersUpdated {
        void cloudPersonalOffersUpdated(ArrayList<Batch> offerList);
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
        void cloudGetPersonalOfferBatchSummarySuccess(Batch batch);

        void cloudGetPersonalOfferBatchSummaryFailure();
    }

    void getBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudGetPersonalOfferBatchDetailSuccess(BatchDetail batchDetail);

        void cloudGetPersonalOfferBatchDetailFailure();
    }

    void getServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudGetPersonalOfferServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudGetPersonalOfferServiceOrderListFailure();
    }

    void getRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudGetPersonalOfferRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudGetPersonalOfferRouteStopListFailure();
    }

    void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudGetPersonalOfferOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudGetPersonalOfferOrderStepListFailure();
    }

}
