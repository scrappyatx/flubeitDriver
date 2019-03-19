/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public class TextDetectionResults {
    private String text;
    private List<TextDetectBlock> blocks;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public List<TextDetectBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<TextDetectBlock> blocks) {
        this.blocks = blocks;
    }
}
