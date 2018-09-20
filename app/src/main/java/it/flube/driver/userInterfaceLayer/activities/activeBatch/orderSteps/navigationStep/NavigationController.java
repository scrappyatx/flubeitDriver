/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


//import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
//import com.mapbox.services.android.telemetry.location.LocationEngine;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;

import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/15/2017
 * Project : Driver
 */

public class NavigationController implements
        UseCaseGetDriverAndActiveBatchCurrentStep.Response,
        UseCaseFinishCurrentStepRequest.Response {

    private final String TAG = "NavigationController";

    private StepFinishedResponse stepResponse;
    private GetDriverAndActiveBatchStepResponse response;
    private ManualConfirmResponse confirmResponse;


    @SuppressWarnings( {"MissingPermission"})
    public NavigationController(AppCompatActivity activity) {
        Timber.tag(TAG).d("created");
    }

    public void getDriverAndActiveBatchStep(GetDriverAndActiveBatchStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep...");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.NAVIGATION, this));

    }

    public void stepFinishedRequest(String milestoneEvent, StepFinishedResponse stepResponse){
        Timber.tag(TAG).d("finishing STEP");
        this.stepResponse = stepResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseFinishCurrentStepRequest(AndroidDevice.getInstance(), milestoneEvent, this));
    }

    public void manuallyConfirmArrival(Context activityContext, ServiceOrderNavigationStep step, ManualConfirmResponse confirmResponse) {
        this.confirmResponse = confirmResponse;

        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);

        builder.setMessage("I have arrived at the destination, but GPS is not working");
        builder.setTitle("Confirm Arrival");
        builder.setPositiveButton("OK", new YesClick(step));
        builder.setNegativeButton("Cancel", new NoClick());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void close(){
        Timber.tag(TAG).d("close");
    }


    /// UseCaseFinishStepRequest.Response
    public void finishCurrentStepComplete(){
        Timber.tag(TAG).d("finishCurrentStepComplete");
        stepResponse.stepFinished();
    }


    private class YesClick implements DialogInterface.OnClickListener {
        private ServiceOrderNavigationStep step;

        public YesClick(ServiceOrderNavigationStep step){
            this.step = step;
        }

        public void onClick(DialogInterface dialog, int id) {
            // User clicked OK button
            Timber.tag(TAG).d("user clicked yes");
            confirmResponse.manualConfirmYes();
        }
    }

    private class NoClick implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            // User clicked No button
            Timber.tag(TAG).d("user clicked no");
            confirmResponse.manualConfirmNo();
        }
    }

    ///
    /// UseCaseGetDriverAndActiveBatchCurrentStep response interface
    ///
    public void useCaseGetDriverAndActiveBatchCurrentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepSuccess");
        response.gotDriverAndStep(driver, batchDetail, serviceOrder, (ServiceOrderNavigationStep) orderStep);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly(Driver driver){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly");
        response.gotDriverButNoStep(driver);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep(){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep");
        response.gotNoDriver();
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch");
        response.gotStepMismatch(driver, foundTaskType);
    }

    public interface GetDriverAndActiveBatchStepResponse {
        void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderNavigationStep orderStep);

        void gotNoDriver();

        void gotDriverButNoStep(Driver driver);

        void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType);
    }

    public interface ManualConfirmResponse{
        void manualConfirmYes();

        void manualConfirmNo();
    }


    public interface StepFinishedResponse {
        void stepFinished();
    }

}
