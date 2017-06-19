/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.ScheduledBatchesActivity;


/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class GotoScheduledBatchesActivityEvent {
    public GotoScheduledBatchesActivityEvent() {

    }
    public void startActivity(Context c) {
        c.startActivity(new Intent(c, ScheduledBatchesActivity.class));
    }
}
