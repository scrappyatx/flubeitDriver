/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageLabel;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class ImageDetectionResultsBuilder {
    private ImageDetectionResults imageDetectionResults;

    private ImageDetectionResultsBuilder(Builder builder){
        this.imageDetectionResults = builder.imageDetectionResults;
    }

    private ImageDetectionResults getImageDetectionResults(){
        return this.imageDetectionResults;
    }

    public static class Builder {
        private ImageDetectionResults imageDetectionResults;

        public Builder(){
            this.imageDetectionResults = new ImageDetectionResults();

            imageDetectionResults.setLabelMap(new ArrayList<ImageLabel>());
            imageDetectionResults.setMostLikelyLabel(new ImageLabel());
        }

        private void validate(ImageDetectionResults imageDetectionResults){
            //do nothing
        }

        public ImageDetectionResults build(){
            ImageDetectionResults imageDetectionResults = new ImageDetectionResultsBuilder(this).getImageDetectionResults();
            validate(imageDetectionResults);
            return imageDetectionResults;
        }
    }
}
