/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.messages.UseCaseGetContactPersons;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class MessagesController {
    private final String TAG = "MessagesController";


    public MessagesController() {
        Timber.tag(TAG).d("created");
    }

    public void getContactPersonInfo(UseCaseGetContactPersons.Response response){
        Timber.tag(TAG).d("getContactPersonInfo");
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetContactPersons(AndroidDevice.getInstance(), response));
    }

    public void close(){

    }
}
