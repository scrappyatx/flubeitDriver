/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.manageBatch.forfeitBatch.UseCaseForfeitBatchRequest;
import timber.log.Timber;

/**
 * Created on 4/17/2018
 * Project : Driver
 */
public class ForfeitBatchConfirmation {

    private static final String TAG = "ForfeitBatchConfirmation";

    private Response response;
    private MobileDeviceInterface device;
    private String batchGuid;

    public void show(AppCompatActivity activity, MobileDeviceInterface device, String batchGuid, Response response){
        Timber.tag(TAG).d("show START...");
        this.response = response;
        this.device = device;
        this.batchGuid = batchGuid;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                .setMessage(activity.getString(R.string.batch_manage_forfeit_batch_dialog_message))
                .setPositiveButton(activity.getString(R.string.batch_manage_forfeit_batch_positive_response), new ForfeitClick())
                .setNegativeButton(activity.getString(R.string.batch_manage_forfeit_batch_negative_response), new KeepClick());

        AlertDialog forfeitDialog = builder.create();
        forfeitDialog.show();
    }

    //// if user clicks ForfeitClick -> FORFEIT BATCH REQUEST
    private class ForfeitClick implements
            DialogInterface.OnClickListener,
            UseCaseForfeitBatchRequest.Response {

        public void onClick(DialogInterface dialog, int id) {
            Timber.tag(TAG).d("Forfeiting Batch");
            response.forfeitBatchDialogMakeForfeitRequest(batchGuid);
            device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseForfeitBatchRequest(device, batchGuid, this));
        }

        public void useCaseForfeitBatchSuccess(String batchGuid){
            response.forfeitBatchSuccess(batchGuid);
        }

        public void useCaseForfeitBatchFailure(String batchGuid){
            response.forfeitBatchFailure(batchGuid);
        }

        public void useCaseForfeitBatchTimeout(String batchGuid){
            response.forfeitBatchTimeout(batchGuid);
        }

        public void useCaseForfeitBatchDenied(String batchGuid, String reason){
            response.forfeitBatchDenied(batchGuid, reason);
        }

    }


    /// if user clicks KeepClick --> FORFEIT BATCH CANCEL

    private class KeepClick implements DialogInterface.OnClickListener {

        public void onClick(DialogInterface dialog, int id) {
            response.forfeitBatchDialogCancelled(batchGuid);
        }
    }

    //// interface for response

    public interface Response {
        void forfeitBatchDialogCancelled(String batchGuid);

        void forfeitBatchDialogMakeForfeitRequest(String batchGuid);

        void forfeitBatchSuccess(String batchGuid);

        void forfeitBatchFailure(String batchGuid);

        void forfeitBatchTimeout(String batchGuid);

        void forfeitBatchDenied(String batchGuid, String reason);


    }

}
