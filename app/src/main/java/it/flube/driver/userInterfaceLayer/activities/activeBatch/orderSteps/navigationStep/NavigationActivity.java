/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.mapbox.mapboxsdk.Mapbox;


import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.NavigationLayoutComponents;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/15/2017
 * Project : Driver
 */

public class NavigationActivity extends AppCompatActivity implements
        NavigationLayoutComponents.Response,
        NavigationController.GetDriverAndActiveBatchStepResponse,
        NavigationController.StepFinishedResponse,
        NavigationController.ManualConfirmResponse{

    private static final String TAG = "NavigationActivity";

    private String activityGuid;

    private NavigationController controller;
    private NavigationLayoutComponents layoutComponents;
    private ServiceOrderNavigationStep orderStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);

        setContentView(R.layout.activity_navigation_step);

        controller = new NavigationController(this);
        layoutComponents = new NavigationLayoutComponents(this, savedInstanceState, activityGuid,"I've arrived", this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Timber.tag(TAG).d("onCreateOptionsMenu (%s)", activityGuid);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_step_overflow_menu, menu);

        menu.findItem(R.id.navigation_step_overflow_menu_user_has_arrived).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_question_circle)
                        .colorRes(R.color.colorTextPrimaryLight)
                        .actionBarSize());

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        layoutComponents.onStart();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutComponents.onResume();
        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.navigation_step_activity_title);
        controller.getDriverAndActiveBatchStep(this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        super.onPause();
        layoutComponents.onPause();
        DrawerMenu.getInstance().clearActivity();

        Timber.tag(TAG).d("onPause (%s)", activityGuid);
    }

    @Override
    public void onStop(){
        super.onStop();
        layoutComponents.onStop();
        Timber.tag(TAG).d("onStop (%s)", activityGuid);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        layoutComponents.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState (%s)", activityGuid);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
        Timber.tag(TAG).d("onRestoreInstanceState (%s)", activityGuid);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        layoutComponents.onLowMemory();
        Timber.tag(TAG).d("onLowMemory (%s)", activityGuid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.close();
        layoutComponents.onDestroy();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }

    ///layout component interface
    public void navigateButtonClicked(){
        //start navigation button clicked
        Timber.tag(TAG).d("navigateButtonClicked (%s)", activityGuid);
        MapUtilities.startNavigation(this, orderStep.getDestination().getTargetLatLon());
    }



    public void stepCompleteClicked(String milestoneWhenFinished){
        Timber.tag(TAG).d("stepCompleteClicked (%s)", activityGuid);
        layoutComponents.showFinishingAnimation();
        controller.stepFinishedRequest(milestoneWhenFinished, this);
    }

    //// overflow menu
    public void clickOverflowMenuUserHasArrived(MenuItem item){
        Timber.tag(TAG).d("clickOverflowMenuUserHasArrived (%s)", activityGuid);
        if (orderStep != null) {
            controller.manuallyConfirmArrival(this, orderStep, this);
        }
    }

    public void clickConfirmManualUserHasArrivedButton(View v){
        Timber.tag(TAG).d("clickConfirmManualUserHasArrivedButton (%s)", activityGuid);
    }

    //// NavigationController.ManualConfirmResponse interface
    public void manualConfirmYes(){
        Timber.tag(TAG).d("manualConfirmYes (%s)", activityGuid);
        if (orderStep != null) {
            layoutComponents.showFinishingAnimation();
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), this);
        }
    }

    public void manualConfirmNo(){
        Timber.tag(TAG).d("manualConfirmNo (%s)", activityGuid);
    }

    ///
    ///  NavigationController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderNavigationStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep (%s)", activityGuid);
        this.orderStep = orderStep;
        layoutComponents.setValues(this,orderStep);
        layoutComponents.setVisible(this);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver (%s)", activityGuid);
        this.orderStep = null;
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep (%s)", activityGuid);
        this.orderStep = null;
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch (%s), taskType -> " + taskType.toString(), activityGuid);
        this.orderStep = null;
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    ////
    //// StepFinsished interface
    ////
    public void stepFinished(){
        Timber.tag(TAG).d("stepFinished (%s)", activityGuid);
        //go to the next step
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }


}
