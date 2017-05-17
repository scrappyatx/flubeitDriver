/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    private Button myLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE);}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("flube.it Driver");



        myLogin = (Button) findViewById(R.id.ok_button);


        myLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perform action on click
                doLoginTest();
            }
        });


    }

    private void doLoginTest() {
        //do something here
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        this.startActivity(goToMainActivity);
        Log.d(TAG,"clicked Login Test");
    }
}
