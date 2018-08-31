/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring;

/**
 * Created on 8/29/2018
 * Project : Driver
 */
public class ServerMonitoringFirebaseConstants {

    //  server monitoring nodes
    public static final String ACTIVE_BATCH_SERVER_NOTIFICATION_NODE = "mobileData/userWriteable/activeBatches";
    public static final String COMPLETED_BATCH_SERVER_NOTIFICATION_NODE = "mobileData/userWriteable/completedBatches";


    /// currentBatch node
    public static final String CURRENT_BATCH_NODE = "currentBatch";
    public static final String BATCH_DETAIL_NODE = "batchDetail";
    public static final String CONTACT_PERSONS_BY_SERVICE_ORDER_NODE = "contactPersonsByServiceOrder";

    /// currentServiceOrder node
    public static final String CURRENT_SERVICE_ORDER_NODE = "currentServiceOrder";
    public static final String SERVICE_ORDER_NODE = "serviceOrder";

    /// currentStep node
    public static final String CURRENT_STEP_NODE = "currentStep";
    public static final String ORDER_STEP_NODE = "step";

    // driverLocation node
    public static final String DRIVER_LOCATION_NODE = "driverLocation";
    public static final String LAST_UPDATE_LOCATION = "lastUpdateLocation";
    public static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";

    // driverInfo node
    public static final String DRIVER_DATA_NODE = "driverData";
    public static final String DRIVER_INFO_NODE = "driverInfo";

    // batchCompleted node
    public static final String CLIENT_ID_PROPERTY = "clientId";
    public static final String BATCH_TYPE_PROPERTY = "batchType";
    public static final String BATCH_COMPLETED_TIME_PROPERTY = "batchCompletedTimestamp";

}
