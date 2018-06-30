/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import timber.log.Timber;

/**
 * Created on 6/28/2018
 * Project : Driver
 */
public class TestOfferAlerts {
    private static final String TAG = "TestOfferAlerts";
    private static final int displayDuration = 2000;

    public void showTestOfferCreatedSuccessAlert(AppCompatActivity activity, TestOfferCreatedAlertHidden response) {
        Timber.tag(TAG).d("showTestOfferCreatedSuccessAlert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.test_offer_created_success_title)
                    .setText(R.string.test_offer_created_success_message)
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new TestOfferCreatedAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showTestOfferCreatedFailureAlert(AppCompatActivity activity, TestOfferCreatedAlertHidden response){
        Timber.tag(TAG).d("showTestOfferCreatedFailureAlert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.test_offer_created_failure_title)
                    .setText(R.string.test_offer_created_failure_message)
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new TestOfferCreatedAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public class TestOfferCreatedAlertListener implements OnHideAlertListener {
        private TestOfferCreatedAlertHidden response;

        public TestOfferCreatedAlertListener(TestOfferCreatedAlertHidden response){
            this.response = response;
        }
        public void onHide(){
            response.testOfferCreatedAlertHidden();
        }
    }

    public interface TestOfferCreatedAlertHidden {
        void testOfferCreatedAlertHidden();
    }


}
