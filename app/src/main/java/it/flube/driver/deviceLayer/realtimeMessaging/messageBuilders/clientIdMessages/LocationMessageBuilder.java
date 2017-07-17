/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.messageBuilders.clientIdMessages;

import android.util.Log;

import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 5/20/2017
 * Project : Driver
 */

public class LocationMessageBuilder {
    private final String TAG = "MessageBuilder";

    private JSONObject mMessageBody;
    private String mMessageName;

    public LocationMessageBuilder(double latitude, double longitude){
        mMessageName = "location";
        mMessageBody = new JSONObject();
        try {
            mMessageBody.put("latitude", latitude);
            mMessageBody.put("longitude", longitude);
        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while building message: " + e.getMessage());
            Rollbar.reportException(e, "critical", "JSON exception while building message --> location");
        }
    }

    public String getMessageName() {
        return mMessageName;
    }

    public JSONObject getMessageBody() {
        return mMessageBody;
    }

}
