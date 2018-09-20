/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import timber.log.Timber;

/**
 * Created on 1/8/2018
 * Project : Driver
 */

public class BatchDetailOrdersAndStopsLayoutComponents {
    public final static String TAG = "BatchDetailOrdersAndStopsLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_orders_and_stops.xml
    ///

    private ConstraintLayout layout;
    private ImageView orders_count_icon;
    private TextView order_count_text;
    private Button order_view_button;
    private ImageView stops_count_icon;
    private TextView stops_count_text;
    private Button stops_view_button;

    public BatchDetailOrdersAndStopsLayoutComponents(AppCompatActivity activity) {
        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_orders_stops_viewgroup);
        //orders & stops viewgroup elements
        orders_count_icon = (ImageView) activity.findViewById(R.id.batch_detail_order_count_icon);
        order_count_text = (TextView) activity.findViewById(R.id.batch_detail_order_count_text);
        order_view_button = (Button) activity.findViewById(R.id.batch_detail_order_view_button);

        stops_count_icon = (ImageView) activity.findViewById(R.id.batch_detail_stop_count_icon);
        stops_count_text = (TextView) activity.findViewById(R.id.batch_detail_stop_count_text);
        stops_view_button = (Button) activity.findViewById(R.id.batch_detail_stop_view_button);
    }

    public void setValuesAndShow(AppCompatActivity activity, BatchDetail batchDetail){

        //orders & stops viewgroup elements
        //Picasso.with(activity)
        Picasso.get()
                .load(UserInterfaceUtilities.getCountIconUrl(batchDetail.getServiceOrderCount()))
                .fit()
                .centerInside()
                .into(orders_count_icon);

        if (batchDetail.getServiceOrderCount()==1) {
            order_count_text.setText("service order");
        } else {
            order_count_text.setText("service orders");
        }
        order_view_button.setText("View");

        //Picasso.with(activity)
        Picasso.get()
                .load(UserInterfaceUtilities.getCountIconUrl(batchDetail.getRouteStopCount()))
                .fit()
                .centerInside()
                .into(stops_count_icon);

        if (batchDetail.getRouteStopCount()==1){
            stops_count_text.setText("destination");
        } else {
            stops_count_text.setText("destinations");
        }
        stops_view_button.setText("View");

        layout.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
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
        orders_count_icon = null;
        order_count_text = null;
        order_view_button = null;

        stops_count_icon = null;
        stops_count_text = null;
        stops_view_button = null;

        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
