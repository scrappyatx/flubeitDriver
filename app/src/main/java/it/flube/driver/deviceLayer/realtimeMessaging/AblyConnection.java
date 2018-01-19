/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import android.support.annotation.NonNull;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.realtime.ChannelState;
import io.ably.lib.realtime.ChannelStateListener;
import io.ably.lib.realtime.CompletionListener;
import io.ably.lib.realtime.ConnectionState;
import io.ably.lib.realtime.ConnectionStateListener;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ClientOptions;
import io.ably.lib.types.ErrorInfo;
import io.ably.lib.types.Param;
import timber.log.Timber;


/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class AblyConnection implements ConnectionStateListener, CompletionListener {

    private static final String TAG = "AblyConnection";
    private final String authUrl;

    private static AblyRealtime ablyRealtime;
    private static Boolean isConnected = false;

    private Channel ablyChannel;
    private ChannelAttachResponse channelAttachResponse;



    public AblyConnection(@NonNull String authUrl) {
        this.authUrl = authUrl;
    }

    public Boolean IsConnected() {
        return isConnected;
    }

    public void serverConnectRequest(@NonNull String clientId, @NonNull String idToken, @NonNull ServerConnectResponse serverConnectResponse){
        Timber.tag(TAG).d("serverConnectRequest START...");
        if (!isConnected) {

            Timber.tag(TAG).d("   clientId     --> " + clientId);
            Timber.tag(TAG).d("   idToken      --> " + idToken);
            Timber.tag(TAG).d("   authUrl      --> " + authUrl);
            try {
                ablyRealtime = new AblyRealtime(getClientOptions(clientId, idToken, authUrl));
                ablyRealtime.connection.on(this);
                isConnected = true;
                Timber.tag(TAG).d("   ...serverConnectRequest SUCCESS");
                serverConnectResponse.serverConnectSuccess();
            } catch (AblyException e) {
                Timber.tag(TAG).e(e);
                isConnected = false;
                Timber.tag(TAG).d("   ...serverConnectRequest FAILURE");
                serverConnectResponse.serverConnectFailure();
            }
        } else {
            Timber.tag(TAG).d("   ...SERVER WAS ALREADY CONNECTED!");
            serverConnectResponse.serverConnectSuccess();
        }
        Timber.tag(TAG).d("...serverConnectRequest COMPLETE");
    }

    private ClientOptions getClientOptions(String clientId, String idToken, String tokenUrl) {
        ClientOptions clientOptions = new ClientOptions();
        clientOptions.authUrl = tokenUrl;
        clientOptions.authMethod = "GET";
        clientOptions.authParams = getAuthorizationParameters(idToken);
        clientOptions.clientId = clientId;
        clientOptions.tls = true;
        clientOptions.logLevel = io.ably.lib.util.Log.VERBOSE;
        clientOptions.echoMessages = false;
        clientOptions.autoConnect = true;
        return clientOptions;
    }

    private Param[] getAuthorizationParameters(String idToken) {
        Param[] param = new Param[1];
        param[0] = new Param("clientId", idToken);
        return param;
    }


    public void serverDisconnectRequest(@NonNull ServerDisconnectResponse response){
        Timber.tag(TAG).d("serverDisconnectRequest START...");

        if (isConnected) {
            Timber.tag(TAG).d("   ...closing ablyRealtime");
            ablyRealtime.close();

        } else {
            Timber.tag(TAG).d("   ...ablyRealtime already closed, doing nothing");
        }

        isConnected = false;
        Timber.tag(TAG).d("...serverDisconnectRequest COMPLETE");
        response.serverDisconnectComplete();
    }

    public void channelAttachRequest(@NonNull String channelName, @NonNull ChannelAttachResponse response) {
        channelAttachResponse = response;
        Timber.tag(TAG).d("channelAttachRequest START");
        Timber.tag(TAG).d("    channel --> " + channelName);
        try {
            ablyChannel = ablyRealtime.channels.get(channelName);
            ablyChannel.attach(this);
        } catch (AblyException e) {
            Timber.tag(TAG).e(e);
            channelAttachResponse.channelAttachFailure();
        }
    }

    /// CompletionListener interface
    public void onSuccess() {
        Timber.tag(TAG).d("channelAttachRequest SUCCESS");
        channelAttachResponse.channelAttachSuccess(ablyChannel);
    }

    public void onError(ErrorInfo reason) {
        Timber.tag(TAG).d("connectRequest FAIL --> " + reason.toString());
        channelAttachResponse.channelAttachFailure();
    }

    public void channelDetachRequest(@NonNull String channelName, @NonNull ChannelDetachResponse response) {
        try {
            ablyChannel.detach();
            ablyRealtime.channels.release(channelName);
        } catch (AblyException e) {
            Timber.tag(TAG).e(e);
        }
        Timber.tag(TAG).d("channelDetachRequest COMPLETE");
        response.channelDetachComplete();
    }

    /// ConnectionStateListener
    public void onConnectionStateChanged(ConnectionStateListener.ConnectionStateChange state) {
        Timber.tag(TAG).d("    connection state change : " + state.previous + " ---> " + state.current );
        if (state.current == ConnectionState.failed) {
            isConnected = false;
            Timber.tag(TAG).w("    connection to ably realtime messaging server FAILED");
        }
    }

    /// interfaces for responses to calling classes
    public interface ServerConnectResponse {
        void serverConnectSuccess();

        void serverConnectFailure();
    }

    public interface ServerDisconnectResponse {
        void serverDisconnectComplete();
    }

    public interface ChannelAttachResponse {
        void channelAttachSuccess(Channel ablyChannel);

        void channelAttachFailure();
    }

    public interface ChannelDetachResponse {
        void channelDetachComplete();
    }
}
