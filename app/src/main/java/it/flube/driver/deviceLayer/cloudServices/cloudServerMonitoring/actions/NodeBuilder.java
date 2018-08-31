/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.libbatchdata.entities.DriverInfo;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.BATCH_COMPLETED_TIME_PROPERTY;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.BATCH_DETAIL_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.BATCH_TYPE_PROPERTY;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.CLIENT_ID_PROPERTY;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.CONTACT_PERSONS_BY_SERVICE_ORDER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.DRIVER_INFO_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.LAST_UPDATE_LOCATION;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.LAST_UPDATE_TIMESTAMP;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.ORDER_STEP_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.SERVICE_ORDER_NODE;

/**
 * Created on 8/30/2018
 * Project : Driver
 */
public class NodeBuilder {
    private static final String TAG = "NodeBuilder";

    public static HashMap<String, Object> getCurrentBatchNode(BatchDetail batchDetail){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_DETAIL_NODE, batchDetail);
        data.put(CONTACT_PERSONS_BY_SERVICE_ORDER_NODE, batchDetail.getContactPersonsByServiceOrder());
        return data;
    }

    public static HashMap<String, Object> getCurrentServiceOrderNode(ServiceOrder serviceOrder){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(SERVICE_ORDER_NODE, serviceOrder);
        return data;
    }

    public static HashMap<String, Object> getCurrentStepNode(OrderStepInterface step){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ORDER_STEP_NODE, step);
        return data;
    }

    public static HashMap<String, Object> getDriverLocationNode(LatLonLocation driverLocation){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(LAST_UPDATE_LOCATION, driverLocation);
        data.put(LAST_UPDATE_TIMESTAMP, ServerValue.TIMESTAMP);
        return data;
    }

    public static HashMap<String, Object> getDriverInfoNode(DriverInfo driverInfo){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(DRIVER_INFO_NODE, driverInfo);
        return data;
    }

    public static HashMap<String, Object> getCompletedBatchNode(String clientId, BatchDetail.BatchType batchType){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(CLIENT_ID_PROPERTY, clientId);
        data.put(BATCH_TYPE_PROPERTY, batchType.toString());
        data.put(BATCH_COMPLETED_TIME_PROPERTY, ServerValue.TIMESTAMP);
        return data;
    }


}
