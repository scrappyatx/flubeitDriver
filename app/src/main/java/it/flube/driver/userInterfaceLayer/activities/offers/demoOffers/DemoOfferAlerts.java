/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 9/10/2017
 * Project : Driver
 */

public class DemoOfferAlerts {
    private static final String TAG = "OfferClaimAlerts";
    private static final int displayDuration = 2000;

    public void showDemoOfferCreatedAlert(AppCompatActivity activity) {
        Timber.tag(TAG).d("showing demo offer created Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.demo_offers_alert_created_title)
                    .setText(R.string.demo_offers_alert_created_message)
                    .setBackgroundColor(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public static class ShowDemoOfferCreatedAlertEvent {
        public ShowDemoOfferCreatedAlertEvent() {

        }
    }
}
