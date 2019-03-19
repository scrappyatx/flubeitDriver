/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.libbatchdata.entities.ImageMatchResults;


/**
 * Created on 3/6/2019
 * Project : Driver
 */
public class ImageMatchResultsBuilder {
    private ImageMatchResults imageMatchResults;

    private ImageMatchResultsBuilder(Builder builder){
        this.imageMatchResults = builder.imageMatchResults;
    }

    private ImageMatchResults getImageMatchResults(){
        return this.imageMatchResults;
    }

    public static class Builder {
        private ImageMatchResults imageMatchResults;

        public Builder(){
            this.imageMatchResults = new ImageMatchResults();
            this.imageMatchResults.setFoundMatchingLabels(false);
            this.imageMatchResults.setMostLikelyMatchingLabel(new ImageLabel());
            this.imageMatchResults.setMatchingLabels(new ArrayList<ImageLabel>());
        }

        private void validate(ImageMatchResults imageMatchResults){
            //do nothing
        }

        public ImageMatchResults build(){
            ImageMatchResults imageMatchResults = new ImageMatchResultsBuilder(this).getImageMatchResults();
            validate(imageMatchResults);
            return imageMatchResults;
        }
    }

}
