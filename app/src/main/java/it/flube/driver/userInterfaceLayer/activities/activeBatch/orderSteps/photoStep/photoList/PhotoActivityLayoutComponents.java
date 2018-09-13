/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import timber.log.Timber;

/**
 * Created on 9/11/2018
 * Project : Driver
 */
public class PhotoActivityLayoutComponents
        implements
            PhotoRequestListLayoutComponents.Response,
            StepDetailSwipeCompleteButtonComponent.Response {
    public static final String TAG="PhotoActivityLayoutComponents";

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private PhotoRequestListLayoutComponents photoList;
    private StepDetailSwipeCompleteButtonComponent stepComplete;

    private ServiceOrderPhotoStep orderStep;
    private Response response;

    public PhotoActivityLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;

        stepTitle = new StepDetailTitleLayoutComponents(activity);
        stepDueBy = new StepDetailDueByLayoutComponents(activity);
        photoList = new PhotoRequestListLayoutComponents(activity, this);
        stepComplete = new StepDetailSwipeCompleteButtonComponent(activity, activity.getResources().getString(R.string.photo_step_completed_step_button_caption), this);

        setInvisible();
        Timber.tag(TAG).d("created");
    }

    public void setValues(AppCompatActivity activity, ServiceOrderPhotoStep orderStep){
        this.orderStep = orderStep;
        stepTitle.setValues(activity, orderStep);
        stepDueBy.setValues(activity, orderStep);
        photoList.setValues(orderStep.getPhotoRequestList());
        Timber.tag(TAG).d("setValues");
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible");
        if (orderStep != null){
            Timber.tag(TAG).d("   ...we have an orderStep");
            stepTitle.setVisible();
            stepDueBy.setVisible();
            photoList.setVisible();

            //check if we are ok to finish
            if (photoList.readyToFinishStep()) {
                Timber.tag(TAG).d("   ...readyToFinishStep");
                stepComplete.setVisible();
            } else {
                Timber.tag(TAG).d("   ...NOT readyToFinishStep");
                stepComplete.setInvisible();
            }
        } else {
            Timber.tag(TAG).d("   ...orderStep is null");
            setInvisible();
        }
    }

    public void setInvisible(){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        photoList.setInvisible();
        stepComplete.setInvisible();
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        stepTitle.setGone();
        stepDueBy.setGone();
        photoList.setGone();
        stepComplete.setGone();
        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        stepTitle.close();
        stepDueBy.close();
        photoList.close();
        stepComplete.close();
        Timber.tag(TAG).d("close");
    }

    public void showWaitingAnimationAndBanner(String bannerText){
        Timber.tag(TAG).d("showWaitingAnimationAndBanner");
        photoList.setGone();
        stepComplete.showWaitingAnimationAndBanner(bannerText);
    }



    ///photoRequestListLayoutComponents interface
    public void photoRequestSelected(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoRequestSelected");
        response.photoRequestSelected(photoRequest);
    }

    ////stepDetailSwipeComplete interface
    public void stepDetailSwipeCompleteButtonClicked(){
        Timber.tag(TAG).d("stepDetailSwipeCompleteButtonClicked");
        response.stepCompleteButtonClicked(photoList.getPhotoRequestList(), orderStep.getMilestoneWhenFinished());
    }

    public interface Response {
        void photoRequestSelected(PhotoRequest photoRequest);

        void stepCompleteButtonClicked(ArrayList<PhotoRequest> photoList, String milestoneWhenFinished);
    }
}
