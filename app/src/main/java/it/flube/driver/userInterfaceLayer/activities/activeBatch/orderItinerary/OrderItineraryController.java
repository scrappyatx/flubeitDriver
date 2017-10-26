/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.GetAccountDetailsResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut.SignOutResponseHandler;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.useCaseLayer.account.UseCaseGetAccountDetails;
import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseSignOut;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class OrderItineraryController implements
    OrderStepListAdapter.Response {

    private final String TAG = "OrderItineraryController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public OrderItineraryController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }



    public void close(){
        device = null;
    }

    public void stepSelected(OrderStepInterface orderStep){
        Timber.tag(TAG).d("order step selected : step guid -> " + orderStep.getGuid());
    }
}
