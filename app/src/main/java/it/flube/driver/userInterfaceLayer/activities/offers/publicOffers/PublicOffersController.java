/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.publicOffers;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class PublicOffersController implements
        OffersListAdapter.Response,
        UseCaseClaimOfferRequest.Response {

    private final String TAG = "PublicOffersController";
    private MobileDeviceInterface device;
    private ExecutorService useCaseExecutor;

    public PublicOffersController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void offerSelected(Batch offer) {
        Timber.tag(TAG).d("offer Selected --> " + offer.getGuid());
        useCaseExecutor.execute(new UseCaseClaimOfferRequest(device, offer.getGuid(),this));
        //useCaseExecutor.execute(new UseCaseOfferSelected(offer, new OfferSelectedResponseHandler()));
    }

    public void useCaseClaimOfferRequestComplete(String batchGuid){
        Timber.tag(TAG).d("made a claim for batch guid -> " + batchGuid);
    }

    public void close(){
        device = null;
        useCaseExecutor = null;
    }

}

