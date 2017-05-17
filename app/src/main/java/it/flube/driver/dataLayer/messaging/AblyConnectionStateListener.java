/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;

import io.ably.lib.realtime.ConnectionStateListener;
import io.ably.lib.types.AblyException;
import it.flube.driver.dataLayer.interfaces.messaging.AblyConnectionCallback;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class AblyConnectionStateListener implements ConnectionStateListener {
    private AblyConnectionCallback mCallback;

    public AblyConnectionStateListener(AblyConnectionCallback callback) {
        mCallback = callback;
    }

    public void onConnectionStateChanged(ConnectionStateChange connectionStateChange) {
        switch (connectionStateChange.current) {
            case closed:
                mCallback.onConnectionCallbackClosed();
                break;
            case initialized:
                mCallback.onConnectionCallbackInitialized();
                break;
            case connecting:
                mCallback.onConnectionCallbackConnecting();
                break;
            case connected:
                mCallback.onConnectionCallbackConnected();
                break;
            case disconnected:
                mCallback.onConnectionCallbackDisconnected();

               // mCallback.onConnectionCallbackException(new Exception("Ably connection was disconnected. We will retry connecting again in 30 seconds."));
                break;
            case suspended:
                mCallback.onConnectionCallbackSuspended();
                //mCallback.onConnectionCallbackException(new Exception("Ably connection was suspended. We will retry connecting again in 60 seconds."));
                break;
            case closing:
                //sessionChannel.unsubscribe(messageListener);
                //sessionChannel.presence.unsubscribe(presenceListener);
                mCallback.onConnectionCallbackClosing();

                break;
            case failed:
                mCallback.onConnectionCallbackFailed();
               // mCallback.onConnectionCallbackException(new Exception("We're sorry, Ably connection failed. Please restart the app."));
                break;
        }
    }
}

