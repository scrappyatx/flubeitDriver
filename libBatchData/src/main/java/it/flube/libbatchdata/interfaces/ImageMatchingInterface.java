/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 3/5/2019
 * Project : Driver
 */
public interface ImageMatchingInterface {

    /// NO_MATCH_REQUIRED -> Always returns TRUE for "foundMatchLabels"
    /// MATCH_ANY_LABEL -> A successful match requires at least one match in the image label list
    /// MATCH_ALL_LABELS -> A successful match requires a match with each item in the image label list

    public enum MatchType {
        NO_MATCH_REQUIRED,
        MATCH_ANY_LABEL,
        MATCH_ALL_LABELS
    }

    public enum MatchPreset {
        NONE,
        VEHICLE,
        VEHICLE_REGISTRATION_PLATE
    }

}
