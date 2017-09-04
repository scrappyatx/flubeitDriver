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
import it.flube.driver.modelLayer.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class AssignedBatchesMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "AssignedBatchesMessageHandler";
    private static final String MESSAGE_NAME = "assignedBatches";

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageAssignedBatches getListener() {
        return new onMessageAssignedBatches();
    }

    public static class onMessageAssignedBatches implements Channel.MessageListener {

        public void onMessage(Message message) {
            Timber.tag(TAG).d("Received assigned batches message : name -> " + message.name + "  data ->" + message.data);

            try {
                Gson mGson = new Gson();
                ArrayList<Batch> batchList = mGson.fromJson(message.data.toString(), new TypeToken<List<Batch>>(){}.getType());
                Timber.tag(TAG).d(Integer.toString(batchList.size()) + " assigned batches");
                EventBus.getDefault().postSticky(new AssignedBatchesMessageHandler.AssignedBatchesEvent(batchList));
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }

        }
    }

    public static class AssignedBatchesEvent {
        private ArrayList<Batch> batchList;
        private int batchCount;

        public AssignedBatchesEvent(ArrayList<Batch> batchList){
            this.batchList = batchList;
            this.batchCount = batchList.size();
        }

        public ArrayList<Batch> getBatchList() {
            return batchList;
        }

        public int getBatchCount() {
            return batchCount;
        }

    }


}
