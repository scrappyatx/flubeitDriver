/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.personalOffers;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_TIMEOUT_VALUE;

/**
 * Created on 10/23/2017
 * Project : Driver
 */

public class PersonalOffersController implements
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden {

    private final String TAG = "PersonalOffersController";
    private MobileDeviceInterface device;
    private ExecutorService useCaseExecutor;

    public PersonalOffersController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();

        Timber.tag(TAG).d("created");
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

