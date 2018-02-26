/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabOrders;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 1/8/2018
 * Project : Driver
 */

public class TabOrdersLayoutComponents implements
        ServiceOrderListAdapter.Response {

    public final static String TAG = "TabOrdersLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_tab_details_viewgroup.xml
    ///
    private RecyclerView orderListView;
    private ServiceOrderListAdapter orderListAdapter;

    public TabOrdersLayoutComponents(AppCompatActivity activity){
        //create orderListView, and set it invisible
        orderListView = (RecyclerView) activity.findViewById(R.id.serviceOrdersView);
        orderListView.setVisibility(View.INVISIBLE);

        //create the adapter for the recycler view
        orderListAdapter = new ServiceOrderListAdapter(this);
        orderListView.setLayoutManager(new LinearLayoutManager(activity));
        orderListView.setAdapter(orderListAdapter);

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setValues(ArrayList<ServiceOrder> ordersList){
        Timber.tag(TAG).d("   setValues --> service order list has " + ordersList.size() + " items");
        if (ordersList.size() > 0) {
            Timber.tag(TAG).d("   ...updating list!");
            orderListAdapter.updateList(ordersList);
        }
    }

    public void setVisible(){
        orderListView.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        orderListView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        orderListView.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void orderSelected(ServiceOrder serviceOrder){
        Timber.tag(TAG).d("...orderSelected -> " + serviceOrder.getGuid());
    }

}
