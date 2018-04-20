/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
import it.flube.driver.useCaseLayer.claimOffer.getOfferData.UseCaseGetOfferData;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimController {
    private final String TAG = "OfferClaimController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public OfferClaimController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
    }

    public void getOfferData(String batchGuid, OfferConstants.OfferType offerType, UseCaseGetOfferData.Response response){
        Timber.tag(TAG).d("getOfferData START...");
        Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);
        Timber.tag(TAG).d("   ...offerType -> " + offerType);

        useCaseExecutor.execute(new UseCaseGetOfferData(device, batchGuid, offerType, response));
    }

    public void claimOfferRequest(String batchGuid, OfferConstants.OfferType offerType, BatchDetail.BatchType batchType, UseCaseClaimOfferRequest.Response response) {
        Timber.tag(TAG).d("claimOfferRequest STARTED");
        Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);
        Timber.tag(TAG).d("   ...offerType -> " + offerType);

        useCaseExecutor.execute(new UseCaseClaimOfferRequest(device, batchGuid, offerType, batchType, response));

    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }

}
