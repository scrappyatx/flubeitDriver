/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offers;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 1/17/2018
 * Project : Driver
 */

public class OffersListLayoutComponent  {

    ///
    ///     wrapper class for the layout file:
    ///     offers_list.xml
    ///

    public final static String TAG = "OffersListLayoutComponent";

    private ConstraintLayout layout;
    private RecyclerView offerListView;
    private OffersListAdapter offersListAdapter;
    private TextView noOffers;

    private Boolean hasOffers;

    public OffersListLayoutComponent(AppCompatActivity activity, String noOffersText){
        layout = (ConstraintLayout) activity.findViewById(R.id.offers_list);

        //create orderListView, and set it invisible
        offerListView = (RecyclerView) activity.findViewById(R.id.offers_recycler_view);

        noOffers = (TextView) activity.findViewById(R.id.no_offers_text);
        noOffers.setText(noOffersText);

        hasOffers = false;

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void onResume(AppCompatActivity activity, OffersListAdapter.Response response){
        //create the adapter for the recycler view
        offersListAdapter = new OffersListAdapter(activity, response);
        offerListView.setLayoutManager(new LinearLayoutManager(activity));
        offerListView.setAdapter(offersListAdapter);
        Timber.tag(TAG).d("...onResume");
    }

    public void onPause(){
        offersListAdapter.close();
    }

    public void setValues(ArrayList<Batch> offersList){
        Timber.tag(TAG).d("   setValues --> offers list has " + offersList.size() + " items");
        if (offersList.size() > 0) {
            //we have offers
            hasOffers = true;
            Timber.tag(TAG).d("   ...updating list!");
            offersListAdapter.updateList(offersList);
        } else {
            //we have no offers
            Timber.tag(TAG).d("   ...no offers");
            hasOffers=false;
        }
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
        if (hasOffers){
            offerListView.setVisibility(View.VISIBLE);
            noOffers.setVisibility(View.GONE);
        } else {
            offerListView.setVisibility(View.GONE);
            noOffers.setVisibility(View.VISIBLE);
        }
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        offerListView.setVisibility(View.INVISIBLE);
        noOffers.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.INVISIBLE);
        offerListView.setVisibility(View.GONE);
        noOffers.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }


}
