/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackCurrentOffers;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedCurrentOffers implements AblyMessageSubscribeCallback {
    private final String TAG = "ReceivedCurrentOffers";
    private RsmReceiveMsgCallbackCurrentOffers mCallback;

    public ReceivedCurrentOffers(RsmReceiveMsgCallbackCurrentOffers callback) {
        mCallback = callback;
    }
    public void onMessage(Message message) {
        Log.d(TAG,"Received current offers: name -> " + message.name + "  data ->" + message.data);

        //convert message to an array of offers
        Gson mGson = new Gson();
        ArrayList<Offer> mOfferList = mGson.fromJson(message.data.toString(),new TypeToken<List<Offer>>(){}.getType());
        Log.d(TAG,Integer.toString(mOfferList.size()) + " current offers");

        //send offers to callback
        mCallback.receiveMsgCurrentOffers(mOfferList);
    }
}
