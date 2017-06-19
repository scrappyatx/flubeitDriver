/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyEntities;

import android.util.Log;

import com.rollbar.android.Rollbar;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ErrorInfo;
import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessagePublishCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyListeners.AblyChannelStateListener;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyListeners.AblyMessagePublishListener;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyListeners.AblyMessageSubscribeListener;


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

        mChannel = AblyRealtimeSingleton.getInstance().getChannel(name);
        try {
            mChannel.attach();
            mChannel.on(mChannelStateListener);
            mChannel.subscribe(mSubcribeListener); //register this class to get callbacks for every message received on this channel
            Log.d(TAG, "channel " + mName + " attached");
        } catch ( AblyException e) {
            Log.e(TAG, "AblyException attaching to channel " + mName + " --> " + e.getMessage());
            Rollbar.reportException(e,"warning","AblyException attaching to channel -> " + e.getMessage());
        }

    }

    public boolean isAttached() {
        return mIsAttached;
    }

    public String getName() {
        return mName;
    }


    public void subscribe(String messageName, AblyMessageSubscribeCallback callback) {
        try {
            mChannel.subscribe(messageName, new AblyMessageSubscribeListener(callback));
        } catch (AblyException e) {
            Log.e(TAG, "Error trying to subscribe to channel " + mName + " --> " + e.getMessage());
            Rollbar.reportException(e,"warning","Error trying to subscribe to channel -> " + e.getMessage());
        }
    }

    public void publish(String name, Object data) {
            try {
                mChannel.publish(name, data, mPublishListener);
                Log.d(TAG, "Publishing message: Channel -> " + mName + " name -> " + name + " message -> " + data.toString());
            } catch (AblyException e) {
                Rollbar.reportException(e,"critical","Error trying to publish a message -> " + e.getMessage());
            }
    }

    //channel callbacks
    public void onChannelCallbackInitialized(String channelName) {
        Log.d(TAG, "*** Channel " + mName + " Initalized");
        mIsAttached = false;
        mCallback.onChannelCallbackInitialized(channelName);
    }

    public void onChannelCallbackAttaching(String channelName) {
        Log.d(TAG, "*** Channel " + mName + " Attaching...");
        mIsAttached = false;
        mCallback.onChannelCallbackAttaching(channelName);
    }

    public void onChannelCallbackAttached(String channelName, boolean resumed) {
        Log.d(TAG, "*** Channel " + mName + " Attached");
        mIsAttached = true;
        if (!resumed) {
            //TODO need to go and request channel history to recover missed messages
            Rollbar.reportMessage("Channel " + channelName + " missed some messages from the server!!!", "critical");
        }
        mCallback.onChannelCallbackAttached(channelName, resumed);
    }

    public void onChannelCallbackDetaching(String channelName) {
        Log.d(TAG, "*** Channel " + mName + " Detaching...");
        mIsAttached = false;
        mCallback.onChannelCallbackDetaching(channelName);
    }

    public void onChannelCallbackDetached(String channelName) {
        Log.d(TAG, "*** Channel " + mName + " Detached");
        mIsAttached = false;
        mCallback.onChannelCallbackDetached(channelName);
    }

    public void onChannelCallbackSuspended(String channelName) {
        Log.d(TAG, "*** Channel " + mName + " Suspended");
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

        Log.d(TAG, "Channel " + mName + " --> message successfully sent");
    }

    public void onError(ErrorInfo reason) {

        Log.d(TAG, "Channel " + mName + "--> error on publish -> " + reason.toString());
    }

    //message subscribe callbacks
    public void onMessage(Message message) {

        Log.d(TAG, "Channel " + mName + " --> message received in channel --> " + mName + " name --> " + message.name + " message --> " + message.data.toString());
    }

}
