/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.useCaseEngine;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

/**
 * Created on 2/1/2019
 * Project : Driver
 */
public class UseCaseThreadFactory implements ThreadFactory {
    private static final String TAG = "UseCaseThreadFactory";

    private static final String PREFIX = "usecase";
    private static final int PRIORITY = ((Thread.NORM_PRIORITY + Thread.MIN_PRIORITY) * 3) / 4;
    private static final boolean DAEMON = false;
    private AtomicLong count = new AtomicLong(0);


    public Thread newThread(@NonNull Runnable runnable) {
        String threadName = PREFIX + "-" + count.getAndIncrement();

        Timber.tag(TAG).d("creating new thread...");
        Timber.tag(TAG).d("   name     -> %s",threadName);
        Timber.tag(TAG).d("   priority -> %s", PRIORITY);
        Timber.tag(TAG).d("   daemon   -> %s", DAEMON);

        Thread t = new Thread(runnable);
        t.setName(threadName);
        t.setPriority(PRIORITY);
        t.setDaemon(DAEMON);

        return t;
    }
}
