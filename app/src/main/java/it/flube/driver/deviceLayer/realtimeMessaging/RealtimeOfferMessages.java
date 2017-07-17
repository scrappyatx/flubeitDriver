/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import org.greenrobot.eventbus.EventBus;


import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.deviceLayer.realtimeMessaging.eventBus.driverMessageEvents.RealtimeMessageClaimOfferResultEvent;
import it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers.ClaimOfferResultMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers.CurrentOffersMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.ClaimOfferRequestMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.CurrentOffersRequestMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.OnDutyMessageHandler;
import it.flube.driver.useCaseLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;


/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeOfferMessages implements RealtimeMessagingInterface.OfferMessages,
        AblyChannel.ChannelConnectResponse, AblyChannel.ChannelDisconnectResponse {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile RealtimeOfferMessages mInstance = new RealtimeOfferMessages();
    }

    private RealtimeOfferMessages() {
        ablyChannel = new AblyChannel(AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingLookingForOffersChannelName(),
                AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingAuthTokenUrl());
    }

    public static RealtimeOfferMessages getInstance() {
        return RealtimeOfferMessages.Loader.mInstance;
    }

    private static final String TAG = "RealtimeOfferMessages";
    private final AblyChannel ablyChannel;


    public void connect(String clientId) {
        ablyChannel.channelConnectRequest(clientId, this);
    }

    public void channelConnectSuccess(){
        Timber.tag(TAG).d("connectSuccess");
        ablyChannel.subscribe(new CurrentOffersMessageHandler());
        ablyChannel.subscribe(new ClaimOfferResultMessageHandler());
        sendMsgRequestCurrentOffers();
    }

    public void channelConnectFailure() {
        Timber.tag(TAG).d("connectFailure");
    }

    public void disconnect() {
        ablyChannel.channelDisconnectRequest(this);
    }

    public void channelDisconnectComplete() {
        Timber.tag(TAG).d("disconnectComplete");
    }

    public void sendMsgOnDuty(Boolean dutyStatus) {
        ablyChannel.publish(new OnDutyMessageHandler.MessageBuilder(true).build());
    }

    public void sendMsgRequestCurrentOffers() {
        ablyChannel.publish(new CurrentOffersRequestMessageHandler.MessageBuilder().build());
    }

    public void sendMsgClaimOfferRequest(String offerOID) {
        ablyChannel.publish(new ClaimOfferRequestMessageHandler.MessageBuilder(offerOID).build());
    }

}
