/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchManage;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageActivity;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 1/13/2018
 * Project : Driver
 */

public class BatchManageLayoutComponents {
    public final static String TAG = "BatchManageLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_batch_manage_new.xml
    ///

    private LottieAnimationView batchStartWaitingAnimation;
    private TextView batchForfeit;
    private SlideView batchStart;



    public BatchManageLayoutComponents(AppCompatActivity activity, SlideView.OnSlideCompleteListener listener){

        batchForfeit = (TextView) activity.findViewById(R.id.batch_manage_forfeit);

        // swipe to start button
        batchStart = (SlideView) activity.findViewById(R.id.batch_start_button);
        batchStart.setOnSlideCompleteListener(listener);

        //animation
        batchStartWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.claim_offer_animation);
        Timber.tag(TAG).d("...components created");
    }

    public void setValuesAndShow(){
        batchForfeit.setVisibility(View.VISIBLE);
        batchStart.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setValuesAndShow");
    }

    public void batchStarted(){
        batchForfeit.setVisibility(View.INVISIBLE);
        batchStart.setVisibility(View.INVISIBLE);

        batchStartWaitingAnimation.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setProgress(0);
        batchStartWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...batchStarted");
    }

    public void setInvisible(){
        batchForfeit.setVisibility(View.INVISIBLE);
        batchStart.setVisibility(View.INVISIBLE);
        batchStartWaitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        batchForfeit.setVisibility(View.GONE);
        batchStart.setVisibility(View.GONE);
        batchStartWaitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        batchForfeit = null;
        batchStart = null;
        batchStartWaitingAnimation = null;
        Timber.tag(TAG).d("components closed");
    }
}
