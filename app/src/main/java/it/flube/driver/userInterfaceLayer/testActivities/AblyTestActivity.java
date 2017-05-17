/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.testActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.AblyTestController;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyTestActivity.ConnectionWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyTestActivity.MessageWasUpdatedEvent;


/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class AblyTestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AblyTestActivity";

    //controller for this activity
    private AblyTestController mController;

    //layout text views
    private TextView mConnectionResults;
    private TextView mMessageResults;

    //layout spinners
    private Spinner mDriverSpinner;
    private Spinner mBatchSpinner;

    private ArrayAdapter<CharSequence> mDriverAdapter;
    private ArrayAdapter<CharSequence> mBatchAdapter;



    //activity lifecycle overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE);}

        Log.d(TAG, "build config.name " + BuildConfig.VERSION_NAME);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ably_test);
        Log.d(TAG, "AblyTestActivity CREATED");

        // setup views
        mConnectionResults = (TextView) findViewById(R.id.connection_result_text);
        mMessageResults = (TextView) findViewById(R.id.message_result_text);

        mConnectionResults.setVisibility(View.INVISIBLE);
        mMessageResults.setVisibility(View.INVISIBLE);

        mDriverSpinner = (Spinner)findViewById(R.id.ably_driver_msg_spinner);
        mBatchSpinner = (Spinner) findViewById(R.id.ably_batch_msg_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        mDriverAdapter = ArrayAdapter.createFromResource(this, R.array.driver_messages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mDriverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDriverSpinner.setAdapter(mDriverAdapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        mBatchAdapter = ArrayAdapter.createFromResource(this, R.array.batch_messages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mBatchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mBatchSpinner.setAdapter(mBatchAdapter);

        //set the listeners
        mDriverSpinner.setOnItemSelectedListener(this);
        mBatchSpinner.setOnItemSelectedListener(this);

    }

    //button click handlers
    public void clickAblyConnect(View v) {
        Log.d(TAG,"*** clicked Ably Connect Button ");
        mController.connect();
    }

    public void clickAblyDisconnect(View v) {
        Log.d(TAG,"*** clicked Ably Disconnect Button ");
        mController.disconnect();
    }

    public void clickAblyCreateChannels(View v) {
        Log.d(TAG, "*** clicked Ably Create Channel Button");
        mController.createChannels();
    }

    public void clickAblyDriverSend(View v) {
        Log.d(TAG,"*** clicked Ably Driver Send Button ");
        ///
        //get value in driver spinner
        String driverMessage = mDriverSpinner.getSelectedItem().toString();
        Log.d(TAG, "---- sending " + driverMessage);
        mController.processDriverMessage(driverMessage);
    }

    public void clickAblyBatchSend(View v) {
        Log.d(TAG,"*** clicked Ably Batch Send Button ");
        //get value in driver spinner
        String batchMessage = mBatchSpinner.getSelectedItem().toString();
        Log.d(TAG, "---- sending " + batchMessage);
        mController.processBatchMessage(batchMessage);

    }

    public void clickGoToPreStartupActivity(View v) {
        Log.d(TAG,"*** clicked GoTo PreStartup Activity Button ");

        Intent intent = new Intent(this,PreStartupActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start PreStartActivity");
    }

    //respond to spinner selections
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    //activity lifecycle overrides

    @Override
    public void onStart() {
        Log.d(TAG, "onStart() START");
        super.onStart();

        //instantiate controller for this activity
        mController = new AblyTestController();
        mController.createConnection();

        //register on eventbus
        EventBus.getDefault().register(this);
        mController.subscribeEventBus();


        Log.d(TAG, "onStart() END");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        Log.d(TAG, "onStop() START");

        //unregister on eventbus
        EventBus.getDefault().unregister(this);
        mController.unsubscribeEventBus();

        super.onStop();
        Log.d(TAG, "onStop() END");
    }

    //event bus events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ConnectionWasUpdatedEvent event) {
        Log.d(TAG, "*** ConnectedWasUpdatedEvent");
        mConnectionResults.setText(event.getResultMessage());
        mConnectionResults.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageWasUpdatedEvent event) {
        Log.d(TAG, "*** MessageWasUpdatedEvent");
        mMessageResults.setText(event.getResultMessage());
        mMessageResults.setVisibility(View.VISIBLE);
    }

}
