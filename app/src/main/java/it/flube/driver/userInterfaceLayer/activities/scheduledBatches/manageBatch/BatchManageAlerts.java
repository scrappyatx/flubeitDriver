/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class BatchManageAlerts {
    private static final String TAG = "BatchManageAlerts";
    private static final int displayDuration = 2000;

    public void showForfeitBatchSuccessAlert(AppCompatActivity activity, ForfeitBatchAlertHidden response) {
        Timber.tag(TAG).d("showing Forfeit Batch Success Alert");
        try {
            Alerter.create(activity)
                    .setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                    .setText(activity.getString(R.string.batch_manage_forfeit_alert_success_dialog_text))
                    .setBackgroundColorRes(R.color.alerterGREEN)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showForfeitBatchAlertFailure(AppCompatActivity activity, ForfeitBatchAlertHidden response){
        Timber.tag(TAG).d("showing Forfeit Batch Failure Alert");
        try {
            Alerter.create(activity)
                    .setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                    .setText(activity.getString(R.string.batch_manage_forfeit_alert_failure_dialog_text))
                    .setBackgroundColorRes(R.color.alerterRED)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }

    }

    public void showForfeitBatchAlertTimeout(AppCompatActivity activity, ForfeitBatchAlertHidden response){
        Timber.tag(TAG).d("showing Forfeit Batch Timeout Alert");
        try {
            Alerter.create(activity)
                    .setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                    .setText(activity.getString(R.string.batch_manage_forfeit_alert_timeout_dialog_text))
                    .setBackgroundColorRes(R.color.alerterORANGE)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showForfeitBatchAlertDeniedWithoutReason(AppCompatActivity activity, ForfeitBatchAlertHidden response){
        Timber.tag(TAG).d("showing Forfeit Batch Denied without reason Alert");
        try {
            Alerter.create(activity)
                    .setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                    .setText(activity.getString(R.string.batch_manage_forfeit_alert_denied_without_reason_dialog_text))
                    .setBackgroundColorRes(R.color.alerterRED)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showForfeitBatchAlertDeniedWithReason(AppCompatActivity activity, String reason, ForfeitBatchAlertHidden response){
        Timber.tag(TAG).d("showing Forfeit Batch Denied with Reason Alert");
        try {
            String alertText = activity.getString(R.string.batch_manage_forfeit_alert_denied_with_reason_dialog_text)
                    + System.getProperty("line.separator") + reason;

            Alerter.create(activity)
                    .setTitle(activity.getString(R.string.batch_manage_forfeit_batch_dialog_title))
                    .setText(alertText)
                    .setBackgroundColorRes(R.color.alerterRED)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }


    public class ForfeitBatchHideAlertListener implements OnHideAlertListener {
        private ForfeitBatchAlertHidden response;
        public ForfeitBatchHideAlertListener(ForfeitBatchAlertHidden response){
            this.response = response;
        }
        public void onHide(){
            response.forfeitBatchAlertHidden();
        }
    }


    public interface ForfeitBatchAlertHidden {
        void forfeitBatchAlertHidden();
    }

}
