/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 9/11/2018
 * Project : Driver
 */
public class PhotoDetailLayoutComponents implements
    StepDetailCompleteButtonComponents.Response {
    private static final String TAG = "PhotoDetailLayoutComponents";

    private PhotoRequestDetailLayoutComponents requestDetail;
    private StepDetailCompleteButtonComponents stepComplete;
    private Response response;

    public PhotoDetailLayoutComponents(AppCompatActivity activity, Response response) {
        this.response = response;

        requestDetail = new PhotoRequestDetailLayoutComponents(activity);
        stepComplete = new StepDetailCompleteButtonComponents(activity, activity.getResources().getString(R.string.photo_detail_step_complete_button_caption), this);

        setInvisible();
        Timber.tag(TAG).d("created");
    }

    public void setValues(AppCompatActivity activity, PhotoRequest photoRequest) {
        requestDetail.setValues(activity, photoRequest);
        Timber.tag(TAG).d("setValues");
    }

    public void showWaitingAnimation(){
        requestDetail.setGone();
        stepComplete.showWaitingAnimationWithNoBanner();
    }

    public void setVisible() {
        Timber.tag(TAG).d("setVisible");
        if (requestDetail.getPhotoRequest()!=null){
            Timber.tag(TAG).d("  ...have a photoRequest");
            requestDetail.setVisible();
            stepComplete.setVisible();
        } else {
            Timber.tag(TAG).d("  ...photoRequest is null");
            setInvisible();
        }
    }

    public void setInvisible(){
        requestDetail.setInvisible();
        stepComplete.setInvisible();
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone() {
        requestDetail.setGone();
        stepComplete.setGone();
        Timber.tag(TAG).d("setGone");
    }

    public void close() {
        requestDetail.close();
        stepComplete.close();
        Timber.tag(TAG).d("close");
    }

    ///step detail complete interface
    public void stepDetailCompleteButtonClicked(){
        Timber.tag(TAG).d("stepDetailCompleteButtonClicked");
        response.takePhotoButtonClicked(requestDetail.getPhotoRequest().getBatchGuid(), requestDetail.getPhotoRequest().getStepGuid(), requestDetail.getPhotoRequest().getGuid());
    }

    public interface Response {
        void takePhotoButtonClicked(String batchGuid, String stepGuid, String photoRequestGuid);
    }
}
