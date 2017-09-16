/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.DemoBatchCreation.DemoNearbyPhotos;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import it.flube.driver.useCaseLayer.generateOffer.UseCaseMakeDemoOfferRequest;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class DemoOffersController implements
        OffersListAdapter.Response,
        UseCaseMakeDemoOfferRequest.Response {
    private final String TAG = "DemoOffersController";
    private ExecutorService useCaseExecutor;

    public DemoOffersController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        Timber.tag(TAG).d("DemoOffersController CREATED");
    }

    public void doMakeDemoOffer(){
        useCaseExecutor.execute(new UseCaseMakeDemoOfferRequest(AndroidDevice.getInstance(), new DemoNearbyPhotos(), this));
        Timber.tag(TAG).d("make demo offer REQUEST...");
    }

    public void makeDemoOfferComplete(){
        Timber.tag(TAG).d("...make demo offer COMPLETE!");
        EventBus.getDefault().postSticky(new DemoOfferAlerts.ShowDemoOfferCreatedAlertEvent());
    }

    public void offerSelected(Offer offer) {
        Timber.tag(TAG).d("offer Selected --> " + offer.getGUID());
        useCaseExecutor.execute(new UseCaseOfferSelected(offer, new OfferSelectedResponseHandler()));
    }

    public void close(){
        useCaseExecutor.shutdown();
    }
}
