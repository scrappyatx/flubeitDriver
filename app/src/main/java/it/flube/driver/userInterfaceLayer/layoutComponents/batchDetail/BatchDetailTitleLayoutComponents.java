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

    ///
    ///     wrapper class for the layout file:
    ///     batch_detail_title_group.xml
    ///
    private ConstraintLayout layout;
    private ImageView batch_icon;
    private TextView batch_title;
    private TextView batch_description;

    public BatchDetailTitleLayoutComponents(AppCompatActivity activity){

        layout = (ConstraintLayout) activity.findViewById(R.id.batch_detail_title_group);
        batch_icon = (ImageView) activity.findViewById(R.id.batch_detail_title_icon);
        batch_title = (TextView) activity.findViewById(R.id.batch_detail_title);
        batch_description = (TextView) activity.findViewById(R.id.batch_detail_description);

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

    public TextView getDescription(){
        return batch_description;
    }

    public void setValues(AppCompatActivity activity, BatchDetail batchDetail){
        // title viewgroup elements
        Picasso.with(activity)
                .load(batchDetail.getIconUrl())
                .into(batch_icon);

        batch_title.setText(batchDetail.getTitle());
        batch_description.setText(batchDetail.getDescription());
        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        layout.setVisibility(View.VISIBLE);
        batch_icon.setVisibility(View.VISIBLE);
        batch_title.setVisibility(View.VISIBLE);
        batch_description.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        layout.setVisibility(View.INVISIBLE);
        batch_icon.setVisibility(View.INVISIBLE);
        batch_title.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        layout.setVisibility(View.GONE);
        batch_icon.setVisibility(View.GONE);
        batch_title.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        batch_icon = null;
        batch_title = null;
        batch_description = null;
        layout = null;
        Timber.tag(TAG).d("components closed");
    }
}
