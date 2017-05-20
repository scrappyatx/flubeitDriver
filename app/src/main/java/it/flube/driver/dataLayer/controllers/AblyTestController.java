/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.ably.lib.types.ErrorInfo;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedAssignedBatchesMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedBatchNotificationMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedBatchRemovalMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedClaimOfferResultMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.ablyTestActivity.ConnectionWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.ablyTestActivity.MessageWasUpdatedEvent;
import it.flube.driver.dataLayer.messaging.RemoteServerMessaging;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyConnectionCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyChannel;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyRealtimeSingleton;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedAssignedBatches;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchNotification;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchRemoval;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedClaimOfferResult;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedCurrentOffers;
import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class AblyTestController implements AblyConnectionCallback, AblyChannelCallback {
    private final String TAG = "AblyTestController";

    private RemoteServerMessaging mMessaging;


    //constructor
    public AblyTestController() {
        String serverUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
        String clientId = DriverSingleton.getInstance().getClientId();
        String lookingForOffers = "LookingForOffers";
        String batchActivity = "BatchActivity";


        mMessaging = new RemoteServerMessaging(serverUrl, clientId, lookingForOffers, batchActivity);

        Log.d(TAG, "AblyTestController CREATED");
    }

    //methods that activities can call
    public void subscribeEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unsubscribeEventBus() {
        EventBus.getDefault().unregister(this);
    }


    public void connect() {
        Log.d(TAG, "*** connect command");
        mMessaging.connect();
    }

    public void disconnect() {
        Log.d(TAG, "*** disconnect command");
        mMessaging.disconnect();
    }

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
                sendClaimOfferRequest();
                break;
        }
    }

    public void processBatchMessage(String message) {
        switch (message) {
            case "Request Assigned Batches":
                sendRequestAssignedBatches();
                break;
            case "Forfeit Batch":
                sendForfeitBatch();
                break;
            case "Batch START":
                sendBatchStart();
                break;
            case "Arrived To Pickup":
                sendArrivedToPickup();
                break;
            case "Driver Takes Vehicle from Customer":
                sendDriverTakesVehicleFromCustomer();
                break;
            case "Arrived To Service":
                sendArrivedToService();
                break;
            case "Service Takes Vehicle":
                sendServiceTakesVehicle();
                break;
            case "Service START":
                sendServiceStart();
                break;
            case "Service COMPLETE":
                sendServiceComplete();
                break;
            case "Driver Takes Vehicle from Service":
                sendDriverTakesVehicleFromService();
                break;
            case "Owner Takes Vehicle":
                sendOwnerTakesVehicle();
                break;
            case "Location Update":
                sendLocationUpdate();
                break;

        }
    }


    // private message commands


    private void sendClaimOfferRequest() {
        Log.d(TAG, "Sending ClaimOfferRequest");
    }

    private void sendRequestAssignedBatches() {
        Log.d(TAG, "Sending RequestAssignedBatches") ;
    }

    private void sendForfeitBatch() {
        Log.d(TAG, "Sending ForfeitBatch");
    }

    private void sendBatchStart() {
        Log.d(TAG, "Sending BatchStart");
    }

    private void sendArrivedToPickup() {
        Log.d(TAG, "Sending ArrivedToPickup");
    }
    private void sendDriverTakesVehicleFromCustomer() {
        Log.d(TAG, "Sending DriverTakesVehicleFromCustomer");
    }
    private void sendArrivedToService() {
        Log.d(TAG, "Sending ArrivedToService");
    }

    private void sendServiceTakesVehicle() {
        Log.d(TAG, "Sending ServiceTakesVehicle");
    }
    private void sendServiceStart() {
        Log.d(TAG, "Sending ServiceStart");
    }
    private void sendServiceComplete() {
        Log.d(TAG, "Sending ServiceComplete");
    }
    private void sendDriverTakesVehicleFromService() {
        Log.d(TAG, "Sending DriverTakesVehicleFromService");
    }
    private void sendOwnerTakesVehicle() {
        Log.d(TAG, "Sending OwnerTakesVehicle");
    }
    private void sendLocationUpdate() {
        Log.d(TAG, "Sending LocationUpdate");
    }


    //event bus -> messages received
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedCurrentOffersMessage event) {
        Log.d(TAG, "*** Received Current Offers Message");
        //now do something with current offers list
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Received Current Offers Message"));
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

    //event bus connection status
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent()



    //connection callbacks

    public void onConnectionCallbackException(Exception ex) {
        Log.d(TAG, "*** Connection Exception -> " + ex.getMessage());
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Exception -> " + ex.getMessage()));
    }

    public void onConnectionCallbackInitialized() {
        Log.d(TAG, "*** Connection Initalized");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Initialized"));
    }

    public void onConnectionCallbackConnecting() {
        Log.d(TAG, "*** Connection Connecting...");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Connecting..."));
    }

    public void onConnectionCallbackConnected() {
        Log.d(TAG, "*** Connection Connected");

        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Connected!!!"));
    }

    public void onConnectionCallbackDisconnected() {
        Log.d(TAG, "*** Connection Disconnected");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Disconnected"));
    }

    public void onConnectionCallbackSuspended() {
        Log.d(TAG, "*** Connection Suspended");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Suspended"));
    }

    public void onConnectionCallbackClosing() {
        Log.d(TAG, "*** Connection Closing...");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Closing..."));
    }

    public void onConnectionCallbackClosed() {
        Log.d(TAG, "*** Connection Closed");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Closed"));
    }

    public void onConnectionCallbackFailed() {
        Log.d(TAG, "*** Connection Failed");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Connection Failed"));
    }


    // channel callbacks
    public void onChannelCallbackInitialized(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Initialized");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Initialized"));
    }

    public void onChannelCallbackAttaching(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Attaching...");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Attaching..."));
    }

    public void onChannelCallbackAttached(String channelName, boolean resumed) {
        Log.d(TAG, "*** Channel " + channelName + " Attached");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Attached"));
    }

    public void onChannelCallbackDetaching(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Detaching...");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Detaching..."));
    }

    public void onChannelCallbackDetached(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Detached");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Detached"));
    }

    public void onChannelCallbackSuspended(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Suspended");
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + " Suspended"));
    }

    public void onChannelCallbackFailed(String channelName, ErrorInfo e) {
        Log.d(TAG, "*** Channel " + channelName + "Failed -> " + e.toString());
        EventBus.getDefault().post(new MessageWasUpdatedEvent("*** Channel " + channelName + "Failed -> " + e.toString()));
    }

}
