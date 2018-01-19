/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;


import android.os.Handler;
import android.os.Looper;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.deviceLayer.realtimeMessaging.messageBuilders.batchMessages.ForfeitBatchMessageBuilder;
import it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers.ClaimOfferResponseMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.ClaimOfferRequestMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.OnDutyMessageHandler;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;


/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeOfferMessages implements RealtimeMessagingInterface.OfferChannel,
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


    public void attach(String clientId) {
        ablyChannel.channelConnectRequest(clientId, AndroidDevice.getInstance().getUser().getIdToken(), this);
    }

    public void channelConnectSuccess(){
        Timber.tag(TAG).d("connectSuccess");
    }

    public void channelConnectFailure() {
        Timber.tag(TAG).d("connectFailure");
    }

    public void detach() {
        ablyChannel.channelDisconnectRequest(this);
    }

    public void channelDisconnectComplete() {
        Timber.tag(TAG).d("disconnectComplete");
    }

    public void sendOnDutyMessage(Boolean dutyStatus) {
        ablyChannel.publish(new OnDutyMessageHandler.MessageBuilder(true).build());
    }


    public void sendClaimOfferRequestMessage(String offerOID, final ClaimOfferResponse response) {

        final ClaimOfferResponseMessageHandler messageHandler = new ClaimOfferResponseMessageHandler(response);
        int timeout = 10000;

        ablyChannel.subscribe(messageHandler);
        ablyChannel.publish(new ClaimOfferRequestMessageHandler.MessageBuilder(offerOID).build());

        Looper.prepare();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ablyChannel.unsubscribe(messageHandler);
                response.claimOfferRequestTimeoutNoResponse();
            }
        }, timeout);

        Looper.loop();

    }


}
