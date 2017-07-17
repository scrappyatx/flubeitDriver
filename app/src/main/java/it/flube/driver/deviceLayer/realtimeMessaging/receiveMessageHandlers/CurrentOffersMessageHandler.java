/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import it.flube.driver.modelLayer.Offer;
import timber.log.Timber;

/**
 * Created on 7/14/2017
 * Project : Driver
 */

public class CurrentOffersMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "CurrentOffersMessageHandler";
    private static final String MESSAGE_NAME = "currentOffers";

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageCurrentOffers getListener() {
        return new onMessageCurrentOffers();
    }

    public static class onMessageCurrentOffers implements Channel.MessageListener {

        public void onMessage(Message message) {
            Timber.tag(TAG).d("Received current offers: name -> " + message.name + "  data ->" + message.data);

            Gson mGson = new Gson();
            try {
                ArrayList<Offer> offerList = mGson.fromJson(message.data.toString(), new TypeToken<List<Offer>>(){}.getType());
                Timber.tag(TAG).d(Integer.toString(offerList.size()) + " current offers");
                EventBus.getDefault().postSticky(new CurrentOffersMessageHandler.CurrentOffersEvent(offerList));
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
    }

    public static class CurrentOffersEvent {
        private ArrayList<Offer> offerList;
        private int offerCount;

        public CurrentOffersEvent(ArrayList<Offer> offerList){
            this.offerList = offerList;
            this.offerCount = offerList.size();
        }

        public ArrayList<Offer> getOfferList() {
            return offerList;
        }

        public int getOfferCount() {
            return offerCount;
        }

    }


}
