/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.orderStep;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class StepDetailDueByLayoutComponents {
    public final static String TAG = "StepDetailTitleLayoutComponents";

    ///
    ///     wrapper class for the layout file:
    ///     step_detail_due_by.xml
    ///

    private final static String DATE_FORMAT_PATTERN = "h:mm aa";

    private TextView stepWorkTiming;
    private TextView stepDueByCaption;
    private TextView stepDueByValue;
    private DateFormat dateFormat;

    public StepDetailDueByLayoutComponents(AppCompatActivity activity){

        stepWorkTiming = (TextView) activity.findViewById(R.id.step_detail_work_timing);
        stepDueByCaption = (TextView) activity.findViewById(R.id.step_detail_complete_by_caption);
        stepDueByValue = (TextView) activity.findViewById(R.id.step_detail_complete_by_value);
        dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
        setInvisible();
        Timber.tag(TAG).d("components created");
    }

    public void setValues(AppCompatActivity activity, OrderStepInterface step){
        // title viewgroup elements
        stepWorkTiming.setText(step.getWorkTimingIconTextMap().get(step.getWorkTiming().toString()));

        switch (step.getWorkTiming()){
            case ON_TIME:
                stepWorkTiming.setTextColor(activity.getResources().getColor(R.color.colorStepTimingOnTime));
                break;
            case LATE:
                stepWorkTiming.setTextColor(activity.getResources().getColor(R.color.colorStepTimingLate));
                break;
            case VERY_LATE:
                stepWorkTiming.setTextColor(activity.getResources().getColor(R.color.colorStepTimingVeryLate));
                break;
            default:
                stepWorkTiming.setTextColor(activity.getResources().getColor(R.color.colorStepTimingOnTime));
                break;
        }
        //stepWorkTiming.setTextColor(activity.getResources().getColor(R.color.colorStepTimingOnTime));


        String dueTime = dateFormat.format(step.getFinishTime().getScheduledTime());
        Timber.tag(TAG).d("---> Formatted due by   : " + dueTime);
        stepDueByValue.setText(dueTime);


        Timber.tag(TAG).d("...setValues");
    }

    public void setVisible(){
        stepWorkTiming.setVisibility(View.VISIBLE);
        stepDueByCaption.setVisibility(View.VISIBLE);
        stepDueByValue.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        stepWorkTiming.setVisibility(View.INVISIBLE);
        stepDueByCaption.setVisibility(View.INVISIBLE);
        stepDueByValue.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        stepWorkTiming.setVisibility(View.GONE);
        stepDueByCaption.setVisibility(View.GONE);
        stepDueByValue.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        stepWorkTiming = null;
        stepDueByCaption = null;
        stepDueByValue = null;
        Timber.tag(TAG).d("components closed");
    }

}
