/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.batchDetail;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.RouteList;
import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 1/16/2018
 * Project : Driver
 */

public class UseCaseGetBatchData implements
    Runnable,
        CloudDatabaseInterface.GetBatchDetailResponse,
        CloudDatabaseInterface.GetServiceOrderListResponse,
        CloudDatabaseInterface.GetRouteStopListResponse {

    private final static String TAG = "UseCaseGetBatchData";

    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final Response response;

    private BatchDetail batchDetail;
    private ArrayList<ServiceOrder> orderList;
    private ArrayList<RouteStop> routeList;

    public UseCaseGetBatchData(MobileDeviceInterface device, String batchGuid, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //Step 1 -> Get batch Detail
        device.getCloudDatabase().getBatchDetailRequest(batchGuid, this);
    }


    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("step 1 -> got batch detail!");
        this.batchDetail = batchDetail;

        //Step 2 -> get the service order list
        device.getCloudDatabase().getServiceOrderListRequest(batchGuid, this);
    }

    public void cloudDatabaseGetServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("step 2 -> got service order list!");
        this.orderList = new ArrayList<ServiceOrder>();
        this.orderList.clear();
        this.orderList.addAll(orderList);

        //step 3 -> get route stop list
        device.getCloudDatabase().getRouteStopListRequest(batchGuid, this);
    }

    public void cloudDatabaseGetRouteStopListSuccess(ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("step 3 -> got route stop list!");
        this.routeList = new ArrayList<RouteStop>();
        this.routeList.clear();
        this.routeList.addAll(routeList);

        response.getBatchDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }

    ////
    ///  FAILURE MODES
    ///
    public void cloudDatabaseGetBatchDetailFailure(){
        Timber.tag(TAG).w("couldn't get batch detail!");
        response.getBatchDataFailure();
    }

    public void cloudDatabaseGetServiceOrderListFailure(){
        Timber.tag(TAG).w("couldn't get service order list!");
        response.getBatchDataFailure();
    }

    public void cloudDatabaseGetRouteStopListFailure(){
        Timber.tag(TAG).w("couldn't get route stop list");
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
