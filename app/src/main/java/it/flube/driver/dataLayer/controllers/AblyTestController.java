/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.ably.lib.types.ErrorInfo;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelAttachedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelAttachingEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelDetachedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelDetachingEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelFailedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelInitializedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents.ChannelSuspendedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionClosedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionClosingEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionConnectedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionConnectingEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionDisconnectedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionExceptionEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionFailedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionInitializedEvent;
import it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.connectionStateEvents.ConnectionSuspendedEvent;
import it.flube.driver.dataLayer.eventBus.messagingEvents.batchMessages.ReceivedAssignedBatchesMessage;
import it.flube.driver.dataLayer.eventBus.messagingEvents.batchMessages.ReceivedBatchNotificationMessage;
import it.flube.driver.dataLayer.eventBus.messagingEvents.batchMessages.ReceivedBatchRemovalMessage;
import it.flube.driver.dataLayer.eventBus.messagingEvents.driverMessages.ReceivedClaimOfferResultMessage;
import it.flube.driver.dataLayer.eventBus.messagingEvents.driverMessages.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.eventBus.activityUIevents.ablyTestActivity.ConnectionWasUpdatedEvent;
import it.flube.driver.dataLayer.eventBus.activityUIevents.ablyTestActivity.MessageWasUpdatedEvent;
import it.flube.driver.dataLayer.messaging.RemoteServerMessaging;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyConnectionCallback;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class AblyTestController {
    private final String TAG = "AblyTestController";

    private RemoteServerMessaging mMessaging;
    private String testOfferOID = "testOfferOID";
    private String testBatchOID = "testBatchOID";
    private double testLat = 30.545750;
    private double testLong = -97.758355;



    //constructor
    public AblyTestController() {
        String serverUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
        String clientId = DriverSingleton.getInstance().getClientId();
        String lookingForOffers = "LookingForOffers";
        String batchActivity = "BatchActivity";

        mMessaging = new RemoteServerMessaging(serverUrl, clientId, lookingForOffers, batchActivity);
        mMessaging.createActiveBatchChannel(testBatchOID);
        Log.d(TAG, "AblyTestController CREATED");
    }

    //the activity will subscribe and unsubscribe this controller to the event bus based
    //on activity lifecycle

    public void subscribeEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unsubscribeEventBus() {
        EventBus.getDefault().unregister(this);
    }

    // commands to connect & disconnect from the remote server messaging connection

    public void connect() {
        Log.d(TAG, "*** connect command");
        mMessaging.connect();
    }

    public void disconnect() {
        Log.d(TAG, "*** disconnect command");
        mMessaging.disconnect();
    }

    //handlers for the driver messages drop-down list
    public void processDriverMessage(String message) {
        switch (message) {
            case "OnDuty TRUE":
                mMessaging.sendMsgOnDuty(true);
                break;
            case "OnDuty FALSE":
               mMessaging.sendMsgOnDuty(false);
                break;
            case "Request Current Offers":
                mMessaging.sendMsgRequestCurrentOffers();
                break;
            case "Claim Offer Request":
                mMessaging.sendMsgClaimOfferRequest(testOfferOID);
                break;
        }
    }

    //handlers for the batch message drop-down list
    public void processBatchMessage(String message) {
        switch (message) {
            case "Request Assigned Batches":
                mMessaging.sendMsgRequestAssignedBatches();
                break;
            case "Forfeit Batch":
                mMessaging.sendMsgForfeitBatch(testBatchOID);
                break;
            case "Batch START":
                mMessaging.sendMsgBatchStart(testBatchOID);
                break;
            case "Arrived To Pickup":
                mMessaging.sendMsgArrivedToPickup(testBatchOID);
                break;
            case "Driver Takes Vehicle from Customer":
                mMessaging.sendMsgDriverTakesVehicleFromCustomer(testBatchOID);
                break;
            case "Arrived To Service":
                mMessaging.sendMsgArrivedToService(testBatchOID);
                break;
            case "Service Takes Vehicle from Driver":
                mMessaging.sendMsgServiceTakesVehicleFromDriver(testBatchOID);
                break;
            case "Service START":
                mMessaging.sendMsgServiceStart(testBatchOID);
                break;
            case "Service COMPLETE":
                mMessaging.sendMsgServiceComplete(testBatchOID);
                break;
            case "Driver Takes Vehicle from Service":
                mMessaging.sendMsgDriverTakesVehicleFromService(testBatchOID);
                break;
            case "Arrived to Dropoff":
                mMessaging.sendMsgArrivedToDropOff(testBatchOID);
                break;
            case "Owner Takes Vehicle from Driver":
                mMessaging.sendMsgOwnerTakesVehicleFromDriver(testBatchOID);
                break;
            case "Location Update":
                mMessaging.sendMsgLocationUpdate(testLat, testLong);
                break;

        }
    }

    // ****
    //update activity UI when messages are received
    // ****

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedCurrentOffersMessage event) {
        Log.d(TAG, "*** Received Current Offers Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Current Offers Message -> " + event.getCurrentOfferList().size() + " offers!"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedClaimOfferResultMessage event) {
        Log.d(TAG, "*** Received Claim Offer Result Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Claim Offer Result Message"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedAssignedBatchesMessage event) {
        Log.d(TAG, "*** Received Assigned Batches Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Assigned Batches Message"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedBatchNotificationMessage event) {
        Log.d(TAG, "*** Received Batch Notification Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Batch Notification Message"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedBatchRemovalMessage event) {
        Log.d(TAG, "*** Received Batch Removal Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Batch Removal Message"));
    }

    // ***
    // Update Activity UI when event bus channel events are received
    // ***

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelAttachingEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received ATTACHING event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " ATTACHING..."));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelAttachedEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received ATTACHED event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " ATTACHED"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelDetachedEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received DETACHED event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " DETACHED"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelDetachingEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received DETACHING event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " DETACHING..."));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelInitializedEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received Initialized event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " INITIALIZED"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelSuspendedEvent event) {
        String channelName = event.getChannelName();
        Log.d(TAG, "*** received Suspended event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " SUSPENDED"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChannelFailedEvent event) {
        String channelName = event.getChannelName();
        String errorMessage = event.getErrorInfo().message;

        Log.d(TAG, "*** received Failed event from channel " + channelName);
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " FAILED --> " + errorMessage));
    }

    // ***
    // Update Activity UI when event bus connection events are received
    // ***
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionClosedEvent event) {
        Log.d(TAG, "*** received Connection CLOSED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Closed"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionClosingEvent event) {
        Log.d(TAG, "*** received Connection CLOSING event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Closing..."));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionConnectedEvent event) {
        Log.d(TAG, "*** received Connection CONNECTED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Connected!!!"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionConnectingEvent event) {
        Log.d(TAG, "*** received Connection CONNECTING event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Connecting..."));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionDisconnectedEvent event) {
        Log.d(TAG, "*** received Connection DISCONNECTED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Disconnected"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionExceptionEvent event) {
        Log.d(TAG, "*** received Connection EXCEPTION event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Exception -> " + event.getException().getMessage()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionFailedEvent event) {
        Log.d(TAG, "*** received Connection FAILED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Failed"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionInitializedEvent event) {
        Log.d(TAG, "*** received Connection INITIALIZED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Initialized"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionSuspendedEvent event) {
        Log.d(TAG, "*** received Connection SUSPENDED event");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Suspended"));
    }


}
