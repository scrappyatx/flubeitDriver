/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;

import com.mapbox.services.android.navigation.ui.v5.instruction.InstructionView;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Position;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowCompletedBatchAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created on 10/15/2017
 * Project : Driver
 */

public class NavigationActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden {

    private static final String TAG = "NavigationActivity";

    private ActivityNavigator navigator;
    private NavigationController controller;
    private DrawerMenu drawer;

    //step detail title viewgroup
    private TextView stepSequence;
    private TextView stepTitle;
    private TextView stepDescription;
    private TextView stepWorkStage;

    //step detail due by viewgroup
    private TextView stepWorkTiming;
    private TextView stepDueByCaption;
    private TextView stepDueByValue;

    //navigation detail address viewgroup
    private TextView stepDestinationCaption;
    private TextView stepDestinationAddress;

    //buttons
    private Button calcRouteButton;
    private Button userHasArrivedButton;
    private Button startNavigationButton;

    private DateFormat dateFormat;

    private MapView mapView;
    private MapboxMap map;

    private LocationLayerPlugin locationPlugin;

    private Position originPosition;
    private Position destinationPosition;
    private DirectionsRoute currentRoute;

    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;
    private Location originLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_step);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //step detail title viewgroup
        stepSequence = (TextView) findViewById(R.id.step_sequence);
        stepTitle = (TextView) findViewById(R.id.step_title);
        stepDescription = (TextView) findViewById(R.id.step_description);
        stepWorkStage = (TextView) findViewById(R.id.step_workStage);

        //step due by viewgroup
        stepWorkTiming = (TextView) findViewById(R.id.step_detail_workTiming);
        stepDueByCaption = (TextView) findViewById(R.id.step_detail_complete_by_caption);;
        stepDueByValue = (TextView) findViewById(R.id.step_detail_complete_by_value);;

        //navigation detail address viewgroup
        stepDestinationCaption = (TextView) findViewById(R.id.nav_detail_destination_caption);
        stepDestinationAddress= (TextView) findViewById(R.id.nav_detail_destination_address);

        //buttons
        calcRouteButton = (Button) findViewById(R.id.calculate_route_button);
        userHasArrivedButton = (Button) findViewById(R.id.user_has_arrived_button);
        startNavigationButton = (Button) findViewById(R.id.start_navigation_button);

        dateFormat = new SimpleDateFormat("h:mm aa", Locale.US);

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


    private void updateStepTitleViewGroup(){
        Timber.tag(TAG).d("updating stepTitleViewgroup...");
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            ServiceOrderNavigationStep step = AndroidDevice.getInstance().getActiveBatch().getNavigationStep();

            stepSequence.setText(step.getSequence().toString());
            stepSequence.setVisibility(View.VISIBLE);

            stepTitle.setText(step.getTitle());
            stepTitle.setVisibility(View.VISIBLE);

            stepDescription.setText(step.getDescription());
            stepDescription.setVisibility(View.VISIBLE);

            stepWorkStage.setText(step.getWorkStageIconTextMap().get(step.getWorkStage().toString()));
            stepWorkStage.setTextColor(getResources().getColor(R.color.colorStepStageActive));
            stepWorkStage.setVisibility(View.VISIBLE);

        } else {
            stepSequence.setVisibility(View.INVISIBLE);
            stepTitle.setVisibility(View.INVISIBLE);
            stepDescription.setVisibility(View.INVISIBLE);
            stepWorkStage.setVisibility(View.INVISIBLE);
            Timber.tag(TAG).d("...can't update stepTitleViewgroup, no active batch");
        }
    }

    private void updateStepDueByViewGroup(){
        Timber.tag(TAG).d("updating stepDueByViewgroup...");
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {

            ServiceOrderNavigationStep step = AndroidDevice.getInstance().getActiveBatch().getNavigationStep();

            stepWorkTiming.setText(step.getWorkTimingIconTextMap().get(step.getWorkTiming().toString()));
            switch (step.getWorkTiming()){
                case ON_TIME:
                    stepWorkTiming.setTextColor(getResources().getColor(R.color.colorStepTimingOnTime));
                    break;
                case LATE:
                    stepWorkTiming.setTextColor(getResources().getColor(R.color.colorStepTimingLate));
                    break;
                case VERY_LATE:
                    stepWorkTiming.setTextColor(getResources().getColor(R.color.colorStepTimingVeryLate));
                    break;
                default:
                    stepWorkTiming.setTextColor(getResources().getColor(R.color.colorStepTimingOnTime));
                    break;
            }
            stepWorkTiming.setTextColor(getResources().getColor(R.color.colorStepTimingOnTime));
            stepWorkTiming.setVisibility(View.VISIBLE);

            stepDueByCaption.setVisibility(View.VISIBLE);

            String dueTime = dateFormat.format(step.getFinishTime().getScheduledTime());
            Timber.tag(TAG).d("---> Formatted due by   : " + dueTime);

            stepDueByValue.setText(dueTime);
            stepDueByValue.setVisibility(View.VISIBLE);

        } else {
            stepWorkTiming.setVisibility(View.INVISIBLE);
            stepDueByCaption.setVisibility(View.INVISIBLE);
            stepDueByValue.setVisibility(View.INVISIBLE);
            Timber.tag(TAG).d("...can't update stepDueByViewgroup, no active batch");
        }
    }

    private void updateDestinationViewGroup(){
        Timber.tag(TAG).d("updating destinationViewgroup...");
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {

            ServiceOrderNavigationStep step = AndroidDevice.getInstance().getActiveBatch().getNavigationStep();
            stepDestinationCaption.setVisibility(View.VISIBLE);

            AddressLocation destAddress = step.getDestination().getTargetAddress();

            String addressText;
            if (destAddress.getStreet2() == null) {
                addressText = destAddress.getStreet1() + System.getProperty("line.separator")
                        + destAddress.getCity() + ", " + destAddress.getState() + " " + destAddress.getZip();
            } else {
                addressText = destAddress.getStreet1() + System.getProperty("line.separator")
                        + destAddress.getStreet2() + System.getProperty("line.separator")
                        + destAddress.getCity() + ", " + destAddress.getState() + " " + destAddress.getZip();
            }

            stepDestinationAddress.setText(addressText);
            stepDestinationAddress.setVisibility(View.VISIBLE);

        } else {

            stepDestinationCaption.setVisibility(View.INVISIBLE);
            stepDestinationAddress.setVisibility(View.INVISIBLE);

            Timber.tag(TAG).d("...can't update destinationViewgroup, no active batch");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.navigation_step_activity_title);
        controller = new NavigationController(this, Mapbox.getAccessToken());

        EventBus.getDefault().register(this);

        updateStepTitleViewGroup();
        updateStepDueByViewGroup();
        updateDestinationViewGroup();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();
        mapView.onPause();

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
    }

    @Override
    public void onStop(){
        super.onStop();
        mapView.onStop();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();




        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

    public void clickStartNavigationButton(View v){
        //start navigation button clicked
        Timber.tag(TAG).d("clicked start navigation");
    }

    public void clickCalculateRouteButton(View v){
        Timber.tag(TAG).d("clicked calculate route");
    }

    public void clickUserHasArrivedButton(View v){
        Timber.tag(TAG).d("cliced I've arrived");
        controller.finishStep();
    }

    public void clickOverflowMenuUserHasArrived(MenuItem item){
        Timber.tag(TAG).d("clicked i've arrived in overflow menu");
    }

    public void clickConfirmManualUserHasArrivedButton(View v){
        Timber.tag(TAG).d("clicked the OK button for manual arrival");
    }


    public void onMapReady(MapboxMap mapboxMap) {
        // Customize map with markers, polylines, etc.
        Timber.tag(TAG).d("adding markers to map...");

        if (AndroidDevice.getInstance().getLocationTelemetry().hasLastGoodPosition()) {
            LatLonLocation devicePosition = AndroidDevice.getInstance().getLocationTelemetry().getLastGoodPosition();
            Timber.tag(TAG).d("...adding device location marker");

            MarkerViewOptions markerViewOptions = new MarkerViewOptions()
                    .position(new LatLng(devicePosition.getLatitude(),devicePosition.getLongitude()))
                    .title("Me")
                    .snippet("Device Position");

            mapboxMap.addMarker(markerViewOptions);
        } else {
            Timber.tag(TAG).d("...no device location");
        }

        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            if (AndroidDevice.getInstance().getActiveBatch().getTaskType() == OrderStepInterface.TaskType.NAVIGATION) {

                ServiceOrderNavigationStep step = AndroidDevice.getInstance().getActiveBatch().getNavigationStep();

                Timber.tag(TAG).d("...adding destination location marker");

                MarkerViewOptions markerViewOptions = new MarkerViewOptions()
                        .position(new LatLng(step.getDestination().getTargetLatLon().getLatitude(), step.getDestination().getTargetLatLon().getLongitude()))
                        .title("destination")
                        .snippet(step.getDestination().getTargetAddress().getStreet1());

                mapboxMap.addMarker(markerViewOptions);

                Timber.tag(TAG).d("...animating camera");
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(step.getDestination().getTargetLatLon().getLatitude(), step.getDestination().getTargetLatLon().getLongitude()))
                        .zoom(17)
                        .bearing(180)
                        .tilt(0)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 7000);
            } else {
                Timber.tag(TAG).d("...no destination -> current step is not Navigation step");
            }

        } else {
            Timber.tag(TAG).d("...no destination -> no active batch");
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("last know device position updated");
        //devicePosition = new LatLonLocation();
        //devicePosition.setLatitude(event.getPosition().getLatitude());
        //devicePosition.setLongitude(event.getPosition().getLongitude());
        //hasDevicePosition = true;
        //mapView.getMapAsync(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowCompletedServiceOrderAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("active batch -> service order completed!");

        ActiveBatchAlerts alert = new ActiveBatchAlerts();
        alert.showServiceOrderCompletedAlert(this, this);
    }

    public void serviceOrderCompletedAlertHidden() {
        Timber.tag(TAG).d("service order completed alert hidden");
    }

}
