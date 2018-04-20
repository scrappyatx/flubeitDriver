/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.appLifecycle;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 11/19/2017
 * Project : Driver
 */

public class UseCaseThingsToDoWhenApplicationResumes implements
        Runnable,
        CloudDemoOfferInterface.StartMonitoringResponse,
        CloudPublicOfferInterface.StartMonitoringResponse,
        CloudPersonalOfferInterface.StartMonitoringResponse,
        CloudScheduledBatchInterface.StartMonitoringResponse,
        CloudActiveBatchInterface.StartMonitoringResponse {

    private final static String TAG = "UseCaseThingsToDoWhenApplicationResumes";

    private final MobileDeviceInterface device;

    public UseCaseThingsToDoWhenApplicationResumes(MobileDeviceInterface device){
        this.device = device;
    }
    public void run(){
        if (device.getUser().isSignedIn()){
            //device has signed in user, start monitoring demo offers, public offers, personal offers, scheduled batches & active batch
            Timber.tag(TAG).d("...device has signed in user, stop monitoring demo offers, public offers, personal offers, scheduled batches and active batch");
            Driver driver = device.getUser().getDriver();
            device.getCloudDemoOffer().startMonitoringRequest(driver, device.getOfferLists(),this);
            device.getCloudPersonalOffer().startMonitoringRequest(driver, device.getOfferLists(),this);
            device.getCloudPublicOffer().startMonitoringRequest(driver, device.getOfferLists(),this);
            device.getCloudScheduledBatch().startMonitoringRequest(driver, device.getOfferLists(),this);
            device.getCloudActiveBatch().startMonitoringRequest(driver, device.getOfferLists(),this);

        } else {
            //do nothing
            //device has signed in user, start monitoring demo offers, public offers, personal offers, scheduled batches & active batch
            Timber.tag(TAG).d("...device has no signed in user, do nothing");
        }
    }

    public void cloudDemoOffersStartMonitoringComplete(){
        Timber.tag(TAG).d("...cloudDemoOffersStartMonitoringComplete");
    }

    public void cloudPersonalOffersStartMonitoringComplete(){
        Timber.tag(TAG).d("...cloudPersonalOffersStartMonitoringComplete");
    }

    public void cloudPublicOffersStartMonitoringComplete(){
        Timber.tag(TAG).d("...cloudPublicOffersStartMonitoringComplete");
    }

    public void cloudScheduledBatchStartMonitoringComplete(){
        Timber.tag(TAG).d("...cloudScheduledBatchStartMonitoringComplete");
    }

    public void cloudActiveBatchStartMonitoringComplete(){
        Timber.tag(TAG).d("...cloudActiveBatchStartMonitoringComplete");
    }

}
