/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import timber.log.Timber;

/**
 * Created on 6/21/2018
 * Project : Driver
 */
public class BatchRemovedAlerts {
    private static final String TAG = "BatchRemovedAlerts";
    private static final int displayDurationUser = 2000;
    private static final int displayDurationAdmin = 5000;

    public void showBatchRemovedByUserAlert(AppCompatActivity activity){
        Timber.tag(TAG).d("showBatchRemovedByUserAlert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.active_batch_removed_by_user_alert_title)
                    .setText(R.string.active_batch_removed_by_user_alert_message)
                    .setBackgroundColorRes(R.color.alerterGREEN)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDurationUser)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showBatchRemovedByAdminAlert(AppCompatActivity activity){
        Timber.tag(TAG).d("showBatchRemovedByAdminAlert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.active_batch_removed_by_admin_alert_title)
                    .setText(R.string.active_batch_removed_by_admin_alert_message)
                    .setBackgroundColorRes(R.color.alerterORANGE)
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setDuration(displayDurationAdmin)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }
}
