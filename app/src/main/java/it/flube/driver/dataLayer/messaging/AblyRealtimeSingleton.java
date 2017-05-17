/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ClientOptions;
import io.ably.lib.types.Param;
import it.flube.driver.dataLayer.interfaces.messaging.AblyChannelCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyConnectionCallback;


/**
 * Created on 5/11/2017
 * Project : Driver
 */

public class AblyRealtimeSingleton implements AblyConnectionCallback {
    private static AblyRealtimeSingleton instance = new AblyRealtimeSingleton();
    private static final String TAG = "AblyRealtimeSingleton";


    private AblyRealtime mAblyRealtime;
    private AblyConnectionCallback mCallback;
    private boolean mIsConnected;

    //channels on this connection
    private ArrayList<AblyChannel> mChannelList;

    private AblyRealtimeSingleton() {
        //initialize channel list
        mChannelList = new ArrayList<AblyChannel>();
    }

    public static AblyRealtimeSingleton getInstance() {
        return instance;
    }


    public void establishConnection(final String clientId, final String tokenUrl, AblyConnectionCallback callback) {
        //instance our network connection
        //setup callbacks for connection state change events
        AblyConnectionStateListener connectionStateListener = new AblyConnectionStateListener(this);
        mCallback = callback;

        //setup authorization parameters
        Param[] param = new Param[1];
        param[0] = new Param("clientId", clientId);

        //setup AblyRealtime Client options
        ClientOptions c = new ClientOptions();
        c.authUrl = tokenUrl;
        c.authMethod = "GET";
        c.authParams = param;
        c.clientId = clientId;
        c.tls = true;
        c.logLevel = io.ably.lib.util.Log.VERBOSE;
        c.echoMessages = false;
        c.autoConnect = false;

        //set our connected status
        mIsConnected = false;

        //now try to connect
        try {
            mAblyRealtime = new AblyRealtime(c);
            mAblyRealtime.connection.on(connectionStateListener);
            Log.d(TAG, "Created AblyRealtime connection");
        } catch (AblyException e) {
            Log.d(TAG, "Error creating AblyRealtime connection --> " + e.getMessage());
            Rollbar.reportException(e,"warning", "Error creating AblyRealtime connection --> " + e.getMessage());
            onConnectionCallbackException(e);
        }
    }

    // methods that can be used by controllers
    public boolean isConnected() {
        return mIsConnected;
    }

    public void connect() {
        if (mAblyRealtime != null) {
            mAblyRealtime.connection.connect();
        }
    }

    public void disconnect() {
        if (mAblyRealtime != null) {
            mAblyRealtime.close();
        }
    }

    public Channel getChannel(String name) { return mAblyRealtime.channels.get(name); }

    public AblyChannel createChannel(String name, AblyChannelCallback callback) {
        AblyChannel ch = new AblyChannel(name, callback);  //create channel
        mChannelList.add(ch);
        return ch;
    }

    // call back handlers
    public void onConnectionCallbackException(Exception ex) {
        Log.d(TAG, "*** Connection Exception -> " + ex.getMessage());
        mIsConnected = false;
        mCallback.onConnectionCallbackException(ex);
    }

    public void onConnectionCallbackInitialized() {
        Log.d(TAG, "*** Connection Initalized");
        mIsConnected = false;
        mCallback.onConnectionCallbackInitialized();
    }

    public void onConnectionCallbackConnecting() {
        Log.d(TAG, "*** Connection Connecting...");
        mIsConnected = false;
        mCallback.onConnectionCallbackConnecting();
    }

    public void onConnectionCallbackConnected() {
        Log.d(TAG, "*** Connection Connected");
        mIsConnected = true;
        mCallback.onConnectionCallbackConnected();
    }

    public void onConnectionCallbackDisconnected() {
        Log.d(TAG, "*** Connection Disconnected");
        mIsConnected = false;
        mCallback.onConnectionCallbackDisconnected();
    }

    public void onConnectionCallbackSuspended() {
        Log.d(TAG, "*** Connection Suspended");
        mIsConnected = false;
        mCallback.onConnectionCallbackSuspended();
    }

    public void onConnectionCallbackClosing() {
        Log.d(TAG, "*** Connection Closing...");
        mIsConnected = false;
        mCallback.onConnectionCallbackClosing();
    }

    public void onConnectionCallbackClosed() {
        Log.d(TAG, "*** Connection Closed");
        mIsConnected = false;
        mCallback.onConnectionCallbackClosed();
    }

    public void onConnectionCallbackFailed() {
        Log.d(TAG, "*** Connection Failed");
        mIsConnected = false;
        mCallback.onConnectionCallbackFailed();
    }

}

