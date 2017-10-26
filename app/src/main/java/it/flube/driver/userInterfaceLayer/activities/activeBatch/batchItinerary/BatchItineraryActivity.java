/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import it.flube.driver.userInterfaceLayer.activities.account.AccountController;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class BatchItineraryActivity extends AppCompatActivity {
    private static final String TAG = "BatchItineraryActivity";

    private BatchItineraryController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private TabLayout tabLayout;

    private ImageView batchIcon;
    private TextView batchTitle;
    private TextView batchDescription;

    private RecyclerView orderListView;
    private ServiceOrderListAdapter orderListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_itinerary);

        tabLayout = (TabLayout) findViewById(R.id.batch_itinerary_tab);
        tabLayout.addOnTabSelectedListener(new ItineraryTabSelectedListener());

        batchIcon = (ImageView) findViewById(R.id.batch_detail_title_icon);
        batchTitle = (TextView) findViewById(R.id.batch_detail_title);
        batchDescription = (TextView) findViewById(R.id.batch_detail_description);

        orderListView = (RecyclerView) findViewById(R.id.serviceOrdersView);

    }

    @Override
    public void onResume(){
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.batch_itinerary_activity_title);
        controller = new BatchItineraryController();

        orderListAdapter = new ServiceOrderListAdapter(this, controller);

        orderListView.setLayoutManager(new LinearLayoutManager(this));
        orderListView.setAdapter(orderListAdapter);
        orderListView.setVisibility(View.INVISIBLE);

        updateBatchDetailInfo();
        updateServiceOrderListInfo();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause(){

        drawer.close();
        controller.close();
        orderListAdapter.close();

        Timber.tag(TAG).d("onPause");

        super.onPause();
    }

    private void updateBatchDetailInfo(){
        Timber.tag(TAG).d("updating batch detail info...");

        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            Timber.tag(TAG).d("   ...getting batch detail");
            BatchDetail batchDetail = AndroidDevice.getInstance().getActiveBatch().getBatchDetail();

            batchTitle.setText(batchDetail.getTitle());
            batchDescription.setText(batchDetail.getDescription());

            Picasso.with(this)
                    .load(batchDetail.getIconUrl())
                    .into(batchIcon);

            batchTitle.setVisibility(View.VISIBLE);
            batchDescription.setVisibility(View.VISIBLE);
            batchIcon.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("   ...no active batch");
            batchTitle.setVisibility(View.INVISIBLE);
            batchDescription.setVisibility(View.INVISIBLE);
            batchIcon.setVisibility(View.INVISIBLE);
        }
    }

    private void updateServiceOrderListInfo(){
        Integer orderCount = AndroidDevice.getInstance().getActiveBatch().getServiceOrderList().size();
        Timber.tag(TAG).d("service order list has " + orderCount + " items");
        if (orderCount > 0){
            Timber.tag(TAG).d("updating list!");
            orderListAdapter.updateList(AndroidDevice.getInstance().getActiveBatch().getServiceOrderList());
            orderListView.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("making list invisible!");
            orderListView.setVisibility(View.INVISIBLE);
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
