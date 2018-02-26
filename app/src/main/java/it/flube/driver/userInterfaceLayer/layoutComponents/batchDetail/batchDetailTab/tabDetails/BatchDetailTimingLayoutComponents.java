/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/7/2018
 * Project : Driver
 */

public class BatchDetailTimingLayoutComponents {
    public final static String TAG = "BatchDetailTimingLayoutComponents";

    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_display_timing_group.xml
    ///

    private ConstraintLayout layout;

    private TextView timing_display_date;
    private TextView timing_display_duration;
    private TextView timing_display_time;
    private TextView timing_display_expiry;

    private Boolean showExpiry;


    public BatchDetailTimingLayoutComponents(AppCompatActivity activity, Boolean showExpiry){
        this.showExpiry = showExpiry;

        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_display_timing_group);
        //timing viewgroup elements
        timing_display_date = (TextView) activity.findViewById(R.id.batch_detail_display_date);
        timing_display_duration = (TextView) activity.findViewById(R.id.batch_detail_display_duration);
        timing_display_time = (TextView) activity.findViewById(R.id.batch_detail_display_time);
        timing_display_expiry = (TextView) activity.findViewById(R.id.batch_detail_display_expiry);

        setInvisible();
        Timber.tag(TAG).d("components created");
    }

    public ConstraintLayout getLayout(){
        return layout;
    }

    public TextView getDate(){
        return timing_display_date;
    }

    public TextView getDuration(){
        return timing_display_duration;
    }

    public TextView getTime(){
        return timing_display_time;
    }

    public TextView getExpiry(){
        return timing_display_expiry;
    }


    public void setValues(BatchDetail batchDetail){
        timing_display_date.setText(batchDetail.getDisplayTiming().getDate());
        timing_display_duration.setText(batchDetail.getDisplayTiming().getDuration());
        timing_display_time.setText(batchDetail.getDisplayTiming().getHours());
        timing_display_expiry.setText(batchDetail.getDisplayTiming().getOfferExpiryDate());
        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        if (showExpiry) {
            timing_display_expiry.setVisibility(View.VISIBLE);
        } else {
            timing_display_expiry.setVisibility(View.INVISIBLE);
        }
        timing_display_date.setVisibility(View.VISIBLE);
        timing_display_duration.setVisibility(View.VISIBLE);
        timing_display_time.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setInvisible(){
        timing_display_date.setVisibility(View.INVISIBLE);
        timing_display_duration.setVisibility(View.INVISIBLE);
        timing_display_time.setVisibility(View.INVISIBLE);
        timing_display_expiry.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);

        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        timing_display_date.setVisibility(View.GONE);
        timing_display_duration.setVisibility(View.GONE);
        timing_display_time.setVisibility(View.GONE);
        timing_display_expiry.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        timing_display_date = null;
        timing_display_duration = null;
        timing_display_time = null;
        timing_display_expiry = null;
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
