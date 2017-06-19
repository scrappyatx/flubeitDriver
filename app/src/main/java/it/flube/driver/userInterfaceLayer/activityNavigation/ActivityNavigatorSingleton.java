/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigation;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.DemoActivity;
import it.flube.driver.userInterfaceLayer.activities.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.HomeNoActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.LoginActivity;
import it.flube.driver.userInterfaceLayer.activities.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.OffersActivity;
import it.flube.driver.userInterfaceLayer.activities.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigatorSingleton {

    ///
    ///  Loader class provides synchronization across threads
    ///  Lazy initialization since Loader class is only called when "getInstance" is called
    ///  volatile keyword guarantees visibility of changes to variables across threads
    ///
    private static class Loader {
        static volatile ActivityNavigatorSingleton mInstance = new ActivityNavigatorSingleton();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private ActivityNavigatorSingleton(){}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static ActivityNavigatorSingleton getInstance() {
        return ActivityNavigatorSingleton.Loader.mInstance;
    }

    ///
    ///  all class variables are static
    ///

    public void gotoActivityAccount(Context c) {
        c.startActivity(new Intent(c, AccountActivity.class));
    }

    public void gotoActivityHome(Context c){
        //TODO need to add logic to determine which home activity to go to, home active batch or home no active batch
        //
        c.startActivity(new Intent(c, HomeNoActiveBatchActivity.class));
    }

    public void gotoActivityDemo(Context c) {
        c.startActivity(new Intent(c, DemoActivity.class));
    }

    public void gotoActivityEarnings(Context c) {
        c.startActivity(new Intent(c, EarningsActivity.class));
    }

    public void gotoActivityHelp(Context c) {
        c.startActivity(new Intent(c, HelpActivity.class));
    }

    public void gotoActivityLogin(Context c) {
        c.startActivity(new Intent(c, LoginActivity.class));
    }

    public void gotoActivityMessages(Context c) {
        c.startActivity(new Intent(c, MessagesActivity.class));
    }

    public void gotoActivityOffers(Context c) {
        c.startActivity(new Intent(c, OffersActivity.class));
    }

    public void gotoActivityScheduledBatches(Context c) {
        c.startActivity(new Intent(c, ScheduledBatchesActivity.class));
    }

    public void gotoActivitySplashScreen(Context c) {
        c.startActivity(new Intent(c, SplashScreenActivity.class));
    }

}
