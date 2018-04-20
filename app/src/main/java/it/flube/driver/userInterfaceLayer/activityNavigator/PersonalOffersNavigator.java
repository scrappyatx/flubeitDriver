/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.personalOffers.PersonalOffersActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_TIMEOUT_VALUE;

/**
 * Created on 4/12/2018
 * Project : Driver
 */
public class PersonalOffersNavigator {
    private static final String TAG = "PersonalOffersNavigator";

    public static void gotoActivityPersonalOffers(Context context) {
        context.startActivity(new Intent(context, PersonalOffersActivity.class));
        Timber.tag(TAG).d("starting activity PersonalOffersActivity.class");
    }

    public static void gotoActivityPersonalOffersAndShowOfferClaimedSuccessAlert(Context context){
        Intent i = new Intent(context, PersonalOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_SUCCESS_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PersonalOffersActivity.class AND show claim offer success alert");
    }

    public static void gotoActivityPersonalOffersAndShowOfferClaimedFailureAlert(Context context){
        Intent i = new Intent(context, PersonalOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_FAILURE_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PersonalOffersActivity.class AND show claim offer failure alert");
    }

    public static void gotoActivityPersonalOffersAndShowOfferClaimedTimeoutAlert(Context context){
        Intent i = new Intent(context, PersonalOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_TIMEOUT_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PersonalOffersActivity.class AND show claim offer timeout alert");
    }
}
