/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.acknowledgeRemovedBatch.FirebaseActiveBatchAcknowledgeRemovedBatch;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.FirebaseActiveBatchNodeMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchContactPersonsByServiceOrderGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchCurrentStepGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchOrderStepListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchPhotoRequestGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchRouteStopListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchServiceOrderListGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchStepGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseAssetTransfer;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseDriverProxyInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseImageStorageUploadResult;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebasePaymentAuthorizationUpdate;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebasePhotoRequestDeviceAbsoluteFilename;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseReceiptRequestDeviceAbsoluteFilenameRequest;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseSignatureRequestDeviceAbsoluteFilename;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchFinish.FirebaseActiveBatchFinishPrep;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchStart;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.saveMapLocation.FirebaseBatchDataSaveMapLocation;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish.FirebaseActiveBatchStepFinishPrep;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.acknowledgeFinishedBatch.FirebaseActiveBatchAcknowledgeFinishedBatch;
//import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.updateActiveBatchServerNode.FirebaseActiveBatchServerNode;
//import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.updateCompletedBatchesServerNode.FirebaseCompletedBatchesServerNode;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

//import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.ACTIVE_BATCH_SERVER_NOTIFICATION_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_START_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_START_RESPONSE_NODE;
//import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.COMPLETED_BATCH_SERVER_NOTIFICATION_NODE;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class ActiveBatchFirebaseWrapper implements
        CloudActiveBatchInterface {

    private static final String TAG = "ActiveBatchFirebaseWrapper";

    private final String driverDb;
    private final String baseNodeBatchData;
    private final String baseNodeActiveBatch;

    private String activeBatchNode;
    private String batchDataNode;
    private String activeBatchNotificationNode;
    private String completedBatchNotificationNode;
    private String batchStartRequestNode;
    private String batchStartResponseNode;


    private FirebaseActiveBatchNodeMonitor firebaseActiveBatchMonitor;

    public ActiveBatchFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment, CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("creating START...");
        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);

        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeActiveBatch = cloudConfig.getCloudDatabaseBaseNodeActiveBatch();
        Timber.tag(TAG).d("   baseNodeActiveBatch = " + baseNodeActiveBatch);
    }

    private void getNodes(Driver driver) {

        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        activeBatchNode = baseNodeActiveBatch+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getActiveBatchNode();
        Timber.tag(TAG).d("activeBatchNode = " + activeBatchNode);

        //activeBatchNotificationNode = ACTIVE_BATCH_SERVER_NOTIFICATION_NODE;
        //Timber.tag(TAG).d("activeBatchNotificationNode = " + activeBatchNotificationNode);

        //completedBatchNotificationNode = COMPLETED_BATCH_SERVER_NOTIFICATION_NODE;
        //Timber.tag(TAG).d("completedBatchNotificationNode = " + completedBatchNotificationNode);

        batchStartRequestNode = BATCH_START_REQUEST_NODE;
        Timber.tag(TAG).d("batchStartRequestNode = " + batchStartRequestNode);

        batchStartResponseNode = BATCH_START_RESPONSE_NODE + "/" + driver.getClientId();
        Timber.tag(TAG).d("batchStartResponseNode = " + batchStartResponseNode);

    }
    ///
    /// MONITOR FOR ACTIVE BATCH
    ///
    public void startMonitoringRequest(Driver driver, OffersInterface offersLists, CloudActiveBatchInterface.StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        //create new monitor & start monitoring
        Timber.tag(TAG).d("   ....creating new monitor");
        firebaseActiveBatchMonitor = new FirebaseActiveBatchNodeMonitor(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode));

        Timber.tag(TAG).d("   ....startListening");
        firebaseActiveBatchMonitor.startListening();

        response.cloudActiveBatchStartMonitoringComplete();
        Timber.tag(TAG).d("....startMonitoringRequest COMPLETE");
    }



    public void stopMonitoringRequest(CloudActiveBatchInterface.StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringRequest START...");

        Timber.tag(TAG).d("   ....checkIfAlreadyMonitoring");
        checkIfAlreadyMonitoring();

        response.cloudActiveBatchStopMonitoringComplete();
        Timber.tag(TAG).d("....stopMonitoringRequest COMPLETE");
    }

    private void checkIfAlreadyMonitoring(){
        if (firebaseActiveBatchMonitor != null) {
            Timber.tag(TAG).d("      ....firebaseActiveBatchMonitor exists, stopListening & set to null");
            firebaseActiveBatchMonitor.stopListening();
            firebaseActiveBatchMonitor = null;
        }
    }


    ////
    ////  STARTING AN ACTIVE BATCH
    ////
    public void startActiveBatchRequest(Driver driver, String batchGuid, ActiveBatchManageInterface.ActorType actorType, StartActiveBatchResponse response){
        Timber.tag(TAG).d("startActiveBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...keeping data data for the active batch synced");
        FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode).child(batchGuid).keepSynced(true);

        new FirebaseActiveBatchStart().startBatchRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode), FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                                                            FirebaseDatabase.getInstance(driverDb).getReference(batchStartRequestNode), FirebaseDatabase.getInstance(driverDb).getReference(batchStartResponseNode),
                                                            driver, batchGuid, actorType, response);
    }


    ////
    //// DOING THE ACTIVE BATCH
    ////
    public void finishActiveBatchStepRequest(Driver driver, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.FinishActiveBatchStepResponse response){
        Timber.tag(TAG).d("finishActiveBatchStepRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchStepFinishPrep().finishStepRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), actorType, response);
    }

    public void finishActiveBatchRequest(Driver driver, ActiveBatchManageInterface.ActorType actorType, String batchGuid, FinishActiveBatchResponse response){
        Timber.tag(TAG).d("finishActiveBatchRequest START...");
        Timber.tag(TAG).d("   batchGuid -> " + batchGuid);
        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchFinishPrep().finishBatchRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode),
                FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                actorType, batchGuid, response);
    }

    public void acknowledgeFinishedBatchRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.AcknowledgeFinishedBatchResponse response){
        Timber.tag(TAG).d("acknowledgeFinishedBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...removing sync node for this batch data");
        FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode).child(batchGuid).keepSynced(false);

        new FirebaseActiveBatchAcknowledgeFinishedBatch().acknowledgeFinishedBatch(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode), response);
    }



    public void acknowledgeRemovedBatchRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse response){
        Timber.tag(TAG).d("acknowledgeRemovedBatchRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        Timber.tag(TAG).d("...removing sync node for this batch data");
        FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode).child(batchGuid).keepSynced(false);

        new FirebaseActiveBatchAcknowledgeRemovedBatch().acknowledgeRemovedBatch(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode), response);
    }



    public void saveMapLocationRequest(Driver driver, String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                LatLonLocation location, CloudActiveBatchInterface.SaveMapLocationResponse response){

        Timber.tag(TAG).d("saveMapLocationRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseBatchDataSaveMapLocation().saveMapLocation(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, location, response);

    }

    ////
    //// UPDATING THE ACTIVE BATCH
    ////

    public void updateDriverProxyInfoRequest(Driver driver, String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber, UpdateDriverProxyInfoResponse response){
        Timber.tag(TAG).d("updatePhotoRequestDeviceAbsoluteFileNameRequest START...");
        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseDriverProxyInfo().updateDriverProxyInfoRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), driver, batchGuid, driverProxyDialNumber, driverProxyDisplayNumber, response);
    }

    public void updatePhotoRequestDeviceAbsoluteFileNameRequest(Driver driver, PhotoRequest photoRequest, String absoluteFileName, Boolean hasFile,
                                                                PhotoRequestDeviceAbsoluteFileNameResponse response){

        Timber.tag(TAG).d("updatePhotoRequestDeviceAbsoluteFileNameRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebasePhotoRequestDeviceAbsoluteFilename().updatePhotoRequestDeviceAbsoluteFilenameRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                photoRequest, absoluteFileName, hasFile, response);

    }

    public void updateSignatureRequestDeviceAbsoluteFileNameRequest(Driver driver, SignatureRequest signatureRequest, String absoluteFileName, Boolean hasFile, SignatureRequestDeviceAbsoluteFileNameResponse response){
        Timber.tag(TAG).d("updateSignatureRequestDeviceAbsoluteFileNameRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseSignatureRequestDeviceAbsoluteFilename().updateSignatureRequestDeviceAbsoluteFilenameRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                signatureRequest, absoluteFileName, hasFile, response);
    }

    public void updateAssetTransferRequest(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer, UpdateAssetTransferResponse response){
        Timber.tag(TAG).d("updateAssetTransferRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseAssetTransfer().updateAssetTransferRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, serviceOrderGuid, stepGuid, assetTransfer, response);
    }

    public void updatePaymentAuthorizationRequest(Driver driver, PaymentAuthorization paymentAuthorization, PaymentAuthorizationUpdateResponse response){
        Timber.tag(TAG).d("updatePaymentAuthorizationRequest START...");
        getNodes(driver);
        new FirebasePaymentAuthorizationUpdate().updatePaymentAuthorizationRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), paymentAuthorization, response);
    }

    public void updateReceiptRequestDeviceAbsoluteFileNameRequest(Driver driver, ReceiptRequest receiptRequest, ReceiptRequestDeviceAbsoluteFileNameReponse response){
        Timber.tag(TAG).d("updateReceiptRequestDeviceAbsoluteFileNameRequest START...");
        getNodes(driver);
        new FirebaseReceiptRequestDeviceAbsoluteFilenameRequest().updateReceiptRequestDeviceFilenameRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), receiptRequest, response);
    }

    public void updateCloudImageStorageUploadResult(Driver driver, FileToUploadInfo fileToUploadInfo, CloudUploadResultResponse response){
        Timber.tag(TAG).d("updateCloudImageStorageUploadResult");
        getNodes(driver);
        new FirebaseImageStorageUploadResult().processUploadResult(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), fileToUploadInfo, response);
    }


    ////
    //// STATUS MONITORING & TRACKING OF THE ACTIVE BATCH
    ////

    ///public void updateActiveBatchServerNodeStatus(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){
    ///    Timber.tag(TAG).d("updateActiveBatchServerNodeStatus START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //    getNodes(driver);

    //    new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode),FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
    //            driver, batchDetail, serviceOrder, step);
    //}

    //public void updateActiveBatchServerNodeStatusWithLocation(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, LatLonLocation driverLocation){
    //    Timber.tag(TAG).d("updateActiveBatchServerNodeStatusWithLocation START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //    getNodes(driver);

    //    new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequestWithLocation(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode),FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
    //            driver, batchDetail, serviceOrder, step, driverLocation);
    //}

    //public void updateActiveBatchServerNodeStatusRemove(Driver driver, String batchGuid){
    //    Timber.tag(TAG).d("updateActiveBatchServerNodeStatusRemove START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //    getNodes(driver);
    //    new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequestRemove(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode),
    //            batchGuid);
    //}

    //public void updateActiveBatchServerNodeStatusLocationOnly(Driver driver, String batchGuid, LatLonLocation driverLocation){
    //    Timber.tag(TAG).d("updateActiveBatchServerNodeStatusLocationOnly START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //   getNodes(driver);
    //    new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateRequestLocationOnly(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), batchGuid, driverLocation)
    //}

    //public void updateActiveBatchServerNodeDriverProxy(Driver driver, String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber){
    //    Timber.tag(TAG).d("updateActiveBatchServerNodeDriverProxy START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //    getNodes(driver);
    //    new FirebaseActiveBatchServerNode().activeBatchServerNodeUpdateDriverProxyInfo(driver, batchGuid, driverProxyDialNumber, driverProxyDisplayNumber);
    //}

    ///    sets the server node for a completed batch

    //public void updateBatchCompletedServerNode(Driver driver, String batchGuid){
    //    Timber.tag(TAG).d("updateBatchCompletedServerNode START...");

    //    Timber.tag(TAG).d("   ....getNodes");
    //    getNodes(driver);

    //    new FirebaseCompletedBatchesServerNode().setCompletedBatchRequest(FirebaseDatabase.getInstance(driverDb).getReference(completedBatchNotificationNode),
    //            FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),driver, batchGuid);
    //}

    ///
    ///  GETTERS FOR ACTIVE BATCH DATA
    ///        BatchSummary, BatchDetail, ServiceOrderList, RouteStopList, OrderStepList
    ///

    public void getActiveBatchPhotoRequestRequest(Driver driver, String batchGuid, String orderStepGuid, String photoRequestGuid, GetActiveBatchPhotoRequestResponse response){
        Timber.tag(TAG).d("getActiveBatchPhotoRequestRequest START...");

        Timber.tag(TAG).d("   ...getNodes");
        getNodes(driver);
        new FirebaseActiveBatchPhotoRequestGet().getActiveBatchPhotoRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, orderStepGuid, photoRequestGuid, response);
    }

    public void getActiveBatchCurrentStepRequest(Driver driver, GetActiveBatchCurrentStepResponse response){
        Timber.tag(TAG).d("getActiveBatchCurrentStepRequest START...");

        Timber.tag(TAG).d("   ...getNodes");
        getNodes(driver);
        new FirebaseActiveBatchCurrentStepGet().getActiveBatchCurrentStepRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNode), response);
    }

    public void getActiveBatchStepRequest(Driver driver, String batchGuid, String stepGuid, GetActiveBatchStepResponse response){
        Timber.tag(TAG).d("getActiveBatchStepRequest START...");

        Timber.tag(TAG).d("   ...getNodes");
        getNodes(driver);
        new FirebaseActiveBatchStepGet().getOrderStepRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, stepGuid, response);
    }

    public void getActiveBatchSummaryRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetBatchSummaryResponse response){
        Timber.tag(TAG).d("getActiveBatchSummaryRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);
        new FirebaseActiveBatchSummaryGet().getBatchSummary(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchDetailRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetBatchDetailResponse response){
        Timber.tag(TAG).d("getBatchDetailRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchDetailGet().getBatchDetailRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchServiceOrderListRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetServiceOrderListResponse response){
        Timber.tag(TAG).d("getServiceOrderListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchServiceOrderListGet().getServiceOrderListRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }



    public void getActiveBatchRouteStopListRequest(Driver driver, String batchGuid, CloudActiveBatchInterface.GetRouteStopListResponse response){
        Timber.tag(TAG).d("getRouteStopListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchRouteStopListGet().getRouteStopListRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),batchGuid, response);
    }



    public void getActiveBatchOrderStepListRequest(Driver driver, String batchGuid, String serviceOrderGuid, CloudActiveBatchInterface.GetOrderStepListResponse response){
        Timber.tag(TAG).d("getOrderStepListRequest START...");

        Timber.tag(TAG).d("   ....getNodes");
        getNodes(driver);

        new FirebaseActiveBatchOrderStepListGet().getOrderStepList(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, serviceOrderGuid, response);
    }

    public void getActiveBatchContactPersonsByServiceOrder(Driver driver, String batchGuid, GetContactPersonsByServiceOrderResponse response){
        Timber.tag(TAG).d("getActiveBatchContactPersonsByServiceOrder START...");

        Timber.tag(TAG).d("   ...getNodes");
        getNodes(driver);

        new FirebaseActiveBatchContactPersonsByServiceOrderGet().getContactPersonsByServiceOrder(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), batchGuid, response);
    }


}
