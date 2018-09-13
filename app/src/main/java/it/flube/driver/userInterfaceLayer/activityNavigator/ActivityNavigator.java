/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary.BatchItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary.OrderItineraryActivity;
import it.flube.driver.userInterfaceLayer.activities.earnings.productionEarnings.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.help.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInActivity;
import it.flube.driver.userInterfaceLayer.activities.messages.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.SignInAuthUiLaunchActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.drawerMenu.DriverManager;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public class ActivityNavigator {
    private static final String TAG = "ActivityNavigator";

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile ActivityNavigator instance = new ActivityNavigator();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private ActivityNavigator() {
        objectGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("created (%s)", objectGuid);
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static ActivityNavigator getInstance() {
        return ActivityNavigator.Loader.instance;
    }

    private String objectGuid;

    ////
    //// HOME
    ////
    public void gotoActivityHome(Context context){
        new HomeNavigator().gotoActivityHome(context);
        Timber.tag(TAG).d("starting activity HomeActivity.class");
    }

    public void gotoActivityHomeAndShowBatchFinishedMessage(Context context, ActiveBatchManageInterface.ActorType actorType, String batchGuid) {
        new HomeNavigator().gotoActivityHomeAndShowBatchFinishedMessage(context, actorType, batchGuid);
        Timber.tag(TAG).d("starting activity HomeActivity.class");
    }

    public void gotoActivityHomeAndShowBatchRemovedMessage(Context context,  ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        new HomeNavigator().gotoActivityHomeAndShowBatchRemovedMessage(context, actorType, batchGuid);
        Timber.tag(TAG).d("starting activity HomeActivity.class");
    }


    //// BATCH ITINERARY

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

    ///
    /// ACTIVE BATCH
    ///
    public void gotoActiveBatchStep(Context context){
        new ActiveBatchNavigator().gotoActiveBatchStep(context);
        Timber.tag(TAG).d("going to active batch step");
    }

    public void gotoActiveBatchStep(Context context, ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                                    Boolean batchStarted, Boolean orderStarted, String batchGuid, String serviceOrderGuid, String stepGuid, OrderStepInterface.TaskType taskType) {

        new ActiveBatchNavigator().gotoActiveBatchStep(context, actorType, actionType, batchStarted, orderStarted, batchGuid, serviceOrderGuid,stepGuid,taskType);
        Timber.tag(TAG).d("going to active batch step");
    }

    ///
    /// WAITING TO FINISH BATCH
    ///
    public void gotoWaitingToFinishBatch(Context context, ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        new WaitingToFinishBatchNavigator().gotoWaitingToFinishBatch(context, actorType, batchGuid);
        Timber.tag(TAG).d("going to WaitingToFinishBatch");
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

    ///// TEST EARNINGS
    public void gotoActivityTestEarnings(Context context){
        DevTestEarningsNavigator.gotoActivityTestEarnings(context);
        Timber.tag(TAG).d("starting TestEarningsActivity.class");
    }

    //// TEST OFFERS MAKE
    public void gotoActivityTestOffersMake(Context context){
        DevTestOffersMakeNavigator.gotoActivityTestOffersMake(context);
        Timber.tag(TAG).d("starting TestOffersMakeActivity.class");
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

    public void gotoActivityPhotoList(Context context, String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType){
        PhotoStepNavigator.gotoActivityPhotoList(context, batchGuid, serviceOrderGuid, orderStepGuid, taskType);
        Timber.tag(TAG).d("starting activity PhotoList");
    }

    public void gotoActivityPhotoDetail(Context context, String batchGuid, String orderStepGuid, String photoRequestGuid){
        PhotoStepNavigator.gotoActivityPhotoDetail(context, batchGuid, orderStepGuid, photoRequestGuid);
        Timber.tag(TAG).d("starting activity PhotoDetail, photoRequestGuid -> " + photoRequestGuid);
    }

    public void gotoActivityPhotoTake(Context context, String batchGuid, String orderStepGuid, String photoRequestGuid){
        PhotoStepNavigator.gotoActivityPhotoTake(context, batchGuid, orderStepGuid, photoRequestGuid);
        Timber.tag(TAG).d("starting activity PhotoTake, photoRequestGuid -> " + photoRequestGuid);
    }

    //// RECEIVE ASSET STEP NAVIGATION
    public void gotoActivityReceiveAssetGetSignature(Context context){
        ReceiveAssetStepNavigator.gotoActivityReceiveAssetGetSignature(context);
        Timber.tag(TAG).d("starting activity ReceiveAssetGetSignature");
    }

    public void gotoActivityReceiveAssetItem(Context context, String assetGuid){
        ReceiveAssetStepNavigator.gotoActivityReceiveAssetItem(context, assetGuid);
        Timber.tag(TAG).d("starting activity ReceiveAssetItem");
    }

    public void gotoActivityReceiveAssetItemList(Context context){
        ReceiveAssetStepNavigator.gotoActivityReceiveAssetItemList(context);
        Timber.tag(TAG).d("starting activity ReceiveAssetItemList");
    }

    /// GIVE ASSET STEP NAVIGATION
    public void gotoActivityGiveAssetGetSignature(Context context){
        GiveAssetStepNavigator.gotoActivityGiveAssetGetSignature(context);
        Timber.tag(TAG).d("starting activity GiveAssetGetSignature");
    }

    public void gotoActivityGiveAssetItem(Context context, String assetGuid){
        GiveAssetStepNavigator.gotoActivityGiveAssetItem(context, assetGuid);
        Timber.tag(TAG).d("startign activity GiveAssetItem");
    }

    public void gotoActivityGiveAssetItemList(Context context){
        GiveAssetStepNavigator.gotoActivityGiveAssetItemList(context);
        Timber.tag(TAG).d("starting activity GiveAssetItemList");
    }

    /// AUTHORIZE PAYMENT NAVIGATION
    public void gotoActivityReceiptDetail(Context context){
        AuthorizePaymentStepNavigator.gotoActivityReceiptDetail(context);
        Timber.tag(TAG).d("starting activity ReceiptDetailActivity");
    }

    public void gotoActivityReceiptTakePhoto(Context context){
        AuthorizePaymentStepNavigator.gotoActivityReceiptPhotoTakeActivity(context);
        Timber.tag(TAG).d("starting activity ReceiptTakeActivity");
    }

}
