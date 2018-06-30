/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.earnings.testEarnings.TestEarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import timber.log.Timber;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class DevTestEarningsNavigator {
    private static final String TAG = "DevTestEarningsNavigator";

    public static void gotoActivityTestEarnings(Context context) {
        context.startActivity(new Intent(context, TestEarningsActivity.class));
        Timber.tag(TAG).d("starting activity TestEarningsActivity.class");
    }
}
