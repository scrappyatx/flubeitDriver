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

    void startDemoBatchRequest(String batchGuid, StartDemoBatchComplete response);

    interface StartDemoBatchComplete {
        void cloudDatabaseStartDemoBatchComplete();
    }


    ///
    ///  DEMO BATCH DATA
    ///

    void saveDemoBatchDataRequest(BatchHolder batchHolder, SaveDemoBatchDataResponse response);

    interface SaveDemoBatchDataResponse {
        void cloudDatabaseDemoBatchDataSaveComplete();
    }

    void deleteDemoBatchDataRequest(String batchGuid, DeleteDemoBatchDataResponse response);

    interface DeleteDemoBatchDataResponse {
        void cloudDatabaseDemoBatchDataDeleteComplete();
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

    interface ActiveBatchUpdated {
        void cloudDatabaseActiveBatchUpdated(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep);

        void cloudDatabaseNoActiveBatch();
    }


    ///
    ///   ACTIVE BATCH COMMANDS
    ///

    void setActiveBatchNodesRequest(String batchGuid, Integer serviceOrderSequence, Integer stepSequence, ActiveBatchNodesUpdated response);

    void setActiveBatchNodesNullRequest(ActiveBatchNodesUpdated response);

    interface ActiveBatchNodesUpdated{
        void cloudDatabaseActiveBatchNodeSetComplete();
    }

    void setBatchDetailStatusRequest(BatchDetail batchDetail, BatchDetail.WorkStatus status, BatchDetailStatusUpdated response);

    interface BatchDetailStatusUpdated {
        void cloudDatabaseBatchDetailStatusSetComplete();
    }

    void setServiceOrderStatusRequest(ServiceOrder serviceOrder, ServiceOrder.ServiceOrderStatus status, ServiceOrderStatusUpdated response);

    interface ServiceOrderStatusUpdated {
        void cloudDatabaseServiceOrderStatusSetComplete();
    }

    void setOrderStepWorkStageRequest(OrderStepInterface step, OrderStepInterface.WorkStage workStage, OrderStepWorkStageUpdated response);

    interface OrderStepWorkStageUpdated {
        void cloudDatabaseOrderStepWorkStageSetComplete();
    }

    void setActiveBatchStartedServerNode(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step);

    void setActiveBatchStartedServerNode(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation);

    void setActiveBatchFinishedServerNode(String batchGuid);

    void setBatchCompletedServerNode(BatchDetail batchDetail);

}