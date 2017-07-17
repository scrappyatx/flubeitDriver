/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.demo.DemoActivity;
import it.flube.driver.userInterfaceLayer.activities.earnings.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.help.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeNoActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInActivity;
import it.flube.driver.userInterfaceLayer.activities.messages.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigator {

    public ActivityNavigator(){}

    public void gotoActivityAccount(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    public void gotoActivityHome(Context context){
        //TODO need to add logic to determine which home activity to go to, home active batch or home no active batch
        //
        context.startActivity(new Intent(context, HomeNoActiveBatchActivity.class));
    }

    public void gotoActivityDemo(Context context) {
        context.startActivity(new Intent(context, DemoActivity.class));
    }

    public void gotoActivityEarnings(Context context) {
        context.startActivity(new Intent(context, EarningsActivity.class));
    }

    public void gotoActivityHelp(Context context) {
        context.startActivity(new Intent(context, HelpActivity.class));
    }

    public void gotoActivityLogin(Context context) {
        context.startActivity(new Intent(context, SignInActivity.class));
    }

    public void gotoActivityMessages(Context context) {
        context.startActivity(new Intent(context, MessagesActivity.class));
    }

    public void gotoActivityOffers(Context context) {
        context.startActivity(new Intent(context, OffersActivity.class));
    }

    public void gotoActivityScheduledBatches(Context context) {
        context.startActivity(new Intent(context, ScheduledBatchesActivity.class));
    }

    public void gotoActivitySplashScreen(Context context) {
        context.startActivity(new Intent(context, SplashScreenActivity.class));
    }

}
