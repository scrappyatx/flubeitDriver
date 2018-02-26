/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails.TabDetailLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations.TabLocationsLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabOrders.TabOrdersLayoutComponents;
import timber.log.Timber;

/**
 * Created on 1/13/2018
 * Project : Driver
 */

public class BatchDetailTabLayoutComponents
        implements TabLayout.OnTabSelectedListener {
    public final static String TAG = "BatchDetailTabLayoutComponents";


    private TabLayout tabLayout;

    private TabDetailLayoutComponents detailTab;
    private TabLocationsLayoutComponents locationsTab;
    private TabOrdersLayoutComponents ordersTab;

    public BatchDetailTabLayoutComponents(AppCompatActivity activity, Bundle savedInstanceState, Boolean showExpiry){

        tabLayout = (TabLayout) activity.findViewById(R.id.batch_itinerary_tab);
        tabLayout.addOnTabSelectedListener(this);

        detailTab = new TabDetailLayoutComponents(activity, showExpiry);
        ordersTab = new TabOrdersLayoutComponents(activity);
        locationsTab = new TabLocationsLayoutComponents(activity, savedInstanceState);

        setInvisible();
    }

    public void setValues(AppCompatActivity activity, BatchDetail batchDetail, ArrayList<ServiceOrder> ordersList, ArrayList<RouteStop> routeList){
        tabLayout.setVisibility(View.INVISIBLE);

        detailTab.setValues(activity, batchDetail);
        ordersTab.setValues(ordersList);
        locationsTab.setValues(activity, routeList);

        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        tabLayout.setVisibility(View.VISIBLE);
        showTab(tabLayout.getSelectedTabPosition());
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        tabLayout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        tabLayout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    //// interface for TabLayout listener

    public void onTabSelected(TabLayout.Tab tab) {
        Timber.tag(TAG).d("selected tab -> " + tab.getPosition() + " : " + tab.getText());
        showTab(tab.getPosition());
    }

    public void onTabReselected(TabLayout.Tab tab){
        Timber.tag(TAG).d("re selected tab -> " + tab.getPosition() + " : " + tab.getText());
    }

    public void onTabUnselected(TabLayout.Tab tab){
        Timber.tag(TAG).d("un selected tab -> " + tab.getPosition() + " : " + tab.getText());
        hideTab(tab.getPosition());
    }

    private void showTab(Integer tabPosition){
        switch (tabPosition){
            case 0:
                detailTab.setVisible();
                break;
            case 1:
                ordersTab.setVisible();
                break;
            case 2:
                locationsTab.setVisible();
                break;
            default:
                Timber.tag(TAG).w("...unknown tab position " + tabPosition);
                break;
        }
        Timber.tag(TAG).d("...showing tab " + tabPosition);
    }
    private void hideTab(Integer tabPosition){
        switch (tabPosition){
            case 0:
                detailTab.setInvisible();
                break;
            case 1:
                ordersTab.setInvisible();
                break;
            case 2:
                locationsTab.setInvisible();
                break;
            default:
                Timber.tag(TAG).w("...unknown tab position " + tabPosition);
                break;
        }
        Timber.tag(TAG).d("...hiding tab " + tabPosition);
    }


    ///  TabLocationsLayoutComponents have to get every activity lifecycle

    public void onStart(){
        locationsTab.onStart();
        Timber.tag(TAG).d("onSaveInstanceState");
    }
    public void onPause() {
        locationsTab.onPause();
        Timber.tag(TAG).d(TAG, "onPause");
    }

    public void onResume(){
        locationsTab.onResume();
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onStop(){
        locationsTab.onStop();
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onSaveInstanceState(Bundle outState) {
        locationsTab.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    public void onLowMemory() {
        locationsTab.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    public void onDestroy() {
        locationsTab.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }

}
