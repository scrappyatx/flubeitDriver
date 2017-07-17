/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Created on 7/12/2017
 * Project : Driver
 */

public class AblyMessageSend {
    private final static String TAG = "AblyMessageSend";

    private final static String ONDUTY_MESSAGE_NAME = "onDuty";
    private final static String REQUEST_CURRENT_OFFERS_MESSAGE_NAME = "requestCurrentOffers";
    private final static String CLAIM_OFFER_REQUEST_MESSAGE_NAME = "claimOfferRequest";

    private final String messageName;
    private final JSONObject messageBody;

    public String getName() {
        return messageName;
    }

    public JSONObject getData(){
        return messageBody;
    }

    private AblyMessageSend(OnDutyMessageBuilder builder) {
        this.messageName = builder.messageName;
        this.messageBody = builder.messageBody;
    }

    private AblyMessageSend(RequestCurrentOffersMessageBuilder builder) {
        this.messageName = builder.messageName;
        this.messageBody = builder.messageBody;
    }

    private AblyMessageSend(ClaimOfferRequestMessageBuilder builder) {
        this.messageName = builder.messageName;
        this.messageBody = builder.messageBody;
    }

    public static String getOnDutyMessageName() {
        return ONDUTY_MESSAGE_NAME;
    }

    public static String getRequestCurrentOffersMessageName(){
        return REQUEST_CURRENT_OFFERS_MESSAGE_NAME;
    }

    public static String getClaimOfferRequestMessageName(){
        return CLAIM_OFFER_REQUEST_MESSAGE_NAME;
    }


    public static class OnDutyMessageBuilder {
        private final String TAG = "OnDutyMessageBuilder";

        private final String messageName;
        private final JSONObject messageBody;

        public OnDutyMessageBuilder(Boolean dutyStatus) {
            messageName = ONDUTY_MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("onDuty", dutyStatus);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public AblyMessageSend build() {
            return new AblyMessageSend(this);
        }
    }


    public static class RequestCurrentOffersMessageBuilder {
        private final String TAG = "RequestCurrentOffersMessageBuilder";

        private JSONObject messageBody;
        private String messageName;

        public RequestCurrentOffersMessageBuilder(){
            messageName = REQUEST_CURRENT_OFFERS_MESSAGE_NAME;
            messageBody = new JSONObject();

            //TODO define a message body for requestCurrentOffers
            try {
                messageBody.put("filler", "filler");
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public AblyMessageSend build() {
            return new AblyMessageSend(this);
        }
    }

    public static class ClaimOfferRequestMessageBuilder {
        private final String TAG = "ClaimOfferRequestMessageBuilder";

        private JSONObject messageBody;
        private String messageName;

        public ClaimOfferRequestMessageBuilder(String offerOID){
            messageName = CLAIM_OFFER_REQUEST_MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("offerOID", offerOID);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public AblyMessageSend build() {
            return new AblyMessageSend(this);
        }

    }
}
