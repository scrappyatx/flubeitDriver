/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail.ReceiptDetailButtonLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 9/11/2018
 * Project : Driver
 */
public class PhotoDetailLayoutComponents implements
        ReceiptDetailButtonLayoutComponents.Response {
    private static final String TAG = "PhotoDetailLayoutComponents";

    private PhotoRequestDetailLayoutComponents requestDetail;
    private ReceiptDetailButtonLayoutComponents stepComplete;
    private String analyzingImageBannerText;
    private Response response;

    public PhotoDetailLayoutComponents(AppCompatActivity activity, Response response) {
        this.response = response;

        requestDetail = new PhotoRequestDetailLayoutComponents(activity);
        stepComplete = new ReceiptDetailButtonLayoutComponents(activity,  this);

        analyzingImageBannerText = activity.getResources().getString(R.string.photo_detail_keep_photo_button_banner);

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
        analyzingImageBannerText = null;
        Timber.tag(TAG).d("close");
    }


    /// response interface StepDetailCompleteButtonComponents
    public void receiptDetailRetakeButtonClicked(){
        Timber.tag(TAG).d("receiptDetailRetakeButtonClicked");
        stepComplete.showWaitingAnimationWithNoBanner();
        response.takePhotoButtonClicked(requestDetail.getPhotoRequest().getBatchGuid(), requestDetail.getPhotoRequest().getStepGuid(), requestDetail.getPhotoRequest().getGuid());
    }

    public void receiptDetailKeepButtonClicked(){
        Timber.tag(TAG).d("receiptDetailKeepButtonClicked");
        stepComplete.showWaitingAnimationAndBanner(analyzingImageBannerText);
        response.keepPhotoButtonClicked(requestDetail.getPhotoRequest());
    }

    public interface Response {
        void takePhotoButtonClicked(String batchGuid, String stepGuid, String photoRequestGuid);

        void keepPhotoButtonClicked(PhotoRequest photoRequest);
    }
}
