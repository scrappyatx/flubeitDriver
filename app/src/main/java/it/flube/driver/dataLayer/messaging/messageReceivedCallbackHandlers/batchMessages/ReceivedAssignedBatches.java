/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.modelLayer.Batch;
import it.flube.driver.useCaseLayer.interfaces.realtimeMessaging.RtmReceiveMsgAssignedBatches;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedAssignedBatches implements AblyMessageSubscribeCallback {
    private final String TAG = "ReceivedAssignedBatches";
    private RtmReceiveMsgAssignedBatches mCallback;

    public ReceivedAssignedBatches(RtmReceiveMsgAssignedBatches callback) {
        mCallback = callback;
    }
    public void onMessage(Message message) {
        Log.d(TAG,"Received assigned batches message : name -> " + message.name + "  data ->" + message.data);

        //convert message to an array of offers
        Gson mGson = new Gson();
        ArrayList<Batch> mBatchList = mGson.fromJson(message.data.toString(),new TypeToken<List<Batch>>(){}.getType());
        Log.d(TAG,Integer.toString(mBatchList.size()) + " assigned batches");

        //send to callback
        mCallback.receiveMsgAssignedBatches(mBatchList);
    }
}
