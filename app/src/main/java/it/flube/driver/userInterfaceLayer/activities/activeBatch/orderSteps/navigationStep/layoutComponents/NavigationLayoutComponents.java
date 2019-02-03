/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.MapUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import timber.log.Timber;

/**
 * Created on 9/19/2018
 * Project : Driver
 */
public class NavigationLayoutComponents implements
    View.OnClickListener,
    StepDetailSwipeCompleteButtonComponent.Response,
    MapboxLayoutComponent.Response {

    private static final String TAG="NavigationLayoutComponents";

    private String activityGuid;

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private MapboxLayoutComponent map;
    private DestinationAddressLayoutComponent address;
    private Button navigateButton;
    private StepDetailSwipeCompleteButtonComponent stepComplete;

    private ServiceOrderNavigationStep orderStep;
    private Response response;
    private Boolean closeEnough;
    private Boolean shouldBeVisible;

    public NavigationLayoutComponents(AppCompatActivity activity, Bundle savedInstanceState, String activityGuid, String stepCompleteButtonCaption, Response response){
        this.response = response;
        this.activityGuid = activityGuid;

        stepTitle = new StepDetailTitleLayoutComponents(activity);
        stepDueBy = new StepDetailDueByLayoutComponents(activity);
        address = new DestinationAddressLayoutComponent(activity);

        map = new MapboxLayoutComponent(activity, savedInstanceState, activityGuid, this);

        navigateButton = (Button) activity.findViewById(R.id.start_navigation_button);
        navigateButton.setOnClickListener(this);

        stepComplete = new StepDetailSwipeCompleteButtonComponent(activity, stepCompleteButtonCaption, this);

        closeEnough = false;
        shouldBeVisible = false;

        setInvisible();
    }

    public void setValues(AppCompatActivity activity, ServiceOrderNavigationStep orderStep){
        this.orderStep = orderStep;

        stepTitle.setValues(activity, orderStep);
        stepDueBy.setValues(activity, orderStep);
        address.setValues(orderStep.getDestination().getTargetAddress());

        map.setDestination(activity, orderStep.getDestination(), orderStep.getCloseEnoughInFeet());
        Timber.tag(TAG).d("setValues");
    }

    public void setVisible(AppCompatActivity activity){
        Timber.tag(TAG).d("setVisible");
        if (orderStep != null) {
            stepTitle.setVisible();
            stepDueBy.setVisible();
            address.setVisible();
            map.setVisible();

            if (MapUtilities.isNavigationPackageAvailable(activity)) {
                Timber.tag(TAG).d("...navigation package available");
                navigateButton.setVisibility(View.VISIBLE);
            } else {
                Timber.tag(TAG).d("...no navigation package available");
                navigateButton.setVisibility(View.INVISIBLE);
            }

            shouldBeVisible =true;
            setButtonState();
        } else {
            setInvisible();
            shouldBeVisible = false;
        }
    }

    public void showFinishingAnimation(){
        Timber.tag(TAG).d("showFinishingAnimation");
        map.setInvisible();
        address.setInvisible();
        navigateButton.setVisibility(View.INVISIBLE);
        stepComplete.showWaitingAnimatingWithNoBanner();
    }

    public void setInvisible(){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        address.setInvisible();
        map.setInvisible();
        navigateButton.setVisibility(View.INVISIBLE);
        stepComplete.setInvisible();
        shouldBeVisible=  false;
        Timber.tag(TAG).d("setInvisible");

    }

    public void setGone(){
        stepTitle.setGone();
        stepDueBy.setGone();
        address.setGone();
        map.setGone();
        navigateButton.setVisibility(View.GONE);
        stepComplete.setGone();
        shouldBeVisible=  false;
        Timber.tag(TAG).d("setGone");
    }

    public void setButtonState(){
        Timber.tag(TAG).d("setButtonState");
        if (shouldBeVisible){
            if (closeEnough){
                navigateButton.setVisibility(View.INVISIBLE);
                stepComplete.setVisible();
            } else {
                navigateButton.setVisibility(View.VISIBLE);
                stepComplete.setInvisible();
            }
        }
    }

    ////states to pass through to mapbox
    public void onStart() {
        map.onStart();
        Timber.tag(TAG).d("onStart");
    }

    public void onPause() {
        map.onPause();
        Timber.tag(TAG).d("onPause");
    }

    public void onResume() {
        map.onResume();
        Timber.tag(TAG).d("onResume");
    }

    public void onStop(){
        map.onStop();
        Timber.tag(TAG).d("onStop");
    }

    public void onLowMemory() {
        map.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    public void onSaveInstanceState(Bundle outState) {
        map.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onDestroy() {
        stepTitle.close();
        stepDueBy.close();
        stepComplete.close();
        address.close();
        map.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

    ///mapbox interface
    public void driverIsCloseEnough(){
        Timber.tag(TAG).d("driverIsCloseEnough");
        this.closeEnough = true;
        setButtonState();
    }

    public void driverIsNotCloseEnough(){
        Timber.tag(TAG).d("driverIsNotCloseEnough");
        this.closeEnough = false;
        setButtonState();
    }


    /// button click interface
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.navigateButtonClicked();
    }

    /// StepDetailSwipeCompleteButtonComponent.Response interface
    public void stepDetailSwipeCompleteButtonClicked(){
        Timber.tag(TAG).d("stepDetailSwipeCompleteButtonClicked");
        response.stepCompleteClicked(orderStep.getMilestoneWhenFinished());
    }


    public interface Response {
        void navigateButtonClicked();

        void stepCompleteClicked(String milestoneWhenFinished);
    }

}
