/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.ably.lib.types.ErrorInfo;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelAttachedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelAttachingEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelDetachedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelDetachingEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelFailedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelInitializedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState.ChannelSuspendedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionClosedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionClosingEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionConnectedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionConnectingEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionDisconnectedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionExceptionEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionFailedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionInitializedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyConnectionState.ConnectionSuspendedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedAssignedBatchesMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedBatchNotificationMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedBatchRemovalMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedClaimOfferResultMessage;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyConnectionCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyChannel;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyRealtimeSingleton;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedAssignedBatches;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchNotification;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchRemoval;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedClaimOfferResult;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedCurrentOffers;
import it.flube.driver.modelLayer.entities.Batch;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackAssignedBatches;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackBatchNotification;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackBatchRemoval;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackClaimOfferResult;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackCurrentOffers;
import it.flube.driver.modelLayer.interfaces.messaging.RemoteServerMessagingInterface;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class RemoteServerMessaging implements RemoteServerMessagingInterface, AblyConnectionCallback, AblyChannelCallback,
        RsmReceiveMsgCallbackCurrentOffers, RsmReceiveMsgCallbackClaimOfferResult, RsmReceiveMsgCallbackBatchRemoval,
        RsmReceiveMsgCallbackBatchNotification, RsmReceiveMsgCallbackAssignedBatches {
    private final String TAG = "RemoteServerMessaging";

    private AblyRealtimeSingleton mAblyRealtime;

    private AblyChannel mLookingForOffers;
    private AblyChannel mBatchActivity;
    private AblyChannel mClientId;


    public RemoteServerMessaging(String serverUrl, String clientId, String lookingForOffersChannelName, String batchActivityChannelName) {
        mAblyRealtime = AblyRealtimeSingleton.getInstance();

        //create connection
        Log.d(TAG, "create ably Connection START...");
        mAblyRealtime.establishConnection(clientId, serverUrl, this);
        Log.d(TAG, "...create ably Connection END");

        //create LookingForOffers channel
        mLookingForOffers = new AblyChannel(lookingForOffersChannelName, this);
        //subscribe for the messages we can receive from the server
        mLookingForOffers.subscribe("currentOffers", new ReceivedCurrentOffers(this));
        mLookingForOffers.subscribe("claimOfferResult", new ReceivedClaimOfferResult(this));

        //create BatchActivity channel
        mBatchActivity = new AblyChannel(batchActivityChannelName, this);

        //create clientId channel
        mClientId = new AblyChannel(clientId, this);
        //subscribe for the messages we can receive from the server
        mClientId.subscribe("assignedBatches", new ReceivedAssignedBatches(this));
        mClientId.subscribe("batchNotification", new ReceivedBatchNotification(this));
        mClientId.subscribe("batchRemoval", new ReceivedBatchRemoval(this));

    }

    //connect & disconnect
    public void connect() {
        mAblyRealtime.connect();
    }

    public void disconnect() {
        mAblyRealtime.disconnect();
    }

    // implement Remote Server Messaging Interface for SENDING MESSAGES
    // messages that can be sent
    public void sendMsgOnDuty(boolean dutyStatus) {

    }

    public void sendMsgRequestCurrentOffers() {
        Log.d(TAG, "Sending RequestCurrentOffers");
        mLookingForOffers.publish("requestCurrentOffers", "Gimme dem offers!");
    }

    public void sendMsgClaimOfferRequest(String offerOID) {
        Log.d(TAG, "Sending ClaimOfferRequest");
        mLookingForOffers.publish("claimOfferRequest", "Gimme dem offers!");
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

    //
    // implement Remote Server Messaging Interface for RECEIVING MESSAGES
    // received messages are posted on event bus so anyone that is interested can receive them
    //

    public void receiveMsgCurrentOffers(ArrayList<Offer> offerList) {
        //publish current offers on event bus
        EventBus.getDefault().post(new ReceivedCurrentOffersMessage(offerList));
    }

    public void receiveMsgClaimOfferResult(String offerOID, String clientId) {
        //publish claim offer result on event bus
        EventBus.getDefault().post(new ReceivedClaimOfferResultMessage());
    }

    public void receiveMsgBatchRemoval(String batchOid, String batchMessage) {
        //publish batch removal on event bus
        EventBus.getDefault().post(new ReceivedBatchRemovalMessage());
    }

    public void receiveMsgBatchNotification(String batchOid, String batchMessage) {
        //publish batch notification on event bus
        //broadcast offers on event bus
        EventBus.getDefault().post(new ReceivedBatchNotificationMessage());
    }

    public void receiveMsgAssignedBatches(ArrayList<Batch> batchList) {
        //broadcast offers on event bus
        EventBus.getDefault().post(new ReceivedAssignedBatchesMessage());
    }

    //
    ///connection callbacks
    // sends an event bus message so that anyone interested can be notified of connection changes
    //

    public void onConnectionCallbackException(Exception ex) {
        Log.d(TAG, "*** Ably Connection Exception -> " + ex.getMessage());
        EventBus.getDefault().post(new ConnectionExceptionEvent(ex));
    }

    public void onConnectionCallbackInitialized() {
        Log.d(TAG, "*** Ably Connection Initalized");
        EventBus.getDefault().post(new ConnectionInitializedEvent());
    }

    public void onConnectionCallbackConnecting() {
        Log.d(TAG, "*** Ably Connection Connecting...");
        EventBus.getDefault().post(new ConnectionConnectingEvent());
    }

    public void onConnectionCallbackConnected() {
        Log.d(TAG, "*** Ably Connection Connected");
        EventBus.getDefault().post(new ConnectionConnectedEvent());
    }

    public void onConnectionCallbackDisconnected() {
        Log.d(TAG, "*** Ably Connection Disconnected");
        EventBus.getDefault().post(new ConnectionDisconnectedEvent());
    }

    public void onConnectionCallbackSuspended() {
        Log.d(TAG, "*** Ably Connection Suspended");
        EventBus.getDefault().post(new ConnectionSuspendedEvent());
    }

    public void onConnectionCallbackClosing() {
        Log.d(TAG, "*** Ably Connection Closing...");
        EventBus.getDefault().post(new ConnectionClosingEvent());
    }

    public void onConnectionCallbackClosed() {
        Log.d(TAG, "*** Ably Connection Closed");
        EventBus.getDefault().post(new ConnectionClosedEvent());
    }

    public void onConnectionCallbackFailed() {
        Log.d(TAG, "*** Ably Connection Failed");
        EventBus.getDefault().post(new ConnectionFailedEvent());
    }

    // channel callbacks
    // sends an event bus message so anyone can be notified of channel events

    public void onChannelCallbackInitialized(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Initialized");
        EventBus.getDefault().post(new ChannelInitializedEvent(channelName));
    }

    public void onChannelCallbackAttaching(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Attaching...");
        EventBus.getDefault().post(new ChannelAttachingEvent(channelName));
    }

    public void onChannelCallbackAttached(String channelName, boolean resumed) {
        Log.d(TAG, "*** Channel " + channelName + " Attached");
        EventBus.getDefault().post(new ChannelAttachedEvent(channelName));
    }

    public void onChannelCallbackDetaching(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Detaching...");
        EventBus.getDefault().post(new ChannelDetachingEvent(channelName));
    }

    public void onChannelCallbackDetached(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Detached");
        EventBus.getDefault().post(new ChannelDetachedEvent(channelName));
    }

    public void onChannelCallbackSuspended(String channelName) {
        Log.d(TAG, "*** Channel " + channelName + " Suspended");
        EventBus.getDefault().post(new ChannelSuspendedEvent(channelName));
    }

    public void onChannelCallbackFailed(String channelName, ErrorInfo e) {
        Log.d(TAG, "*** Channel " + channelName + "Failed -> " + e.toString());
        EventBus.getDefault().post(new ChannelFailedEvent(channelName, e));
    }


}
