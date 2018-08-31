/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.BatchFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.BatchRemoved;
import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.BatchStarted;
import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.LocationUpdate;
import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.OrderStarted;
import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.StepStarted;
import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.ACTIVE_BATCH_SERVER_NOTIFICATION_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.COMPLETED_BATCH_SERVER_NOTIFICATION_NODE;


/**
 * Created on 8/29/2018
 * Project : Driver
 */
public class ServerMonitoringFirebaseWrapper implements
        CloudServerMonitoringInterface {

    private static final String TAG = "ServerMonitoringFirebaseWrapper";

    private String driverDb;
    private String activeBatchNotificationNode;
    private String completedBatchNotificationNode;


    public ServerMonitoringFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        Timber.tag(TAG).d("targetEnvironment -> " + targetEnvironment);

        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);
        activeBatchNotificationNode = ACTIVE_BATCH_SERVER_NOTIFICATION_NODE;
        completedBatchNotificationNode = COMPLETED_BATCH_SERVER_NOTIFICATION_NODE;

        Timber.tag(TAG).d("driverDb          -> " + driverDb);
        Timber.tag(TAG).d("    activeBatchNotificationNode = " + activeBatchNotificationNode);
        Timber.tag(TAG).d("    completedBatchNotificationNode = " + completedBatchNotificationNode);
    }

    public void batchStartedRequest(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, CloudServerMonitoringInterface.BatchStartedResponse response){
        Timber.tag(TAG).d("batchStartedRequest...");
        new BatchStarted().batchStartedRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), batchDetail, serviceOrder, step, response);
    }

    public void orderStartedRequest(Driver driver, ServiceOrder serviceOrder, OrderStepInterface step, CloudServerMonitoringInterface.OrderStartedResponse response){
        Timber.tag(TAG).d("orderStartedRequest...");
        new OrderStarted().orderStartedRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), serviceOrder, step, response);
    }

    public void stepStartedRequest(Driver driver, OrderStepInterface step, CloudServerMonitoringInterface.StepStartedResponse response){
        Timber.tag(TAG).d("stepStartedRequest...");
        new StepStarted().stepStartedRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), step, response);
    }

    public void locationUpdateRequest(String batchGuid, LatLonLocation driverLocation, CloudServerMonitoringInterface.LocationUpdateResponse response){
        Timber.tag(TAG).d("locationUpdateRequest...");
        new LocationUpdate().locationUpdateRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), batchGuid, driverLocation, response);
    }

    public void batchRemovedRequest(Driver driver, BatchDetail batchDetail, BatchRemovedResponse response){
        Timber.tag(TAG).d("batchRemovedRequest...");
        new BatchRemoved().batchRemovedRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode), batchDetail.getBatchGuid(), response);
    }

    public  void batchFinishedRequest(Driver driver, BatchDetail batchDetail, BatchFinishedResponse response){
        Timber.tag(TAG).d("batchFinishedRequest...");
        new BatchFinished().batchFinishedRequest(FirebaseDatabase.getInstance(driverDb).getReference(activeBatchNotificationNode),FirebaseDatabase.getInstance(driverDb).getReference(completedBatchNotificationNode),
                                                    driver.getClientId(), batchDetail, response);

    }

}
