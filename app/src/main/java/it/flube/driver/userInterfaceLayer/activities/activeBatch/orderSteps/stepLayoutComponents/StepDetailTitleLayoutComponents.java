/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class StepDetailTitleLayoutComponents {

    public final static String TAG = "StepDetailTitleLayoutComponents";

    ///
    ///     wrapper class for the layout file:
    ///     step_detail_title_group.xml
    ///
    private TextView stepSequence;
    private TextView stepTitle;
    private TextView stepDescription;
    private TextView stepWorkStage;

    public StepDetailTitleLayoutComponents(AppCompatActivity activity){

        stepSequence = (TextView) activity.findViewById(R.id.step_sequence);
        stepTitle = (TextView) activity.findViewById(R.id.step_title);
        stepDescription = (TextView) activity.findViewById(R.id.step_description);
        stepWorkStage = (TextView) activity.findViewById(R.id.step_workStage);

        setInvisible();
        Timber.tag(TAG).d("components created");
    }

    public void setValues(@NonNull AppCompatActivity activity, @NonNull OrderStepInterface step){
        // title viewgroup elements
        stepSequence.setText(String.format("%s",step.getSequence()));
        stepTitle.setText(step.getTitle());
        stepDescription.setText(step.getDescription());
        stepWorkStage.setText(step.getWorkStageIconTextMap().get(step.getWorkStage().toString()));
        stepWorkStage.setTextColor(activity.getResources().getColor(R.color.colorStepStageActive));
        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        stepSequence.setVisibility(View.VISIBLE);
        stepTitle.setVisibility(View.VISIBLE);
        stepDescription.setVisibility(View.GONE);
        stepWorkStage.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        stepSequence.setVisibility(View.INVISIBLE);
        stepTitle.setVisibility(View.INVISIBLE);
        stepDescription.setVisibility(View.GONE);
        stepWorkStage.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        stepSequence.setVisibility(View.GONE);
        stepTitle.setVisibility(View.GONE);
        stepDescription.setVisibility(View.GONE);
        stepWorkStage.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        stepSequence = null;
        stepTitle = null;
        stepDescription = null;
        stepWorkStage = null;
        Timber.tag(TAG).d("components closed");
    }



}
