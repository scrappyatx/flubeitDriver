/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.demo.DemoActivity;
import it.flube.driver.userInterfaceLayer.activities.earnings.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.help.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeNoActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferClaimActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchManageActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchMapActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInActivity;
import it.flube.driver.userInterfaceLayer.activities.messages.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;
import timber.log.Timber;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigator {
    private static final String TAG = "ActivityNavigator";

    public ActivityNavigator(){}

    public void gotoActivityAccount(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
        Timber.tag(TAG).d("starting activity AccountActivity.class");
    }

    public void gotoActivityHome(Context context){
        //TODO need to add logic to determine which home activity to go to, home active batch or home no active batch
        //
        if (AndroidDevice.getInstance().getUser().hasActiveBatch()) {
            context.startActivity(new Intent(context, HomeActiveBatchActivity.class));
            Timber.tag(TAG).d("starting activity HomeActiveBatchActivity.class");
        } else {
            context.startActivity(new Intent(context, HomeNoActiveBatchActivity.class));
            Timber.tag(TAG).d("starting activity HomeNoActiveBatchActivity.class");
        }
    }

    public void gotoActivityDemo(Context context) {
        context.startActivity(new Intent(context, DemoActivity.class));
        Timber.tag(TAG).d("starting activity DemoActivity.class");
    }

    public void gotoActivityEarnings(Context context) {
        context.startActivity(new Intent(context, EarningsActivity.class));
        Timber.tag(TAG).d("starting activity EarningsActivity.class");
    }

    public void gotoActivityHelp(Context context) {
        context.startActivity(new Intent(context, HelpActivity.class));
        Timber.tag(TAG).d("starting activity HelpActivity.class");
    }

    public void gotoActivityLogin(Context context) {
        context.startActivity(new Intent(context, SignInActivity.class));
        Timber.tag(TAG).d("starting activity SignInActivity.class");
    }

    public void gotoActivityMessages(Context context) {
        context.startActivity(new Intent(context, MessagesActivity.class));
        Timber.tag(TAG).d("starting activity MessagesActivity.class");
    }

    public void gotoActivityOffers(Context context) {
        context.startActivity(new Intent(context, OffersActivity.class));
        Timber.tag(TAG).d("starting activity OffersActivity.class");
    }

    public void gotoActivityOfferClaim(Context context) {
        context.startActivity(new Intent(context, OfferClaimActivity.class));
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class");
    }

    public void gotoActivityScheduledBatches(Context context) {
        context.startActivity(new Intent(context, ScheduledBatchesActivity.class));
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class");
    }

    public void gotoActivityBatchManage(Context context) {
        context.startActivity(new Intent(context, BatchManageActivity.class));
        Timber.tag(TAG).d("starting activity BatchManageActivity.class");
    }

    public void gotoActivityBatchMap(Context context) {
        context.startActivity(new Intent(context, BatchMapActivity.class));
        Timber.tag(TAG).d("starting activity BatchMapActivity.class");
    }


    public void gotoActivitySplashScreen(Context context) {
        context.startActivity(new Intent(context, SplashScreenActivity.class));
        Timber.tag(TAG).d("starting activity SplashScreenActivity.class");
    }

}
