/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 1/19/2018
 * Project : Driver
 */

public class UseCaseLocationUpdateRequest implements
        Runnable,
        CloudActiveBatchInterface.SaveMapLocationResponse,
        CloudServerMonitoringInterface.LocationUpdateResponse {


    private final static String TAG = "UseCaseLocationUpdateRequest";

    private MobileDeviceInterface device;
    private Driver driver;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private LatLonLocation location;

    private Response response;

    public UseCaseLocationUpdateRequest(MobileDeviceInterface device,
                                        String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                        LatLonLocation location, Response response){


        this.device = device;
        this.driver = device.getCloudAuth().getDriver();
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.location = location;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        ///save location update to batch data node
        Timber.tag(TAG).d("   ...sending location to cloud database");
        //device.getCloudActiveBatch().saveMapLocationRequest(driver, batchGuid, serviceOrderGuid, orderStepGuid, location, this);

        //TODO remember to take this one out you dumbass
        device.getCloudServerMonitoring().locationUpdateRequest(batchGuid, location, this);
    }

    public void cloudActiveBatchSaveMapLocationComplete(){
        Timber.tag(TAG).d("   ...UseCase COMPLETE");
        //now save date to server monitoring node
        device.getCloudServerMonitoring().locationUpdateRequest(batchGuid, location, this);
    }

    public void cloudServerMonitoringLocationUpdateComplete(String batchGuid){
        Timber.tag(TAG).d("cloudServerMonitoringLocationUpdateComplete");
        response.useCaseLocationUpdateComplete();
    }

    public interface Response {
        void useCaseLocationUpdateComplete();
    }
}
