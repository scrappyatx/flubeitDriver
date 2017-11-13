/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public interface CloudDatabaseInterface {


    public enum ActionType {
        BATCH_STARTED,
        ORDER_STARTED,
        STEP_STARTED,
        BATCH_FINISHED,
        BATCH_REMOVED,
        NO_BATCH,
        NOT_SPECIFIED
    }

    public enum ActorType {
        MOBILE_USER,
        SERVER_ADMIN,
        NOT_SPECIFIED
    }
    ///
    /// CONNECT & DISCONNECT
    ///
    void connectRequest(AppRemoteConfigInterface remoteConfig, Driver driver, ConnectResponse response);

    interface ConnectResponse {
        void cloudDatabaseConnectComplete();
    }

    void disconnect();

    ///
    ///  START & STOP MONITORING
    ///

    void startMonitoring();

    void stopMonitoring();

    //
    //  USER INFO
    //
    void saveUserRequest(SaveResponse response);

    interface SaveResponse {
        void cloudDatabaseUserSaveComplete();
    }

    //
    //  DEVICE INFO
    //
    void saveDeviceInfoRequest(DeviceInfo deviceInfo, SaveDeviceInfoResponse response);

    interface SaveDeviceInfoResponse {
        void cloudDatabaseDeviceInfoSaveComplete();
    }

    ///
    ///  DEMO OFFERS
    ///
    void addDemoOfferToOfferListRequest(String batchGuid, AddDemoOfferToOfferListResponse response);

    interface AddDemoOfferToOfferListResponse {
        void cloudDatabaseAddDemoOfferToOfferListComplete();
    }

    void removeDemoOfferFromOfferListRequest(String batchGuid, RemoveDemoOfferFromOfferListResponse response);

    interface RemoveDemoOfferFromOfferListResponse {
        void cloudDatabaseRemoveDemoOfferFromOfferListComplete();
    }

    ///
    ///  DEMO BATCHES
    ///
    void addDemoBatchToScheduledBatchListRequest(String batchGuid, AddDemoBatchToScheduledBatchListResponse response);

    interface AddDemoBatchToScheduledBatchListResponse {
        void cloudDatabaseAddDemoBatchToScheduledBatchListComplete();
    }

    void removeDemoBatchFromScheduledBatchListRequest(String batchGuid, RemoveDemoBatchFromScheduledBatchListResponse response);

    interface RemoveDemoBatchFromScheduledBatchListResponse {
        void cloudDatabaseRemoveDemoBatchFromScheduledBatchListComplete();
    }

    ///
    ///  BATCH DATA
    ///

    void saveBatchDataRequest(BatchHolder batchHolder, SaveBatchDataResponse response);

    interface SaveBatchDataResponse {
        void cloudDatabaseBatchDataSaveComplete();
    }

    void deleteBatchDataRequest(String batchGuid, DeleteBatchDataResponse response);

    interface DeleteBatchDataResponse {
        void cloudDatabaseBatchDataDeleteComplete();
    }

    ///
    ///  GET BATCH INFORMATION
    ///
    void getBatchDetailRequest(String batchGuid, GetBatchDetailResponse response);

    interface GetBatchDetailResponse {
        void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail);

        void cloudDatabaseGetBatchDetailFailure();
    }

    void getServiceOrderListRequest(String batchGuid, GetServiceOrderListResponse response);

    interface GetServiceOrderListResponse {
        void cloudDatabaseGetServiceOrderListSuccess(ArrayList<ServiceOrder> orderList);

        void cloudDatabaseGetServiceOrderListFailure();
    }

    void getRouteStopListRequest(String batchGuid, GetRouteStopListResponse response);

    interface GetRouteStopListResponse {
        void cloudDatabaseGetRouteStopListSuccess(ArrayList<RouteStop> stopList);

        void cloudDatabaseGetRouteStopListFailure();
    }

    void getOrderStepListRequest(String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response);

    interface GetOrderStepListResponse {
        void cloudDatabaseGetOrderStepListSuccess(ArrayList<OrderStepInterface> stepList);

        void cloudDatabaseGetOrderStepListFailure();
    }

    ///
    ///  OFFERS UPDATED
    ///

    interface PublicOffersUpdated {
        void cloudDatabasePublicOffersUpdated(ArrayList<Batch> offerList);
    }

    interface PersonalOffersUpdated {
        void cloudDatabasePersonalOffersUpdated(ArrayList<Batch> offerList);
    }

    interface DemoOffersUpdated {
        void cloudDatabaseDemoOffersUpdated(ArrayList<Batch> offerList);
    }

    ///
    ///   SCHEDULED BATCHES UPDATED
    ///
    interface ScheduledBatchesUpdated {
        void cloudDatabaseScheduledBatchesUpdated(ArrayList<Batch> batchList);
    }

    ///
    ///   ACTIVE BATCH UPDATED
    ///

    //interface ActiveBatchUpdated {
    //    void cloudDatabaseActiveBatchUpdated(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep);
    //
    //    void cloudDatabaseNoActiveBatch();
    //}

    interface ActiveBatchUpdated {
        void stepStarted(ActorType actorType, ActionType actionType, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step);

        void batchFinished(ActorType actorType, String batchGuid);

        void batchRemoved(ActorType actorType, String batchGuid);

        void noBatch();
    }

    ///
    ///   ACTIVE BATCH COMMANDS
    ///

    void startScheduledBatchRequest(String batchGuid, ActorType actorType, StartScheduledBatchResponse response);

    interface StartScheduledBatchResponse {
        void cloudDatabaseStartScheduledBatchComplete();
    }

    void removeActiveBatchRequest(ActorType actorType, RemoveActiveBatchResponse response);

    interface RemoveActiveBatchResponse {
        void cloudDatabaseRemoveActiveBatchComplete();
    }

    ///void startActiveBatchStepRequest(ActorType actorType, StartActiveBatchStepResponse response);
    ///
    ///interface StartActiveBatchStepResponse {
    ///    void cloudDatabaseStartActiveBatchStepComplete();
    ///}

    void finishActiveBatchStepRequest(ActorType actorType, FinishActiveBatchStepResponse response);

    interface FinishActiveBatchStepResponse {
        void cloudDatabaseFinishActiveBatchStepComplete();
    }

    void acknowledgeFinishedBatchRequest(AcknowledgeFinishedBatchResponse response);

    interface AcknowledgeFinishedBatchResponse {
        void cloudDatabaseFinishedBatchAckComplete();
    }

    void acknowledgeRemovedBatchRequest(AcknowledgeRemovedBatchResponse response);

    interface AcknowledgeRemovedBatchResponse {
        void cloudDatabaseRemovedBatchAckComplete();
    }

    ///
    /// ActiveBatchNodes -> want to work these out of public interface
    ///
    ///void setActiveBatchNodesRequest(String batchGuid, Integer serviceOrderSequence, Integer stepSequence,
    ///                                CloudDatabaseInterface.ActionType actionType,
    ///                                ActiveBatchNodesUpdated response);

    ///void setActiveBatchNodesNullRequest(ActiveBatchNodesUpdated response);
///
    ///interface ActiveBatchNodesUpdated{
    ///    void cloudDatabaseActiveBatchNodeSetComplete();
    ///}

    ///
    ///
    ///

   /// void setBatchDetailStatusRequest(BatchDetail batchDetail, BatchDetail.WorkStatus status, BatchDetailStatusUpdated response);

    ///interface BatchDetailStatusUpdated {
    ///    void cloudDatabaseBatchDetailStatusSetComplete();
    ///}

    ///void setServiceOrderStatusRequest(ServiceOrder serviceOrder, ServiceOrder.ServiceOrderStatus status, ServiceOrderStatusUpdated response);
///
    ///interface ServiceOrderStatusUpdated {
    ///    void cloudDatabaseServiceOrderStatusSetComplete();
    ///}

    ///void setOrderStepWorkStageRequest(OrderStepInterface step, OrderStepInterface.WorkStage workStage, OrderStepWorkStageUpdated response);
///
    ///interface OrderStepWorkStageUpdated {
    ///    void cloudDatabaseOrderStepWorkStageSetComplete();
    ///}

    ////
    ///     sets the server node for the active batch
    ///

    void updateActiveBatchServerNodeStatus(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step);

    void updateActiveBatchServerNodeStatus(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation);

    void updateActiveBatchServerNodeStatus(String batchGuid);

    ///    sets the server node for a completed batch

    void updateBatchCompletedServerNode(BatchDetail batchDetail);

}