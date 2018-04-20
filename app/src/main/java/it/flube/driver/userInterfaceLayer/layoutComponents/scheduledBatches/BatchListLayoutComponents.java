/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.scheduledBatches;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 4/16/2018
 * Project : Driver
 */
public class BatchListLayoutComponents {
    public final static String TAG = "BatchListLayoutComponents";

    private ConstraintLayout layout;
    private RecyclerView batchListView;
    private BatchListAdapter batchListAdapter;
    private TextView noBatches;

    private Boolean hasBatches;

    public BatchListLayoutComponents(AppCompatActivity activity, String noBatchesText){
        layout = (ConstraintLayout) activity.findViewById(R.id.batch_list);

        //create orderListView, and set it invisible
        batchListView = (RecyclerView) activity.findViewById(R.id.batches_recycler_view);

        noBatches = (TextView) activity.findViewById(R.id.no_batches_text);
        noBatches.setText(noBatchesText);

        hasBatches = false;

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void onResume(AppCompatActivity activity, BatchListAdapter.Response response){
        //create the adapter for the recycler view
        batchListAdapter = new BatchListAdapter(activity, response);
        batchListView.setLayoutManager(new LinearLayoutManager(activity));
        batchListView.setAdapter(batchListAdapter);
        Timber.tag(TAG).d("...onResume");
    }

    public void onPause(){
        batchListAdapter.close();
    }

    public void setValues(ArrayList<Batch> batchList){
        Timber.tag(TAG).d("   setValues --> batch list has " + batchList.size() + " items");
        if (batchList.size() > 0) {
            //we have offers
            hasBatches = true;
            Timber.tag(TAG).d("   ...updating list!");
            batchListAdapter.updateList(batchList);
        } else {
            //we have no offers
            Timber.tag(TAG).d("   ...no offers");
            hasBatches=false;
        }
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
        if (hasBatches){
            batchListView.setVisibility(View.VISIBLE);
            noBatches.setVisibility(View.GONE);
        } else {
            batchListView.setVisibility(View.GONE);
            noBatches.setVisibility(View.VISIBLE);
        }
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        batchListView.setVisibility(View.INVISIBLE);
        noBatches.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.INVISIBLE);
        batchListView.setVisibility(View.GONE);
        noBatches.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

}
