/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ForfeitBatchResponseHandler;
import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseForfeitBatchRequest;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchManageController  {
    private final String TAG = "BatchManageController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;
    private AlertDialog forfeitDialog;
    private BatchCloudDB batch;


    public BatchManageController() {
        Timber.tag(TAG).d("controller CREATED");
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
    }

    public void confirmForfeit(AppCompatActivity activity, BatchCloudDB batch){
        this.batch = batch;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Forfeit Batch")
                .setMessage("Are you sure you want to forfeit this batch?")
                .setPositiveButton("Yes, Forfeit Batch", new ForfeitClick())
                .setNegativeButton("No, Keep Batch", new KeepClick());

        forfeitDialog = builder.create();
        forfeitDialog.show();
    }

    public void forfeitDialogKeepBatch() {
        Timber.tag(TAG).d("Keeping Batch");

    }

    public void forfeitDialogForfeitBatch() {
        Timber.tag(TAG).d("Forfeiting Batch");
        useCaseExecutor.execute(new UseCaseForfeitBatchRequest(device, batch, new ForfeitBatchResponseHandler()));
    }


    public void close(){
        useCaseExecutor.shutdown();
        Timber.tag(TAG).d("controller CLOSED");
    }

    private class ForfeitClick implements DialogInterface.OnClickListener {

        public void onClick(DialogInterface dialog, int id) {
            forfeitDialogForfeitBatch();
        }
    }

    private class KeepClick implements DialogInterface.OnClickListener {

        public void onClick(DialogInterface dialog, int id) {
            forfeitDialogKeepBatch();
        }
    }

}
