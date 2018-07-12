/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.ForfeitBatchMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.OnDutyMessageHandler;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeBatchMessages implements RealtimeMessagingInterface.BatchChannel,
        AblyChannel.ChannelConnectResponse, AblyChannel.ChannelDisconnectResponse {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static  RealtimeBatchMessages mInstance = new RealtimeBatchMessages();
    }

    private RealtimeBatchMessages() {
        //ablyChannel = new AblyChannel(AndroidDevice.getInstance().getCloudConfig().getRealtimeMessagingBatchActivityChannelName(),
        //        AndroidDevice.getInstance().getCloudConfig().getRealtimeMessagingAuthTokenUrl());
        ablyChannel = new AblyChannel("xxx", "yyy");
    }

    public static RealtimeBatchMessages getInstance() {
        return RealtimeBatchMessages.Loader.mInstance;
    }

    private static final String TAG = "RealtimeBatchMessages";
    private final AblyChannel ablyChannel;


    public void connect(String clientId) {
        ablyChannel.channelConnectRequest(clientId, AndroidDevice.getInstance().getUser().getIdToken(), this);
    }

    public void channelConnectSuccess(){
        Timber.tag(TAG).d("connectSuccess");
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

    public void sendMsgForfeitBatch(String batchOID) {
        ablyChannel.publish(new ForfeitBatchMessageHandler.MessageBuilder(batchOID).build());
    }
}
