/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 8/12/2018
 * Project : Driver
 */
public class UserTriggerLayoutComponents implements
    SlideView.OnSlideCompleteListener {
    private static final String TAG="UserTriggerLayoutComponents";

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private StepDetailSwipeCompleteButtonComponent stepComplete;
    private LottieAnimationView waitingAnimation;
    private TextView displayMessage;
    private Response response;

    private ServiceOrderUserTriggerStep orderStep;

    public UserTriggerLayoutComponents(AppCompatActivity activity, Response response){
        this.response =response;

        stepTitle = new StepDetailTitleLayoutComponents(activity);
        stepDueBy = new StepDetailDueByLayoutComponents(activity);
        stepComplete = new StepDetailSwipeCompleteButtonComponent(activity, activity.getResources().getString(R.string.user_trigger_step_completed_step_button_caption), this);

        waitingAnimation = (LottieAnimationView) activity.findViewById(R.id.user_trigger_waiting_animation);

        displayMessage = (TextView) activity.findViewById(R.id.user_trigger_display_message);

        Timber.tag(TAG).d("created");
    }

    public void setValues(AppCompatActivity activity, ServiceOrderUserTriggerStep orderStep){
        Timber.tag(TAG).d("setValues START...");

        this.orderStep = orderStep;
        stepTitle.setValues(activity, orderStep);
        stepDueBy.setValues(activity, orderStep);
        displayMessage.setText(orderStep.getDisplayMessage());
        Timber.tag(TAG).d("...setValues COMPLETE");
    }

    public void showWaitingForTriggerAnimation(){
        if (orderStep != null){
            stepTitle.setVisible();
            stepDueBy.setVisible();
            stepComplete.setVisible();
            displayMessage.setVisibility(View.VISIBLE);

            waitingAnimation.setVisibility(View.VISIBLE);
            waitingAnimation.setProgress(0);
            waitingAnimation.playAnimation();
        } else {
            setInvisible();
        }

    }

    public void showStepCompletingAnimation(AppCompatActivity activity){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        waitingAnimation.setVisibility(View.INVISIBLE);
        displayMessage.setVisibility(View.INVISIBLE);
        stepComplete.showWaitingAnimationAndBanner(activity.getResources().getString(R.string.user_trigger_step_completed_banner_text));
    }

    public void setInvisible(){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        stepComplete.setInvisible();
        displayMessage.setVisibility(View.INVISIBLE);
        waitingAnimation.setVisibility(View.INVISIBLE);
    }

    public void close(){
        response = null;
        stepTitle = null;
        stepDueBy = null;
        stepComplete = null;
        waitingAnimation = null;
        displayMessage = null;
        orderStep = null;
    }

    ///
    /// STEP COMPLETE interface
    ///
    public void onSlideComplete(SlideView v){
        Timber.tag(TAG).d("onSlideComplete");
        response.stepCompleteButtonSwiped(orderStep.getMilestoneWhenFinished());
    }

    public interface Response {
        void stepCompleteButtonSwiped(String milestoneEvent);
    }

}
