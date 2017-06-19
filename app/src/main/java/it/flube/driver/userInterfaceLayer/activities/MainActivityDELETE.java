/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.network.toBeDeleted.HttpMessagingDELETE;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.dataLayer.interfaces.toBeDeleted.EbEventClientIdDELETE;
import it.flube.driver.dataLayer.interfaces.toBeDeleted.EbEventOfferListDELETE;
import it.flube.driver.dataLayer.presenters.OffersListAdapter;
import it.flube.driver.dataLayer.services.ServerCommService;
import it.flube.driver.userInterfaceLayer.activities.signIn.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;



public class MainActivityDELETE extends AppCompatActivity {

    private static final String TAG = "MainActivityDELETE";
    public static final String MyPREFERENCES = "MyPrefs" ;
    private Switch mySwitch;
    private Switch testSwitch;
    private TextView myStatus;
    private Button myLogin;
    private Button myGetClient;
    private Button myGetTokenRequest;
    private RecyclerView mOfferView;
    private LinearLayoutManager mLinearLayoutManager;
    private OffersListAdapter mAdapter;
    private ArrayList<Offer> mOfferList;
    private boolean mOnDuty = false;
    //private SplashScreenController mStartupController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);}

        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate()");
        setContentView(R.layout.activity_main);
        //setTitle("Offers");

        if (savedInstanceState == null) {
            //starting fresh
            Log.d(TAG, "**** starting fresh ***");

        } else {
            //returning to a known state
            Log.d(TAG, "**** returning to a known state");
            mOnDuty = savedInstanceState.getBoolean("OnDuty");
        }

            //mStartupController = new SplashScreenController(getApplicationContext());
            //mStartupController.loadDriver();
            //mStartupController.saveDriver();
            //mStartupController.loadDriver();


            //setup view
            mOfferView = (RecyclerView) findViewById(R.id.offersView);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mOfferView.setLayoutManager(mLinearLayoutManager);


            mySwitch = (Switch) findViewById(R.id.online_switch);
            myStatus = (TextView) findViewById(R.id.status_text);
            testSwitch = (Switch) findViewById(R.id.test_switch);
            myLogin = (Button) findViewById(R.id.login_button);

            myGetClient = (Button) findViewById(R.id.client_button);
            myGetTokenRequest = (Button) findViewById(R.id.token_button);

            //set the switch to OFF
            mySwitch.setChecked(mOnDuty);

            testSwitch.setChecked(false);

            //initialize offer list
            mOfferList = new ArrayList<Offer>();
            mAdapter = new OffersListAdapter(mOfferList);
            mOfferView.setAdapter(mAdapter);

            //turn offerlist visibility off
            mOfferView.setVisibility(View.INVISIBLE);

            //Log.d(TAG,"mOfferList size before test data : " + Integer.toString(mOfferList.size()));
            //generateTestData(mOfferList);
            //Log.d(TAG,"mOfferList size after test data : " + Integer.toString(mOfferList.size()));

            //turn text status visibility on & text to go online for offers
            myStatus.setVisibility(View.VISIBLE);
            myStatus.setText("Go online to see current offers");


            //attach a listener to check for changes in state
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {
                        mySwitch.setText("Online");
                        myStatus.setText("No current offers");

                        //start service
                        Intent i = ServerCommService.newIntent(MainActivityDELETE.this);
                        i.putExtra("action", "onDuty");

                        //set offer list as invisible - it won't turn on until we receive an offer update from remote server
                        mOfferView.setVisibility(View.INVISIBLE);

                        //set status text as visible
                        myStatus.setVisibility(View.VISIBLE);

                        //start the service to communicate with remote server
                        MainActivityDELETE.this.startService(i);

                        //mStartupController.setDutyStatus(true);


                    } else {
                        mySwitch.setText("Offline");
                        myStatus.setText("Go online to see current offers");

                        //shut down service
                        Intent i = ServerCommService.newIntent(MainActivityDELETE.this);
                        i.putExtra("action", "offDuty");

                        //this will actually stop the service, when the service handles the intent
                        //it will stop itself because flube.it.flube.it.driver is going off duty
                        MainActivityDELETE.this.startService(i);
                        //mStartupController.setDutyStatus(false);

                        //set offer list as invisible - it won't turn on until we receive an offer update from remote server
                        mOfferView.setVisibility(View.INVISIBLE);

                        //set status text as visible
                        myStatus.setVisibility(View.VISIBLE);

                    }

                }
            });

            testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {
                        testSwitch.setText("Real Offers");
                    } else {
                        testSwitch.setText("Test Offers");
                        try {
                            String thisresult = HttpMessagingDELETE.getAblyToken("test");
                            Log.d(TAG, "response from token test :" + thisresult);
                        } catch (IOException e) {
                            Log.d(TAG,"IOException :" + e.toString());
                            Rollbar.reportException(e,"warning", "obsolete code, should never be calleld");
                        }
                    }
                }
            });

            myLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //perform action on click
                    doLoginTest();
                }
            });

            myGetClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //perform action on click
                    getClientIdTest(v);
                }
            });

           myGetTokenRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //perform action on click
                    getClientIdTest(v);
                }
           });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("OnDuty", mOnDuty);
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG,"onRestoreInstanceState()");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG,"onStart()");
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        Log.d(TAG,"onStop()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
    }
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart()");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOfferEvent(EbEventOfferListDELETE event) {

        ArrayList<Offer> offerList = EbEventOfferListDELETE.getOfferList();

        Log.d(TAG,"*** offers received in MainActivityDELETE ***");
        Log.d(TAG," offers available = " + offerList.size());

        if (offerList.size() > 0) {
            //we have offers

            //turn text status visibility OFF
            myStatus.setVisibility(View.INVISIBLE);

            //update adapter
            mAdapter.updateList(offerList);

            //turn offer list visibility ON
            mOfferView.setVisibility(View.VISIBLE);

        } else {
            //we don't have offers

            //turn text status visibiliyt ON
            myStatus.setVisibility(View.VISIBLE);

            //turn offer list visibility OFF
            mOfferView.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClientIdEvent(EbEventClientIdDELETE clientId) {
        Log.d(TAG,"*** client ID received : " + clientId.getEbEventClientId());

    }

    private void doLoginTest() {
        Intent goToLoginActivity = new Intent(this, LoginActivity.class);
        this.startActivity(goToLoginActivity);
        Log.d(TAG,"clicked Login Test");
    }

    private String getClientId() {
        SharedPreferences myPrefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (myPrefs.contains("clientID")) {
            return myPrefs.getString("classId",null);
        } else {
            return null;
        }
    }

    private void getClientIdTest(View v) {
        try {
            HttpMessagingDELETE.getClientID("test", "passwword");
            Log.d(TAG,"**** Did a client ID request");
        } catch (IOException e) {
            Log.d(TAG,"Exception in getClientIDtest : " + e.toString());
            Rollbar.reportException(e,"warning","Exception in getClientIDtest : " + e.toString());
        }

    }

    private void getTokenRequestTest(View v) {


    }

    private void generateTestData(ArrayList<Offer> offerList) {
        Offer mOffer;



        // element 1
        mOffer = new Offer();
        mOffer.setOfferOID("X001");
        mOffer.setServiceProviderImage("jiffylube");
        mOffer.setOfferDate("01-April-2017");
        mOffer.setOfferTime("10:00 AM - 12:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$28");
        mOffer.setEstimatedEarningsExtra("+ tips");

        offerList.add(mOffer);

        //element 2
        mOffer = new Offer();
        mOffer.setOfferOID("X002");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("12:00 PM - 2:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        offerList.add(mOffer);

        //element 3
        mOffer = new Offer();
        mOffer.setOfferOID("X003");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("2:30 PM - 3:30 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        offerList.add(mOffer);

        //element 4
        mOffer = new Offer();
        mOffer.setOfferOID("X004");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("3:30 PM - 4:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        offerList.add(mOffer);

        //element 5
        mOffer = new Offer();
        mOffer.setOfferOID("X005");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("4:00 PM - 5:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);

        //element 6
        mOffer = new Offer();
        mOffer.setOfferOID("X006");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("5:00 PM - 6:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);

        //element 7
        mOffer = new Offer();
        mOffer.setOfferOID("X007");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("2:00 PM - 4:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);

        //element 8
        mOffer = new Offer();
        mOffer.setOfferOID("X008");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("2:00 PM - 4:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);

        //element 9
        mOffer = new Offer();
        mOffer.setOfferOID("X009");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("2:00 PM - 4:00 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$26");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);

        //element 10
        mOffer = new Offer();
        mOffer.setOfferOID("X010");
        mOffer.setServiceProviderImage("kwikkar");
        mOffer.setOfferDate("02-April-2017");
        mOffer.setOfferTime("2:30 PM - 3:45 PM");
        mOffer.setOfferDuration("2 hours");
        mOffer.setServiceDescription("Oil Change");
        mOffer.setEstimatedEarnings("$32");
        mOffer.setEstimatedEarningsExtra("+ tips");

        //mOfferList.add(mOffer);
    }

}

