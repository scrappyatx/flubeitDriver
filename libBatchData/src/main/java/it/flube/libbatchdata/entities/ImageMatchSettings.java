/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.ArrayList;

import it.flube.libbatchdata.interfaces.ImageMatchingInterface;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class ImageMatchSettings {
    ///
    ///  Defines the requirements for what has to match for the photo to be accepted.
    ///
    ///
    private ImageMatchingInterface.MatchType matchType;
    private String matchDescription;
    private ArrayList<String> matchEntityIds;
    private float confidenceThreshold;

    public ImageMatchingInterface.MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(ImageMatchingInterface.MatchType matchType) {
        this.matchType = matchType;
    }

    public String getMatchDescription() {
        return matchDescription;
    }

    public void setMatchDescription(String matchDescription) {
        this.matchDescription = matchDescription;
    }

    public ArrayList<String> getMatchEntityIds() {
        return matchEntityIds;
    }

    public void setMatchEntityIds(ArrayList<String> matchEntityIds) {
        this.matchEntityIds = matchEntityIds;
    }

    public float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public void setConfidenceThreshold(float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
    }
}
