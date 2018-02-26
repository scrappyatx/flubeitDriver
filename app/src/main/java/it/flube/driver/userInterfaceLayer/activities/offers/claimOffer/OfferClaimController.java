/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.useCaseLayer.batchDetail.UseCaseGetBatchData;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimDemoOfferRequest;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
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

    public void getOfferData(String batchGuid, UseCaseGetBatchData.Response response){
        Timber.tag(TAG).d("getOfferData START...");
        useCaseExecutor.execute(new UseCaseGetBatchData(device, batchGuid, response));
    }

    public void claimOfferRequest(BatchDetail batchDetail, UseCaseClaimDemoOfferRequest.Response response) {
        Timber.tag(TAG).d("claimOfferRequest STARTED");


        switch (batchDetail.getBatchType()) {
            case MOBILE_DEMO:
                useCaseExecutor.execute(new UseCaseClaimDemoOfferRequest(device, batchDetail.getBatchGuid(), response));
                break;
            case PRODUCTION:
            case PRODUCTION_TEST:
                Timber.tag(TAG).d("tried to claim a production_test offer");

            default:
                //useCaseExecutor.execute(new UseCaseClaimOfferRequestDEPRECATED(device, batchDetail.getBatchGuid(), new ClaimPublicOfferResponseHandler()));
                break;
        }
    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }

}
