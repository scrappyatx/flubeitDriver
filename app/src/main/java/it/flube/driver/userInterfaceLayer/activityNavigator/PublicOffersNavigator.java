/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.personalOffers.PersonalOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.publicOffers.PublicOffersActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_TIMEOUT_VALUE;

/**
 * Created on 4/12/2018
 * Project : Driver
 */
public class PublicOffersNavigator {
    private static final String TAG = "PublicOffersNavigator";

    public static void gotoActivityPublicOffers(Context context) {
        context.startActivity(new Intent(context, PublicOffersActivity.class));
        Timber.tag(TAG).d("starting activity PublicOffersActivity.class");
    }

    public static void gotoActivityPublicOffersAndShowOfferClaimedSuccessAlert(Context context){
        Intent i = new Intent(context, PublicOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_SUCCESS_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PublicOffersActivity.class AND show claim offer success alert");
    }

    public static void gotoActivityPublicOffersAndShowOfferClaimedFailureAlert(Context context){
        Intent i = new Intent(context, PublicOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_FAILURE_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PublicOffersActivity.class AND show claim offer failure alert");
    }

    public static void gotoActivityPublicOffersAndShowOfferClaimedTimeoutAlert(Context context){
        Intent i = new Intent(context, PublicOffersActivity.class);
        i.putExtra(CLAIM_OFFER_RESULT_KEY, CLAIM_OFFER_TIMEOUT_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity PublicOffersActivity.class AND show claim offer timeout alert");
    }
}
