/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.help;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HelpActivity extends AppCompatActivity {
    private static final String TAG = "HelpActivity";

    private HelpController controller;
    private String activityGuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        controller = new HelpController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }


    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.help_activity_title);


        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        DrawerMenu.getInstance().close();
        super.onPause();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);

    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }


    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        super.onDestroy();
    }

}
