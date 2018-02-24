/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.photoList;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.PhotoRequest;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabOrders.ServiceOrderListAdapter;
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class PhotoRequestListLayoutComponents {

        public final static String TAG = "PhotoRequestListLayoutComponents";
        ///
        ///     wrapper class for the layout file:
        ///     batch_tab_details_viewgroup.xml
        ///
        private RecyclerView listView;
        private PhotoRequestListAdapter adapter;


    public PhotoRequestListLayoutComponents(AppCompatActivity activity, PhotoRequestListAdapter.Response response){
        listView = (RecyclerView) activity.findViewById(R.id.photo_request_list_view);
        listView.setVisibility(View.INVISIBLE);

        adapter = new PhotoRequestListAdapter(activity, response);
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setAdapter(adapter);

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setValues(ArrayList<PhotoRequest> photoRequestList){
        Timber.tag(TAG).d("   setValues --> photo request list has " + photoRequestList.size() + " items");
        adapter.updateList(photoRequestList);
    }

    public void setVisible(){
        listView.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        listView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        listView.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){

    }

}
