/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.DemoBatchCreation.DemoBatchNearbyPhotos;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowDemoOfferCreatedAlertEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class DemoOffersController implements
        OffersListAdapter.Response,
        UseCaseMakeDemoBatchRequest.Response,
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

    public void checkIfClaimAlertNeedsToBeShown(AppCompatActivity activity){
        /// get the batch data for the batchGuid that was used to launch this activity
        if (activity.getIntent().hasExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY)){
            //get the result
            String resultKey = activity.getIntent().getStringExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY);
            Timber.tag(TAG).d(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY + " --> " + resultKey);
            switch (resultKey){
                case DemoOffersActivity.CLAIM_OFFER_SUCCESS_VALUE:
                    Timber.tag(TAG).d("claim offer SUCCESS!");
                    new OfferClaimAlerts().showSuccessAlert(activity, this);
                    break;
                case DemoOffersActivity.CLAIM_OFFER_FAILURE_VALUE:
                    Timber.tag(TAG).d("claim offer FAILURE!");
                    new OfferClaimAlerts().showFailureAlert(activity, this);
                    break;
                case DemoOffersActivity.CLAIM_OFFER_TIMEOUT_VALUE:
                    Timber.tag(TAG).d("claim offer TIMEOUT");
                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            // now remove the key, only want to show an alert once
            activity.getIntent().removeExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY);
        } else {
            Timber.tag(TAG).d("activity started with no claim_offer_result");
        }
    }

    public void checkIfMakeAlertNeedsToBeShown(AppCompatActivity activity, DemoOfferAlerts.DemoOfferCreatedAlertHidden hidden){
        if (activity.getIntent().hasExtra(DemoOffersActivity.MAKE_OFFER_RESULT_KEY)){
            String resultKey = activity.getIntent().getStringExtra(DemoOffersActivity.MAKE_OFFER_RESULT_KEY);
            Timber.tag(TAG).d(DemoOffersActivity.MAKE_OFFER_RESULT_KEY + " --> " + resultKey);
            switch(resultKey){
                case DemoOffersActivity.MAKE_OFFER_SUCCESS_VALUE:
                    Timber.tag(TAG).d("make offer SUCCESS!");
                    new DemoOfferAlerts().showDemoOfferCreatedAlert(activity, hidden);
                    break;
                case DemoOffersActivity.MAKE_OFFER_FAILURE_VALUE:
                    Timber.tag(TAG).d("make offer FAILURE!");

                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            //now remove the key, only want to show an alert once
            activity.getIntent().removeExtra(DemoOffersActivity.MAKE_OFFER_RESULT_KEY);
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
