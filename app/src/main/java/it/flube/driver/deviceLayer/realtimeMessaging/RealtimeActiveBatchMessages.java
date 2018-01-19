/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.CurrentLocationMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.CurrentlyDoingMessageHandler;
import it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers.MilestoneEventMessageHandler;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeActiveBatchMessages implements
        RealtimeMessagingInterface.ActiveBatchChannel,
        AblyChannel.ChannelConnectResponse,
        AblyChannel.ChannelDisconnectResponse {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile RealtimeActiveBatchMessages instance = new RealtimeActiveBatchMessages();
    }

    private RealtimeActiveBatchMessages() {

    }

    public static RealtimeActiveBatchMessages getInstance() {
        return RealtimeActiveBatchMessages.Loader.instance;
    }

    private static final String TAG = "RealtimeActiveBatchMessages";
    private AblyChannel ablyChannel;


    public void connectRequest(String batchGuid, ActiveBatchChannelConnectResponse response){
        Timber.tag(TAG).d("connectRequest START...");
        attach(batchGuid);
        response.activeBatchChannelConnectComplete();
        Timber.tag(TAG).d("...connectRequest COMPLETE");
    }

    public void disconnectRequest(ActiveBatchChannelDisconnectResponse response){
        Timber.tag(TAG).d("disconnectRequest START...");
        if (ablyChannel != null){
            Timber.tag(TAG).d("   ...disconnecting ablyChannel");
            ablyChannel.channelDisconnectRequest(this);
        } else {
            Timber.tag(TAG).d("   ...ablyChannel is null, doing nothing");
        }
        response.activeBatchChannelDisconnectComplete();
        Timber.tag(TAG).d("...disconnectRequest COMPLETE");
    }

    public void attach(String batchGuid) {
        Timber.tag(TAG).d("attaching to : " + batchGuid);
        ablyChannel = new AblyChannel(batchGuid,
                AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingAuthTokenUrl());
        ablyChannel.channelConnectRequest(AndroidDevice.getInstance().getUser().getDriver().getClientId(), AndroidDevice.getInstance().getUser().getIdToken(), this);
    }

    public void channelConnectSuccess(){
        Timber.tag(TAG).d("connectSuccess");
    }

    public void channelConnectFailure() {
        Timber.tag(TAG).d("connectFailure");
    }

    public void detach() {
        if (ablyChannel != null) {
            Timber.tag(TAG).d("disconnecting channel..");
            ablyChannel.channelDisconnectRequest(this);
        } else {
            Timber.tag(TAG).d("channel is already disconnected!");
        }
    }

    public void channelDisconnectComplete() {
        Timber.tag(TAG).d("disconnectComplete");
        ablyChannel = null;
    }


    public void sendMsgLocationUpdate(double latitude, double longitude) {
        Timber.tag(TAG).d("publishing location -> latitude : " + latitude + " longitude : " + longitude);
        ablyChannel.publish(new CurrentLocationMessageHandler.MessageBuilder(latitude, longitude).build());
    }

    public void sendMilestoneEvent(String milestoneEvent){
        Timber.tag(TAG).d("publishing milestone -> " + milestoneEvent);
        ablyChannel.publish(new MilestoneEventMessageHandler.MessageBuilder(milestoneEvent).build());
    }

    public void sendCurrentlyDoing(String stepTitle){
        Timber.tag(TAG).d("publishing currentlyDoing -> " + stepTitle);
        ablyChannel.publish(new CurrentlyDoingMessageHandler.MessageBuilder(stepTitle).build());
    }

}
