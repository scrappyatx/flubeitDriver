/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.Map;


import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/8/2017
 * Project : Driver
 */

public class ServiceOrderNavigationStep extends AbstractStep implements OrderStepInterface {

    private double closeEnoughInFeet;
    private Boolean atDestination;
    private Destination destination;

    /// implement the getters/setters for the unique data in this step type
    public double getCloseEnoughInFeet() {
        return closeEnoughInFeet;
    }

    public void setCloseEnoughInFeet(double closeEnoughInFeet) {
        this.closeEnoughInFeet = closeEnoughInFeet;
    }

    public Boolean getAtDestination() {
        return atDestination;
    }

    public void setAtDestination(Boolean atDestination) {
        this.atDestination = atDestination;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
