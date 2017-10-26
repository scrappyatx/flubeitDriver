/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class BatchItineraryController implements
        ServiceOrderListAdapter.Response {

    private final String TAG = "BatchItineraryController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public BatchItineraryController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void orderSelected(ServiceOrder serviceOrder){
        Timber.tag(TAG).d("service order selected : order guid -> " + serviceOrder.getGuid());
    }


    public void close(){
        device = null;
    }
}
