/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.List;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class TextDetectLine {
    private float confidence;
    private String text;
    private List<TextDetectElement> elements;

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

    public List<TextDetectElement> getElements() {
        return elements;
    }

    public void setElements(List<TextDetectElement> elements) {
        this.elements = elements;
    }
}
