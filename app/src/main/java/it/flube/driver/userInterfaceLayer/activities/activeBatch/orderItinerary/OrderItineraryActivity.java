/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class OrderItineraryActivity extends AppCompatActivity {
    private static final String TAG = "OrderItineraryActivity";

    private OrderItineraryController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private TabLayout tabLayout;

    private TextView orderTitle;
    private TextView orderDescription;

    private RecyclerView stepListView;
    private OrderStepListAdapter stepListAdapter;

    private String activityGuid;

    //TODO need to build a layout components for this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_itinerary);

        tabLayout = (TabLayout) findViewById(R.id.order_itinerary_tab);
        tabLayout.addOnTabSelectedListener(new ItineraryTabSelectedListener());

        orderTitle = (TextView) findViewById(R.id.order_detail_title);
        orderDescription = (TextView) findViewById(R.id.order_detail_description);

        stepListView = (RecyclerView) findViewById(R.id.orderStepsView);

        controller = new OrderItineraryController();

        stepListAdapter = new OrderStepListAdapter(this, controller);

        stepListView.setLayoutManager(new LinearLayoutManager(this));
        stepListView.setAdapter(stepListAdapter);
        stepListView.setVisibility(View.INVISIBLE);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume(){
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.order_itinerary_activity_title);
        updateOrderDetailInfo();
        updateStepListInfo();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }


    @Override
    public void onPause(){
        DrawerMenu.getInstance().clearActivity();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);

        super.onPause();
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        stepListAdapter.close();

        super.onDestroy();
    }




    private void updateOrderDetailInfo(){
        Timber.tag(TAG).d("updating service order info...");

        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            Timber.tag(TAG).d("   ...getting order detail");
            ServiceOrder order = AndroidDevice.getInstance().getActiveBatch().getServiceOrder();

            orderTitle.setText(order.getTitle());
            orderDescription.setText(order.getDescription());

            orderTitle.setVisibility(View.VISIBLE);
            orderDescription.setVisibility(View.VISIBLE);

        } else {
            Timber.tag(TAG).d("   ...no active batch");
            orderTitle.setVisibility(View.INVISIBLE);
            orderDescription.setVisibility(View.INVISIBLE);
        }
    }

    private void updateStepListInfo(){
        Integer stepCount = AndroidDevice.getInstance().getActiveBatch().getOrderStepList().size();
        Timber.tag(TAG).d("order step list has " + stepCount + " items");
        if (stepCount > 0){
            Timber.tag(TAG).d("updating list!");
            stepListAdapter.updateList(AndroidDevice.getInstance().getActiveBatch().getOrderStepList());
            stepListView.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("making list invisible!");
            stepListView.setVisibility(View.INVISIBLE);
        }
    }

    private class ItineraryTabSelectedListener implements TabLayout.OnTabSelectedListener {
        public void onTabSelected(TabLayout.Tab tab) {
            Timber.tag(TAG).d("selected tab -> " + tab.getPosition() + " : " + tab.getText());
        }

        public void onTabReselected(TabLayout.Tab tab){
            Timber.tag(TAG).d("re selected tab -> " + tab.getPosition() + " : " + tab.getText());
        }

        public void onTabUnselected(TabLayout.Tab tab){
            Timber.tag(TAG).d("un selected tab -> " + tab.getPosition() + " : " + tab.getText());
        }
    }


}
