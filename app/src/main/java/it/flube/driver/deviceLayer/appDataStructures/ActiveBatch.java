/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appDataStructures;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 10/16/2017
 * Project : Driver
 */

public class ActiveBatch implements ActiveBatchInterface {
    private static final String TAG = "ActiveBatch";




    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;
    private OrderStepInterface.TaskType taskType;

    private ArrayList<ServiceOrder> serviceOrderList;
    private ArrayList<OrderStepInterface> orderStepList;
    private ArrayList<RouteStop> routeStopList;

    private Boolean hasActiveBatch;
    private Boolean hasServiceOrderList;
    private Boolean hasOrderStepList;
    private Boolean hasRouteStopList;

    public ActiveBatch() {
        this.serviceOrderList = new ArrayList<ServiceOrder>();
        this.orderStepList = new ArrayList<OrderStepInterface>();
        this.routeStopList = new ArrayList<RouteStop>();

        this.hasActiveBatch=false;
        this.hasServiceOrderList=false;
        this.hasOrderStepList=false;
        this.hasRouteStopList=false;

        clear();
    }

    public void clear(){
        batchDetail = null;
        serviceOrder = null;
        step = null;
        taskType = null;
        this.hasActiveBatch = false;
    }
    public void setActiveBatch(@NonNull BatchDetail batchDetail, @NonNull ServiceOrder serviceOrder, @NonNull OrderStepInterface step){
        this.batchDetail = batchDetail;
        this.serviceOrder = serviceOrder;
        this.step = step;
        this.taskType = step.getTaskType();
        this.hasActiveBatch = true;
    }

    private void updateHasActiveBatch(){
        if ((batchDetail != null) && (serviceOrder != null) && (step != null)) {
            this.hasActiveBatch = true;
        } else {
            this.hasActiveBatch = false;
        }
    }

    /// batchDetail
    public void setBatchDetail(@NonNull BatchDetail batchDetail){
        this.batchDetail = batchDetail;
        updateHasActiveBatch();
    }

    public BatchDetail getBatchDetail(){
        return batchDetail;
    }

    /// serviceOrder
    public void setServiceOrder(@NonNull ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        updateHasActiveBatch();
    }

    public ServiceOrder getServiceOrder(){
        return serviceOrder;
    }

    /// step
    public void setStep(@NonNull OrderStepInterface step) {
        this.step = step;
        this.taskType = step.getTaskType();
        updateHasActiveBatch();
    }

    public OrderStepInterface getStep(){
        return step;
    }

    public ServiceOrderNavigationStep getNavigationStep(){
        return (ServiceOrderNavigationStep) step;
    }

    public ServiceOrderPhotoStep getPhotoStep(){
        return (ServiceOrderPhotoStep) step;
    }

    public ServiceOrderReceiveAssetStep getReceiveAssetStep() {
        return (ServiceOrderReceiveAssetStep) step;
    }

    public ServiceOrderGiveAssetStep getGiveAssetStep() { return (ServiceOrderGiveAssetStep) step; }

    public ServiceOrderUserTriggerStep getUserTriggerStep() { return (ServiceOrderUserTriggerStep) step; }

    public ServiceOrderAuthorizePaymentStep getAuthorizePaymentStep() { return (ServiceOrderAuthorizePaymentStep) step; }

    /// task type
    public OrderStepInterface.TaskType getTaskType(){
        return taskType;
    }

    /// hasActiveBatch
    public Boolean hasActiveBatch(){
        return this.hasActiveBatch;
    }

    /// Service Order List
    public void setServiceOrderList(){
        this.hasServiceOrderList = false;
    }

    public void setServiceOrderList(@NonNull ArrayList<ServiceOrder> orderList){
        this.serviceOrderList.clear();
        this.serviceOrderList.addAll(orderList);
        this.hasServiceOrderList = true;
    }

    public Boolean hasServiceOrderList(){
        return this.hasServiceOrderList;
    }

    public ArrayList<ServiceOrder> getServiceOrderList(){
        return this.serviceOrderList;
    }


    /// Order Step List
    public void setOrderStepList(){
        this.hasOrderStepList = false;
    }

    public void setOrderStepList(@NonNull ArrayList<OrderStepInterface> stepList){
        this.orderStepList.clear();
        this.orderStepList.addAll(stepList);
        this.hasOrderStepList = true;
    }

    public Boolean hasOrderStepList(){
        return this.hasOrderStepList;
    }

    public ArrayList<OrderStepInterface> getOrderStepList(){
        return this.orderStepList;
    }

    /// Route Stop List
    public void setRouteStopList(){
        this.hasRouteStopList = false;
    }

    public void setRouteStopList(@NonNull ArrayList<RouteStop> stopList){
        this.routeStopList.clear();
        this.routeStopList.addAll(stopList);
        this.hasRouteStopList = true;
    }

    public Boolean hasRouteStopList(){
        return this.hasRouteStopList;
    }

    public ArrayList<RouteStop> getRouteStopList(){
        return this.routeStopList;
    }

}
