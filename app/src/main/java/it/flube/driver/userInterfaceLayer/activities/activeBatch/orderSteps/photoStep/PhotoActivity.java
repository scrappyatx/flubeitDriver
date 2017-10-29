/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary.ServiceOrderListAdapter;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationController;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoActivity extends AppCompatActivity implements
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden {

    private static final String TAG = "PhotoActivity";

    private ActivityNavigator navigator;
    private PhotoController controller;
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

    //step finished button
    private Button stepFinishedButton;


    private RecyclerView photoRequestListView;
    //private ServiceOrderListAdapter orderListAdapter;

    private DateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_step);

        //step detail title viewgroup
        stepSequence = (TextView) findViewById(R.id.step_sequence);
        stepTitle = (TextView) findViewById(R.id.step_title);
        stepDescription = (TextView) findViewById(R.id.step_description);
        stepWorkStage = (TextView) findViewById(R.id.step_workStage);

        //step due by viewgroup
        stepWorkTiming = (TextView) findViewById(R.id.step_detail_workTiming);
        stepDueByCaption = (TextView) findViewById(R.id.step_detail_complete_by_caption);
        stepDueByValue = (TextView) findViewById(R.id.step_detail_complete_by_value);

        //photoRequest recycler view
        photoRequestListView = (RecyclerView) findViewById(R.id.photoRequestListView);
        //buttons
        stepFinishedButton = (Button) findViewById(R.id.step_complete_button);

        dateFormat = new SimpleDateFormat("h:mm aa", Locale.US);

    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.photo_step_activity_title);
        controller = new PhotoController();

        updateStepTitleViewGroup();
        updateStepDueByViewGroup();
        updatePhotoRequestList();
        stepFinishedButton.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();

        drawer.close();
        controller.close();

        EventBus.getDefault().unregister(this);
    }

    private void updateStepTitleViewGroup(){
        Timber.tag(TAG).d("updating stepTitleViewgroup...");
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            ServiceOrderPhotoStep step = AndroidDevice.getInstance().getActiveBatch().getPhotoStep();

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

            ServiceOrderPhotoStep step = AndroidDevice.getInstance().getActiveBatch().getPhotoStep();

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

    private void updatePhotoRequestList(){
        photoRequestListView.setVisibility(View.INVISIBLE);
    }

    public void clickStepCompleteButton(View v){
        Timber.tag(TAG).d("clicked step complete button");
        controller.stepFinished();
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
