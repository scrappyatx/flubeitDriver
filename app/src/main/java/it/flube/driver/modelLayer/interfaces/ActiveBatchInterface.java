/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 10/16/2017
 * Project : Driver
 */

public interface ActiveBatchInterface {

    /// CURRENT STEP INFO

    Boolean hasActiveBatch();

    void clear();

    void setActiveBatch(@NonNull BatchDetail batchDetail, @NonNull ServiceOrder serviceOrder, @NonNull OrderStepInterface step);

    BatchDetail getBatchDetail();

    ServiceOrder getServiceOrder();

    OrderStepInterface getStep();

    OrderStepInterface.TaskType getTaskType();

    ServiceOrderNavigationStep getNavigationStep();

    ServiceOrderPhotoStep getPhotoStep();

    // SERVICE ORDER LIST

    Boolean hasServiceOrderList();

    void setServiceOrderList();

    void setServiceOrderList(@NonNull ArrayList<ServiceOrder> orderList);

    ArrayList<ServiceOrder> getServiceOrderList();

    // ORDER STEP LIST

    Boolean hasOrderStepList();

    void setOrderStepList();

    void setOrderStepList(@NonNull ArrayList<OrderStepInterface> stepList);

    ArrayList<OrderStepInterface> getOrderStepList();


    // ROUTE STOP LIST

    Boolean hasRouteStopList();

    void setRouteStopList();

    void setRouteStopList(@NonNull ArrayList<RouteStop> routeStopList);

    ArrayList<RouteStop> getRouteStopList();

}
