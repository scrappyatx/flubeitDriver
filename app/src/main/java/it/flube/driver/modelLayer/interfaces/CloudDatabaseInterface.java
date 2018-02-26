/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

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
    void connectDriverRequest(AppRemoteConfigInterface remoteConfig, Driver driver, ConnectResponse response);

    interface ConnectResponse {
        void cloudDatabaseConnectDriverComplete();
    }

    void disconnectDriverRequest(DisconnectResponse response);

    interface DisconnectResponse {
        void cloudDatabaseDisconnectDriverComplete();
    }

    ///
    ///  START & STOP MONITORING
    ///

    void startMonitoringRequest(StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudDatabaseStartMonitoringComplete();
    }

    void stopMonitoringRequest(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudDatabaseStopMonitoringComplete();
    }

    ///
    /// USER PROFILE INFO
    ///
    void getUserProfileRequest(String clientId, String email, UserProfileResponse response);

    interface UserProfileResponse {
        void cloudDatabaseGetUserProfileSuccess(Driver driver);

        void cloudDatabaseGetUserProfileNotFound();

        void cloudDatabaseGetUserProfileAccessDenied();
    }

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
    ///  PUBLIC OFFER CLAIM REQUEST
    ///  PERSONAL OFFER CLAIM REQUEST
    ///
    void claimOfferRequest(String batchGuid, BatchDetail.BatchType batchType, ClaimOfferResponse response);

    interface ClaimOfferResponse {
        void cloudDatabaseClaimOfferRequestComplete();
    }
    ///
    ///
    ///

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
    /// BATCH FORFEIT REQUEST
    ///
    void batchForfeitRequest(String batchGuid, BatchDetail.BatchType batchType, BatchForfeitResponse response);

    interface BatchForfeitResponse {
        void cloudDatabaseBatchForfeitRequestComplete();
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

    void saveMapLocationRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                LatLonLocation location, SaveMapLocationResponse response);

    interface SaveMapLocationResponse {
        void cloudDatabaseSaveMapLocationComplete();
    }

    ///
    ///  File upload tasks -> for files uploading to storage
    ///

    void addPhotoUploadTaskToNotStartedRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                               String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                               AddPhotoUploadTaskResponse response);

    interface AddPhotoUploadTaskResponse {
        void cloudDatabaseAddPhotoUploadTaskComplete();
    }

    void movePhotoUploadTaskToInProgress(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                         String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                         String sessionUriString, Double progress,
                                         MovePhotoUploadTaskInProgressResponse response);

    interface MovePhotoUploadTaskInProgressResponse {
        void cloudDatabaseMovePhotoUploadTaskToInProgressComplete();
    }

    void movePhotoUploadTaskToFinished(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                       String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                       MovePhotoUploadTaskFinishedResponse response);


    interface MovePhotoUploadTaskFinishedResponse {
        void cloudDatabaseMovePhotoUploadTaskFinishedComplete();
    }

    void movePhotoUploadTaskToFailed(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                     String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                     MovePhotoUploadTaskFailedResponse response);


    interface MovePhotoUploadTaskFailedResponse {
        void cloudDatabaseMoveUploadTaskFailedComplete(Integer attempts);
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