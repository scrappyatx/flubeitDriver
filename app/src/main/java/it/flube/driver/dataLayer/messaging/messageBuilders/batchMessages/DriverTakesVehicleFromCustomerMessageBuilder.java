/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageBuilders.batchMessages;

import android.util.Log;

import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 5/20/2017
 * Project : Driver
 */

public class DriverTakesVehicleFromCustomerMessageBuilder {
    private final String TAG = "MessageBuilder";

    private JSONObject mMessageBody;
    private String mMessageName;

    public DriverTakesVehicleFromCustomerMessageBuilder(String batchOID){
        mMessageName = "driverTakesVehicle";
        mMessageBody = new JSONObject();
        try {
            mMessageBody.put("batchOID", batchOID);
        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while publishing message: " + e.getMessage());
            Rollbar.reportException(e, "critical", "JSON exception while trying to build message --> driverTakesVehicle");
        }
    }
    public String getMessageName() {
        return mMessageName;
    }
    public JSONObject getMessageBody() {
        return mMessageBody;
    }
}
