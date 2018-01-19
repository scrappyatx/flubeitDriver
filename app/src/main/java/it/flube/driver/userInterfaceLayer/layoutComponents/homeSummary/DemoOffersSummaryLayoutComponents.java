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
import timber.log.Timber;

/**
 * Created on 1/11/2018
 * Project : Driver
 */

public class DemoOffersSummaryLayoutComponents {
    public final static String TAG = "DemoOffersSummaryLayoutComponents";

    ///
    ///     wrapper class for the layout file:
    ///     home_summary_demo_offers.xml
    ///
    private ConstraintLayout layout;

    private TextView title;
    private TextView detail;
    private Button button;

    public DemoOffersSummaryLayoutComponents(AppCompatActivity activity){
        layout = (ConstraintLayout) activity.findViewById(R.id.home_summary_demo_offers);

        title = (TextView) activity.findViewById(R.id.summary_demo_offers_title);
        detail = (TextView) activity.findViewById(R.id.summary_demo_offers_detail);
        button = (Button) activity.findViewById(R.id.summary_demo_offers_button);

        Timber.tag(TAG).d("components created");
    }

    public void setValuesAndShow(AppCompatActivity activity, Integer offerCount){
        Timber.tag(TAG).d("setValuesAndShow -> " + offerCount + " offers");

        layout.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        detail.setVisibility(View.VISIBLE);

        String detailText;
        if (offerCount < 1) {
            detailText = activity.getResources().getString(R.string.home_summary_demo_offers_detail_no_offers);
            button.setVisibility(View.GONE);
        } else if (offerCount == 1) {
            detailText = activity.getResources().getString(R.string.home_summary_demo_offers_detail_one_offer);
            button.setVisibility(View.VISIBLE);
        } else {
            String detailFormat = activity.getResources().getString(R.string.home_summary_demo_offers_detail_some_offers);
            detailText = String.format(detailFormat, offerCount);
            button.setVisibility(View.VISIBLE);
        }
        detail.setText(detailText);
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
