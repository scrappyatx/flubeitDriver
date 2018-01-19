/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/7/2018
 * Project : Driver
 */

public class BatchDetailEarningsLayoutComponents {
    public final static String TAG = "BatchDetailEarningsLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_display_earnings_group.xml
    ///

    private ConstraintLayout layout;

    private TextView earnings_base;
    private TextView earnings_extra;


    public BatchDetailEarningsLayoutComponents(AppCompatActivity activity){
        //earnings viewgroup elements
        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_display_earnings_group);

        earnings_base = (TextView) activity.findViewById(R.id.batch_detail_potential_earnings);
        earnings_extra = (TextView) activity.findViewById(R.id.batch_detail_plus_tips);
        setInvisible();
    }

    public void setValues(BatchDetail batchDetail){

        //earnings viewgroup elements
        earnings_base.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(batchDetail.getPotentialEarnings().getPayRateInCents()/100));

        String displayExtraEarnings = "";
        if (batchDetail.getPotentialEarnings().getPlusTips()) {
            displayExtraEarnings = "+ Tips";
        }
        earnings_extra.setText(displayExtraEarnings);

        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        earnings_base.setVisibility(View.VISIBLE);
        earnings_extra.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        earnings_base.setVisibility(View.INVISIBLE);
        earnings_extra.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        earnings_base.setVisibility(View.GONE);
        earnings_extra.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        earnings_base = null;
        earnings_extra = null;
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
