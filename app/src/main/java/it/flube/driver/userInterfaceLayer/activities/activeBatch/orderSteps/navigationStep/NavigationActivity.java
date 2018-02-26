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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

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
    private DateFormat dateFormat;

    //navigation detail address viewgroup
    private TextView stepDestinationCaption;
    private TextView stepDestinationAddress;

    //buttons
    private Button calcRouteButton;
    private Button userHasArrivedButton;
    private Button startNavigationButton;

    //map variables
    private MapView mapView;
    private MapboxMap map;

    private Marker userLocationMarker;
    private MarkerOptions userLocationMarkerOptions;
    private Icon userIcon;

    private Marker destinationMarker;
    private MarkerOptions destinationMarkerOptions;


    //the navigation step for this activity
    private ServiceOrderNavigationStep step;
    private LatLonLocation currentUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_step);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        createTitleViewGroupElements();
        createDueByViewGroupElements();
        createDestinationViewGroupElements();
        createButtonElements();
        createMapMarkerOptions();
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
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.navigation_step_activity_title);
        controller = new NavigationController(this, Mapbox.getAccessToken());
        step = getStep();
        currentUserLocation = getCurrentUserLocation();

        EventBus.getDefault().register(this);

        updateStepTitleViewGroup(step);
        updateStepDueByViewGroup(step);
        updateDestinationViewGroup(step);
        updateButtonElements(step);

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
        step = null;
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
        MapUtilities.startNavigation(this, step.getDestination().getTargetLatLon());
    }

    public void clickCalculateRouteButton(View v){
        Timber.tag(TAG).d("clicked calculate route");
    }

    public void clickUserHasArrivedButton(View v){
        Timber.tag(TAG).d("cliced I've arrived");
        String milestoneEvent;

        if (step != null) {
            milestoneEvent = step.getMilestoneWhenFinished();
        } else {
            milestoneEvent = "no milestone";
        }
        controller.finishStep(milestoneEvent);
    }

    public void clickOverflowMenuUserHasArrived(MenuItem item){
        Timber.tag(TAG).d("clicked i've arrived in overflow menu");
        if (step != null) {
            controller.manuallyConfirmArrival(this, step);
        }
    }

    public void clickConfirmManualUserHasArrivedButton(View v){
        Timber.tag(TAG).d("clicked the OK button for manual arrival");
    }

    @SuppressWarnings( {"MissingPermission"})
    public void onMapReady(MapboxMap mapboxMap) {
        Timber.tag(TAG).d("onMapReady START...");
        this.map = mapboxMap;

        // Customize map with markers, polylines, etc.
        Timber.tag(TAG).d("adding markers to map...");

        if (currentUserLocation != null) {
            userLocationMarkerOptions.setPosition(new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude()));
        }

        if (step != null) {
            destinationMarkerOptions.setPosition(new LatLng(step.getDestination().getTargetLatLon().getLatitude(), step.getDestination().getTargetLatLon().getLongitude()));
        }

        if (currentUserLocation != null) {
            if (step != null) {
                // both device & destination position
                updateMapWithUserLocationAndDestination(mapboxMap, currentUserLocation, step.getDestination().getTargetLatLon());
                Timber.tag(TAG).d("   ...both destination and device");
            } else {
                //device position ONLY
                updateMapWithUserLocationOnly(mapboxMap, currentUserLocation);
                Timber.tag(TAG).d("   ...device ONLY");
            }
        } else {
            if (step != null) {
                // destination position only
                updateMapWithDestinationOnly(mapboxMap, step.getDestination().getTargetLatLon());
                Timber.tag(TAG).d("   ...destination ONLY");

            } else {
                // no position at all
                //do nothing
                Timber.tag(TAG).d("   ...no position info at all!");
            }
        }
        Timber.tag(TAG).d("...onMapReady COMPLETE");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("device position update received :");
        Timber.tag(TAG).d("   ... latitude  : " + event.getPosition().getLatitude());
        Timber.tag(TAG).d("   ... longitude : " + event.getPosition().getLongitude());

        currentUserLocation = event.getPosition();

        if (map != null){
            if (userLocationMarker != null) {
                Timber.tag(TAG).d("updating marker on map");
                userLocationMarker.setPosition(new LatLng(event.getPosition().getLatitude(), event.getPosition().getLongitude()));
                map.updateMarker(userLocationMarker);

                if (step != null){
                    Timber.tag(TAG).d("zooming map");
                    Double newZoom = CalculateDistance.getZoomLevel(currentUserLocation, step.getDestination().getTargetLatLon(),map.getMinZoomLevel(), map.getMaxZoomLevel());
                    map.setZoom(newZoom);
                }

            } else {
                Timber.tag(TAG).d("creating marker on map");
                userLocationMarkerOptions.setPosition(new LatLng(event.getPosition().getLatitude(), event.getPosition().getLongitude()));
                userLocationMarker = map.addMarker(userLocationMarkerOptions);
            }
            updateButtonElements(step);
        } else {
            Timber.tag(TAG).d("there is no map");
        }
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

    private void updateMapWithUserLocationAndDestination(MapboxMap mapboxMap, LatLonLocation deviceLocation, LatLonLocation destinationLocation){

        Timber.tag(TAG).d("device location:");
        Timber.tag(TAG).d("    latitude  : " + deviceLocation.getLatitude());
        Timber.tag(TAG).d("    longitude : " + deviceLocation.getLongitude());
        Timber.tag(TAG).d("destination location : ");
        Timber.tag(TAG).d("    latitude  : " + destinationLocation.getLatitude());
        Timber.tag(TAG).d("    longitude : " + destinationLocation.getLongitude());

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()))
                .include(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()))
                .build();

        Timber.tag(TAG).d("LatLngBounds :");
        Timber.tag(TAG).d("    center :");
        Timber.tag(TAG).d("        latitude   -> " + latLngBounds.getCenter().getLatitude());
        Timber.tag(TAG).d("        longitude  -> " + latLngBounds.getCenter().getLongitude());
        Timber.tag(TAG).d("    latitude span  -> " + latLngBounds.getLatitudeSpan());
        Timber.tag(TAG).d("    longitude span -> " + latLngBounds.getLongitudeSpan());
        Timber.tag(TAG).d("    long West  -> " + latLngBounds.getLonWest());
        Timber.tag(TAG).d("    long East  -> " + latLngBounds.getLonEast());
        Timber.tag(TAG).d("    lat North  -> " + latLngBounds.getLatNorth());
        Timber.tag(TAG).d("    lat South  -> " + latLngBounds.getLatSouth());

        //destinationMarker.setPosition(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()));
        //userLocationMarker.setPosition(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()));
        destinationMarkerOptions.setPosition(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()));
        userLocationMarkerOptions.setPosition(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()));

        destinationMarker = mapboxMap.addMarker(destinationMarkerOptions);
        userLocationMarker = mapboxMap.addMarker(userLocationMarkerOptions);

        Double zoomLevel = CalculateDistance.getZoomLevel(deviceLocation, destinationLocation, mapboxMap.getMinZoomLevel(), mapboxMap.getMaxZoomLevel());

        Timber.tag(TAG).d("updating camera position");
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 0);
       // mapboxMap.moveCamera(cameraUpdate);

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()))
                .zoom(zoomLevel)
                .bearing(0)
                .tilt(0)
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

    }

    private void updateMapWithDestinationOnly(MapboxMap mapboxMap, LatLonLocation destinationLocation) {
        destinationMarkerOptions.setPosition(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()));

        destinationMarker = mapboxMap.addMarker(destinationMarkerOptions);



        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()))
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
    }

    private void updateMapWithUserLocationOnly(MapboxMap mapboxMap, LatLonLocation deviceLocation){

        userLocationMarkerOptions.setPosition(new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude()));

        userLocationMarker = mapboxMap.addMarker(userLocationMarkerOptions);

        CameraPosition position = new CameraPosition.Builder()
                .target((new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude())))
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
    }

    @Nullable
    private ServiceOrderNavigationStep getStep(){
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            return AndroidDevice.getInstance().getActiveBatch().getNavigationStep();
        } else {
            return null;
        }
    }

    @Nullable
    private LatLonLocation getCurrentUserLocation(){
        if (AndroidDevice.getInstance().getLocationTelemetry().hasLastGoodPosition()) {
            return AndroidDevice.getInstance().getLocationTelemetry().getLastGoodPosition();
        } else {
            return null;
        }
    }

    public void createTitleViewGroupElements(){
        stepSequence = (TextView) findViewById(R.id.step_sequence);
        stepTitle = (TextView) findViewById(R.id.step_title);
        stepDescription = (TextView) findViewById(R.id.step_description);
        stepWorkStage = (TextView) findViewById(R.id.step_workStage);
    }

    private void updateStepTitleViewGroup(ServiceOrderNavigationStep step){
        Timber.tag(TAG).d("updating stepTitleViewgroup...");
        if (step != null) {

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

    public void createDueByViewGroupElements(){
        stepWorkTiming = (TextView) findViewById(R.id.step_detail_work_timing);
        stepDueByCaption = (TextView) findViewById(R.id.step_detail_complete_by_caption);;
        stepDueByValue = (TextView) findViewById(R.id.step_detail_complete_by_value);
        dateFormat = new SimpleDateFormat("h:mm aa", Locale.US);
    }

    private void updateStepDueByViewGroup(ServiceOrderNavigationStep step){
        Timber.tag(TAG).d("updating stepDueByViewgroup...");
        if (step != null) {

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

    public void createDestinationViewGroupElements(){
        stepDestinationCaption = (TextView) findViewById(R.id.nav_detail_destination_caption);
        stepDestinationAddress= (TextView) findViewById(R.id.nav_detail_destination_address);
    }

    private void updateDestinationViewGroup(ServiceOrderNavigationStep step){
        Timber.tag(TAG).d("updating destinationViewgroup...");
        if (step != null) {
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

    public void createButtonElements(){
        calcRouteButton = (Button) findViewById(R.id.calculate_route_button);
        userHasArrivedButton = (Button) findViewById(R.id.user_has_arrived_button);
        startNavigationButton = (Button) findViewById(R.id.start_navigation_button);
    }


    public void updateButtonElements(ServiceOrderNavigationStep step){
        Timber.tag(TAG).d("updating button elements...");
        if (step != null) {
            Timber.tag(TAG).d("...have a step");
            calcRouteButton.setVisibility(View.INVISIBLE);

            if (currentUserLocation != null) {
                Timber.tag(TAG).d("...have a current location");

                if (CalculateDistance.isCloseEnough(step.getDestination().getTargetLatLon(), currentUserLocation, step.getCloseEnoughInFeet())) {
                    Timber.tag(TAG).d("...we are close enough");
                    userHasArrivedButton.setVisibility(View.VISIBLE);
                    startNavigationButton.setVisibility(View.INVISIBLE);
                } else {
                    Timber.tag(TAG).d("...we are not close enough");
                    userHasArrivedButton.setVisibility(View.INVISIBLE);

                    if (MapUtilities.isNavigationPackageAvailable(this)) {
                        Timber.tag(TAG).d("...navigation package available");
                        startNavigationButton.setVisibility(View.VISIBLE);
                    } else {
                        Timber.tag(TAG).d("...no navigation package available");
                        startNavigationButton.setVisibility(View.INVISIBLE);
                    }

                }

            } else {
                Timber.tag(TAG).d("...don't have a current location");
                userHasArrivedButton.setVisibility(View.INVISIBLE);

                if (MapUtilities.isNavigationPackageAvailable(this)) {
                    Timber.tag(TAG).d("...navigation package available");
                    startNavigationButton.setVisibility(View.VISIBLE);
                } else {
                    Timber.tag(TAG).d("...no navigation package available");
                    startNavigationButton.setVisibility(View.INVISIBLE);
                }

            }
        } else {
            Timber.tag(TAG).d("...don't have a step");
            calcRouteButton.setVisibility(View.INVISIBLE);
            userHasArrivedButton.setVisibility(View.INVISIBLE);
            startNavigationButton.setVisibility(View.INVISIBLE);
        }
    }

    public void createMapMarkerOptions(){
        // scale the icon to use as large icon in notification
        //int height = (int) getResources().getDimension(android.R.dimen.notification_large_icon_height);
        //int width = (int) getResources().getDimension(android.R.dimen.notification_large_icon_width);
        //Bitmap b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapbox_user_icon), width, height, false);
        Timber.tag(TAG).d("createMapMarkerOptions START...");

        userLocationMarkerOptions = new MarkerOptions()
                .title("Me")
                //.icon(IconFactory.getInstance(this).fromBitmap(b))
                .icon(IconFactory.getInstance(this).defaultMarker())
                .snippet("My Location");

        destinationMarkerOptions = new MarkerOptions()
                .title("Destination")
                .icon(IconFactory.getInstance(this).defaultMarker());

        try {
            Drawable d = ContextCompat.getDrawable(this, R.drawable.mapbox_user_icon);
            Bitmap b = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(b);
            d.setBounds(0,0,canvas.getWidth(), canvas.getHeight());
            d.draw(canvas);

            Timber.tag(TAG).d("using custom icon");
            userLocationMarkerOptions.setIcon(IconFactory.getInstance(this).fromBitmap(b));
        } catch (OutOfMemoryError e) {
            Timber.tag(TAG).d("using default icon");
            userLocationMarkerOptions.setIcon(IconFactory.getInstance(this).defaultMarker());
        }
    }

}
