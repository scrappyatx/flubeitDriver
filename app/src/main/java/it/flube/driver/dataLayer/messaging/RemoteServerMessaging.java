/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.types.ErrorInfo;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.ablyTestActivity.ConnectionWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.ablyTestActivity.MessageWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.messaging.AblyChannelCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyConnectionCallback;
import it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.batchMessages.ReceivedAssignedBatches;
import it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.batchMessages.ReceivedBatchNotification;
import it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.batchMessages.ReceivedBatchRemoval;
import it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.driverMessages.ReceivedClaimOfferResult;
import it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.driverMessages.ReceivedCurrentOffers;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.messaging.RsmReceiveMessageCallback;
import it.flube.driver.modelLayer.interfaces.messaging.RemoteServerMessagingInterface;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class RemoteServerMessaging implements RemoteServerMessagingInterface, AblyConnectionCallback, AblyChannelCallback {
    private final String TAG = "RemoteServerMessaging";

    private AblyRealtimeSingleton mAblyRealtime;
    private RsmReceiveMessageCallback mReceivedMessageCallback;

    private AblyChannel mLookingForOffers;
    private AblyChannel mBatchActivity;

    public RemoteServerMessaging(String serverUrl, RsmReceiveMessageCallback callback) {
        mAblyRealtime = AblyRealtimeSingleton.getInstance();
        mReceivedMessageCallback = callback;

        //create connection
        Log.d(TAG, "create ably Connection START...");
        String clientId = DriverSingleton.getInstance().getClientId();
        mAblyRealtime.establishConnection(clientId, serverUrl, this);
        Log.d(TAG, "...create ably Connection END");

        //create channels
        mLookingForOffers = new AblyChannel("LookingForOffers", this);

        //subscribe for the messages we can receive from the server
        mLookingForOffers.subscribe("currentOffers", new ReceivedCurrentOffers());
        mLookingForOffers.subscribe("claimOfferResult", new ReceivedClaimOfferResult());


        mBatchActivity = new AblyChannel("BatchActivity", this);

        //subscribe for the messages we can receive from the server
        mBatchActivity.subscribe("assignedBatches", new ReceivedAssignedBatches());
        mBatchActivity.subscribe("batchNotification", new ReceivedBatchNotification());
        mBatchActivity.subscribe("batchRemoval", new ReceivedBatchRemoval());


    }

    //connect & disconnect
    public void connect() {
        mAblyRealtime.connect();
    }

    public void disconnect() {
        mAblyRealtime.disconnect();
    }

    // messages that can be sent

    public void sendMsgOnDuty(boolean dutyStatus) {

    }

    public void sendMsgRequestCurrentOffers() {

    }

    public void sendMsgClaimOfferRequest(String offerOID) {

    }

    public void sendMsgRequestAssignedBatches() {

    }

    public void sendMsgForfeitBatch(String batchOID) {

    }

    public void sendMsgBatchStart(String batchOID) {

    }

    public void sendMsgLocationUpdate() {

    }

    public void sendMsgArrivedToPickup() {

    }

    public void sendMsgDriverTakesVehicle() {

    }

    public void sendMsgArrivedToService() {

    }

    public void sendMsgServiceTakesVehicle() {

    }

    public void sendMsgServiceStart() {

    }

    public void sendMsgServiceComplete() {

    }

    public void sendMsgDriverTakesCarFromService() {

    }

    public void sendMsgArrivedToDropOff() {

    }

    public void sendMsgOwnerTakesVehicle() {

    }

    ///connection callbacks
    // sends an event bus message so that anyone interested can be notified of connection changes
    public void onConnectionCallbackException(Exception ex) {
        Log.d(TAG, "*** Ably Connection Exception -> " + ex.getMessage());
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Exception -> " + ex.getMessage()));
    }

    public void onConnectionCallbackInitialized() {
        Log.d(TAG, "*** Ably Connection Initalized");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Initialized"));
    }

    public void onConnectionCallbackConnecting() {
        Log.d(TAG, "*** Ably Connection Connecting...");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Connecting..."));
    }

    public void onConnectionCallbackConnected() {
        Log.d(TAG, "*** Ably Connection Connected");

        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Connected!!!"));
    }

    public void onConnectionCallbackDisconnected() {
        Log.d(TAG, "*** Ably Connection Disconnected");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Disconnected"));
    }

    public void onConnectionCallbackSuspended() {
        Log.d(TAG, "*** Ably Connection Suspended");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Suspended"));
    }

    public void onConnectionCallbackClosing() {
        Log.d(TAG, "*** Ably Connection Closing...");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Closing..."));
    }

    public void onConnectionCallbackClosed() {
        Log.d(TAG, "*** Ably Connection Closed");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Closed"));
    }

    public void onConnectionCallbackFailed() {
        Log.d(TAG, "*** Ably Connection Failed");
        EventBus.getDefault().post(new ConnectionWasUpdatedEvent("*** Ably Connection Failed"));
    }

    // channel callbacks
    // sends an event bus message so anyone can be notified of channel events

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
