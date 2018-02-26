/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/11/2018
 * Project : Driver
 */

public class ActiveBatchSummaryLayoutComponents {
    public final static String TAG = "ActiveBatchSummaryLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     home_summary_active_batch.xml
    ///
    private ConstraintLayout layout;

    private TextView title;
    private TextView detail;
    private Button button;

    public ActiveBatchSummaryLayoutComponents(AppCompatActivity activity){
        layout = (ConstraintLayout) activity.findViewById(R.id.home_summary_active_batch);

        title = (TextView) activity.findViewById(R.id.summary_active_batch_title);
        detail = (TextView) activity.findViewById(R.id.summary_active_batch_detail);
        button = (Button) activity.findViewById(R.id.summary_active_batch_button);
    }

    public void setActiveBatch(BatchDetail batchDetail){
        detail.setText(batchDetail.getTitle());

        layout.setVisibility(View.VISIBLE);

        title.setVisibility(View.VISIBLE);
        detail.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set ACTIVE BATCH");
    }

    public void setNoActiveBatch(AppCompatActivity activity){
        detail.setText(activity.getResources().getString(R.string.home_summary_active_batch_detail_no_active_batch));

        layout.setVisibility(View.VISIBLE);

        title.setVisibility(View.VISIBLE);
        detail.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);

        Timber.tag(TAG).d("...set NO ACTIVE BATCH");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        title = null;
        detail = null;
        button = null;
        Timber.tag(TAG).d("components closed");
    }

}
