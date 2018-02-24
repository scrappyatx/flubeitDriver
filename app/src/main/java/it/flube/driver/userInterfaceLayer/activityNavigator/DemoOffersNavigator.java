/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import timber.log.Timber;

/**
 * Created on 2/11/2018
 * Project : Driver
 */

public class DemoOffersNavigator {
    private static final String TAG = "DemoOffersNavigator";

    ///
    ///  DEMO OFFERS
    ///
    public static void gotoActivityDemoOffers(Context context) {
        context.startActivity(new Intent(context, DemoOffersActivity.class));
        Timber.tag(TAG).d("starting activity DemoOffersActivity.class");
    }

    public static void gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(Context context){
        Intent i = new Intent(context, DemoOffersActivity.class);
        i.putExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY, DemoOffersActivity.CLAIM_OFFER_SUCCESS_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class AND show claim offer success alert");
    }

    public static void gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(Context context){
        Intent i = new Intent(context, DemoOffersActivity.class);
        i.putExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY, DemoOffersActivity.CLAIM_OFFER_FAILURE_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class AND show claim offer failure alert");
    }

    public static void gotoActivityDemoOffersAndShowOfferClaimedTimeoutAlert(Context context){
        Intent i = new Intent(context, DemoOffersActivity.class);
        i.putExtra(DemoOffersActivity.CLAIM_OFFER_RESULT_KEY, DemoOffersActivity.CLAIM_OFFER_TIMEOUT_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class AND show claim offer timeout alert");
    }

    public static void gotoActivityDemoOffersAndShowDemoOfferMakeSuccess(Context context){
        Intent i = new Intent(context, DemoOffersActivity.class);
        i.putExtra(DemoOffersActivity.MAKE_OFFER_RESULT_KEY, DemoOffersActivity.MAKE_OFFER_SUCCESS_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity DemoOffersActivity.class AND show make offer success alert");
    }

    public static void gotoActivityDemoOffersAndShowDemoOfferMakeFailure(Context context){
        Intent i = new Intent(context, DemoOffersActivity.class);
        i.putExtra(DemoOffersActivity.MAKE_OFFER_RESULT_KEY, DemoOffersActivity.MAKE_OFFER_FAILURE_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity DemoOffersActivity.class AND show make offer success alert");
    }
}
