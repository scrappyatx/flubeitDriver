/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public class ImageDetectionResults {
    private ImageLabel mostLikelyLabel;
    private ArrayList<ImageLabel> labelMap;

    public ImageLabel getMostLikelyLabel() {
        return mostLikelyLabel;
    }

    public void setMostLikelyLabel(ImageLabel mostLikelyLabel) {
        this.mostLikelyLabel = mostLikelyLabel;
    }

    public ArrayList<ImageLabel> getLabelMap() {
        return labelMap;
    }

    public void setLabelMap(ArrayList<ImageLabel> labelMap) {
        this.labelMap = labelMap;
    }
}
