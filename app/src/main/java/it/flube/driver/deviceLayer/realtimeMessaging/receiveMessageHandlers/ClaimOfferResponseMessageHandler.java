/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers;

import com.google.gson.Gson;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;


/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class ClaimOfferResponseMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "ClaimOfferResponseMessageHandler";
    private static final String MESSAGE_NAME = "claimOfferResponse";
    private final RealtimeMessagingInterface.OfferChannel.ClaimOfferResponse response;

    public ClaimOfferResponseMessageHandler(RealtimeMessagingInterface.OfferChannel.ClaimOfferResponse response){
        this.response = response;
    }

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageClaimOfferResult getListener() {
        return new onMessageClaimOfferResult();
    }

    public class onMessageClaimOfferResult implements Channel.MessageListener {

        public void onMessage(Message message) {
            try {
                Timber.tag(TAG).d("Received claim offer result message : name -> " + message.name + "  data ->" + message.data);
                OfferResultJSON offerResult = new Gson().fromJson(message.data.toString(), OfferResultJSON.class);
                response.receiveClaimOfferResponseMessage(offerResult.getOfferOID(), offerResult.getBatchOID(), offerResult.getClientId());

            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
    }

    private class OfferResultJSON {
        private String offerOID;
        private String batchOID;
        private String clientId;
        public String getOfferOID(){ return offerOID; }
        public String getBatchOID(){ return batchOID; }
        public String getClientId(){ return clientId; }
    }
}
