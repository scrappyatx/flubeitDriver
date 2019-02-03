/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.useCaseEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import it.flube.driver.modelLayer.interfaces.UseCaseInterface;
import timber.log.Timber;

/**
 * Created on 10/4/2017
 * Project : Driver
 */

public class UseCaseEngine implements UseCaseInterface {

    private static final String TAG = "UseCaseEngine";

    private static final long MB = 1024L * 1024L;

    private final ExecutorService useCaseExecutor;
    private final ExecutorService uploadExecutor;

    private static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    public UseCaseEngine() {

        // https://stackoverflow.com/questions/30294794/how-to-choose-size-of-thread-pool-on-android

        Timber.tag(TAG).d("number of cores -> " + CPU_COUNT);
        Timber.tag(TAG).d("core pool size  -> " + CORE_POOL_SIZE);
        Timber.tag(TAG).d("max pool size   -> " + MAX_POOL_SIZE);

        //useCaseExecutor = Executors.newSingleThreadExecutor();
        Timber.tag(TAG).d("making useCaseExecutor thread pool with %s threads", MAX_POOL_SIZE);
        useCaseExecutor = Executors.newFixedThreadPool(MAX_POOL_SIZE, new UseCaseThreadFactory());

        Timber.tag(TAG).d("making scheduledExecutor thread pool with %s threads", CORE_POOL_SIZE);
        //scheduledExecutor = Executors.newScheduledThreadPool(CORE_POOL_SIZE, new BackgroundThreadFactory());
        uploadExecutor = Executors.newFixedThreadPool(MAX_POOL_SIZE, new BackgroundThreadFactory());

        //useCaseExecutor = Executors.newCachedThreadPool();
    }

    public ExecutorService getUseCaseExecutor(){
        Timber.tag(TAG).d("getUseCaseExecutor");
        Timber.tag(TAG).d("      total memory -> %s", getMb(Runtime.getRuntime().totalMemory()));
        Timber.tag(TAG).d("      free memory  -> %s", getMb(Runtime.getRuntime().freeMemory()));
        Timber.tag(TAG).d("      max memory   -> %s", getMb(Runtime.getRuntime().maxMemory()));
        Timber.tag(TAG).d("      used memory  -> %s", getMb(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

        return useCaseExecutor;
    }

    public ExecutorService getUploadExecutor(){
        Timber.tag(TAG).d("getScheduledExecutor");
        return uploadExecutor;
    }

    private String getMb(long bytes){
        return String.valueOf(bytes / MB);
    }

}
