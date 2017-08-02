/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.serviceOrder;

import it.flube.driver.modelLayer.interfaces.ServiceOrderStepInterface;

/**
 * Created on 7/29/2017
 * Project : Driver
 */

public class OrderStepNavigation implements ServiceOrderStepInterface {
    private static final String TAG = "OrderStepNavigation";


    public OrderStepNavigation(){

    }

    public String getTitle() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public TaskType getTaskType() {
        return TaskType.NAVIGATION;
    }

    public void startStep() {

    }

    public void completeStep() {

    }

}
