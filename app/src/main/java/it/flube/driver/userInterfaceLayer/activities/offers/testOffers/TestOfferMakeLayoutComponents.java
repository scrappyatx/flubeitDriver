/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class TestOfferMakeLayoutComponents {
    ///
    ///     wrapper class for the layout file:
    ///     activity_test_offers_make.xml
    ///

    public final static String TAG = "TestOfferMakeLayoutComponents";

    private RecyclerView optionListView;
    private LottieAnimationView waitingAnimation;
    private TestOfferOptionsListAdapter optionListAdapter;

    public TestOfferMakeLayoutComponents(AppCompatActivity activity){

        //create orderListView, and set it invisible
        optionListView = (RecyclerView) activity.findViewById(R.id.options_recycler_view);
        waitingAnimation = (LottieAnimationView) activity.findViewById(R.id.waiting_animation);
        waitingAnimation.useHardwareAcceleration(true);
        waitingAnimation.enableMergePathsForKitKatAndAbove(true);


        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void onResume(AppCompatActivity activity, TestOfferOptionsListAdapter.Response response){
        //create the adapter for the recycler view
        optionListAdapter = new TestOfferOptionsListAdapter(activity, response);
        optionListView.setLayoutManager(new LinearLayoutManager(activity));
        optionListView.setAdapter(optionListAdapter);
        Timber.tag(TAG).d("...onResume");
    }

    public void onPause(){
        optionListAdapter.close();
    }

    public void setValues(ArrayList<TestOfferOption> optionList){
        Timber.tag(TAG).d("   setValues --> offers list has " + optionList.size() + " items");
        optionListAdapter.updateList(optionList);
    }

    public void showWaitingAnimation(){
        optionListView.setVisibility(View.GONE);
        waitingAnimation.setVisibility(View.VISIBLE);
        waitingAnimation.setProgress(0);
        waitingAnimation.playAnimation();
    }

    public void setVisible(){
        optionListView.setVisibility(View.VISIBLE);
        waitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        optionListView.setVisibility(View.INVISIBLE);
        waitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        optionListView.setVisibility(View.GONE);
        waitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        optionListView = null;

        waitingAnimation.setImageBitmap(null);

        waitingAnimation = null;
        optionListAdapter = null;

        Timber.tag(TAG).d("close");
    }

}
