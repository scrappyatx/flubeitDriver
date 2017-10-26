/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import timber.log.Timber;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimActivity extends AppCompatActivity {
    private static final String TAG = "OfferClaimActivity";

    private ActivityNavigator navigator;
    private OfferClaimController controller;
    private DrawerMenu drawer;

    private BatchDetail offerDetail;

    private ConstraintLayout titleLayout;
    private ConstraintLayout timingLayout;
    private ConstraintLayout earningsLayout;
    private ConstraintLayout ordersStopsLayout;
    private ConstraintLayout distanceLayout;

    private ImageView batch_icon;
    private TextView batch_title;
    private TextView batch_description;

    private TextView timing_display_date;
    private TextView timing_display_duration;
    private TextView timing_display_time;
    private TextView timing_display_expiry;

    private TextView earnings_base;
    private TextView earnings_extra;

    private ImageView orders_count_icon;
    private TextView order_count_text;
    private Button order_view_button;
    private ImageView stops_count_icon;
    private TextView stops_count_text;
    private Button stops_view_button;

    private ImageView distance_icon;
    private TextView distance_to_travel;

    private Button offerClaimButton;
    private LottieAnimationView claimOfferWaitingAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_claim);

        titleLayout = (ConstraintLayout) findViewById(R.id.batch_detail_title_viewgroup);
        timingLayout = (ConstraintLayout) findViewById(R.id.batch_detail_timing_viewgroup);
        earningsLayout = (ConstraintLayout) findViewById(R.id.batch_detail_earnings_viewgroup);
        ordersStopsLayout = (ConstraintLayout) findViewById(R.id.batch_detail_orders_stops_viewgroup);
        distanceLayout = (ConstraintLayout) findViewById(R.id.batch_detail_distance_viewgroup);

        // title viewgroup elements
        batch_icon = (ImageView) findViewById(R.id.batch_detail_title_icon);
        batch_title = (TextView) findViewById(R.id.batch_detail_title);
        batch_description = (TextView) findViewById(R.id.batch_detail_description);

        //timing viewgroup elements
        timing_display_date = (TextView) findViewById(R.id.batch_detail_display_date);
        timing_display_duration = (TextView) findViewById(R.id.batch_detail_display_duration);
        timing_display_time = (TextView) findViewById(R.id.batch_detail_display_time);
        timing_display_expiry = (TextView) findViewById(R.id.batch_detail_display_expiry);

        //earnings viewgroup elements
        earnings_base = (TextView) findViewById(R.id.batch_detail_potential_earnings);
        earnings_extra = (TextView) findViewById(R.id.batch_detail_plus_tips);

        //orders & stops viewgroup elements
        orders_count_icon = (ImageView) findViewById(R.id.batch_detail_order_count_icon);
        order_count_text = (TextView) findViewById(R.id.batch_detail_order_count_text);
        order_view_button = (Button) findViewById(R.id.batch_detail_order_view_button);

        stops_count_icon = (ImageView) findViewById(R.id.batch_detail_stop_count_icon);
        stops_count_text = (TextView) findViewById(R.id.batch_detail_stop_count_text);
        stops_view_button = (Button) findViewById(R.id.batch_detail_stop_view_button);

        //distance viewgroup elements
        distance_icon = (ImageView) findViewById(R.id.batch_detail_distance_icon);
        distance_to_travel = (TextView) findViewById(R.id.batch_detail_distance_to_travel);

        // claim offer button
        claimOfferWaitingAnimation = (LottieAnimationView) findViewById(R.id.claim_offer_animation);
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);

        offerClaimButton = (Button) findViewById(R.id.offer_claim_button);
        offerClaimButton.setVisibility(View.INVISIBLE);

        //set all viewgroups GONE
        titleLayout.setVisibility(View.GONE);
        timingLayout.setVisibility(View.GONE);
        earningsLayout.setVisibility(View.GONE);
        ordersStopsLayout.setVisibility(View.GONE);
        distanceLayout.setVisibility(View.GONE);

        Timber.tag(TAG).d("onCreate");
    }
    @Override
    public void onResume() {
        super.onResume();


        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.offers_claim_activity_title);
        controller = new OfferClaimController();

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    public void clickClaimButton(View v) {
        Timber.tag(TAG).d("clicked claim button");
        offerClaimButton.setVisibility(View.INVISIBLE);

        claimOfferWaitingAnimation.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setProgress(0);
        claimOfferWaitingAnimation.playAnimation();


        controller.claimOfferRequest(offerDetail);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("*** Offer was selected event");
        offerDetail = event.getBatchDetail();

        // title viewgroup elements
        Picasso.with(this)
                .load(offerDetail.getIconUrl())
                .into(batch_icon);

        batch_title.setText(offerDetail.getTitle());
        batch_description.setText(offerDetail.getDescription());
        titleLayout.setVisibility(View.VISIBLE);

        //timing viewgroup elements
        timing_display_date.setText(offerDetail.getDisplayTiming().getDate());
        timing_display_duration.setText(offerDetail.getDisplayTiming().getDuration());
        timing_display_time.setText(offerDetail.getDisplayTiming().getHours());
        timing_display_expiry.setText(offerDetail.getDisplayTiming().getOfferExpiryDate());
        timingLayout.setVisibility(View.VISIBLE);

        //earnings viewgroup elements
        earnings_base.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                        .format(offerDetail.getPotentialEarnings().getPayRateInCents()/100));

        String displayExtraEarnings = "";
        if (offerDetail.getPotentialEarnings().getPlusTips()) {
            displayExtraEarnings = "+ Tips";
        }
        earnings_extra.setText(displayExtraEarnings);
        earningsLayout.setVisibility(View.VISIBLE);

        //orders & stops viewgroup elements
        Picasso.with(this)
                .load(UserInterfaceUtilities.getCountIconUrl(offerDetail.getServiceOrderCount()))
                .into(orders_count_icon);

        if (offerDetail.getServiceOrderCount()==1) {
            order_count_text.setText("service order");
        } else {
            order_count_text.setText("service orders");
        }
        order_view_button.setText("View");

        Picasso.with(this)
                .load(UserInterfaceUtilities.getCountIconUrl(offerDetail.getRouteStopCount()))
                .into(stops_count_icon);

        ordersStopsLayout.setVisibility(View.VISIBLE);
        if (offerDetail.getRouteStopCount()==1){
            stops_count_text.setText("destination");
        } else {
            stops_count_text.setText("destinations");
        }
        stops_view_button.setText("View");
        ordersStopsLayout.setVisibility(View.VISIBLE);

        //distance viewgroup elements
        Picasso.with(this)
                .load(offerDetail.getDisplayDistance().getDistanceIndicatorUrl())
                .into(distance_icon);
        distance_to_travel.setText(offerDetail.getDisplayDistance().getDistanceToTravel());
        distanceLayout.setVisibility(View.VISIBLE);

        offerClaimButton.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);
    }

}
