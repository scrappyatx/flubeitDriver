/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer.getOfferData;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 4/10/2018
 * Project : Driver
 */
public class UseCaseGetPublicOfferData implements
        Runnable,
        CloudPublicOfferInterface.GetBatchDetailResponse,
        CloudPublicOfferInterface.GetServiceOrderListResponse,
        CloudPublicOfferInterface.GetRouteStopListResponse {

    private final static String TAG = "UseCaseGetPublicOfferData";

    private final CloudPublicOfferInterface publicOffer;
    private final Driver driver;
    private final String batchGuid;
    private final Response response;

    private BatchDetail batchDetail;
    private ArrayList<ServiceOrder> orderList;
    private ArrayList<RouteStop> routeList;

    public UseCaseGetPublicOfferData(MobileDeviceInterface device, String batchGuid, Response response){
        this.publicOffer = device.getCloudPublicOffer();
        this.driver = device.getCloudAuth().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //Step 1 -> Get batch Detail
        publicOffer.getBatchDetailRequest(driver, batchGuid, this);
    }

    public void cloudGetPublicOfferBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("step 1 -> got batch detail!");
        this.batchDetail = batchDetail;

        //Step 2 -> get the service order list
        publicOffer.getServiceOrderListRequest(driver, batchGuid, this);
    }

    public void cloudGetPublicOfferServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("step 2 -> got service order list!");
        this.orderList = new ArrayList<ServiceOrder>();
        this.orderList.clear();
        this.orderList.addAll(orderList);

        //step 3 -> get route stop list
        publicOffer.getRouteStopListRequest(driver, batchGuid, this);
    }

    ////
    //// An Offer may not have ANY route stops.
    ////

    public void cloudGetPublicOfferRouteStopListSuccess(ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("step 3 -> got route stop list!");
        this.routeList = new ArrayList<RouteStop>();
        this.routeList.clear();
        this.routeList.addAll(routeList);

        response.getOfferDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }

    public void cloudGetPublicOfferRouteStopListFailure(){
        Timber.tag(TAG).w("couldn't get route stop list");
        this.routeList = new ArrayList<RouteStop>();
        response.getOfferDataSuccess(this.batchDetail, this.orderList, this.routeList);
    }



    ///
    /// Failure Modes
    ///

    public void cloudGetPublicOfferBatchDetailFailure(){
        Timber.tag(TAG).w("couldn't get batch detail!");
        response.getOfferDataFailure();
    }

    public void cloudGetPublicOfferServiceOrderListFailure(){
        Timber.tag(TAG).w("couldn't get service order list!");
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
