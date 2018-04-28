/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary.BatchItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary.OrderItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.driver.userInterfaceLayer.activities.earnings.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.help.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.personalOffers.PersonalOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.publicOffers.PublicOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchMapActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInActivity;
import it.flube.driver.userInterfaceLayer.activities.messages.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInAuthUiLaunchActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_TIMEOUT_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.BATCH_DEINED_REASON_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_TIMEOUT_VALUE;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigator {
    private static final String TAG = "ActivityNavigator";

    public ActivityNavigator(){}

    public void gotoActivityHome(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
        Timber.tag(TAG).d("starting activity HomeActivity.class");
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
        new ActiveBatchNavigator().gotoActiveBatchStep(context);
        Timber.tag(TAG).d("going to active batch step");
    }

    ///
    ///  DEMO OFFERS
    ///
    public void gotoActivityDemoOffers(Context context) {
        DemoOffersNavigator.gotoActivityDemoOffers(context);
    }

    public void gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(Context context){
        DemoOffersNavigator.gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(context);
    }

    public void gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(Context context){
        DemoOffersNavigator.gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(context);
    }

    public void gotoActivityDemoOffersAndShowOfferClaimedTimeoutAlert(Context context){
        DemoOffersNavigator.gotoActivityDemoOffersAndShowOfferClaimedTimeoutAlert(context);
    }

    public void gotoActivityDemoOffersAndShowDemoOfferMakeSuccess(Context context){
       DemoOffersNavigator.gotoActivityDemoOffersAndShowDemoOfferMakeSuccess(context);
    }

    public void gotoActivityDemoOffersAndShowDemoOfferMakeFailure(Context context){
        DemoOffersNavigator.gotoActivityDemoOffersAndShowDemoOfferMakeFailure(context);
    }

    //// DEMO OFFERS MAKE
    public void gotoActivityDemoOffersMake(Context context){
        DemoOffersMakeNavigator.gotoActivityDemoOffersMake(context);
    }

    //// PERSONAL OFFERS

    public void gotoActivityPersonalOffers(Context context){
        PersonalOffersNavigator.gotoActivityPersonalOffers(context);
    }

    public  void gotoActivityPersonalOffersAndShowOfferClaimedSuccessAlert(Context context){
        PersonalOffersNavigator.gotoActivityPersonalOffersAndShowOfferClaimedSuccessAlert(context);
    }

    public void gotoActivityPersonalOffersAndShowOfferClaimedFailureAlert(Context context){
        PersonalOffersNavigator.gotoActivityPersonalOffersAndShowOfferClaimedFailureAlert(context);
    }

    public void gotoActivityPersonalOffersAndShowOfferClaimedTimeoutAlert(Context context){
        PersonalOffersNavigator.gotoActivityPersonalOffersAndShowOfferClaimedTimeoutAlert(context);
    }

    //// PUBLIC OFFERS

    public void gotoActivityPublicOffers(Context context) {
        PublicOffersNavigator.gotoActivityPublicOffers(context);
    }

    public void gotoActivityPublicOffersAndShowOfferClaimedSuccessAlert(Context context){
        PublicOffersNavigator.gotoActivityPublicOffersAndShowOfferClaimedSuccessAlert(context);
    }

    public void gotoActivityPublicOffersAndShowOfferClaimedFailureAlert(Context context){
        PublicOffersNavigator.gotoActivityPublicOffersAndShowOfferClaimedFailureAlert(context);
    }

    public void gotoActivityPublicOffersAndShowOfferClaimedTimeoutAlert(Context context){
        PublicOffersNavigator.gotoActivityPublicOffersAndShowOfferClaimedTimeoutAlert(context);
    }

    /////

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



    //// OFFER CLAIM

    public void gotoActivityOfferClaim(Context context, OfferConstants.OfferType offerType, String batchGuid) {
        OfferClaimNavigator.gotoActivityOfferClaim(context, offerType, batchGuid);
        Timber.tag(TAG).d("starting activity OfferClaimActivity.class");
    }


    ///
    /// SCHEDULED BATCHES
    ///
    public void gotoActivityScheduledBatches(Context context) {
        ScheduledBatchNavigator.gotoActivityScheduledBatches(context);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class");
    }

    public void gotoActivityScheduledBatchesAndShowBatchForfeitSuccessAlert(Context context){
        ScheduledBatchNavigator.gotoActivityScheduledBatchesAndShowBatchForfeitSuccessAlert(context);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit success");
    }

    public void gotoActivityScheduledBatchesAndShowBatchForfeitFailureAlert(Context context){
        ScheduledBatchNavigator.gotoActivityScheduledBatchesAndShowBatchForfeitFailureAlert(context);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit failure");
    }

    public void gotoActivityScheduledBatchesAndShowBatchForfeitTimeoutAlert(Context context){
        ScheduledBatchNavigator.gotoActivityScheduledBatchesAndShowBatchForfeitTimeoutAlert(context);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit timeout");
    }

    public void gotoActivityScheduledBatchesAndShowBatchForfeitDeniedAlert(Context context, String reason){
        ScheduledBatchNavigator.gotoActivityScheduledBatchesAndShowBatchForfeitDeniedAlert(context, reason);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit denied");
    }

    public void gotoActivityBatchManage(Context context, String batchGuid) {
        ScheduledBatchNavigator.gotoActivityBatchManage(context, batchGuid);
        Timber.tag(TAG).d("starting activity BatchManageActivity.class");
    }

    public void gotoActivityBatchMap(Context context) {
        ScheduledBatchNavigator.gotoActivityBatchMap(context);
        Timber.tag(TAG).d("starting activity BatchMapActivity.class");
    }

    ///
    /// SPLASH START UP SCREEN
    ///
    public void gotoActivitySplashScreen(Context context) {
        context.startActivity(new Intent(context, SplashScreenActivity.class));
        Timber.tag(TAG).d("starting activity SplashScreenActivity.class");
    }

    public void gotoActivityAuthUiSignIn(Context context) {
        context.startActivity(new Intent(context, SignInAuthUiLaunchActivity.class));
        Timber.tag(TAG).d("starting activity SignInAuthUiLaunchActivity.class");
        //new FirebaseAuthUiSignIn().signIn(context);
        //Timber.tag(TAG).d("starting activity AuthUI signin");
    }

    //// PHOTO STEP NAVIGATION
    public void gotoActivityPhotoDetail(Context context, String photoRequestGuid){
        PhotoStepNavigator.gotoActivityPhotoDetail(context, photoRequestGuid);
        Timber.tag(TAG).d("starting activity PhotoDetail, photoRequestGuid -> " + photoRequestGuid);
    }

}
