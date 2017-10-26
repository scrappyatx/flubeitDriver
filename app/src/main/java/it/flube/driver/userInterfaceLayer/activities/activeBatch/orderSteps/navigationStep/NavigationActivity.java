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

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created on 10/15/2017
 * Project : Driver
 */

public class NavigationActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final String TAG = "NavigationActivity";

    private ActivityNavigator navigator;
    private NavigationController controller;
    private DrawerMenu drawer;

    private ImageView stepCountImage;
    private TextView stepTitle;
    private TextView dueByText;
    private TextView destinationAddressText;

    private Button startNavigationButton;

    private MapView mapView;
    private InstructionView instructionView;
    private MapboxMap map;

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private ServiceOrderNavigationStep step;
    private Boolean hasActiveBatch;

    private LatLonLocation devicePosition;
    private Boolean hasDevicePosition;

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

        //instructionView = (InstructionView) findViewById(R.id.instructionView);

        stepCountImage = (ImageView) findViewById(R.id.step_count_icon);
        stepTitle = (TextView) findViewById(R.id.step_title);
        destinationAddressText = (TextView) findViewById(R.id.nav_detail_destination_address);

        dueByText = (TextView) findViewById(R.id.due_by_text);
        dueByText.setVisibility(VISIBLE);

        startNavigationButton = (Button) findViewById(R.id.start_navigation_button);

        hasActiveBatch = false;
        hasDevicePosition = false;

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.navigation_step_activity_title);
        controller = new NavigationController(this, Mapbox.getAccessToken());


        EventBus.getDefault().register(this);

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



    private void updateActiveBatchInfo(){
        ActiveBatchInterface activeBatch = AndroidDevice.getInstance().getActiveBatch();
        Timber.tag(TAG).d("   updating active batch info...");

        if (activeBatch.hasActiveBatch()){
            Timber.tag(TAG).d("      ...hasActiveBatch = TRUE");
            if (activeBatch.getTaskType() == OrderStepInterface.TaskType.NAVIGATION) {
                Timber.tag(TAG).d("      ...taskType = NAVIGATION");
                hasActiveBatch = true;
                batchDetail = activeBatch.getBatchDetail();
                serviceOrder = activeBatch.getServiceOrder();
                step = activeBatch.getNavigationStep();
            } else {
                Timber.tag(TAG).d("      ...taskType not NAVIGATION");
                hasActiveBatch = false;
                batchDetail = null;
                serviceOrder = null;
                step = null;
            }
        } else {
            Timber.tag(TAG).d("      ...hasActiveBatch = FALSE");
            hasActiveBatch = false;
            batchDetail = null;
            serviceOrder = null;
            step = null;
        }
    }

    private void updateScreenInfo(){
        if (hasActiveBatch){

            //TODO put "sequence" property into step interface, so that each step knows what sequence it is
            Picasso.with(this)
                    .load(UserInterfaceUtilities.getCountIconUrl(1))
                    .into(stepCountImage);
            stepCountImage.setVisibility(VISIBLE);

            stepTitle.setText(step.getTitle());
            stepTitle.setVisibility(VISIBLE);

            String dueByCaption = "Arrive on or before : " + step.getFinishTime().getScheduledTime().toString();
            dueByText.setText(dueByCaption);
            dueByText.setVisibility(VISIBLE);

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
            destinationAddressText.setText(addressText);
            destinationAddressText.setVisibility(VISIBLE);

        } else {
            stepCountImage.setVisibility(INVISIBLE);
            stepTitle.setVisibility(INVISIBLE);
            dueByText.setVisibility(INVISIBLE);
            destinationAddressText.setVisibility(INVISIBLE);
        }

        if (!hasDevicePosition){
            hasDevicePosition = true;
            devicePosition = new LatLonLocation();
            //2001 summercrest cove, round rock tx 78681
            // lat lon = (30.545792, -97.757828)
            devicePosition.setLatitude(30.545792);
            devicePosition.setLongitude(-97.757828);
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
        updateActiveBatchInfo();
        updateScreenInfo();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();
        mapView.onPause();
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


        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();

        mapView.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

    public void clickStartNavigationButton(View v){
        //start navigation button clicked
        Timber.tag(TAG).d("clicked start navigation");
        if (hasDevicePosition) {

            String awsPool = null;

            Boolean simulateRoute = true;

            controller.startNavigation(this, devicePosition, step.getDestination().getTargetLatLon());
        } else {
            Timber.tag(TAG).d("   ...no device position");
        }


    }

    public void clickCalculateRouteButton(View v){
        Timber.tag(TAG).d("clicked calculate route");
    }

    public void clickUserHasArrivedButton(View v){
        Timber.tag(TAG).d("cliced I've arrived");
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

        if (hasDevicePosition) {
            Timber.tag(TAG).d("...adding device location marker");

            MarkerViewOptions markerViewOptions = new MarkerViewOptions()
                    .position(new LatLng(devicePosition.getLatitude(),devicePosition.getLongitude()))
                    .title("Me")
                    .snippet("Device Position");

            mapboxMap.addMarker(markerViewOptions);
        } else {
            Timber.tag(TAG).d("...no device location");
        }

        if (hasActiveBatch) {
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
                    .tilt(30)
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position),7000);

        } else {
            Timber.tag(TAG).d("...no destination");
        }



    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("last know device position updated");
        devicePosition = new LatLonLocation();
        devicePosition.setLatitude(event.getPosition().getLatitude());
        devicePosition.setLongitude(event.getPosition().getLongitude());
        hasDevicePosition = true;
        //mapView.getMapAsync(this);
    }

}
