/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class BatchListDisplayHolderBuilder {
    private static final String TAG="BatchListDisplayHolderBuilder";

    private ArrayList<BatchDisplayHolder> batchDisplayList;

    private BatchListDisplayHolderBuilder(@NonNull Builder builder){
        this.batchDisplayList = builder.batchDisplayList;
    }

    public ArrayList<BatchDisplayHolder> getBatchDisplayList(){
        return this.batchDisplayList;
    }

    private static class Builder {
        private ArrayList<BatchDisplayHolder> batchDisplayList;

        public Builder(){
            this.batchDisplayList = new ArrayList<BatchDisplayHolder>();
        }

        public Builder batchList(ArrayList<Batch> batchList){
            buildBatchDisplayHolderArray(batchList);
            return this;
        }

        private void validate(ArrayList<BatchDisplayHolder> batchDisplayList){

        }

        public ArrayList<BatchDisplayHolder> build(){
            ArrayList<BatchDisplayHolder> batchDisplayList = new BatchListDisplayHolderBuilder(this).getBatchDisplayList();
            validate(batchDisplayList);
            return batchDisplayList;
        }

        private void buildBatchDisplayHolderArray(ArrayList<Batch> batchList){
            // sort batchList by startBy date
            // new section header everytime the date changes

        }

    }

}
