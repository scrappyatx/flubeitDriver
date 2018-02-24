/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersMakeActivity;
import timber.log.Timber;

/**
 * Created on 2/14/2018
 * Project : Driver
 */

public class DemoOffersMakeNavigator {
    private static final String TAG = "DemoOffersMakeNavigator";

    ///
    ///  DEMO OFFERS MAKE
    ///
    public static void gotoActivityDemoOffersMake(Context context) {
        context.startActivity(new Intent(context, DemoOffersMakeActivity.class));
        Timber.tag(TAG).d("starting activity DemoOffersMakeActivity.class");
    }
}
