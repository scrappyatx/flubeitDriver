/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch.getBatchData;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.claimOffer.getOfferData.UseCaseGetPublicOfferData;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/17/2018
 * Project : Driver
 */
public class UseCaseGetBatchData implements
        Runnable,
        CloudScheduledBatchInterface.GetBatchDetailResponse,
        CloudScheduledBatchInterface.GetServiceOrderListResponse,
        CloudScheduledBatchInterface.GetRouteStopListResponse {

    private final static String TAG = "UseCaseGetPublicOfferData";

    private final CloudScheduledBatchInterface cloudBatch;
    private final Driver driver;
    private final String batchGuid;
    private final UseCaseGetBatchData.Response response;

    private BatchDetail batchDetail;
    private ArrayList<ServiceOrder> orderList;
    private ArrayList<RouteStop> routeList;

    public UseCaseGetBatchData(MobileDeviceInterface device, String batchGuid, UseCaseGetBatchData.Response response){
        this.cloudBatch = device.getCloudScheduledBatch();
        this.driver = device.getUser().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //Step 1 -> Get batch Detail
        cloudBatch.getScheduledBatchDetailRequest(driver, batchGuid, this);

    }

    public void cloudGetScheduledBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("step 1 -> got batch detail!");
        this.batchDetail = batchDetail;

        //Step 2 -> get the service order list
        cloudBatch.getScheduledBatchServiceOrderListRequest(driver, batchGuid, this);
    }

    public void cloudGetScheduledBatchServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("step 2 -> got service order list!");
        this.orderList = new ArrayList<ServiceOrder>();
        this.orderList.clear();
        this.orderList.addAll(orderList);

        //step 3 -> get route stop list
        cloudBatch.getScheduledBatchRouteStopListRequest(driver, batchGuid, this);
    }

    ////
    ////    A batch may not always have a route stop list (if there are no navigation steps in the service order)
    ////
    public void cloudGetScheduledBatchRouteStopListSuccess(ArrayList<RouteStop> stopList){
        Timber.tag(TAG).d("step 3 -> got route stop list!");
        this.routeList = new ArrayList<RouteStop>();
        this.routeList.clear();
        this.routeList.addAll(routeList);

        response.getBatchDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }

    public void cloudGetScheduledBatchRouteStopListFailure(){
        Timber.tag(TAG).w("couldn't get route stop list");
        this.routeList = new ArrayList<RouteStop>();
        response.getBatchDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }

    ///
    /// Failure Modes
    ///
    public void cloudGetScheduledBatchDetailFailure(){
        Timber.tag(TAG).w("couldn't get batch detail!");
        response.getBatchDataFailure();
    }

    public void cloudGetScheduledBatchServiceOrderListFailure(){
        Timber.tag(TAG).w("couldn't get service order list!");
        response.getBatchDataFailure();
    }




    ///
    ///  Interface
    ///
    public interface Response {
        void getBatchDataSuccess(BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList);

        void getBatchDataFailure();
    }
}
