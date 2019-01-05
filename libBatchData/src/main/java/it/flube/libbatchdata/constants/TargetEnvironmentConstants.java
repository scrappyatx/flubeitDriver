/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants;


/**
 * Created on 8/22/2018
 * Project : Driver
 */
public class TargetEnvironmentConstants {

    ///
    /// The target environments - we have 4 types of target environments
    ///
    public enum TargetEnvironment {
        PRODUCTION,
        DEMO,
        STAGING,
        DEVELOPMENT
    }
    ///
    /// The default target environment
    ///
    public static final TargetEnvironment DEFAULT_TARGET_ENVIRONMENT = TargetEnvironment.DEVELOPMENT;

}
