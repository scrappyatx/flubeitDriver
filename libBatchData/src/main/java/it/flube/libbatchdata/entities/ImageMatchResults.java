/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.ArrayList;

/**
 * Created on 3/6/2019
 * Project : Driver
 */
public class ImageMatchResults {
    private Boolean foundMatchingLabels;
    private ImageLabel mostLikelyMatchingLabel;

    private ArrayList<ImageLabel> matchingLabels;


    public Boolean getFoundMatchingLabels() {
        return foundMatchingLabels;
    }

    public void setFoundMatchingLabels(Boolean foundMatchingLabels) {
        this.foundMatchingLabels = foundMatchingLabels;
    }

    public ImageLabel getMostLikelyMatchingLabel() {
        return mostLikelyMatchingLabel;
    }

    public void setMostLikelyMatchingLabel(ImageLabel mostLikelyMatchingLabel) {
        this.mostLikelyMatchingLabel = mostLikelyMatchingLabel;
    }

    public ArrayList<ImageLabel> getMatchingLabels() {
        return matchingLabels;
    }

    public void setMatchingLabels(ArrayList<ImageLabel> matchingLabels) {
        this.matchingLabels = matchingLabels;
    }
}
