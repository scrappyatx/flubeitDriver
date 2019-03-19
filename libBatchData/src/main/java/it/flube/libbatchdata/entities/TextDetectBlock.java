/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.List;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class TextDetectBlock {
    private float confidence;
    private String text;
    private List<TextDetectLine> lines;

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TextDetectLine> getLines() {
        return lines;
    }

    public void setLines(List<TextDetectLine> lines) {
        this.lines = lines;
    }
}
