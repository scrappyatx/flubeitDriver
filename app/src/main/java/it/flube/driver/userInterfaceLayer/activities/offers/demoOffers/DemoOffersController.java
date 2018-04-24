/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchNearbyPhotos;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowDemoOfferCreatedAlertEvent;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_TIMEOUT_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.MAKE_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.MAKE_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.MAKE_OFFER_SUCCESS_VALUE;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class DemoOffersController implements
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden  {

    private final String TAG = "DemoOffersController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public DemoOffersController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();

        Timber.tag(TAG).d("DemoOffersController CREATED");
    }

    public void checkIfClaimAlertNeedsToBeShown(AppCompatActivity activity){
        /// get the batch data for the batchGuid that was used to launch this activity
        if (activity.getIntent().hasExtra(CLAIM_OFFER_RESULT_KEY)){
            //get the result
            String resultKey = activity.getIntent().getStringExtra(CLAIM_OFFER_RESULT_KEY);
            Timber.tag(TAG).d(CLAIM_OFFER_RESULT_KEY + " --> " + resultKey);
            switch (resultKey){
                case CLAIM_OFFER_SUCCESS_VALUE:
                    Timber.tag(TAG).d("claim offer SUCCESS!");
                    new OfferClaimAlerts().showSuccessAlert(activity, this);
                    break;
                case CLAIM_OFFER_FAILURE_VALUE:
                    Timber.tag(TAG).d("claim offer FAILURE!");
                    new OfferClaimAlerts().showFailureAlert(activity, this);
                    break;
                case CLAIM_OFFER_TIMEOUT_VALUE:
                    Timber.tag(TAG).d("claim offer TIMEOUT");
                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            // now remove the key, only want to show an alert once
            activity.getIntent().removeExtra(CLAIM_OFFER_RESULT_KEY);
        } else {
            Timber.tag(TAG).d("activity started with no claim_offer_result");
        }
    }

    public void checkIfMakeAlertNeedsToBeShown(AppCompatActivity activity, DemoOfferAlerts.DemoOfferCreatedAlertHidden hidden){
        if (activity.getIntent().hasExtra(MAKE_OFFER_RESULT_KEY)){
            String resultKey = activity.getIntent().getStringExtra(MAKE_OFFER_RESULT_KEY);
            Timber.tag(TAG).d(MAKE_OFFER_RESULT_KEY + " --> " + resultKey);
            switch(resultKey){
                case MAKE_OFFER_SUCCESS_VALUE:
                    Timber.tag(TAG).d("make offer SUCCESS!");
                    new DemoOfferAlerts().showDemoOfferCreatedAlert(activity, hidden);
                    break;
                case MAKE_OFFER_FAILURE_VALUE:
                    Timber.tag(TAG).d("make offer FAILURE!");

                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            //now remove the key, only want to show an alert once
            activity.getIntent().removeExtra(MAKE_OFFER_RESULT_KEY);
        } else {
            Timber.tag(TAG).d("activity started with no make_offer_result");
        }
    }



    //// claim offer alert interfaces

    public void claimOfferSuccessAlertHidden() {
        Timber.tag(TAG).d("claim offer success alert hidden");
    }

    public void claimOfferFailureAlertHidden() {
        Timber.tag(TAG).d("claim offer failure alert hidden");
    }
    public void claimOfferTimeoutAlertHidden() {
        Timber.tag(TAG).d("claim offer timeout alert hidden");
    }

    public void close(){
        device = null;
        useCaseExecutor = null;
    }
}
