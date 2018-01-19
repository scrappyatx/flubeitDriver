/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;



import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import io.ably.lib.realtime.Channel;
import io.ably.lib.realtime.ChannelState;
import io.ably.lib.realtime.ChannelStateListener;
import io.ably.lib.realtime.CompletionListener;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ErrorInfo;
import io.ably.lib.types.Message;
import timber.log.Timber;

/**
 * Created on 7/9/2017
 * Project : Driver
 */

public class AblyChannel implements AblyConnection.ServerConnectResponse, AblyConnection.ServerDisconnectResponse,
        AblyConnection.ChannelAttachResponse, AblyConnection.ChannelDetachResponse,
        ChannelStateListener, Channel.MessageListener, CompletionListener {

    private static final String TAG = "AblyChannel";
    private final AblyConnection ablyConnection;

    private Channel ablyChannel;
    private String channelName;
    private Boolean firstAttached;
    private AblyChannel.ChannelConnectResponse channelConnectResponse;
    private AblyChannel.ChannelDisconnectResponse channelDisconnectResponse;
    private Boolean isConnected;

    public AblyChannel(String channelName, String authUrl){
        this.channelName = channelName;
        ablyConnection = new AblyConnection(authUrl);
        isConnected = false;
    }

    public void channelConnectRequest(String clientId, String idToken, ChannelConnectResponse response) {
        channelConnectResponse = response;
        Timber.tag(TAG).d("try to connect to ably realtime messaging server");
        Timber.tag(TAG).d("    clientID --> " + clientId);
        ablyConnection.serverConnectRequest(clientId, idToken, this);
    }

    /// AblyChannel.ServerConnectResponse callbacks
    public void serverConnectSuccess(){
        Timber.tag(TAG).d("serverConnect SUCCESS");
        Timber.tag(TAG).d("try to attach channel --> " + channelName);
        ablyConnection.channelAttachRequest(channelName, this);
    }

    public void channelAttachSuccess(Channel channel){
        isConnected = true;
        firstAttached = true;
        ablyChannel = channel;
        subscribeToAllMessageNames();
        Timber.tag(TAG).d("attached to channel --> " + channelName);
        channelConnectResponse.channelConnectSuccess();
    }

    private void subscribeToAllMessageNames() {
        try {
            ablyChannel.subscribe(this);
            Timber.tag(TAG).d(ablyChannel.name + " --> subscribed to all messages");
        } catch (AblyException e) {
            Timber.tag(TAG).e(e);
        }
    }

    //received message callback
    public void onMessage(Message message) {
        Timber.tag(TAG).d("RECEIVED MESSAGE : channel --> " + channelName + " message name --> " + message.name + " message body -> " + message.toString());
    }

    public void serverConnectFailure(){
        isConnected = false;
        Timber.tag(TAG).d("serverConnect FAILURE");
        channelConnectResponse.channelConnectFailure();
    }

    public void channelAttachFailure(){
        isConnected = false;
        Timber.tag(TAG).d("channelAttach FAILURE");
        channelConnectResponse.channelConnectFailure();

    }

    public void channelDisconnectRequest(ChannelDisconnectResponse response){
        channelDisconnectResponse = response;
        Timber.tag(TAG).d("disconnectRequest");
        ablyConnection.channelDetachRequest(channelName, this);
    }

    ///AblyChannel.DisconnectResponse callbacks
    public void channelDetachComplete(){
        isConnected = false;
        ablyConnection.serverDisconnectRequest(this);
        Timber.tag(TAG).d("disconnectComplete");
        channelDisconnectResponse.channelDisconnectComplete();
    }

    public void serverDisconnectComplete(){
        Timber.tag(TAG).d("serverDisconnectComplete");
    }


    public void subscribe(MessageReceive message) {
        try {
            ablyChannel.subscribe(message.getName(), message.getListener());
        } catch (AblyException e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void unsubscribe(MessageReceive message) {
        ablyChannel.unsubscribe(message.getName(), message.getListener());
    }


    public void publish(MessageSend message) {
        if (isConnected) {
            try {
                Timber.tag(TAG).d("SENDING MESSAGE : channel -> " + channelName + " message name -> " + message.getName() + " message body -> " + message.getData().toString());
                //JsonElement element = new Gson().fromJson(message.getData().toString(), JsonElement.class);
                //ablyChannel.publish(message.getName(),element, this);
                ablyChannel.publish(message.getName(),message.getData().toString(), this);
            } catch (AblyException e) {
                Timber.tag(TAG).e(e);
            }
        } else {
            Timber.tag(TAG).d("SEND MESSAGE ATTEMPT ON DISCONNECTED CHANNEL : channel -> " + channelName);
        }
    }
    /// publish message completion callbacks

    public void onSuccess() {
        Timber.tag(TAG).d("SEND MESSAGE SUCCESS : channel -> " + channelName);
    }

    public void onError(ErrorInfo reason) {
        Timber.tag(TAG).d("SEND MESSAGE FAIL : channel -> " + channelName + " reason -> " + reason.toString());
    }

    /// channel state change listener
    public void onChannelStateChanged(ChannelStateChange stateChange) {
        Timber.tag(TAG).d("channel state change : " + stateChange.previous + " ---> " + stateChange.current);

        if (stateChange.current == ChannelState.attached) {
            if ((!stateChange.resumed) && (!firstAttached)) {
                //data has been lost
                Timber.tag(TAG).w("possible DATA LOSS in channel " + ablyChannel.name + " : " + stateChange.previous + " ---> " + stateChange.current);
            }
            firstAttached = false;
        }

        if (stateChange.current == ChannelState.failed) {
            isConnected = false;
            Timber.tag(TAG).w("channel " + ablyChannel.name + " FAILED : "  + stateChange.previous + " ---> " + stateChange.current);
        }
    }


    public interface ChannelConnectResponse {
        void channelConnectSuccess();

        void channelConnectFailure();
    }

    public interface ChannelDisconnectResponse {
        void channelDisconnectComplete();
    }

    public interface MessageReceive {
        String getName();

        Channel.MessageListener getListener();
    }

    public interface MessageSend {
        String getName();

        JSONObject getData();
    }

}
