/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents;

import android.content.Context;
import android.content.Intent;


import it.flube.driver.userInterfaceLayer.activities.HomeNoActiveBatchActivity;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class GotoHomeActivityEvent {

    public GotoHomeActivityEvent() {

    }

    public void startActivity(Context c) {
        //TODO need to add logic to determine which home activity to go to, home active batch or home no active batch
        //
        c.startActivity(new Intent(c, HomeNoActiveBatchActivity.class));
    }
}
