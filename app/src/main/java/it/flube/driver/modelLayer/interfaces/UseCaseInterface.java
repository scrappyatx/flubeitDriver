/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created on 10/4/2017
 * Project : Driver
 */

public interface UseCaseInterface {

    public ExecutorService getUseCaseExecutor();

    public ExecutorService getUploadExecutor();

}
