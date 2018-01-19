/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyConnection;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 7/31/2017
 * Project : Driver
 */

public class AblyConnectionWrapper implements
        RealtimeMessagingInterface.Connection,
        AblyConnection.ServerConnectResponse,
        AblyConnection.ServerDisconnectResponse {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile AblyConnectionWrapper instance = new AblyConnectionWrapper();
    }

    private AblyConnectionWrapper() {
        ablyConnection = new AblyConnection(AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingAuthTokenUrl());
    }

    public static AblyConnectionWrapper getInstance() {
        return AblyConnectionWrapper.Loader.instance;
    }

    private static final String TAG = "AblyConnectionWrapper";
    private final AblyConnection ablyConnection;

    private RealtimeMessagingInterface.OfferChannel offerChannel;
    private RealtimeMessagingInterface.BatchChannel batchChannel;
    private RealtimeMessagingInterface.ActiveBatchChannel activeBatchChannel;

    private RealtimeMessagingInterface.Connection.ConnectResponse connectResponse;
    private RealtimeMessagingInterface.Connection.DisconnectResponse disconnectResponse;



    public void messageServerConnectRequest(String clientId, String idToken, RealtimeMessagingInterface.Connection.ConnectResponse connectResponse) {
        this.connectResponse = connectResponse;
        ablyConnection.serverConnectRequest(clientId, idToken, this);
    }

    public void serverConnectSuccess() {
        connectResponse.messageServerConnectSuccess();
    }

    public void serverConnectFailure() {
        connectResponse.messageServerConnectFailure();
    }


    public void messageServerDisconnectRequest(RealtimeMessagingInterface.Connection.DisconnectResponse disconnectResponse) {
        this.disconnectResponse = disconnectResponse;
        ablyConnection.serverDisconnectRequest(this);
    }

    public void serverDisconnectComplete() {
        disconnectResponse.messageServerDisconnectComplete();
    }

    public RealtimeMessagingInterface.OfferChannel getOfferChannel() {
        if (offerChannel == null) {

        }
        return null;
    }

    public RealtimeMessagingInterface.BatchChannel getBatchChannel() {
        return null;
    }

    public RealtimeMessagingInterface.ActiveBatchChannel getActiveBatchChannel() {
        return null;
    }
}
