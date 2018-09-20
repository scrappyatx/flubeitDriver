/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents.NavigationLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
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

        setContentView(R.layout.activity_navigation_step);

        controller = new NavigationController(this);
        layoutComponents = new NavigationLayoutComponents(this, savedInstanceState, "I've arrived", this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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

        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d( "onPause");
        super.onPause();
        layoutComponents.onPause();
        DrawerMenu.getInstance().clearActivity();

        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onStop(){
        super.onStop();
        layoutComponents.onStop();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        layoutComponents.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        layoutComponents.onLowMemory();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.close();
        layoutComponents.onDestroy();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    ///layout component interface
    public void navigateButtonClicked(){
        //start navigation button clicked
        Timber.tag(TAG).d("clicked start navigation");
        MapUtilities.startNavigation(this, orderStep.getDestination().getTargetLatLon());
    }



    public void stepCompleteClicked(String milestoneWhenFinished){
        Timber.tag(TAG).d("stepCompleteClicked (%s)", activityGuid);
        layoutComponents.showFinishingAnimation();
        controller.stepFinishedRequest(milestoneWhenFinished, this);
    }

    //// overflow menu
    public void clickOverflowMenuUserHasArrived(MenuItem item){
        Timber.tag(TAG).d("clicked i've arrived in overflow menu");
        if (orderStep != null) {
            controller.manuallyConfirmArrival(this, orderStep, this);
        }
    }

    public void clickConfirmManualUserHasArrivedButton(View v){
        Timber.tag(TAG).d("clicked the OK button for manual arrival");
    }

    //// NavigationController.ManualConfirmResponse interface
    public void manualConfirmYes(){
        Timber.tag(TAG).d("manualConfirmYes");
        if (orderStep != null) {
            layoutComponents.showFinishingAnimation();
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), this);
        }
    }

    public void manualConfirmNo(){
        Timber.tag(TAG).d("manualConfirmNo");
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
        Timber.tag(TAG).d("stepFinished");
        //go to the next step
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }


}
