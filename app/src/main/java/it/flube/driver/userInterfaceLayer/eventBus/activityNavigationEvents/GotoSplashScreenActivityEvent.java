/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents;

import android.content.Context;
import android.content.Intent;


import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;

/**
 * Created on 5/8/2017
 * Project : Driver
 */

public class GotoSplashScreenActivityEvent {

    public GotoSplashScreenActivityEvent() {

    }

    public void startActivity(Context c) {
        c.startActivity(new Intent(c, SplashScreenActivity.class));
    }
}
