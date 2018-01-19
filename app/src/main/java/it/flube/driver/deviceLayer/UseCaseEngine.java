/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.modelLayer.interfaces.UseCaseInterface;

/**
 * Created on 10/4/2017
 * Project : Driver
 */

public class UseCaseEngine implements UseCaseInterface {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile UseCaseEngine instance = new UseCaseEngine();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private UseCaseEngine() {
        //useCaseExecutor = Executors.newSingleThreadExecutor();
        useCaseExecutor = Executors.newFixedThreadPool(4);
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static UseCaseEngine getInstance() {
        return UseCaseEngine.Loader.instance;
    }


    private static final String TAG = "UseCaseEngine";
    private ExecutorService useCaseExecutor;

    public ExecutorService getUseCaseExecutor(){
        return useCaseExecutor;
    }

}
