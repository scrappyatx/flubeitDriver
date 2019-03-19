/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.imageLabelAnalysis;

import it.flube.libbatchdata.builders.ImageMatchResultsBuilder;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.ImageMatchSettings;
import it.flube.libbatchdata.entities.ImageLabel;
import timber.log.Timber;

/**
 * Created on 3/5/2019
 * Project : Driver
 */
public class ImageLabelAnalysis implements
    ImageLabelMatchAny.Response,
    ImageLabelMatchAll.Response {

    public static final String TAG = "ImageLabelAnalysis";

    /// Purpose of this class is to take ImageDetectionResults, and compare them with ImageMatchSettings
    /// and create and populate a ImageMatchResults object
    ///
    ///

    private Response response;

    public void analyzeImageRequest(ImageDetectionResults detected, ImageMatchSettings settings, Response response){
        Timber.tag(TAG).d("analyzeImageRequest");

        /// NO_MATCH_REQUIRED -> Always returns TRUE for "foundMatchLabels"
        /// MATCH_ANY_LABEL -> A successful match requires at least one match in the image label list
        /// MATCH_ALL_LABELS -> A successful match requires a match with each item in the image label list

        Timber.tag(TAG).d("   matchType -> %s", settings.getMatchType().toString());

        switch (settings.getMatchType()){
            case MATCH_ANY_LABEL:
                this.response = response;
                new ImageLabelMatchAny().imageLabelMatchAnyRequest(detected, settings, this);
                break;
            case MATCH_ALL_LABELS:
                this.response = response;
                new ImageLabelMatchAll().imageLabelMatchAllRequest(detected, settings, this);
                break;
            case NO_MATCH_REQUIRED:
                ImageMatchResults results = new ImageMatchResultsBuilder.Builder().build();
                results.setFoundMatchingLabels(true);
                response.analyzeImageComplete(results);
                break;
        }
    }

    /// response interface for MATCH ANY
    public void imageLabelMatchAnyComplete(ImageMatchResults imageMatchResults){
        Timber.tag(TAG).d("imageLabelMatchAnyComplete");
        response.analyzeImageComplete(imageMatchResults);
        response = null;
    }

    //response interface for MATCH ALL
    public void imageLabelMatchAllComplete(ImageMatchResults imageMatchResults){
        Timber.tag(TAG).d("imageLabelMatchAnyComplete");
        response.analyzeImageComplete(imageMatchResults);
        response = null;
    }

    public interface Response {
        void analyzeImageComplete(ImageMatchResults results);
    }


}
