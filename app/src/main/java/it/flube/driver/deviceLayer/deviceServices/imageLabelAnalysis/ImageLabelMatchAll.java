/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.imageLabelAnalysis;

import it.flube.libbatchdata.builders.ImageMatchResultsBuilder;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.ImageMatchSettings;
import timber.log.Timber;

/**
 * Created on 3/9/2019
 * Project : Driver
 */
public class ImageLabelMatchAll {
    private static final String TAG = "ImageLabelMatchAll";


    /// NO_MATCH_REQUIRED -> Always returns TRUE for "foundMatchLabels"
    /// MATCH_ANY_LABEL -> A successful match requires at least one match in the image label list
    /// MATCH_ALL_LABELS -> A successful match requires a match with each item in the image label list

    public void imageLabelMatchAllRequest(ImageDetectionResults detected, ImageMatchSettings settings, Response response) {
        //// need to match ALL labels in detected with a label in settings

        ///create a results object
        ImageMatchResults results = new ImageMatchResultsBuilder.Builder().build();

        if (detected.getLabelMap().size() > 0) {
            Timber.tag(TAG).d("we have %s labels in our list", detected.getLabelMap().size());

            /// we assume ALL labels will be found, so we set the result to TRUE.
            /// if we find a single label that doesn't match, we'll set it to FALSE
            results.setFoundMatchingLabels(true);

            // loop through the labels that were found, see if we have a matching label that exceeds the confidenceThreshold
            for (ImageLabel label : detected.getLabelMap()) {
                Timber.tag(TAG).d("**** ImageLabel START ****");
                Timber.tag(TAG).d("   label      -> %s", label.getLabel());
                Timber.tag(TAG).d("   entityId   -> %s", label.getEntityId());
                Timber.tag(TAG).d("   confidence -> %s", Float.toString(label.getConfidence()));


                //if the confidence of this label doesn't exceed the confidence threshold in settings, there is no reason to even compare it
                if (label.getConfidence() >= settings.getConfidenceThreshold()) {
                    Timber.tag(TAG).d("    --- EXCEEDS CONFIDENCE THRESHOLD %s, checking EntityId ---", Float.toString(settings.getConfidenceThreshold()));

                    //loop through the matching entityIds in our settings, see if we have a match
                    for (String checkEntityId : settings.getMatchEntityIds()) {
                        Timber.tag(TAG).d("      --- EntityId Check START ---");
                        Timber.tag(TAG).d("         matching entityId -> %s", checkEntityId);


                        if (label.getEntityId().equals(checkEntityId)) {
                            Timber.tag(TAG).d("         MATCHES!, adding to results");
                            results.getMatchingLabels().add(label);

                            /// now check to see if this is our MOST LIKELY found matching label
                            if (label.getConfidence() > results.getMostLikelyMatchingLabel().getConfidence()) {
                                Timber.tag(TAG).d("     this is our most likely matching label");
                                results.setMostLikelyMatchingLabel(label);
                            }

                        } else {
                            Timber.tag(TAG).d("         NO MATCH!, not adding to results");
                            /// since we found a SINGLE label in detected that didn't match, the entire result is FALSE
                            results.setFoundMatchingLabels(false);
                        }
                        Timber.tag(TAG).d("      --- EntityId Check END ---");
                    }
                } else {
                    Timber.tag(TAG).d("    --- BELOW CONFIDENCE THRESHOLD %s ---", Float.toString(settings.getConfidenceThreshold()));
                }
                Timber.tag(TAG).d("**** ImageLabel END ****");
            }
            Timber.tag(TAG).d("we're DONE, returning results");
            response.imageLabelMatchAllComplete(results);

        } else {
            Timber.tag(TAG).d("we have an empty label map list");
            response.imageLabelMatchAllComplete(results);
        }
    }

    public interface Response {
        void imageLabelMatchAllComplete(ImageMatchResults imageMatchResults);
    }
}
