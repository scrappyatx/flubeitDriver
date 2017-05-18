/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.driverMessages;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessageSubscribeCallback;
import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedCurrentOffers implements AblyMessageSubscribeCallback {
    private final String TAG = "ReceivedCurrentOffers";

    public void onMessage(Message message) {
        Log.d(TAG,"Received current offers: name -> " + message.name + "  data ->" + message.data);

        //convert message to an array of offers
        Gson mGson = new Gson();
        ArrayList<Offer> mOfferList = mGson.fromJson(message.data.toString(),new TypeToken<List<Offer>>(){}.getType());
        Log.d(TAG,Integer.toString(mOfferList.size()) + " current offers");

        //broadcast offers on event bus
        EventBus.getDefault().post(new ReceivedCurrentOffersMessage(mOfferList));
    }
}
