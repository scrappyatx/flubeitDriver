/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.messageBuilders.driverMessages;

import android.util.Log;

import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Created on 5/20/2017
 * Project : Driver
 */

public class RequestCurrentOffersMessageBuilder {
    private final String TAG = "MessageBuilder";

    private JSONObject mMessageBody;
    private String mMessageName;

    public RequestCurrentOffersMessageBuilder(){
        mMessageName = "requestCurrentOffers";
        mMessageBody = new JSONObject();

        //TODO define a message body for requestCurrentOffers
        try {
            mMessageBody.put("filler", "filler");
        } catch (JSONException e) {
            Timber.tag(TAG).e(e);
        }
    }

    public String getMessageName() {
        return mMessageName;
    }

    public JSONObject getMessageBody() {
        return mMessageBody;
    }
}
