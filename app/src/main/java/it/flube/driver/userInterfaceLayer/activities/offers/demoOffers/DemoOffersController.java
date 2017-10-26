/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.DemoBatchCreation.DemoBatchNearbyPhotos;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowDemoOfferCreatedAlertEvent;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.driver.useCaseLayer.listenForOffers.UseCaseListenForOffers;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class DemoOffersController implements
        OffersListAdapter.Response,
        UseCaseMakeDemoBatchRequest.Response {
    private final String TAG = "DemoOffersController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public DemoOffersController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();

        Timber.tag(TAG).d("DemoOffersController CREATED");
    }

    public void listenForOffers(){
        useCaseExecutor.execute(new UseCaseListenForOffers(device));
    }

    public void doMakeDemoOffer(){
        useCaseExecutor.execute(new UseCaseMakeDemoBatchRequest(device, new DemoBatchNearbyPhotos(), this));
        Timber.tag(TAG).d("make demo offer REQUEST...");
    }

    public void makeDemoBatchComplete(){
        Timber.tag(TAG).d("...make demo batch COMPLETE!");
        EventBus.getDefault().postSticky(new ShowDemoOfferCreatedAlertEvent());
    }

    public void offerSelected(Batch offer) {
        Timber.tag(TAG).d("offer Selected --> " + offer.getGuid());
        useCaseExecutor.execute(new UseCaseOfferSelected(device, offer, new OfferSelectedResponseHandler()));
    }

    public void close(){
        device = null;
        useCaseExecutor = null;
    }
}
