/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/8/2018
 * Project : Driver
 */

public class TabDetailLayoutComponents {
    public final static String TAG = "TabDetailLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_tab_details_viewgroup.xml
    ///
    private ConstraintLayout layout;

    private BatchDetailTimingLayoutComponents timing;
    private BatchDetailEarningsLayoutComponents earnings;
    private BatchDetailDistanceLayoutComponents distance;


    public TabDetailLayoutComponents(AppCompatActivity activity, Boolean showExpiry){
        layout = (ConstraintLayout) activity.findViewById(R.id.batch_tab_details_viewgroup);

        timing = new BatchDetailTimingLayoutComponents(activity, showExpiry);
        earnings = new BatchDetailEarningsLayoutComponents(activity);
        distance = new BatchDetailDistanceLayoutComponents(activity);

        setInvisible();
    }

    public void setValues(AppCompatActivity activity, BatchDetail batchDetail){
        timing.setValues(batchDetail);
        earnings.setValues(batchDetail);
        distance.setValues(activity, batchDetail);
        Timber.tag(TAG).d("...setValues");
    }


    public void setVisible(){
        timing.setVisible();
        earnings.setVisible();
        distance.setVisible();
        layout.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        timing.setInvisible();
        earnings.setInvisible();
        distance.setInvisible();
        layout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        timing.setGone();
        earnings.setGone();
        distance.setGone();
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        timing.close();
        earnings.close();
        distance.close();
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
