/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public interface CloudActiveBatchInterface {

    ///
    /// MONITOR FOR ACTIVE BATCH
    ///
    void startMonitoringRequest(Driver driver, OffersInterface offersLists, StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudActiveBatchStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudActiveBatchStopMonitoringComplete();
    }


    interface ActiveBatchUpdated {
        void stepStarted(ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step);

        void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid);

        void batchRemoved(ActiveBatchManageInterface.ActorType actorType, String batchGuid);

        void noBatch();
    }

    ////
    ////  STARTING AN ACTIVE BATCH
    ////
    void startActiveBatchRequest(Driver driver, String batchGuid, ActiveBatchManageInterface.ActorType actorType, StartActiveBatchResponse response);

    interface StartActiveBatchResponse {
        void cloudStartActiveBatchComplete();
    }

    ////
    //// DOING THE ACTIVE BATCH
    ////
    void finishActiveBatchStepRequest(Driver driver, ActiveBatchManageInterface.ActorType actorType, FinishActiveBatchStepResponse response);

    interface FinishActiveBatchStepResponse {
        void cloudActiveBatchFinishStepComplete();
    }

    void acknowledgeFinishedBatchRequest(Driver driver, String batchGuid, AcknowledgeFinishedBatchResponse response);

    interface AcknowledgeFinishedBatchResponse {
        void cloudActiveBatchFinishedBatchAckComplete();
    }

    void acknowledgeRemovedBatchRequest(Driver driver, String batchGuid, AcknowledgeRemovedBatchResponse response);

    interface AcknowledgeRemovedBatchResponse {
        void cloudActiveBatchRemovedBatchAckComplete();
    }

    void saveMapLocationRequest(Driver driver, String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                LatLonLocation location, SaveMapLocationResponse response);

    interface SaveMapLocationResponse {
        void cloudActiveBatchSaveMapLocationComplete();
    }

    ////
    //// STATUS MONITORING & TRACKING OF THE ACTIVE BATCH
    ////

    void updateActiveBatchServerNodeStatus(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step);

    void updateActiveBatchServerNodeStatus(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation);

    void updateActiveBatchServerNodeStatus(Driver driver, String batchGuid);

    ///    sets the server node for a completed batch

     void updateBatchCompletedServerNode(Driver driver, String batchGuid);

    ///
    ///  GETTERS FOR ACTIVE BATCH DATA
    ////        BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList
    ///

    void getActiveBatchCurrentStepRequest(Driver driver, GetActiveBatchCurrentStepResponse response);

    interface GetActiveBatchCurrentStepResponse {
        void cloudGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep);

        void cloudGetActiveBatchCurrentStepFailure();
    }

    void getActiveBatchSummaryRequest(Driver driver, String batchGuid, GetBatchSummaryResponse response);

    interface GetBatchSummaryResponse {
        void cloudGetActiveBatchSummarySuccess(Batch batch);

        void cloudGetActiveBatchSummaryFailure();
    }

    void getActiveBatchDetailRequest(Driver driver, String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail);

        void cloudGetActiveBatchDetailFailure();
    }

    void getActiveBatchServiceOrderListRequest(Driver driver, String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudGetActiveBatchServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudGetActiveBatchServiceOrderListFailure();
    }

    void getActiveBatchRouteStopListRequest(Driver driver, String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudGetActiveBatchRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudGetActiveBatchRouteStopListFailure();
    }

    void getActiveBatchOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudGetActiveBatchOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudGetActiveBatchOrderStepListFailure();
    }
}
