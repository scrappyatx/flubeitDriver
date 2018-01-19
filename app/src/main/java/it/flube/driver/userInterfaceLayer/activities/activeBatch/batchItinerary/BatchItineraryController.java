/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class BatchItineraryController  {

    private final String TAG = "BatchItineraryController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public BatchItineraryController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }


    public void close(){
        device = null;
    }
}
