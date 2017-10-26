/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchGotoNextStep {
    private static final String TAG = "FirebaseActiveBatchGotoNextStep";


    public interface Response {
        void gotoNextStepSuccess();

        void gotoNextStepNewOrderStarted();

        void gotoNextStepBatchComplete();

        void gotoNextStepDiscontinuity();

    }
}
