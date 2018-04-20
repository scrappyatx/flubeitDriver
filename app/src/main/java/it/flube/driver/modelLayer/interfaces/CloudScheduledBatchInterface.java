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
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 3/23/2018
 * Project : Driver
 */

public interface CloudScheduledBatchInterface {

    ///
    /// MONITOR FOR SCHEDULED BATCHES
    ///
    void startMonitoringRequest(Driver driver, OffersInterface offersLists, StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudScheduledBatchStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudScheduledBatchStopMonitoringComplete();
    }

    ///
    ///   SCHEDULED BATCHES UPDATED
    ///
    interface ScheduledBatchesUpdated {
        void cloudScheduledBatchesUpdated(ArrayList<Batch> batchList);
    }

    ///
    /// BATCH FORFEIT REQUEST
    ///
    void batchForfeitRequest(Driver driver, String batchGuid, BatchForfeitResponse response);

    interface BatchForfeitResponse {
        void cloudScheduledBatchForfeitSuccess(String batchGuid);

        void cloudScheduledBatchForfeitFailure(String batchGuid);

        void cloudScheduledBatchForfeitTimeout(String batchGuid);

        void cloudScheduledBatchForfeitDenied(String batchGuid, String reason);
    }

    ///
    /// BATCH START REQUEST
    ///

    void startScheduledBatchRequest(Driver driver, String batchGuid, StartScheduledBatchResponse response);

    interface StartScheduledBatchResponse {
        void cloudStartScheduledBatchComplete();
    }

    ////
    //// GETTERS for ScheduledBatchData
    ////        BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList

    void getScheduledBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response);

    interface GetBatchSummaryResponse {
        void cloudGetScheduledBatchSummarySuccess(Batch batch);

        void cloudGetScheduledBatchSummaryFailure();
    }

    void getScheduledBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudGetScheduledBatchDetailSuccess(BatchDetail batchDetail);

        void cloudGetScheduledBatchDetailFailure();
    }

    void getScheduledBatchServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudGetScheduledBatchServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudGetScheduledBatchServiceOrderListFailure();
    }

    void getScheduledBatchRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudGetScheduledBatchRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudGetScheduledBatchRouteStopListFailure();
    }

    void getScheduledBatchOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudGetScheduledBatchOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudGetScheduledBatchOrderStepListFailure();
    }
}
