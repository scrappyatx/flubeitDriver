/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail;

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

public class BatchDetailTitleLayoutComponents {
    public final static String TAG = "BatchDetailTitleLayoutComponents";

    public final static String PRODUCTION_TEST_TEXT = "Production TEST";
    public final static String MOBILE_DEMO_TEXT = "Mobile DEMO";
    public final static String PRODUCTION_TEXT = "Production";
    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_title_group.xml
    ///
    private ConstraintLayout layout;
    private ImageView batch_icon;
    private TextView batch_title;
    private TextView batch_type;
    //private TextView batch_description;

    private Boolean showBatchType;

    public BatchDetailTitleLayoutComponents(AppCompatActivity activity){

        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_title_group);
        batch_icon = (ImageView) activity.findViewById(R.id.batch_detail_title_icon);
        batch_title = (TextView) activity.findViewById(R.id.batch_detail_title);
        batch_type = (TextView) activity.findViewById(R.id.batch_detail_batch_type);
        //batch_description = (TextView) activity.findViewById(R.id.batch_detail_description);

        showBatchType = false;

        setInvisible();
        Timber.tag(TAG).d("components created");
    }

    public ConstraintLayout getLayout(){
        return layout;
    }

    public ImageView getIcon(){
        return batch_icon;
    }

    public TextView getTitle(){
        return batch_title;
    }

    //public TextView getDescription(){
    ///    return batch_description;
    //}

    public void setValues(AppCompatActivity activity, BatchDetail batchDetail){
        // title viewgroup elements
        //Picasso.with(activity)
        Picasso.get()
                .load(batchDetail.getIconUrl())
                .fit()
                .centerInside()
                .into(batch_icon);

        batch_title.setText(batchDetail.getTitle());
        //batch_description.setText(batchDetail.getDescription());

        Timber.tag(TAG).d("   batchType -> " + batchDetail.getBatchType().toString());
        switch (batchDetail.getBatchType()){
            case PRODUCTION:
                showBatchType = false;
                batch_type.setText(PRODUCTION_TEXT);
                break;
            case PRODUCTION_TEST:
                showBatchType = true;
                batch_type.setText(PRODUCTION_TEST_TEXT);
                break;
            case MOBILE_DEMO:
                showBatchType = true;
                batch_type.setText(MOBILE_DEMO_TEXT);
                break;
        }
        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
        batch_icon.setVisibility(View.VISIBLE);
        batch_title.setVisibility(View.VISIBLE);
        //batch_description.setVisibility(View.VISIBLE);

        if (showBatchType) {
            batch_type.setVisibility(View.VISIBLE);
        } else {
            batch_type.setVisibility(View.GONE);
        }
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        batch_icon.setVisibility(View.INVISIBLE);
        batch_title.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        batch_type.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.GONE);
        batch_icon.setVisibility(View.GONE);
        batch_title.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        batch_type.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        batch_icon = null;
        batch_title = null;
        //batch_description = null;
        batch_type = null;
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
