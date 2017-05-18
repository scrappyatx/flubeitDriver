/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import android.util.Log;

import com.rollbar.android.Rollbar;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.realtime.ChannelStateListener;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ErrorInfo;
import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.messaging.AblyChannelCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessagePublishCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessageSubscribeCallback;


/**
 * Created on 5/13/2017
 * Project : Driver
 */

public class AblyChannel implements AblyChannelCallback, AblyMessagePublishCallback, AblyMessageSubscribeCallback {
    private static final String TAG = "AblyChannel";

    private AblyRealtime mAblyRealtime;
    private Channel mChannel;
    private String mName;
    private boolean mIsAttached;

    // listeners used internally by this class
    private AblyChannelStateListener mChannelStateListener;
    private AblyMessageSubscribeListener mSubcribeListener;
    private AblyMessagePublishListener mPublishListener;

    // callback handlers
    private AblyChannelCallback mCallback;


    public AblyChannel(String name, AblyChannelCallback callback) {
        mName = name;
        mChannelStateListener = new AblyChannelStateListener(mName, this);
        mSubcribeListener = new AblyMessageSubscribeListener(this);
        mPublishListener = new AblyMessagePublishListener(this);
        mCallback = callback;
        mIsAttached = false;

        //if  (AblyRealtimeSingleton.getInstance().isConnected()) {
            mChannel = AblyRealtimeSingleton.getInstance().getChannel(name);
            try {
                mChannel.attach();
                mChannel.on(mChannelStateListener);
                mChannel.subscribe(mSubcribeListener); //register this class to get callbacks for every message received on this channel
                Log.d(TAG, "channel attached");
            } catch ( AblyException e) {
                Log.e(TAG, "AblyException attaching to channel -> " + e.getMessage());
                Rollbar.reportException(e,"warning","AblyException attaching to channel -> " + e.getMessage());
            }
        //} else {
            //Log.d(TAG, "Tried to create a channel when AblyRealtimeSingleton is NOT connected");
        //}
    }

    public boolean IsAttached() {
        return mIsAttached;
    }


    public void subscribe(String messageName, AblyMessageSubscribeCallback callback) {
        try {
            mChannel.subscribe(messageName, new AblyMessageSubscribeListener(callback));
        } catch (AblyException e) {
            Log.e(TAG, "Error trying to subscribe to channel " + e.getMessage());
            Rollbar.reportException(e,"warning","Error trying to subscribe to channel -> " + e.getMessage());
        }
    }

    public void publish(String name, Object data) {
            try {
                mChannel.publish(name, data, mPublishListener);
            } catch (AblyException e) {
                Rollbar.reportException(e,"critical","Error trying to publish a message -> " + e.getMessage());
            }
    }

    //channel callbacks
    public void onChannelCallbackInitialized(String channelName) {
        Log.d(TAG, "*** Channel Initalized");
        mIsAttached = false;
        mCallback.onChannelCallbackInitialized(channelName);
    }

    public void onChannelCallbackAttaching(String channelName) {
        Log.d(TAG, "*** Channel Attaching...");
        mIsAttached = false;
        mCallback.onChannelCallbackAttaching(channelName);
    }

    public void onChannelCallbackAttached(String channelName, boolean resumed) {
        Log.d(TAG, "*** Channel Attached");
        mIsAttached = true;
        if (!resumed) {
            //TODO need to go and request channel history to recover missed messages
            Rollbar.reportMessage("Channel " + channelName + " missed some messages from the server!!!", "critical");
        }
        mCallback.onChannelCallbackAttached(channelName, resumed);
    }

    public void onChannelCallbackDetaching(String channelName) {
        Log.d(TAG, "*** Channel Detaching...");
        mIsAttached = false;
        mCallback.onChannelCallbackDetaching(channelName);
    }

    public void onChannelCallbackDetached(String channelName) {
        Log.d(TAG, "*** Channel Detached");
        mIsAttached = false;
        mCallback.onChannelCallbackDetached(channelName);
    }

    public void onChannelCallbackSuspended(String channelName) {
        Log.d(TAG, "*** Channel Suspended");
        mIsAttached = false;
        mCallback.onChannelCallbackSuspended(channelName);
    }

    public void onChannelCallbackFailed(String channelName, ErrorInfo e) {
        Log.d(TAG, "*** Channel Failed -> " + Integer.toString(e.code) + " : " + e.message);
        mIsAttached = false;
        mCallback.onChannelCallbackFailed(channelName, e);
    }

    // message publish callbacks
    public void onSuccess() {

        Log.d(TAG, "success on publish");
    }

    public void onError(ErrorInfo reason) {

        Log.d(TAG, "error on publish -> " + reason.toString());
    }

    //message subscribe callbacks
    public void onMessage(Message message) {

        Log.d(TAG, "subscribe -> message received in channel");
    }

}
