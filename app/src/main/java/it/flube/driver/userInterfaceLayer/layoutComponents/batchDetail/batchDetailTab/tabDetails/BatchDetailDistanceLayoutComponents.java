/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/7/2018
 * Project : Driver
 */

public class BatchDetailDistanceLayoutComponents {
    public final static String TAG = "BatchDetailDistanceLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_display_distance_group.xml
    ///

    private ConstraintLayout layout;
    private ImageView distance_icon;
    private TextView distance_to_travel;

    public BatchDetailDistanceLayoutComponents(AppCompatActivity activity){
        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_display_distance_group);

        distance_icon = (ImageView) activity.findViewById(R.id.batch_detail_distance_icon);
        distance_to_travel = (TextView) activity.findViewById(R.id.batch_detail_distance_to_travel);
        setInvisible();
    }

    public ConstraintLayout getLayout(){
        return layout;
    }

    public ImageView getDistanceIcon(){
        return distance_icon;
    }

    public TextView getDistanceToTravel(){
        return distance_to_travel;
    }

    public void setValues(AppCompatActivity activity, BatchDetail batchDetail){
        Picasso.with(activity)
                .load(batchDetail.getDisplayDistance().getDistanceIndicatorUrl())
                .into(distance_icon);

        distance_to_travel.setText(batchDetail.getDisplayDistance().getDistanceToTravel());
        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        distance_icon.setVisibility(View.VISIBLE);
        distance_to_travel.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        distance_icon.setVisibility(View.INVISIBLE);
        distance_to_travel.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        distance_icon.setVisibility(View.GONE);
        distance_to_travel.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        distance_icon = null;
        distance_to_travel = null;
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
