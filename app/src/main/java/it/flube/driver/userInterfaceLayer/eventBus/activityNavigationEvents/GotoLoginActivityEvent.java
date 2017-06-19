/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.signIn.LoginActivity;

/**
 * Created by Bryan on 5/3/2017.
 */

public class GotoLoginActivityEvent {

    public GotoLoginActivityEvent() {

    }
    public void startActivity(Context c) {
        c.startActivity(new Intent(c, LoginActivity.class));
    }
}
