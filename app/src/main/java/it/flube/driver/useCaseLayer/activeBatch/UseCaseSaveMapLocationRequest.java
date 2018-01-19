/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 1/19/2018
 * Project : Driver
 */

public class UseCaseSaveMapLocationRequest implements
        Runnable,
        CloudDatabaseInterface.SaveMapLocationResponse {


    private final static String TAG = "UseCaseSaveMapLocationRequest";

    private MobileDeviceInterface device;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private LatLonLocation location;

    public UseCaseSaveMapLocationRequest(MobileDeviceInterface device,
                                       String batchGuid, String serviceOrderGuid, String orderStepGuid,
                                       LatLonLocation location){


        this.device = device;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.location = location;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        Timber.tag(TAG).d("   ...sending location to realtime messaging");
        device.getRealtimeActiveBatchMessages().sendMsgLocationUpdate(location.getLatitude(), location.getLongitude());

        Timber.tag(TAG).d("   ...sending location to cloud database");
        device.getCloudDatabase().saveMapLocationRequest(batchGuid, serviceOrderGuid, orderStepGuid, location, this);

    }

    public void cloudDatabaseSaveMapLocationComplete(){
        Timber.tag(TAG).d("   ...UseCase COMPLETE");
    }
}
