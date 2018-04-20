/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer.getOfferData;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 4/10/2018
 * Project : Driver
 */
public class UseCaseGetDemoOfferData implements
    Runnable,
    CloudDemoOfferInterface.GetBatchDetailResponse,
    CloudDemoOfferInterface.GetServiceOrderListResponse,
    CloudDemoOfferInterface.GetRouteStopListResponse {

    private final static String TAG = "UseCaseGetDemoOfferData";

    private final CloudDemoOfferInterface demoOffer;
    private final Driver driver;
    private final String batchGuid;
    private final Response response;

    private BatchDetail batchDetail;
    private ArrayList<ServiceOrder> orderList;
    private ArrayList<RouteStop> routeList;

    public UseCaseGetDemoOfferData(MobileDeviceInterface device, String batchGuid, Response response){
        this.demoOffer = device.getCloudDemoOffer();
        this.driver = device.getUser().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("clientId  -> " + driver.getClientId());
        Timber.tag(TAG).d("batchGuid -> " + batchGuid);

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //Step 1 -> Get batch Detail
        demoOffer.getBatchDetailRequest(driver, batchGuid, this);
    }

    public void cloudGetDemoBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("step 1 -> got batch detail!");
        this.batchDetail = batchDetail;

        //Step 2 -> get the service order list
        demoOffer.getServiceOrderListRequest(driver, batchGuid, this);
    }

    public void cloudGetDemoServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("step 2 -> got service order list!");
        this.orderList = new ArrayList<ServiceOrder>();
        this.orderList.clear();
        this.orderList.addAll(orderList);

        //step 3 -> get route stop list
        demoOffer.getRouteStopListRequest(driver, batchGuid, this);
    }

    public void cloudGetDemoRouteStopListSuccess(ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("step 3 -> got route stop list!");
        this.routeList = new ArrayList<RouteStop>();
        this.routeList.clear();
        this.routeList.addAll(routeList);

        response.getOfferDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }

    ///
    /// Failure Modes
    ///

    public void cloudGetDemoBatchDetailFailure(){
        Timber.tag(TAG).w("couldn't get batch detail!");
        response.getOfferDataFailure();
    }

    public void cloudGetDemoServiceOrderListFailure(){
        Timber.tag(TAG).w("couldn't get service order list!");
        response.getOfferDataFailure();
    }

    public void cloudGetDemoRouteStopListFailure(){
        Timber.tag(TAG).w("couldn't get route stop list");
        response.getOfferDataFailure();
    }

    ///
    ///  Interface
    ///
    public interface Response {
        void getOfferDataSuccess(BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList);

        void getOfferDataFailure();
    }
}