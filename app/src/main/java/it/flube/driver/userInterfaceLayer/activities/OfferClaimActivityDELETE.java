/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;

public class OfferClaimActivityDELETE extends AppCompatActivity {
    private static final String TAG = "OfferClaimActivityDELETE";
    private Button mClaimOfferButton;
    private Button mBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_claim);
        setTitle("Claim Offer");

        mClaimOfferButton = (Button) findViewById(R.id.claim_button);
        mClaimOfferButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                //send a message that we want to claim the offer

            }
        });

        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go back to offers activity
                Log.d(TAG,"onClick start");
                Context context = OfferClaimActivityDELETE.this;
                Intent claimOfferIntent = new Intent(context, MainActivityDELETE.class);
                context.startActivity(claimOfferIntent);
                Log.d(TAG,"onClick complete");

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
