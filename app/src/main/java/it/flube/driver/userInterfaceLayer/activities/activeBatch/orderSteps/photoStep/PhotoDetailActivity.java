/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import timber.log.Timber;

/**
 * Created on 1/22/2018
 * Project : Driver
 */

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String TAG = "PhotoDetailActivity";

    public static final String PHOTO_REQUEST_GUID_KEY = "photoRequestGuid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_photo_step);


    }

    @Override
    public void onResume() {
        super.onResume();




        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();


    }


}
