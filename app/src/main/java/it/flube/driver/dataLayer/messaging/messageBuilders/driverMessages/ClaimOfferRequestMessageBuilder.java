/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageBuilders.driverMessages;

import android.util.Log;

import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 5/20/2017
 * Project : Driver
 */

public class ClaimOfferRequestMessageBuilder {
    private final String TAG = "MessageBuilder";

    private JSONObject mMessageBody;
    private String mMessageName;

    public ClaimOfferRequestMessageBuilder(String offerOID){
        mMessageName = "claimOfferRequest";
        mMessageBody = new JSONObject();
        try {
            mMessageBody.put("offerOID", offerOID);
        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while building message: " + e.getMessage());
            Rollbar.reportException(e, "critical", "JSON exception while building message --> claimOfferRequest");
        }
    }

    public String getMessageName() {
        return mMessageName;
    }

    public JSONObject getMessageBody() {
        return mMessageBody;
    }

}
