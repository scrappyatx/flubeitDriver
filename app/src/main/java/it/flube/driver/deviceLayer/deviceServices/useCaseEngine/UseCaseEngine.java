/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.useCaseEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.modelLayer.interfaces.UseCaseInterface;

/**
 * Created on 10/4/2017
 * Project : Driver
 */

public class UseCaseEngine implements UseCaseInterface {

    private static final String TAG = "UseCaseEngine";
    private final ExecutorService useCaseExecutor;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    public UseCaseEngine() {
        //useCaseExecutor = Executors.newSingleThreadExecutor();
        //useCaseExecutor = Executors.newFixedThreadPool(4);
        useCaseExecutor = Executors.newCachedThreadPool();
    }

    public ExecutorService getUseCaseExecutor(){
        return useCaseExecutor;
    }


}
