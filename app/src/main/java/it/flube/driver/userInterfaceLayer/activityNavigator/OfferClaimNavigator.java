/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.OFFER_TYPE_KEY;

/**
 * Created on 3/23/2018
 * Project : Driver
 */

public class OfferClaimNavigator {
    private static final String TAG = "OfferClaimNavigator";

    public static void gotoActivityOfferClaim(Context context, OfferConstants.OfferType offerType, String batchGuid) {
        Intent i = new Intent(context, OfferClaimActivity.class);
        i.putExtra(OFFER_TYPE_KEY, offerType.toString());
        i.putExtra(BATCH_GUID_KEY, batchGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class...");
        Timber.tag(TAG).d("   ...offerType = " + offerType);
        Timber.tag(TAG).d("   ...batchGuid = " + batchGuid);

    }
}
