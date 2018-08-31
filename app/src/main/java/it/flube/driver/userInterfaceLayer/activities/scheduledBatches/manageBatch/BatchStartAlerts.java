/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 8/28/2018
 * Project : Driver
 */
public class BatchStartAlerts implements
        OnHideAlertListener,
        View.OnClickListener {
    private static final String TAG = "BatchStartAlerts";

    private static final int displayDuration = 2000;
    private static final int displayDurationFailure = 10000;
    private Response response;

    public void showBatchStartedSuccess(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("showBatchStartedSuccess");
        /// show alert that disappears after display duration
        this.response = response;
        Alerter.create(activity)
                .setTitle(activity.getString(R.string.batch_manage_start_alert_dialog_title))
                .setText(activity.getString(R.string.batch_manage_start_alert_dialog_text))
                .setBackgroundColorRes(R.color.alerterGREEN)
                .setIcon(R.drawable.ic_mood_black_24dp)
                .setDuration(displayDuration)
                .setOnHideListener(this)
                .show();

    }

    public void showBatchStartedFailure(AppCompatActivity activity, Response response) {
        Timber.tag(TAG).d("showBatchStartedFailure");
        /// show alert that must be button clicked to dismiss
        this.response = response;
        Alerter.create(activity)
                .setTitle(activity.getString(R.string.batch_manage_start_failure_alert_dialog_title))
                .setText(activity.getString(R.string.batch_manage_start_failure_alert_dialog_text))
                .setBackgroundColorRes(R.color.alerterRED)
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .setDuration(displayDurationFailure)
                .addButton(activity.getString(R.string.batch_manage_start_failure_alert_button_text), R.style.AlertButton, this)
                .setOnHideListener(this)
                .show();
    }

    public void showBatchStartedTimeout(AppCompatActivity activity, Response response) {
        Timber.tag(TAG).d("showBatchStartedTimeout");
        /// show alert that must be button clicked to dismiss
        this.response = response;
        Alerter.create(activity)
                .setTitle(activity.getString(R.string.batch_manage_start_timeout_alert_dialog_title))
                .setText(activity.getString(R.string.batch_manage_start_timeout_alert_dialog_text))
                .setBackgroundColorRes(R.color.alerterORANGE)
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .setDuration(displayDurationFailure)
                .addButton(activity.getString(R.string.batch_manage_start_timeout_alert_button_text), R.style.AlertButton, this)
                .setOnHideListener(this)
                .show();
    }

    public void showBatchStartedDenied(AppCompatActivity activity, Response response, String reason){
        Timber.tag(TAG).d("showBatchStartedDenied, reason -> " + reason);
        /// show alert that must be button clicked to dismiss
        this.response = response;
        Alerter.create(activity)
                .setTitle(activity.getString(R.string.batch_manage_start_denied_alert_dialog_title))
                .setText(reason)
                .setBackgroundColorRes(R.color.alerterRED)
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .setDuration(displayDurationFailure)
                .addButton(activity.getString(R.string.batch_manage_start_denied_alert_button_text), R.style.AlertButton, this)
                .setOnHideListener(this)
                .show();

    }


    //// button click listener
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        // hide any currently showing alerts
        Alerter.hide();
    }

    /// alert hidden interface
    public void onHide(){
        Timber.tag(TAG).d("onHide");
        response.batchStartAlertHidden();
    }

    //// response interface
    public interface Response {
        void batchStartAlertHidden();
    }
}
