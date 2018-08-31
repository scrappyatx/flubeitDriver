/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions.NodeBuilder;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.CURRENT_STEP_NODE;

/**
 * Created on 8/30/2018
 * Project : Driver
 */
public class StepStarted  implements
        OnCompleteListener<Void> {
    private static final String TAG = "StepStarted";

    private String batchGuid;
    private CloudServerMonitoringInterface.StepStartedResponse response;

    public void stepStartedRequest(DatabaseReference destRef, OrderStepInterface step, CloudServerMonitoringInterface.StepStartedResponse response) {
        Timber.tag(TAG).d("stepStartedRequest START...");

        this.batchGuid = step.getBatchGuid();
        this.response = response;

        Timber.tag(TAG).d("   destRef -> " + destRef.toString());
        Timber.tag(TAG).d("   batchGuid -> " + batchGuid);

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(CURRENT_STEP_NODE, NodeBuilder.getCurrentStepNode(step));

        destRef.child(batchGuid).updateChildren(data).addOnCompleteListener(this);
    }


    ///
    /// OnCompleteListener
    ///
    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudServerMonitoringStepStartedComplete(batchGuid);
    }
}
