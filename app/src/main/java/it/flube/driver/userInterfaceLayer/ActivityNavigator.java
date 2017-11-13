/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary.BatchItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary.OrderItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.earnings.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.help.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeNoActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.personalOffers.PersonalOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.publicOffers.PublicOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchMapActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInActivity;
import it.flube.driver.userInterfaceLayer.activities.messages.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;
import timber.log.Timber;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigator {
    private static final String TAG = "ActivityNavigator";

    public ActivityNavigator(){}

    public void gotoActivityHome(Context context){
        context.startActivity(new Intent(context, HomeNoActiveBatchActivity.class));
        Timber.tag(TAG).d("starting activity HomeNoActiveBatchActivity.class");
    }

    public void gotoBatchItinerary(Context context){
        context.startActivity(new Intent(context, BatchItineraryActivity.class));
        Timber.tag(TAG).d("starting activity BatchItineraryActivity.class");
    }

    public void gotoOrderItinerary(Context context){
        context.startActivity(new Intent(context, OrderItineraryActivity.class));
        Timber.tag(TAG).d("starting activity OrderItineraryActivity.class");
    }

    public void gotoActivityAccount(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
        Timber.tag(TAG).d("starting activity AccountActivity.class");
    }

    public void gotoActiveBatchStep(Context context){

        ActiveBatchInterface activeBatch = AndroidDevice.getInstance().getActiveBatch();
        if (activeBatch.hasActiveBatch()){
            OrderStepInterface.TaskType taskType = activeBatch.getTaskType();

            Timber.tag(TAG).d("gotoActiveBatchStep...");
            switch (taskType){
                case NAVIGATION:
                    Timber.tag(TAG).d("  ...starting NavigationActivity.class");
                    context.startActivity(new Intent(context, NavigationActivity.class));
                    break;
                case TAKE_PHOTOS:
                    Timber.tag(TAG).d("  ...starting PhotoActivity.class");
                    context.startActivity(new Intent(context, PhotoActivity.class));
                    break;
            }

        } else {
            Timber.tag(TAG).d("can't go to ActiveBatchStep, no active batch");
            context.startActivity(new Intent(context, HomeNoActiveBatchActivity.class));
        }

    }

    public void gotoActivityDemoOffers(Context context) {
        context.startActivity(new Intent(context, DemoOffersActivity.class));
        Timber.tag(TAG).d("starting activity DemoOffersActivity.class");
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

    public void gotoActivityPersonalOffers(Context context){
        context.startActivity(new Intent(context, PersonalOffersActivity.class));
        Timber.tag(TAG).d("starting activity PersonalOffersActivity.class");
    }

    public void gotoActivityPublicOffers(Context context) {
        context.startActivity(new Intent(context, PublicOffersActivity.class));
        Timber.tag(TAG).d("starting activity PublicOffersActivity.class");
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
