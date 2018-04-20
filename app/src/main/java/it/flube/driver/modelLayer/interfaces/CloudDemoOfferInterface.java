/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 3/24/2018
 * Project : Driver
 */

public interface CloudDemoOfferInterface {

    ///
    /// MONITOR FOR DEMO OFFERS
    ///
    void startMonitoringRequest(Driver driver, OffersInterface offersLists, StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudDemoOffersStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudDemoOffersStopMonitoringComplete();
    }

    ///
    ///  DEMO OFFERS UPDATED
    ///

    interface DemoOffersUpdated {
        void cloudDemoOffersUpdated(ArrayList<Batch> offerList);
    }

    ///
    ///  ADD DEMO OFFER
    ///
    void addDemoOfferRequest(Driver driver, BatchHolder batchHolder, AddDemoOfferResponse response);

    interface AddDemoOfferResponse {
        void cloudAddDemoOfferComplete();
    }

    ////
    ////  CLAIM DEMO OFFER
    ////
    void claimOfferRequest(Driver driver, String batchGuid, ClaimOfferResponse response);

    interface ClaimOfferResponse {
        void cloudClaimOfferRequestSuccess(String batchGuid);
    }


    ////
    ////  GETTERS FOR BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList
    ////
    void getBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response);

    interface GetBatchSummaryResponse {
        void cloudGetDemoBatchSummarySuccess(Batch batch);

        void cloudGetDemoBatchSummaryFailure();
    }

    void getBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudGetDemoBatchDetailSuccess(BatchDetail batchDetail);

        void cloudGetDemoBatchDetailFailure();
    }

    void getServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudGetDemoServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudGetDemoServiceOrderListFailure();
    }

    void getRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudGetDemoRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudGetDemoRouteStopListFailure();
    }

    void getOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudGetOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudGetOrderStepListFailure();
    }

}
