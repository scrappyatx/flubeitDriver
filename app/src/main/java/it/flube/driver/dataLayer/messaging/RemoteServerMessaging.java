/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;
import org.greenrobot.eventbus.EventBus;
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
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyConnectionCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyChannel;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities.AblyRealtimeSingleton;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ArriveToPickupMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ArrivedToDropOffMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ArrivedToServiceMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.BatchStartMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.DriverTakesVehicleFromCustomerMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.DriverTakesVehicleFromService;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ForfeitBatchMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.OwnerTakesVehicleMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.RequestAssignedBatchesMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ServiceCompleteMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ServiceStartMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages.ServiceTakesVehicleMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.clientIdMessages.LocationMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedAssignedBatches;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchNotification;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages.ReceivedBatchRemoval;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedClaimOfferResult;
import it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages.ReceivedCurrentOffers;
import it.flube.driver.dataLayer.messaging.messageBuilders.driverMessages.ClaimOfferRequestMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.driverMessages.RequestCurrentOffersMessageBuilder;
import it.flube.driver.dataLayer.messaging.messageBuilders.driverMessages.SendOnDutyMessageBuilder;
import it.flube.driver.modelLayer.entities.Batch;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackAssignedBatches;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackBatchNotification;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackBatchRemoval;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackClaimOfferResult;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackCurrentOffers;
import it.flube.driver.modelLayer.interfaces.messaging.RemoteServerMessagingInterface;

import static java.util.Objects.isNull;

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
    private AblyChannel mActiveBatch;


    public RemoteServerMessaging(String serverUrl, String clientId, String lookingForOffersChannelName, String batchActivityChannelName) {
        mAblyRealtime = AblyRealtimeSingleton.getInstance();

        //create connection
        Log.d(TAG, "create ably Connection START...");
        mAblyRealtime.establishConnection(clientId, serverUrl, this);
        Log.d(TAG, "...create ably Connection END");

        //create LookingForOffers channel
        mLookingForOffers = mAblyRealtime.createChannel(lookingForOffersChannelName, this);
        //subscribe for the messages we can receive from the server
        mLookingForOffers.subscribe("currentOffers", new ReceivedCurrentOffers(this));
        mLookingForOffers.subscribe("claimOfferResult", new ReceivedClaimOfferResult(this));

        //create BatchActivity channel
        mBatchActivity = mAblyRealtime.createChannel(batchActivityChannelName, this);

        //create clientId channel
        mClientId = mAblyRealtime.createChannel(clientId, this);
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

    //attach to ActiveBatch channel
    public void createActiveBatchChannel(String name) {
        mActiveBatch = mAblyRealtime.createChannel(name, this);
    }

    public void releaseActiveBatchChannel(String name) {
        mAblyRealtime.releaseChannel(name);
    }


    // implement Remote Server Messaging Interface for SENDING MESSAGES
    // messages that can be sent
    public void sendMsgOnDuty(boolean dutyStatus) {
        Log.d(TAG, "Sending onDuty");
        SendOnDutyMessageBuilder mb = new SendOnDutyMessageBuilder(dutyStatus);
        mLookingForOffers.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgRequestCurrentOffers() {
        Log.d(TAG, "Sending RequestCurrentOffers");
        RequestCurrentOffersMessageBuilder mb = new RequestCurrentOffersMessageBuilder();
        mLookingForOffers.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgClaimOfferRequest(String offerOID) {
        Log.d(TAG, "Sending ClaimOfferRequest");
        ClaimOfferRequestMessageBuilder mb  = new ClaimOfferRequestMessageBuilder(offerOID);
        mLookingForOffers.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgRequestAssignedBatches() {
        Log.d(TAG, "Sending RequestAssignedBatches");
        RequestAssignedBatchesMessageBuilder mb = new RequestAssignedBatchesMessageBuilder();
        mBatchActivity.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgForfeitBatch(String batchOID) {
        Log.d(TAG, "Sending ForfeitBatch");
        ForfeitBatchMessageBuilder mb = new ForfeitBatchMessageBuilder(batchOID);
        mBatchActivity.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgBatchStart(String batchOID) {
        Log.d(TAG, "Sending Batch Start");
        BatchStartMessageBuilder mb = new BatchStartMessageBuilder(batchOID);
        mBatchActivity.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgLocationUpdate(double latitude, double longitude) {
        Log.d(TAG, "Sending Location");
        LocationMessageBuilder mb = new LocationMessageBuilder(latitude, longitude);
        mBatchActivity.publish(mb.getMessageName(), mb.getMessageBody());
    }

    public void sendMsgArrivedToPickup(String batchOID) {
        Log.d(TAG, "Sending ArrivedToPickup");
        ArriveToPickupMessageBuilder mb = new ArriveToPickupMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ArrivedToPickup.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgDriverTakesVehicleFromCustomer(String batchOID) {
        Log.d(TAG, "Sending DriverTakesVehiclelFromCustomer");
        DriverTakesVehicleFromCustomerMessageBuilder mb = new DriverTakesVehicleFromCustomerMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send driverTakesVehicle.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgArrivedToService(String batchOID) {
        Log.d(TAG, "Sending ArrivedToService");
        ArrivedToServiceMessageBuilder mb = new ArrivedToServiceMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ArrivedToService.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgServiceTakesVehicleFromDriver(String batchOID) {
        Log.d(TAG, "Sending ServiceTakesVehicleFromDriver");
        ServiceTakesVehicleMessageBuilder mb = new ServiceTakesVehicleMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ServiceTakesVehicle.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgServiceStart(String batchOID) {
        Log.d(TAG, "Sending ServiceStart");
        ServiceStartMessageBuilder mb = new ServiceStartMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ServiceStart.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgServiceComplete(String batchOID) {
        Log.d(TAG, "Sending ServiceComplete");
        ServiceCompleteMessageBuilder mb = new ServiceCompleteMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ServiceComplete.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgDriverTakesVehicleFromService(String batchOID) {
        Log.d(TAG, "Sending DriverTakesVehicleFromService");
        DriverTakesVehicleFromService mb = new DriverTakesVehicleFromService(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send DriverTakesCarFromService.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgArrivedToDropOff(String batchOID) {
        Log.d(TAG, "Sending ArrivedToDropOff");
        ArrivedToDropOffMessageBuilder mb = new ArrivedToDropOffMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send ArrivedToDropOff.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
    }

    public void sendMsgOwnerTakesVehicleFromDriver(String batchOID) {
        Log.d(TAG, "Sending OwnerTakesVehicleFromDriver");
        OwnerTakesVehicleMessageBuilder mb = new OwnerTakesVehicleMessageBuilder(batchOID);
        if (mActiveBatch.getName().equals(batchOID)) {
            mActiveBatch.publish(mb.getMessageName(), mb.getMessageBody());
        } else {
            Log.d(TAG,"Error trying to send OwnerTakesVehicle.  supplied batchOID (" + batchOID + ") does not equal active batch channel name (" + mActiveBatch.getName() + ")");
        }
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
