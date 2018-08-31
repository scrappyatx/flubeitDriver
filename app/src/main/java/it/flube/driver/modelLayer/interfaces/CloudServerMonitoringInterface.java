/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/29/2018
 * Project : Driver
 */
public interface CloudServerMonitoringInterface {

    ////
    //// STATUS MONITORING & TRACKING OF THE ACTIVE BATCH
    ////
    void batchStartedRequest(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step, BatchStartedResponse response);

    interface BatchStartedResponse {
        void cloudServerMonitoringBatchStartedComplete(String batchGuid);
    }

    void orderStartedRequest(Driver driver, ServiceOrder serviceOrder, OrderStepInterface step, OrderStartedResponse response);

    interface OrderStartedResponse {
        void cloudServerMonitoringOrderStartedComplete(String batchGuid);
    }

    void stepStartedRequest(Driver driver, OrderStepInterface step, StepStartedResponse response);

    interface StepStartedResponse {
        void cloudServerMonitoringStepStartedComplete(String batchGuid);
    }

    void locationUpdateRequest(String batchGuid, LatLonLocation driverLocation, LocationUpdateResponse response);

    interface LocationUpdateResponse {
        void cloudServerMonitoringLocationUpdateComplete(String batchGuid);
    }

    void batchRemovedRequest(Driver driver, BatchDetail batchDetail, BatchRemovedResponse response);

    interface BatchRemovedResponse {
        void cloudServerMonitoringBatchRemovedComplete(String batchGuid);
    }

    void batchFinishedRequest(Driver driver, BatchDetail batchDetail, BatchFinishedResponse response);

    interface BatchFinishedResponse {
        void cloudServerMonitoringBatchFinishedComplete(String batchGuid);
    }

}
